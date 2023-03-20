package com.example.movie.utils;
import java.util.UUID;

public class RandomStringUtil {

    public static String generateRandomString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
