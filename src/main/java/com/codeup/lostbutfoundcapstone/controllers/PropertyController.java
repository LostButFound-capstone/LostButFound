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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/property")
    public String property(Model model){

        return "home";
    }

    @PostMapping("/property/{id}")
    public String propertyId(@PathVariable long id, Model model){

        return null;
    }

    @GetMapping("/property/create")
    public String createProperty(Model model) {
        model.addAttribute("property", new Property());
        model.addAttribute("categories", propertyCategoryDao.findAll());
        model.addAttribute("locations", locationDao.findAll());
        return "property/create";
    }

    @PostMapping("/property/create")
    public String createPostProperty(@ModelAttribute Property property) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getById(1L);

        property.setUser(user);




        Property savedProperty = propertyDao.save(property);

        emailService.prepareAndSend(property, property.getTitle(), "Check this out!");

        return "redirect:/property/" + savedProperty.getId();
    }

    @GetMapping("/user/profile")
    public String showProfilePage(Model model) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getById(2L);



        model.addAttribute("currentUser", user);
        model.addAttribute("properties", propertyDao.findPropertyByUser(user));

        return "users/profile";
    }

    @GetMapping("/property/listings")
    public String showListings(Model model, @RequestParam(name = "location" , required = false) String location_id, @RequestParam(name = "category" , required = false) String category_id, @RequestParam(name = "date_found" , required = false) String date_found) {
        model.addAttribute("properties", propertyDao.findAll());
        model.addAttribute("locations", locationDao.findAll());
        model.addAttribute("categories", propertyCategoryDao.findAll());


        if(location_id == null && category_id == null) {
            System.out.println("both actually null");
            model.addAttribute("properties", propertyDao.findAll());
        } else if (location_id.equals("null") && category_id.equals("null")) {
            System.out.println("both equal null but as a string");
            model.addAttribute("properties", propertyDao.findAll());
        } else if (!location_id.equals("null") && category_id.equals("null")) {
            System.out.println("location id is not equal to null string but category id is");
            Long locationIdNum = Long.parseLong(location_id);
            Location location = locationDao.getById(locationIdNum);
            model.addAttribute("properties", propertyDao.findPropertyByLocation(location));
        } else if (!category_id.equals("null") && location_id.equals("null")) {
            System.out.println("category id is not equal to null string but location id is");
            Long categoryIdNum = Long.parseLong(category_id);
            PropertyCategory category = propertyCategoryDao.getById(categoryIdNum);
            model.addAttribute("properties", propertyDao.findPropertyByCategories(category));
        } else if (!category_id.equals("null") && !location_id.equals("null")) {
            System.out.println("both category and location are not null");
            Long locationIdNum = Long.parseLong(location_id);
            Location location = locationDao.getById(locationIdNum);
            Long categoryIdNum = Long.parseLong(category_id);
            PropertyCategory category = propertyCategoryDao.getById(categoryIdNum);
            model.addAttribute("properties", propertyDao.findPropertyByCategoriesAndLocation(category, location));
        }

        System.out.println(location_id);
        System.out.println(category_id);

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

    @GetMapping("/property/edit")
    public String editProperty(Model model) {
        model.addAttribute("property", new Property());
        model.addAttribute("categories", propertyCategoryDao.findAll());
        model.addAttribute("locations", locationDao.findAll());
        return "property/edit-dummy";
    }

}
