package com.example.movie;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.movie.entity.User;
import com.example.movie.service.UserService;
import com.example.movie.utils.JwtTokenUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMethod {
    @Autowired
    private UserService userService;

    @Test
    public void Test() {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        System.out.println("user1".equals(jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.generateToken(userService.getOne(new QueryWrapper<User>().eq("username", "user1")).getUsername()))));
    }
}
