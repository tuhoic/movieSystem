package com.example.movie.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;

/**
 * <p>
 *
 * </p>
 */
@Data
public class Comment implements Serializable {

    private Integer id;

    private String username;

    private String headPortrait;

    private Integer movieId;

    private String content;

    private String createTime;



}
