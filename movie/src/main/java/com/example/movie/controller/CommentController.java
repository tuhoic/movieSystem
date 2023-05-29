package com.example.movie.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.movie.common.ResponseData;
import com.example.movie.common.ResultCode;
import com.example.movie.entity.Movie;
import com.example.movie.entity.User;
import com.example.movie.service.CommentService;
import com.example.movie.service.MovieService;
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
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    public CommentService commentService;

    @Autowired
    public UserService userService;

    @Autowired
    public MovieService movieService;


    @GetMapping("/get")
    public ResponseData<Page<com.example.movie.dto.Comment>> get(@RequestParam("userId") Integer userId,
                                                                 @RequestParam(defaultValue = "1") int current,
                                                                 @RequestParam(defaultValue = "10") int pageSize) {
        QueryWrapper<com.example.movie.entity.Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByAsc("create_time");

        Page<com.example.movie.entity.Comment> page = commentService.page(new Page<>(current, pageSize), queryWrapper);
        if (page != null) {
            Page<com.example.movie.dto.Comment> commentPage = new Page<>(current, pageSize);

            List<com.example.movie.entity.Comment> records = page.getRecords();
            List<com.example.movie.dto.Comment> newRecords = new ArrayList<>();

            for (com.example.movie.entity.Comment comment : records) {
                com.example.movie.dto.Comment newComment = new com.example.movie.dto.Comment();
                BeanUtils.copyProperties(comment, newComment);
                newComment.setCreateTime(comment.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("id", comment.getUserId());


                User one = userService.getOne(queryWrapper1);
                newComment.setUsername(one.getUsername());
                newComment.setHeadPortrait(one.getHeadPortrait());
                newComment.setMovieName(movieService.getOne(new QueryWrapper<Movie>().eq("id", comment.getMovieId())).getTitle());

                newRecords.add(newComment);
            }

            commentPage.setRecords(newRecords);
            commentPage.setTotal(newRecords.size());
            return ResponseData.success(commentPage);
        } else {
            return ResponseData.failed(ResultCode.FAILED);
        }
    }

    @GetMapping("/list")
    public ResponseData<Page<com.example.movie.dto.Comment>> list(@RequestParam("movieId") Integer movieId,
                                                                  @RequestParam(defaultValue = "1") int current,
                                                                  @RequestParam(defaultValue = "10") int pageSize) {
        QueryWrapper<com.example.movie.entity.Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("movie_id", movieId);
        queryWrapper.orderByAsc("create_time");

        Page<com.example.movie.entity.Comment> page = commentService.page(new Page<>(current, pageSize), queryWrapper);
        if (page != null) {
            Page<com.example.movie.dto.Comment> commentPage = new Page<>(current, pageSize);

            List<com.example.movie.entity.Comment> records = page.getRecords();
            List<com.example.movie.dto.Comment> newRecords = new ArrayList<>();

            for (com.example.movie.entity.Comment comment : records) {
                com.example.movie.dto.Comment newComment = new com.example.movie.dto.Comment();
                BeanUtils.copyProperties(comment, newComment);
                newComment.setCreateTime(comment.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("id", comment.getUserId());

                User one = userService.getOne(queryWrapper1);
                newComment.setUsername(one.getUsername());
                newComment.setHeadPortrait(one.getHeadPortrait());

                newRecords.add(newComment);
            }

            commentPage.setRecords(newRecords);
            commentPage.setTotal(newRecords.size());
            return ResponseData.success(commentPage);
        } else {
            return ResponseData.failed(ResultCode.FAILED);
        }
    }

    @PostMapping("add")
    public ResponseData<com.example.movie.entity.Comment> add(@RequestBody com.example.movie.entity.Comment comment) {
        if (commentService.save(comment)) {
            return ResponseData.success(null, "保存成功！");
        } else {
            return ResponseData.failed(ResultCode.FAILED);
        }
    }
}

