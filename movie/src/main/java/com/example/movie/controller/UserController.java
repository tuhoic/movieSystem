package com.example.movie.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.movie.common.ResponseData;
import com.example.movie.common.ResultCode;
import com.example.movie.entity.User;
import com.example.movie.entity.Usertokens;
import com.example.movie.service.UserService;
import com.example.movie.service.UsertokensService;
import com.example.movie.utils.JwtTokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UsertokensService usertokensService;

    private final JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    @PostMapping("/login")
    public ResponseData<String> login(@RequestBody User requestUser) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", requestUser.getUsername());
        queryWrapper.eq("password", requestUser.getPassword());

        User user = userService.getOne(queryWrapper);
        if (user != null) {
            String token = jwtTokenUtil.generateToken(user.getUsername());
            Usertokens tokens = new Usertokens();
            tokens.setUserId(user.getId());
            tokens.setToken(token);
            tokens.setExpiration(LocalDateTime.now().plusMinutes(30));
            usertokensService.save(tokens);
            return ResponseData.success(token);
        } else {
            return ResponseData.failed(ResultCode.FAILED, "用户名或密码错误!");
        }
    }


    @PostMapping("/logout")
    public ResponseData<String> logout(HttpServletRequest request) {
        // 从请求头中获取 token
        String token = request.getHeader("Authorization");
        if (token != null) {
            QueryWrapper<Usertokens> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("token", token);
            usertokensService.remove(queryWrapper);
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
        String token = httpServletRequest.getHeader("Authorization");

        QueryWrapper<Usertokens> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("token", token);

        Usertokens one = usertokensService.getOne(queryWrapper);
        if (one == null) {
            return ResponseData.failed(ResultCode.UNAUTHORIZED);
        }

        User user = userService.getById(one.getUserId());
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

    @PostMapping("/upload")
    public ResponseData<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + ".jpg";
            File dest = new File("D:\\graduate\\project\\movie\\src\\main\\resources\\static\\images\\" + fileName);
            file.transferTo(dest);
            return ResponseData.success(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseData.failed(ResultCode.FAILED, "图片上传失败");
        }
    }

    @PostMapping("/update")
    public ResponseData<String> updateUserInfo(@RequestBody User user, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");

        QueryWrapper<Usertokens> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("token", token);

        Usertokens one = usertokensService.getOne(queryWrapper);
        if (one == null) {
            return ResponseData.failed(ResultCode.UNAUTHORIZED);
        }

        User currentUser = userService.getById(one.getUserId());
        if (currentUser != null) {
            // 更新用户信息
            BeanUtils.copyProperties(user, currentUser);

            boolean result = userService.updateById(currentUser);
            if (result) {
                return ResponseData.success("用户信息修改成功");
            } else {
                return ResponseData.failed(ResultCode.FAILED, "用户信息修改失败");
            }
        } else {
            return ResponseData.failed(ResultCode.UNAUTHORIZED, "用户不存在或已被删除");
        }
    }


}

