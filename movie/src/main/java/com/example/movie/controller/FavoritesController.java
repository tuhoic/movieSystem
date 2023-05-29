package com.example.movie.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.movie.common.ResponseData;
import com.example.movie.common.ResultCode;
import com.example.movie.entity.Favorites;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Rating;
import com.example.movie.entity.User;
import com.example.movie.service.FavoritesService;
import com.example.movie.service.MovieService;
import com.example.movie.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoritesController {

    @Autowired
    private FavoritesService favoritesService;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @GetMapping("/get")
    public ResponseData<Favorites> get(@RequestParam int userId,
                                       @RequestParam int movieId) {
        QueryWrapper<Favorites> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("movie_id", movieId);

        Favorites one = favoritesService.getOne(queryWrapper);
        if (one != null) {
            return ResponseData.success(one);
        } else {
            return ResponseData.failed(ResultCode.FAILED);
        }
    }

    @GetMapping("/getFavorite")
    public ResponseData<Page<com.example.movie.dto.Favorites>> getRating(@RequestParam("userId") Integer userId,
                                                   @RequestParam(defaultValue = "1") int current,
                                                   @RequestParam(defaultValue = "10") int pageSize) {
        QueryWrapper<com.example.movie.entity.Favorites> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByAsc("create_time");

        Page<Favorites> page = favoritesService.page(new Page<>(current, pageSize), queryWrapper);
        if (page != null) {
            Page<com.example.movie.dto.Favorites> favoritesPage = new Page<>(current, pageSize);

            List<Favorites> records = page.getRecords();
            List<com.example.movie.dto.Favorites> newRecords = new ArrayList<>();

            for (com.example.movie.entity.Favorites favorites : records) {
                com.example.movie.dto.Favorites newFavorites = new com.example.movie.dto.Favorites();
                BeanUtils.copyProperties(favorites, newFavorites);
                newFavorites.setCreateTime(favorites.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("id", favorites.getUserId());
                newFavorites.setMovieName(movieService.getOne(new QueryWrapper<Movie>().eq("id", favorites.getMovieId())).getTitle());

                User one = userService.getOne(queryWrapper1);
                newFavorites.setUsername(one.getUsername());

                newRecords.add(newFavorites);
            }

            favoritesPage.setRecords(newRecords);
            favoritesPage.setTotal(newRecords.size());
            return ResponseData.success(favoritesPage);
        } else {
            return ResponseData.failed(ResultCode.FAILED);
        }
    }

    @GetMapping("/list")
    public ResponseData<Integer> getList(@RequestParam int movieId) {
        QueryWrapper<Favorites> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("movie_id", movieId);

        return ResponseData.success(favoritesService.count(queryWrapper));
    }

    @PostMapping("/add")
    public ResponseData<Boolean> add(@RequestBody Favorites favorites) {
        if (favoritesService.save(favorites)) {
            return ResponseData.success();
        } else {
            return ResponseData.failed(ResultCode.FAILED);
        }
    }

    @PostMapping("/delete")
    public ResponseData<Boolean> delete(@RequestBody Favorites favorites) {
        QueryWrapper<Favorites> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", favorites.getUserId());
        queryWrapper.eq("movie_id", favorites.getMovieId());

        if (favoritesService.remove(queryWrapper)) {
            return ResponseData.success();
        } else {
            return ResponseData.failed(ResultCode.FAILED);
        }
    }
}

