package com.example.movie.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Your Name
 * @since 2023-05-11
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    @TableField("imdbId")
    private Integer imdbid;

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
