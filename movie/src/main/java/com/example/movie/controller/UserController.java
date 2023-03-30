package com.example.movie.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.movie.common.ResponseData;
import com.example.movie.common.ResultCode;
import com.example.movie.entity.User;
import com.example.movie.service.UserService;
import com.example.movie.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private final JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    @PostMapping("/login")
    public ResponseData<String> login(@RequestBody User requestUser) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", requestUser.getUsername());
        queryWrapper.eq("password", requestUser.getPassword());

        User user = userService.getOne(queryWrapper);
        if (user != null) {
            String token = jwtTokenUtil.generateToken(user);
            return ResponseData.success(token);
        } else {
            return ResponseData.failed(ResultCode.FAILED, "用户名或密码错误!");
        }
    }


    @PostMapping("/logout")
    public ResponseData<String> logout(HttpServletRequest request) {
        // 从请求头中获取 token
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            // 删除缓存的 token
            String authToken = token.substring(7);
            // do something to delete token
        }
        return ResponseData.success();
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
        System.out.println(requestUser.getUsername());
        System.out.println(requestUser.getEmail());
        System.out.println(requestUser.getPassword());

        if (!save) {
            return ResponseData.failed(ResultCode.FAILED, "未知错误，请重试！");
        } else {
            return ResponseData.success(null, "注册成功！");
        }
    }

    @GetMapping("/info")
    public ResponseData<User> getInfo(HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader("Authorization");
        String token = authHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);

        User user = userService.getOne(queryWrapper);
        if (user != null) {
            return ResponseData.success(user);
        } else {
            return ResponseData.failed(ResultCode.UNAUTHORIZED, "用户不存在或已被删除!");
        }
    }

    @PostMapping("/modification")
    public ResponseData<User> modification(@RequestBody User requeseUser) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", requeseUser.getUsername());
        queryWrapper.eq("email", requeseUser.getEmail());

        User user = userService.getOne(queryWrapper);
        System.out.println(requeseUser);
        System.out.println(user);
        if (user == null) {
            return ResponseData.failed(ResultCode.FAILED, "用户不存在或注册邮箱不对!");
        }

        user.setPassword(requeseUser.getPassword());
        boolean isUpdate = userService.updateById(user);
        if (!isUpdate) {
            return ResponseData.failed(ResultCode.FAILED, "发生未知错误，请重试!");
        }
        return ResponseData.success();
    }
}

