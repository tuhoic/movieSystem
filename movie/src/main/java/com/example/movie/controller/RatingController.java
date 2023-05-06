package com.example.movie.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.movie.common.ResponseData;
import com.example.movie.common.ResultCode;
import com.example.movie.entity.Rating;
import com.example.movie.entity.User;
import com.example.movie.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

