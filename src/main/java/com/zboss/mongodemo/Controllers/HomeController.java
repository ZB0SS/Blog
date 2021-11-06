package com.zboss.mongodemo.Controllers;

import com.zboss.mongodemo.Models.User;
import com.zboss.mongodemo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;
    @Autowired
    public HomeController(UserRepository userRepository, MongoTemplate mongoTemplate) {
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/profile/{username}")
    String proflie(@PathVariable("username") String username, Model model) {
        Query q = new Query().addCriteria(Criteria.where("username").is(username));
        try {
            User user = mongoTemplate.find(q, User.class).get(0);
            model.addAttribute("user", user);
            return "home";
        } catch (Exception e) {
            model.addAttribute("isFail", username + " doesn't exist");
            return "index";
        }
    }
}
