package com.example.movie.service.impl;

import com.example.movie.entity.Admin;
import com.example.movie.mapper.AdminMapper;
import com.example.movie.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author Your Name
 * @since 2023-05-11
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
