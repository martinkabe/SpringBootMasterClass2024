package org.example.springbootmasterclass.post;

public record Post(
        Integer id,
        Integer userId,
        String title,
        String body
) {
}
