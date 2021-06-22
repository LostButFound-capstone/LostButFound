package com.codeup.lostbutfoundcapstone.controllers;


import com.codeup.lostbutfoundcapstone.DAOs.PropertyCategoryRepository;
import com.codeup.lostbutfoundcapstone.DAOs.PropertyRepository;
import com.codeup.lostbutfoundcapstone.DAOs.UserRepository;
import com.codeup.lostbutfoundcapstone.models.Property;
import com.codeup.lostbutfoundcapstone.models.PropertyCategory;
import com.codeup.lostbutfoundcapstone.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @GetMapping("/property/create")
    public String createProperty(Model model) {
        model.addAttribute("property", new Property());
        model.addAttribute("categories", propertyCategoryDao.findAll());
        return "create";
    }

    @PostMapping("/property/create")
    public String createPostProperty(@ModelAttribute Property property) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        property.setUser(user);

        Property savedProperty = propertyDao.save(property);

        emailService.prepareAndSend(property, property.getTitle(), property.getBody());

        return "redirect:/property/" + savedProperty.getId();
    }
}
