package com.example.movie.service.impl;

import com.example.movie.pojo.Movie;
import com.example.movie.mapper.MovieMapper;
import com.example.movie.service.MovieService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuMing
 * @since 2023-03-18
 */
@Service
public class MovieServiceImpl extends ServiceImpl<MovieMapper, Movie> implements MovieService {

}
