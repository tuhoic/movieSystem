package com.example.movie.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String director;

    private String cast;

    private Integer duration;

    private String genre;

    private String releaseDate;

    private Float rating;

    private String coverUrl;

    private String fullSizeCoverUrl;

    private String country;

    private String writer;


}
