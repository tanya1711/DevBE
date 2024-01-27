package org.example.controller;

import org.example.model.User;
import org.example.service.MongoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    @Autowired
    private MongoDBService mongoDBService;
    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public String showRegistrationForm(@RequestBody User user) {
        mongoDBService.enterDataToUserCollection(user);
        System.out.println("yesss");
        return "ok";
    }

}
