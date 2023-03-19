package com.example.movie.service.impl;

import com.example.movie.pojo.User;
import com.example.movie.mapper.UserMapper;
import com.example.movie.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}