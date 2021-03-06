package com.codeup.lostbutfoundcapstone.controllers;


import com.codeup.lostbutfoundcapstone.DAOs.*;
import com.codeup.lostbutfoundcapstone.models.*;
import com.codeup.lostbutfoundcapstone.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final InquiryRepository inquiryDao;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;


    public PropertyController(PropertyRepository propertyDao, PropertyCategoryRepository propertyCategoryDao, UserRepository userDao, EmailService emailService, LocationRepository locationDao, InquiryRepository inquiryDao, PasswordEncoder passwordEncoder) {
        this.propertyDao = propertyDao;
        this.propertyCategoryDao = propertyCategoryDao;
        this.userDao =  userDao;
        this.emailService = emailService;
        this.locationDao = locationDao;
        this.inquiryDao = inquiryDao;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/")
    public String property(Model model){

        return "home";
    }



    @PostMapping("/property/{id}")
    public String propertyId(@PathVariable long id, Model model){

        return null;
    }

    @GetMapping("/create")
    public String createProperty(Model model) {

        model.addAttribute("property", new Property());
        model.addAttribute("categories", propertyCategoryDao.findAll());
        model.addAttribute("locations", locationDao.findAll());


        return "property/create";
    }

    @PostMapping("/create")
    public String createPostProperty(@ModelAttribute Property property, @RequestParam(name = "categories", required = false) List<String> categories, @RequestParam("date") String date) throws ParseException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

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

        property.setUser(user);
        property.setDate_found(dateFound);
        property.setDate_posted(currentDate);
        property.setCategories(categoryList);

        Property savedProperty = propertyDao.save(property);


        return "redirect:/listings";
    }

    @GetMapping("/profile")
    public String redirectProfilePage() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        return "redirect:/profile/" + user.getId();
    }

    @GetMapping("/profile/{id}")
    public String showProfilePage(@PathVariable Long id, Model model) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getById(id);

        List<Property> properties = propertyDao.findPropertyByUser(user);

        List<Property> inquiredProperties = new ArrayList<>();

//        for (int i = 0; i < properties.size(); i++) {
//            if(properties.get(i).getInquiries().size() != 0) {
//                inquiredProperties.add(properties.get(i));
//            }
//        }
        for (Property property : properties) {
            if (property.getInquiries().size() != 0) {
                inquiredProperties.add(property);
            }
        }

        model.addAttribute("currentUser", user);
        model.addAttribute("properties", propertyDao.findPropertyByUser(user));
        model.addAttribute("inquiries", inquiryDao.findInquiryByUser(user));
        model.addAttribute("inquiredProperties", inquiredProperties);

        return "users/profile-dummy2";
    }

    @GetMapping("/profile/edit/{id}")
    public String showEditProfile(@PathVariable Long id, Model model) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getById(id);

        model.addAttribute("currentUser", user);
        model.addAttribute("properties", propertyDao.findPropertyByUser(user));
        System.out.println("user.getPassword() = " + user.getPassword());
        return "users/edit-profile";
    }

    @PostMapping("/profile/edit/{id}")
    public String editProfile(@PathVariable Long id, @RequestParam(name = "username") String username, @RequestParam(name = "email") String email, @RequestParam(name = "password") String password, @RequestParam(name = "profilePicture") String profileImgURL) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getById(id);

        String hash = passwordEncoder.encode(password);

        user.setVerified(false);
        user.setAdmin(false);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(hash);
        user.setProfile_image_path(profileImgURL);

        userDao.save(user);

        return "redirect:/profile/" + id;
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
            model.addAttribute("imagePath", "static/img/the_alamo.jpg");
        } else if(!date.equals("") && location_id.equals("null") && category_id.equals("null")) {
            System.out.println("date found is not equal to null string but location id and category id is");
            Date newDate = formatter.parse(date);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date_found = dateFormat.format(newDate);

            model.addAttribute("properties", propertyDao.findPropertyByDate_found(date_found));

        } else if (!location_id.equals("null") && category_id.equals("null") && date.equals("")) {
            System.out.println("location id is not equal to null string but date found and category id is");
            Long locationIdNum = Long.parseLong(location_id);

            Location location = locationDao.getById(locationIdNum);
            model.addAttribute("properties", propertyDao.findPropertyByLocation(location));

        } else if (!category_id.equals("null") && location_id.equals("null") && date.equals("")) {
            System.out.println("category id is not equal to null string but date found and location id is");
            Long categoryIdNum = Long.parseLong(category_id);
            PropertyCategory category = propertyCategoryDao.getById(categoryIdNum);

            model.addAttribute("properties", propertyDao.findPropertyByCategories(category));

        } else if(!date.equals("") && !location_id.equals("null") && category_id.equals("null")) {
            System.out.println("date found and location id is not equal to null string but category id is");
            Date newDate = formatter.parse(date);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date_found = dateFormat.format(newDate);

            model.addAttribute("properties", propertyDao.findPropertyByLocationAndDate_found(date_found, location_id));

        } else if(!date.equals("") && !category_id.equals("null") && location_id.equals("null")) {
            System.out.println("date found and location id are not null but location is");

            Date newDate = formatter.parse(date);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date_found = dateFormat.format(newDate);

            model.addAttribute("properties", propertyDao.findPropertyByCategoriesAndDate_found(category_id, date_found));

        } else if (!category_id.equals("null") && !location_id.equals("null") && date.equals("")) {
            System.out.println("both category and location are not null but date found is null string");
            Long locationIdNum = Long.parseLong(location_id);
            Location location = locationDao.getById(locationIdNum);
            Long categoryIdNum = Long.parseLong(category_id);
            PropertyCategory category = propertyCategoryDao.getById(categoryIdNum);

            model.addAttribute("properties", propertyDao.findPropertyByCategoriesAndLocation(category, location));

        } else if(!category_id.equals("null") && !location_id.equals("null") && !date.equals("")) {
            System.out.println("All inputs are being used");
            Date newDate = formatter.parse(date);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date_found = dateFormat.format(newDate);

            model.addAttribute("properties", propertyDao.findPropertyByCategoriesAndLocationAndDate_found(category_id, date_found, location_id));
        }

        System.out.println(location_id);
        System.out.println(category_id);
        System.out.println(date);
        return "property/listings";
    }

//    @PostMapping("/search-results")
//    public String showSearch(Model model @RequestParam(name = ) ) {
////        Long locationIdNum = Long.parseLong(location_id);
////        Long categoryIdNum = Long.parseLong(category_id);
//
//
//        return "redirect:";
//    }

    @GetMapping("/edit/{id}")
    public String showEditProperty(@PathVariable Long id, Model model) {
        Property property = propertyDao.getById(id);
        Date date = property.getDate_found();

        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);

        model.addAttribute("dateFound", dateString);
        model.addAttribute("datePosted", property.getDate_posted());
        model.addAttribute("property", propertyDao.getById(id));
        model.addAttribute("categories", propertyCategoryDao.findAll());
        model.addAttribute("locations", locationDao.findAll());
        return "property/edit_create";
    }

    @PostMapping("/edit/{id}")
    public String editProperty(@PathVariable Long id, @ModelAttribute Property property, @RequestParam(name = "categories", required = false) List<String> categories, @RequestParam("date") String date, @RequestParam("datePosted") String date_posted) throws ParseException {
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

        property.setUser(propertyDao.getById(id).getUser());
        property.setDate_posted(propertyDao.getById(id).getDate_posted());
        property.setDate_found(dateFound);
        property.setCategories(categoryList);

        propertyDao.save(property);

        return "redirect:/profile/" + propertyDao.getById(id).getUser().getId();
    }

    @PostMapping("/delete/{id}")
    public String postsDelete(@PathVariable Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        propertyDao.deletePropertyCategories(id.toString());
        propertyDao.deletePropertyById(id.toString());

        return "redirect:/profile/" + user.getId();
    }

    @PostMapping("/delete-inquiry/{id}")
    public String inquiriesDelete(@PathVariable Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        inquiryDao.delete(inquiryDao.getById(id));

        return "redirect:/profile/" + user.getId();
    }

    @GetMapping("/inquiry/{id}")
    public String showInquiry(@PathVariable Long id, Model model) {
        //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Property property = propertyDao.getById(id);

        model.addAttribute("listing", property);
        model.addAttribute("inquiry", new Inquiry());

        return "inquiry/inquiry_create";
    }

    @PostMapping("/inquiry/{id}")
    public String createInquiry(@PathVariable Long id, @ModelAttribute Inquiry inquiry, @RequestParam(name = "imageURL") String imageURL, @RequestParam(name = "imageDescription") String imageDescription, @RequestParam(name = "property-id") String propertyId) throws ParseException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        InquiryImage image = new InquiryImage(imageURL, imageDescription, inquiry);
        List<InquiryImage> images = new ArrayList<>();
        images.add(image);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date blankDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = formatter.parse(dateFormat.format(blankDate));

        Long propertyIdLong = Long.parseLong(propertyId);
        Property propertyInquired = propertyDao.getById(propertyIdLong);

        inquiry.setUser(user);
        inquiry.setDate_posted(currentDate);
        inquiry.setImages(images);
        inquiry.setProperty(propertyInquired);
        Property property = propertyDao.getById(id);

        User userPoster = property.getUser();
        Inquiry savedInquiry = inquiryDao.save(inquiry);

        emailService.prepareAndSend(property, "LostButFound Inquiry from " + savedInquiry.getUser().getUsername(), "Hey " + userPoster.getUsername() + "! Looks like someone made an inquiry for one of the items you found on LostButFound! Here is " + user.getUsername() + " inquiry: " + savedInquiry.getInquiry_description() + "  Please paste this URL in your browser to view the picture from the inquiry: " + image.getPath());

        return "redirect:/listings";
    }

    @PostMapping("/search-results")
    public String searchBar(Model model, @RequestParam(name = "searchBar") String searchString) {

        model.addAttribute("searchProperties", propertyDao.findPropertyByTitle(searchString));

        return "property/listings";
    }

    @GetMapping("/verified-users")
    public String showVerifiedUsers(Model model) {

        model.addAttribute("verifiedUsers", userDao.findByVerified(true));


        return "users/verified-users";
    }

    @GetMapping("/card")
    public String showCard(Model model) {

        model.addAttribute("properties", propertyDao.findAll());


        return "card-scratch";
    }

    @PostMapping("/inquiry-complete/{id}")
    public String inquiryComplete(@PathVariable Long id) {

        Property inquiredProperty = propertyDao.getById(id);
        inquiredProperty.setInquiry_complete(true);

        return "redirect:/profile";
    }

    @GetMapping("/nombre-shutup")
    public String nombreShutup(Model model) {

        model.addAttribute("properties", propertyDao.findAll());

        return "nombre-shutup/nombre-shutup";
    }
}
