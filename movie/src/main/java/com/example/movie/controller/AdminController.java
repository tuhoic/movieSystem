package com.example.movie.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.movie.common.ResponseData;
import com.example.movie.common.ResultCode;
import com.example.movie.entity.*;
import com.example.movie.service.*;
import com.example.movie.utils.JwtTokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author Your Name
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private AdmintokensService admintokensService;

    private final JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    @PostMapping("/login")
    public ResponseData<String> login(@RequestBody Admin requeseAdmin) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", requeseAdmin.getUsername());
        queryWrapper.eq("password", requeseAdmin.getPassword());

        Admin admin = adminService.getOne(queryWrapper);
        if (admin != null) {
            String token = jwtTokenUtil.generateToken(admin.getUsername());
            Admintokens tokens = new Admintokens();
            tokens.setAdminId(admin.getId());
            tokens.setToken(token);

            tokens.setExpiration(LocalDateTime.now().plusMinutes(30));
            admintokensService.save(tokens);
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
            QueryWrapper<Admintokens> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("token", token);
            admintokensService.remove(queryWrapper);
        }
        return ResponseData.success();
    }

    @GetMapping("/info")
    public ResponseData<Admin> getInfo(HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader("Authorization");
        QueryWrapper<Admintokens> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("token", authHeader);

        Admintokens admintokens = admintokensService.getOne(queryWrapper);
        if (admintokens == null) {
            return ResponseData.failed(ResultCode.UNAUTHORIZED);
        }

        int adminId = admintokens.getAdminId();
        QueryWrapper<Admin> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", adminId);

        Admin admin = adminService.getOne(queryWrapper1);
        if (admin != null) {
            return ResponseData.success(admin);
        } else {
            return ResponseData.failed(ResultCode.UNAUTHORIZED, "用户不存在或已被删除!");
        }
    }

    @GetMapping("/userList")
    public ResponseData<Page<User>> userList(@RequestParam(defaultValue = "1") int current,
                                         @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseData.success(userService.page(new Page<>(current, pageSize)));
    }

    @GetMapping("/movieList")
    public ResponseData<Page<Movie>> movieList(@RequestParam(defaultValue = "1") int current,
                                          @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseData.success(movieService.page(new Page<>(current, pageSize)));
    }

    @GetMapping("/ratingList")
    public ResponseData<Page<com.example.movie.dto.Rating>> ratingList(@RequestParam(defaultValue = "1") int current,
                                                                       @RequestParam(defaultValue = "10") int pageSize) {
        Page<Rating> ratingPage = ratingService.page(new Page<>(current, pageSize));
        List<Rating> records = ratingPage.getRecords();

        List<com.example.movie.dto.Rating> newRecords = new ArrayList<>();
        for (Rating rating : records) {
            com.example.movie.dto.Rating newRating = new com.example.movie.dto.Rating();
            BeanUtils.copyProperties(rating, newRating);

            newRating.setUsername(userService.getOne(new QueryWrapper<User>().eq("id", rating.getUserId())).getUsername());
            newRating.setMovieName(movieService.getOne(new QueryWrapper<Movie>().eq("id", rating.getMovieId())).getTitle());
            newRating.setCreateTime(rating.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            newRecords.add(newRating);
        }

        Page<com.example.movie.dto.Rating> page = new Page<>(current, pageSize);
        page.setRecords(newRecords);
        page.setTotal(newRecords.size());
        return ResponseData.success(page);
    }

    @GetMapping("/commentList")
    public ResponseData<Page<com.example.movie.dto.Comment>> commentList(@RequestParam(defaultValue = "1") int current,
                                                                         @RequestParam(defaultValue = "10") int pageSize) {
        Page<Comment> commentPage = commentService.page(new Page<>(current, pageSize));
        List<Comment> records = commentPage.getRecords();

        List<com.example.movie.dto.Comment> newRecords = new ArrayList<>();
        for (Comment comment : records) {
            com.example.movie.dto.Comment newComment = new com.example.movie.dto.Comment();
            BeanUtils.copyProperties(comment, newComment);

            newComment.setUsername(userService.getOne(new QueryWrapper<User>().eq("id", comment.getUserId())).getUsername());
            newComment.setMovieName(movieService.getOne(new QueryWrapper<Movie>().eq("id", comment.getMovieId())).getTitle());
            newComment.setCreateTime(comment.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            newRecords.add(newComment);
        }

        Page<com.example.movie.dto.Comment> page = new Page<>(current, pageSize);
        page.setRecords(newRecords);
        page.setTotal(newRecords.size());
        return ResponseData.success(page);
    }

    @PostMapping("/user/get")
    public ResponseData<Page<User>> getUser(@RequestParam String username,
                                        @RequestParam(defaultValue = "1") int current,
                                        @RequestParam(defaultValue = "10") int pageSize) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);

        return ResponseData.success(userService.page(new Page<>(current, pageSize), queryWrapper));
    }

    @PostMapping("/user/add")
    public ResponseData<Boolean> addUser(@RequestBody User user) {
        if (userService.save(user)) {
            return ResponseData.success(null, "创建用户成功!");
        } else {
            return ResponseData.failed(ResultCode.FAILED);
        }
    }

    @PostMapping("/user/update")
    public ResponseData<Boolean> updateUser(@RequestBody User user) {
        if (userService.updateById(user)) {
            return ResponseData.success(null, "修改用户成功!");
        } else {
            return ResponseData.failed(ResultCode.FAILED);
        }
    }

    @GetMapping("/user/delete")
    public ResponseData<Boolean> deleteUser(@RequestParam int userId) {
        if (userService.removeById(userId)) {
            return ResponseData.success(null, "删除用户成功!");
        } else {
            return ResponseData.failed(ResultCode.FAILED);
        }
    }

    @PostMapping("/movie/get")
    public ResponseData<Page<Movie>> getMovie(@RequestParam String title,
                                            @RequestParam(defaultValue = "1") int current,
                                            @RequestParam(defaultValue = "10") int pageSize) {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", title);

        return ResponseData.success(movieService.page(new Page<>(current, pageSize), queryWrapper));
    }

    @PostMapping("/movie/add")
    public ResponseData<Boolean> addMovie(@RequestBody Movie movie) {
        if (movieService.save(movie)) {
            return ResponseData.success(null, "创建电影成功!");
        } else {
            return ResponseData.failed(ResultCode.FAILED);
        }
    }

    @PostMapping("/movie/update")
    public ResponseData<Boolean> updateMovie(@RequestBody Movie movie) {
        if (movieService.updateById(movie)) {
            return ResponseData.success(null, "修改电影成功!");
        } else {
            return ResponseData.failed(ResultCode.FAILED);
        }
    }

    @GetMapping("/movie/delete")
    public ResponseData<Boolean> deleteMovie(@RequestParam int movieId) {
        if (movieService.removeById(movieId)) {
            return ResponseData.success(null, "创建电影成功!");
        } else {
            return ResponseData.failed(ResultCode.FAILED);
        }
    }

    @PostMapping("/rating/get")
    public ResponseData<Page<Rating>> getRating(@RequestParam String username,
                                              @RequestParam(defaultValue = "1") int current,
                                              @RequestParam(defaultValue = "10") int pageSize) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);

        User one = userService.getOne(queryWrapper);

        if (one != null) {
            QueryWrapper<Rating> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("user_id", one.getId());
            return ResponseData.success(ratingService.page(new Page<>(current, pageSize), queryWrapper1));
        } else {
            return ResponseData.success();
        }
    }

    @GetMapping("/rating/delete")
    public ResponseData<Boolean> deleteMRating(@RequestParam int ratingId) {
        if (ratingService.removeById(ratingId)) {
            return ResponseData.success(null, "删除评分成功!");
        } else {
            return ResponseData.success(null, "用户不存在或未评分！");

        }
    }

    @PostMapping("/comment/get")
    public ResponseData<Page<Comment>> getComment(@RequestParam String username,
                                               @RequestParam(defaultValue = "1") int current,
                                               @RequestParam(defaultValue = "10") int pageSize) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);

        User one = userService.getOne(queryWrapper);

        if (one != null) {
            QueryWrapper<Comment> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("user_id", one.getId());
            return ResponseData.success(commentService.page(new Page<>(current, pageSize), queryWrapper1));
        } else {
            return ResponseData.success();
        }
    }

    @GetMapping("/comment/delete")
    public ResponseData<Boolean> deleteComment(@RequestParam int commentId) {
        if (commentService.removeById(commentId)) {
            return ResponseData.success(null, "删除评分成功!");
        } else {
            return ResponseData.failed(ResultCode.FAILED);
        }
    }
}

