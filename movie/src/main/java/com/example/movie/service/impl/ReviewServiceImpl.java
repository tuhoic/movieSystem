package com.example.movie.service.impl;

import com.example.moviesystem.pojo.Review;
import com.example.moviesystem.mapper.ReviewMapper;
import com.example.moviesystem.service.ReviewService;
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
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {

}
