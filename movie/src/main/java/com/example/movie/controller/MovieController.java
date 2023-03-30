package com.example.movie.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.movie.common.ResponseData;
import com.example.movie.common.ResultCode;
import com.example.movie.entity.Movie;
import com.example.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

//    @GetMapping("/list")
//    public ResponseData<Page<Movie>> list(Page<Movie> page) {
//        if (page == null) {
//            page = new Page<>(1, 20);
//        }
//        // 执行分页查询
//        Page<Movie> movieList = movieService.page(page);
//        return ResponseData.success(movieList);
//    }

    @GetMapping("/search")
    public ResponseData<Page<Movie>> search(@RequestParam(defaultValue = "") String title,
                                            @RequestParam(defaultValue = "1") int current,
                                            @RequestParam(defaultValue = "20") int pageSize) {
        Page<Movie> page = new Page<>(current, pageSize);

        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", title);
        Page<Movie> moviePage = movieService.page(page, queryWrapper);
        return ResponseData.success(moviePage);
    }

    @GetMapping("/{id]")
    public ResponseData<Movie> get(int movieId) {
        Movie movie = movieService.getById(movieId);
        if (movie != null) {
            return ResponseData.success(movie);
        } else {
            return ResponseData.failed(ResultCode.VALIDATE_FAILED, "电影不存在!");
        }
    }
}

