package com.example.movie.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("movie")
public class Movie {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String director;
    private String cast;
    private Integer duration;

}