package com.zboss.mongodemo.Repositories;

import com.zboss.mongodemo.Models.NewBlog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewBlogRepository extends MongoRepository<NewBlog, String> {
}
