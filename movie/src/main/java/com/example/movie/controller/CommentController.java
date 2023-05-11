package com.example.movie.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.movie.common.ResponseData;
import com.example.movie.common.ResultCode;
import com.example.movie.entity.User;
import com.example.movie.service.CommentService;
import com.example.movie.service.UserService;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public ResponseData<Page<com.example.movie.dto.Comment>> list(@RequestParam("movieId") Integer movieId) {
        QueryWrapper<com.example.movie.entity.Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("movie_id", movieId);

        Page<com.example.movie.entity.Comment> page = commentService.page(new Page<>(1, 10), queryWrapper);
        if (page != null) {
            Page<com.example.movie.dto.Comment> commentPage = new Page<>(1, 10);

            List<com.example.movie.entity.Comment> records = page.getRecords();
            List<com.example.movie.dto.Comment> newRecords = new ArrayList<>();

            for (com.example.movie.entity.Comment comment : records) {
                com.example.movie.dto.Comment newComment = new com.example.movie.dto.Comment();
                BeanUtils.copyProperties(comment, newComment);

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

