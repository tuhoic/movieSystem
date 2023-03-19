package com.example.movie.service.impl;

import com.example.movie.entity.Movie;
import com.example.movie.mapper.MovieMapper;
import com.example.movie.service.MovieService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Your Name
 * @since 2023-03-19
 */
@Service
public class MovieServiceImpl extends ServiceImpl<MovieMapper, Movie> implements MovieService {

}
