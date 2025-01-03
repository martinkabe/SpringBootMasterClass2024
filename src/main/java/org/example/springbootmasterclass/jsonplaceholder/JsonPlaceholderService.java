package org.example.springbootmasterclass.jsonplaceholder;

import org.example.springbootmasterclass.post.Post;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;

public interface JsonPlaceholderService {
    @GetExchange("/posts")
    List<Post> getAllPosts();

    @GetExchange("/posts/{id}")
    Post getPostById(@PathVariable("id") Integer id);

    @DeleteExchange("/posts/{id}")
    void deletePostById(@PathVariable("id") Integer id);

    @PostExchange("/posts")
    void createPost(@RequestBody Post post);

    @PutExchange("/posts/{id}")
    void updatePost(@PathVariable("id") Integer id,
                    @RequestBody Post post);
}
