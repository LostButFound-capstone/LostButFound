package com.codeup.lostbutfoundcapstone.controllers;

import com.codeup.lostbutfoundcapstone.DAOs.PropertyCategoryRepository;
import com.codeup.lostbutfoundcapstone.DAOs.PropertyRepository;
import com.codeup.lostbutfoundcapstone.DAOs.UserRepository;
import com.codeup.lostbutfoundcapstone.models.Inquiry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InquiryController {

    private final PropertyRepository propertyDao;

    private final PropertyCategoryRepository propertyCategoryDao;

    private final UserRepository userDao;



    public InquiryController(PropertyRepository propertyDao, PropertyCategoryRepository propertyCategoryDao, UserRepository userDao){

        this.propertyDao = propertyDao;
        this.propertyCategoryDao = propertyCategoryDao;
        this.userDao =  userDao;
    }



    @GetMapping("/inquiry")
    public String showInquiryForm(Model model){

                model.addAttribute("listing",propertyDao.getById(3L));
                model.addAttribute("inquiry", new Inquiry());
                model.addAttribute("user", userDao.getById(2L));
        return "inquiry/inquiry_create";
    }


}
