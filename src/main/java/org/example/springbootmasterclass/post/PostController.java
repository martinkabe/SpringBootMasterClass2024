package org.example.springbootmasterclass.post;

import org.example.springbootmasterclass.jsonplaceholder.JsonPlaceholderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {
    private final PostService postService;
    private final JsonPlaceholderService jsonPlaceholderService;

    public PostController(PostService postService,
                          JsonPlaceholderService jsonPlaceholderService) {
        this.postService = postService;
        this.jsonPlaceholderService = jsonPlaceholderService;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return jsonPlaceholderService.getAllPosts();
    }

    @GetMapping("{id}")
    public Post getPostById(@PathVariable("id") Integer id) {
        return postService.getPostById(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePostById(@PathVariable("id") Integer id) {
        jsonPlaceholderService.deletePostById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@RequestBody Post post) {
        jsonPlaceholderService.createPost(post);
    }

    @PutMapping("{id}")
    public void updatePost(@PathVariable("id") Integer id,
                           @RequestBody Post post) {
        postService.updatePost(id, post);
    }
}
