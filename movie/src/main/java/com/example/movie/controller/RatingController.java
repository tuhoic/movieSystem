package com.example.movie.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.movie.common.ResponseData;
import com.example.movie.common.ResultCode;
import com.example.movie.entity.Comment;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Rating;
import com.example.movie.entity.User;
import com.example.movie.service.MovieService;
import com.example.movie.service.RatingService;
import com.example.movie.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @GetMapping("/get")
    public ResponseData<Rating> get(@RequestParam("userId") Integer userId, @RequestParam("movieId") Integer movieId) {
        QueryWrapper<Rating> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("movie_id", movieId);

        Rating one = ratingService.getOne(queryWrapper);
        if (one != null) {
            return ResponseData.success(one);
        } else {
            return ResponseData.failed(ResultCode.FAILED);
        }
    }

    @GetMapping("/getRating")
    public ResponseData<Page<com.example.movie.dto.Rating>> getRating(@RequestParam("userId") Integer userId,
                                                                @RequestParam(defaultValue = "1") int current,
                                                                @RequestParam(defaultValue = "10") int pageSize) {
        QueryWrapper<com.example.movie.entity.Rating> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByAsc("create_time");

        Page<Rating> page = ratingService.page(new Page<>(current, pageSize), queryWrapper);
        if (page != null) {
            Page<com.example.movie.dto.Rating> ratingPage = new Page<>(current, pageSize);

            List<Rating> records = page.getRecords();
            List<com.example.movie.dto.Rating> newRecords = new ArrayList<>();

            for (com.example.movie.entity.Rating rating : records) {
                com.example.movie.dto.Rating newRating = new com.example.movie.dto.Rating();
                BeanUtils.copyProperties(rating, newRating);
                newRating.setCreateTime(rating.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("id", rating.getUserId());
                newRating.setMovieName(movieService.getOne(new QueryWrapper<Movie>().eq("id", rating.getMovieId())).getTitle());

                User one = userService.getOne(queryWrapper1);
                newRating.setUsername(one.getUsername());

                newRecords.add(newRating);
            }

            ratingPage.setRecords(newRecords);
            ratingPage.setTotal(newRecords.size());
            return ResponseData.success(ratingPage);
        } else {
            return ResponseData.failed(ResultCode.FAILED);
        }
    }


    @PostMapping("/add")
    public ResponseData<Rating> add(@RequestBody Rating rating) {
        QueryWrapper<Rating> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", rating.getUserId());
        queryWrapper.eq("movie_id", rating.getMovieId());

        Rating one = ratingService.getOne(queryWrapper);
        if (one != null) {
            return ResponseData.failed(ResultCode.FAILED);
        } else {
            if (ratingService.save(rating)) {
                return ResponseData.success(null, "保存成功！");
            } else {
                return ResponseData.failed(ResultCode.FAILED);
            }
        }
    }

    @PostMapping("/update")
    public ResponseData<Rating> update(@RequestBody Rating rating) {
        QueryWrapper<Rating> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", rating.getUserId());
        queryWrapper.eq("movie_id", rating.getMovieId());

        if (ratingService.update(rating, queryWrapper)) {
            return ResponseData.success();
        } else {
            return ResponseData.failed(ResultCode.FAILED);
        }
    }
}

