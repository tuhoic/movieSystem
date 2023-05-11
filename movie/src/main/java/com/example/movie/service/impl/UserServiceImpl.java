package com.example.movie.service.impl;

import com.example.movie.entity.User;
import com.example.movie.mapper.UserMapper;
import com.example.movie.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Your Name
 * @since 2023-05-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
