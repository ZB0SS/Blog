package com.zboss.mongodemo.Controllers;

import com.mongodb.DBObject;
import com.mongodb.DuplicateKeyException;
import com.zboss.mongodemo.Models.User;
import com.zboss.mongodemo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
public class AuthenticationController {
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;
    @Autowired
    public AuthenticationController(UserRepository userRepository, MongoTemplate mongoTemplate) {
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/")
    String index() {
        return "index";
    }

    @GetMapping("/signup")
    String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    String postSignUP(@RequestBody String data, Model model) {
        String[] formattedData = data.split("&|=");
        String email = formattedData[1].replace("%40", "@");
        String username = formattedData[3];
        String firstName = formattedData[5];
        String lastName = formattedData[7];
        String password = formattedData[9];
        User user = (new User(email, username, password, firstName, lastName));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e.getClass().getName());
            model.addAttribute("message", "An account exists under that email or username, please try again.");
            return "signup";
        }
        model.addAttribute("message", "");
        return "redirect:/profile/" + user.getUsername();
    }

    @PostMapping("/")
    String signIn(@RequestBody String data, Model model) {
        String[] formattedData = data.split("&|=");
        String email = formattedData[1];
        String password = formattedData[3];
        Query query = new Query().addCriteria(Criteria.where("email").is(email.replace("%40", "@")));
        try {
            User user = mongoTemplate.find(query, User.class).get(0);
            model.addAttribute("isFail", "");
            return "redirect:/profile/" + user.getUsername();
        }
        catch (Exception e) {
            model.addAttribute("isFail", "Sorry, but that account doesn't exist");
            return "index";
        }
    }

}


