package com.example.movie.controller;

import com.example.movie.common.ResponseData;
import com.example.movie.entity.Category;
import com.example.movie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseData<Category> getById(@PathVariable("id") Integer id) {
        return ResponseData.success(categoryService.getById(id));
    }

    @GetMapping
    public ResponseData<List<Category>> list() {
        return ResponseData.success(categoryService.list());
    }

    @PostMapping
    public ResponseData<Object> save(@RequestBody Category category) {
        categoryService.save(category);
        return ResponseData.success(null);
    }

    @PutMapping
    public ResponseData<Object> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return ResponseData.success(null);
    }

    @DeleteMapping("/{id}")
    public ResponseData<Object> deleteById(@PathVariable("id") Integer id) {
        categoryService.removeById(id);
        return ResponseData.success(null);
    }
}
