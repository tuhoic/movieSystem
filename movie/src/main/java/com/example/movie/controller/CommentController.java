package com.example.movie.controller;

import com.example.movie.entity.Comment;
import com.example.movie.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public Comment getById(@PathVariable("id") Integer id) {
        return commentService.getById(id);
    }

    @GetMapping
    public List<Comment> list() {
        return commentService.list();
    }

    @PostMapping
    public void save(@RequestBody Comment comment) {
        commentService.save(comment);
    }

    @PutMapping
    public void update(@RequestBody Comment comment) {
        commentService.updateById(comment);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Integer id) {
        commentService.removeById(id);
    }
}
