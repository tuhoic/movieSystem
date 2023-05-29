package com.example.movie.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Comment implements Serializable {

    private Integer id;

    private String username;

    private String movieName;

    private String headPortrait;

    private Integer userId;

    private Integer movieId;

    private String content;

    private String createTime;

}
