package com.example.movie.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Your Name
 * @since 2023-03-19
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

    private LocalDate releaseDate;

    private Float rating;

    private String coverUrl;

    private String fullSizeCoverUrl;

    private String country;

    private String writer;


}
