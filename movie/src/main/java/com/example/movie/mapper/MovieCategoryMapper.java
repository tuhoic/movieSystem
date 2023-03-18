package com.example.movie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.movie.entity.MovieCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MovieCategoryMapper extends BaseMapper<MovieCategory> {
}
