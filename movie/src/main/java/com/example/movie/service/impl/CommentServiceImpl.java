package com.example.movie.service.impl;

import com.example.movie.entity.Comment;
import com.example.movie.mapper.CommentMapper;
import com.example.movie.service.CommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
