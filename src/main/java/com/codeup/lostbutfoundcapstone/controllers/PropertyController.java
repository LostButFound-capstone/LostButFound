package com.codeup.lostbutfoundcapstone.controllers;


import com.codeup.lostbutfoundcapstone.DAOs.LocationRepository;
import com.codeup.lostbutfoundcapstone.DAOs.PropertyCategoryRepository;
import com.codeup.lostbutfoundcapstone.DAOs.PropertyRepository;
import com.codeup.lostbutfoundcapstone.DAOs.UserRepository;
import com.codeup.lostbutfoundcapstone.models.Location;
import com.codeup.lostbutfoundcapstone.models.Property;
import com.codeup.lostbutfoundcapstone.models.PropertyCategory;
import com.codeup.lostbutfoundcapstone.models.User;
import com.codeup.lostbutfoundcapstone.services.EmailService;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Controller
public class PropertyController {

    private final PropertyRepository propertyDao;
    private final PropertyCategoryRepository propertyCategoryDao;
    private final LocationRepository locationDao;
    private final UserRepository userDao;
    private final EmailService emailService;



    public PropertyController(PropertyRepository propertyDao, PropertyCategoryRepository propertyCategoryDao, UserRepository userDao, EmailService emailService, LocationRepository locationDao){
        this.propertyDao = propertyDao;
        this.propertyCategoryDao = propertyCategoryDao;
        this.userDao =  userDao;
        this.emailService = emailService;
        this.locationDao = locationDao;
    }


    @GetMapping("/home")
    public String property(Model model){

        return "home";
    }

//    @PostMapping("/property/{id}")
//    public String propertyId(@PathVariable long id, Model model){
//
//        return null;
//    }

    @GetMapping("/create")
    public String createProperty(Model model) {

        model.addAttribute("property", new Property());
        model.addAttribute("categories", propertyCategoryDao.findAll());
        model.addAttribute("locations", locationDao.findAll());



        return "property/create";
    }

    @PostMapping("/create")
    public String createPostProperty(@ModelAttribute Property property, @RequestParam(name = "categories", required = false) List<String> categories, @RequestParam("date") String date) throws ParseException {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date blankDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = formatter.parse(dateFormat.format(blankDate));
        Date dateFound = formatter.parse(date);

        System.out.println("current date = " + currentDate);
        System.out.println("date found = " + dateFound);

        List<PropertyCategory> categoryList = new ArrayList<>();
        if (categories != null) {
            for (String categoryIdString : categories) {
                Long category_id = Long.parseLong(categoryIdString);
                PropertyCategory category = propertyCategoryDao.getById(category_id);
                categoryList.add(category);
            }
        }

        User user = userDao.getById(1L);
        property.setUser(user);
        property.setDate_found(dateFound);
        property.setDate_posted(currentDate);
        property.setCategories(categoryList);

        Property savedProperty = propertyDao.save(property);

//        emailService.prepareAndSend(property, property.getTitle(), "Check this out!");

        return "redirect:/listings";
    }

    @GetMapping("/profile")
    public String showProfilePage(Model model) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getById(1L);



        model.addAttribute("currentUser", user);
        model.addAttribute("properties", propertyDao.findPropertyByUser(user));

        return "users/profile";
    }

    @GetMapping("/listings")
    public String showListings(Model model, @RequestParam(name = "location" , required = false) String location_id, @RequestParam(name = "category" , required = false) String category_id, @RequestParam(name = "date_found" , required = false) String date) throws ParseException {
        model.addAttribute("properties", propertyDao.findAll());
        model.addAttribute("locations", locationDao.findAll());
        model.addAttribute("categories", propertyCategoryDao.findAll());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        if(location_id == null && category_id == null && date == null) {
            System.out.println("All actually null");
            model.addAttribute("properties", propertyDao.findAll());
        } else if (location_id.equals("null") && category_id.equals("null") && date.equals("")) {
            System.out.println("location_id and category_id equal null but as a string, while date is an empty string");
            System.out.println("date = " + date);
            model.addAttribute("properties", propertyDao.findAll());
        } else if(date != null && !date.equals("") && location_id.equals("null") && category_id.equals("null")) {
//            Date newDate = new Date();
            Date newDate = formatter.parse(date);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date_found = dateFormat.format(newDate);
            System.out.println("newDate = " + newDate);
            System.out.println("date_found = " + date_found);
            model.addAttribute("properties", propertyDao.findPropertyByDate_found(date_found));
        } else if (!location_id.equals("null") && category_id.equals("null") && date.equals("")) {
            System.out.println("location id is not equal to null string but date found and category id is");
            Long locationIdNum = Long.parseLong(location_id);
            Location location = locationDao.getById(locationIdNum);
            model.addAttribute("properties", propertyDao.findPropertyByLocation(location));
        } else if(!date.equals("") && !location_id.equals("null") && category_id.equals("null")) {
            Date newDate = formatter.parse(date);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date_found = dateFormat.format(newDate);
            System.out.println("date found and location id is not equal to null string but category id is");
            model.addAttribute("properties", propertyDao.findPropertyByLocationAndDate_found(date_found, location_id));
        } else if (!category_id.equals("null") && location_id.equals("null") && date.equals("")) {
            System.out.println("category id is not equal to null string but date found and location id is");
            Long categoryIdNum = Long.parseLong(category_id);
            PropertyCategory category = propertyCategoryDao.getById(categoryIdNum);
            model.addAttribute("properties", propertyDao.findPropertyByCategories(category));
        } else if(!date.equals("") && !category_id.equals("null") && location_id.equals("null")) {
            System.out.println("date found and location id are not null but location is");

        } else if (!category_id.equals("null") && !location_id.equals("null") && date.equals("")) {
            System.out.println("both category and location are not null but date found is null string");
            Long locationIdNum = Long.parseLong(location_id);
            Location location = locationDao.getById(locationIdNum);
            Long categoryIdNum = Long.parseLong(category_id);
            PropertyCategory category = propertyCategoryDao.getById(categoryIdNum);
            model.addAttribute("properties", propertyDao.findPropertyByCategoriesAndLocation(category, location));
        }

        System.out.println(location_id);
        System.out.println(category_id);
        System.out.println(date);

        return "property/listings-dummy";
    }

//    @PostMapping("/property/listings")
//    public String filterFunctionality(@RequestParam(name = "location") String location_id, @RequestParam(name = "category") String category_id, @RequestParam(name = "date_found") String date_found) {
////        Long locationIdNum = Long.parseLong(location_id);
////        Long categoryIdNum = Long.parseLong(category_id);
//
//
//        return "redirect:/property/listings";
//    }

    @GetMapping("/edit/{id}")
    public String editProperty(@PathVariable Long id, Model model) {
        model.addAttribute("property", propertyDao.getById(id));
        model.addAttribute("categories", propertyCategoryDao.findAll());
        model.addAttribute("locations", locationDao.findAll());
        return "property/edit-dummy";
    }

}
