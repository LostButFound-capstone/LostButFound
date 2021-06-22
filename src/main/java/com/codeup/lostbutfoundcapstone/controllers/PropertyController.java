package com.codeup.lostbutfoundcapstone.controllers;


import com.codeup.lostbutfoundcapstone.DAOs.PropertyCategoryRepository;
import com.codeup.lostbutfoundcapstone.DAOs.PropertyRepository;
import com.codeup.lostbutfoundcapstone.DAOs.UserRepository;
import com.codeup.lostbutfoundcapstone.models.PropertyCategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PropertyController {

    private final PropertyRepository propertyDao;

    private final PropertyCategoryRepository propertyCategoryDao;

    private final UserRepository userDao;



    public PropertyController(PropertyRepository propertyDao, PropertyCategoryRepository propertyCategoryDao, UserRepository userDao){

        this.propertyDao = propertyDao;
        this.propertyCategoryDao = propertyCategoryDao;
        this.userDao =  userDao;
    }


    @GetMapping("/property")
    public String property(Model model){

        return "home";

    }

    @PostMapping("/property/{id}")
    public String propertyId(@PathVariable long id, Model model){

        return null;
    }


}
