package com.example.movie.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.movie.common.ResponseData;
import com.example.movie.common.ResultCode;
import com.example.movie.common.UserBasedCollaborativeFiltering;
import com.example.movie.entity.Movie;
import com.example.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserBasedCollaborativeFiltering userBasedCollaborativeFiltering;

    @GetMapping("/search")
    public ResponseData<Page<Movie>> search(@RequestParam(defaultValue = "") String title,
                                            @RequestParam(defaultValue = "1") int current,
                                            @RequestParam(defaultValue = "20") int pageSize,
                                            @RequestParam(defaultValue = "All") String genre) {
        Page<Movie> page = new Page<>(current, pageSize);

        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", title);
        if (!genre.equals("All")) {
            queryWrapper.like("genre", genre);
        }
        Page<Movie> moviePage = movieService.page(page, queryWrapper);
        return ResponseData.success(moviePage);
    }

    @GetMapping("/{movieId}")
    public ResponseData<Movie> get(@PathVariable int movieId) {
        Movie movie = movieService.getById(movieId);
        if (movie != null) {
            return ResponseData.success(movie);
        } else {
            return ResponseData.failed(ResultCode.VALIDATE_FAILED, "电影不存在!");
        }
    }

    @GetMapping("/recommendations")
    public ResponseData<Page<Movie>> getRecommendations(@RequestParam int userId,
                                                        @RequestParam(defaultValue = "1") int current,
                                                        @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseData.success(userBasedCollaborativeFiltering.recommendItems(userId, current, pageSize));
    }

}

