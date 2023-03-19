package com.example.movie.service.impl;

import com.example.movie.pojo.Rating;
import com.example.movie.mapper.RatingMapper;
import com.example.movie.service.RatingService;
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
public class RatingServiceImpl extends ServiceImpl<RatingMapper, Rating> implements RatingService {

}