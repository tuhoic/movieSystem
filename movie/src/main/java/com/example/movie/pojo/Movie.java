package com.example.movie.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuMing
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String director;

    private String cast;

    private Integer duration;

    private String genre;

    private Date releaseDate;

    private Float rating;

    private String poster;


}
