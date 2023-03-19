package com.example.movie.controller;

import com.example.movie.entity.MovieCategory;
import com.example.movie.service.MovieCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie-category")
public class MovieCategoryController {
    @Autowired
    private MovieCategoryService movieCategoryService;

    @GetMapping("/{id}")
    public MovieCategory getById(@PathVariable("id") Integer id) {
        return movieCategoryService.getById(id);
    }

    @GetMapping
    public List<MovieCategory> list() {
        return movieCategoryService.list();
    }

    @PostMapping
    public void save(@RequestBody MovieCategory movieCategory) {
        movieCategoryService.save(movieCategory);
    }

    @PutMapping
    public void update(@RequestBody MovieCategory movieCategory) {
        movieCategoryService.updateById(movieCategory);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Integer id) {
        movieCategoryService.removeById(id);
    }
}
