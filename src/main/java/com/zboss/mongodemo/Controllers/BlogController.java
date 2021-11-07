package com.zboss.mongodemo.Controllers;

import com.zboss.mongodemo.Models.NewBlog;
import com.zboss.mongodemo.Repositories.NewBlogRepository;
import com.zboss.mongodemo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
public class BlogController {
    private final UserRepository userRepository;
    private  final NewBlogRepository blogRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public BlogController(UserRepository userRepository, NewBlogRepository blogRepository, MongoTemplate mongoTemplate) {
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/create-blog")
    String createBlog(Model model) {
        model.addAttribute("html", "");
        return "createBlog";
    }

    @PostMapping("/create-blog")
    String postCreateBlog(@RequestBody String data, Model model) {
        String[] formattedData = data.split("=|&");
        String title;
        String username;
        String image;
        String body;

        try {
            title = URLDecoder.decode(formattedData[1], StandardCharsets.UTF_8);
            username = URLDecoder.decode(formattedData[3], StandardCharsets.UTF_8);
            image = URLDecoder.decode(formattedData[5], StandardCharsets.UTF_8);
            body = URLDecoder.decode(formattedData[7], StandardCharsets.UTF_8);
        } catch (Exception e) {
            model.addAttribute("error", "Not all fields were filled");
            return "error";
        }

        NewBlog blog = new NewBlog(title, body, image, username);
        try {
            blogRepository.save(blog);
        } catch (Exception e) {
            model.addAttribute("error", title + " is already an existing blog title");
            return "error";
        }
        return "createBlog";
    }
}
