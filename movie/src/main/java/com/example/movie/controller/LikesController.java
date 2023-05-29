package com.example.movie.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.movie.common.ResponseData;
import com.example.movie.common.ResultCode;
import com.example.movie.entity.Likes;
import com.example.movie.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikesController {

    @Autowired
    private LikesService likesService;

    @GetMapping("/get")
    public ResponseData<Likes> get(@RequestParam int userId,
                                   @RequestParam int movieId) {
        QueryWrapper<Likes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("movie_id", movieId);

        Likes one = likesService.getOne(queryWrapper);
        if (one != null) {
            return ResponseData.success(one);
        } else {
            return ResponseData.failed(ResultCode.FAILED);
        }
    }

    @GetMapping("/list")
    public ResponseData<Integer> getList(@RequestParam int movieId) {
        QueryWrapper<Likes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("movie_id", movieId);

         return ResponseData.success(likesService.count(queryWrapper));
    }

    @PostMapping("/add")
    public ResponseData<Boolean> add(@RequestBody Likes likes) {
        QueryWrapper<Likes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", likes.getUserId());
        queryWrapper.eq("movie_id", likes.getMovieId());

        if (likesService.save(likes)) {
            return ResponseData.success();
        } else {
            return ResponseData.failed(ResultCode.FAILED);
        }
    }

    @PostMapping("/delete")
    public ResponseData<Boolean> delete(@RequestBody Likes likes) {
        QueryWrapper<Likes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", likes.getUserId());
        queryWrapper.eq("movie_id", likes.getMovieId());

        if (likesService.remove(queryWrapper)) {
            return ResponseData.success();
        } else {
            return ResponseData.failed(ResultCode.FAILED);
        }
    }
}

