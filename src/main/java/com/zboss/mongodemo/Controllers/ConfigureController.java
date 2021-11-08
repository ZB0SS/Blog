package com.zboss.mongodemo.Controllers;

import com.zboss.mongodemo.Models.User;
import com.zboss.mongodemo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
public class ConfigureController {
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ConfigureController(MongoTemplate mongoTemplate, UserRepository userRepository){
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/profile/{username}/config")
    String configure(@PathVariable("username") String username, Model model) {
        Query q = new Query().addCriteria(Criteria.where("username").is(username));
        try {
            User user = mongoTemplate.find(q, User.class).get(0);
            model.addAttribute("user", user);
            return "configure";
        } catch (Exception e) {
            model.addAttribute("error", username + " doesn't exist");
            return "error";
        }
    }

    @PostMapping("/config")
    String postConfigure(@RequestBody String data, Model model)  {
        String[] formattedData = data.split("&|=");
        String firstName = formattedData[1];
        String email = formattedData[3];
        String lastName = formattedData[5];
        String avatar = formattedData[7];
        String password;
        try {
            password = formattedData[9];
        }
        catch (Exception e) {
            password = "";
        }
        if (password.equals("") || email.equals("")) {
            model.addAttribute("error", "Please fill in both the password and email field next time.");
            return "error";
        }
        Query q = new Query().addCriteria(Criteria.where("email").is(email.replace("%40", "@")));
        User user = mongoTemplate.find(q, User.class).get(0);
        if (password.equals(user.getPassword())) {
            Update update = new Update();
            if (!firstName.equals("")) {
                user.setFirstName(firstName);
                update.set("firstName", firstName);
            }
            if (!lastName.equals("")) {
                user.setLastName(lastName);
                update.set("lastName", lastName);
            }
            if (!avatar.equals("")) {
                String av = URLDecoder.decode(avatar, StandardCharsets.UTF_8);
                user.setAvatar(av);
                update.set("avatar", av);
            }
            mongoTemplate.updateFirst(q, update, User.class);

        } else {
            model.addAttribute("error", "Incorrect Password");
            return "error";
        }
        model.addAttribute("user", user);
        return "redirect:/profile/" + user.getUsername();
    }


}
