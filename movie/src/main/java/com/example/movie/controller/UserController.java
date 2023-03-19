package com.example.movie.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.movie.common.ResponseData;
import com.example.movie.common.ResultCode;
import com.example.movie.pojo.User;
import com.example.movie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wuMing
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ResponseData<User> login(@RequestBody User requestUser) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", requestUser.getUsername());
        queryWrapper.eq("password", requestUser.getPassword());
        User user = userService.getOne(queryWrapper);
        if (user != null) {
            return ResponseData.success(user);
        } else {
            return ResponseData.failed(ResultCode.FAILED, "用户民或密码错误!");
        }
    }

    @PostMapping("/register")
    public ResponseData<User> register(@RequestBody User requestUser) {
        // 检查用户名是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", requestUser.getUsername());
        User user = userService.getOne(queryWrapper);
        if (user != null) {
            return ResponseData.failed(ResultCode.VALIDATE_FAILED, "用户名已存在！");
        }

        // 检查邮箱是否已注册
        queryWrapper.clear();
        queryWrapper.eq("email", requestUser.getEmail());
        user = userService.getOne(queryWrapper);
        if (user != null) {
            return ResponseData.failed(ResultCode.FAILED, "邮箱已注册！");
        }

        // 注册
        boolean save = userService.save(requestUser);
        if (!save) {
            return ResponseData.failed(ResultCode.FAILED, "未知错误，请重试！");
        } else {
            return ResponseData.success(null, "注册成功！");
        }
    }
}

