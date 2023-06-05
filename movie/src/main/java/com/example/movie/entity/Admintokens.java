package com.example.movie.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Your Name
 * @since 2023-05-12
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Admintokens implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private Integer adminId;

    private String token;

    private LocalDateTime expiration;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
