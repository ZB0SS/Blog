package com.zboss.mongodemo.Controllers;

import com.zboss.mongodemo.Models.NewBlog;
import com.zboss.mongodemo.Models.User;
import com.zboss.mongodemo.Repositories.NewBlogRepository;
import com.zboss.mongodemo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;
    private final NewBlogRepository blogRepository;
    @Autowired
    public HomeController(UserRepository userRepository, MongoTemplate mongoTemplate, NewBlogRepository blogRepository) {
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
        this.blogRepository = blogRepository;
    }

    @GetMapping("/profile/{username}")
    String proflie(@PathVariable("username") String username, Model model) {
        Query q = new Query().addCriteria(Criteria.where("username").is(username));
        try {
            User user = mongoTemplate.find(q, User.class).get(0);
            model.addAttribute("user", user);
            int i = 1;
            List<ArrayList<NewBlog>> listOfLists = new ArrayList<ArrayList<NewBlog>>();
            List<NewBlog> blogList = blogRepository.findAll();
            int k;
            for (k = 0; k < blogList.size(); k += 3) {
                NewBlog j = blogList.get(k);
                NewBlog l;
                NewBlog z;
                try {
                    l = blogList.get(k + 1);
                    try {
                        z = blogList.get(k + 2);
                    } catch (Exception e) {
                        NewBlog[] x = {j, l};
                        listOfLists.add(new ArrayList<>(List.of(x)));
                        break;
                    }
                } catch (Exception e) {
                    NewBlog[] x = {j};
                    listOfLists.add(new ArrayList<>(List.of(x)));
                    break;
                }
                NewBlog[] x = {j, l, z};
                listOfLists.add(new ArrayList<>(List.of(x)));
            }
            model.addAttribute("lists", listOfLists);
            return "home";
        } catch (Exception e) {
            model.addAttribute("error", username + " doesn't exist");
            return "error";
        }
    }
}
