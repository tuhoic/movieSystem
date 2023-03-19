package com.example.movie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.movie.entity.MovieCategory;
import com.example.movie.mapper.MovieCategoryMapper;
import com.example.movie.service.MovieCategoryService;
import org.springframework.stereotype.Service;

@Service
public class MovieCategoryServiceImpl extends ServiceImpl<MovieCategoryMapper, MovieCategory> implements MovieCategoryService {
}
