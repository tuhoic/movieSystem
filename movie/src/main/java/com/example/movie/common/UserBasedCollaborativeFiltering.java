package com.example.movie.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Rating;
import com.example.movie.service.MovieService;
import com.example.movie.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserBasedCollaborativeFiltering {

    @Autowired
    private MovieService movieService;

    @Autowired
    private RatingService ratingService;

    // 电影id
    private int[] movieIds;

    // 用户-电影评分矩阵
    private double[][] ratings;

    // 相似度矩阵
    private double[][] similarities;

    // 用户数量
    private int userCount;

    // 电影数量
    private int itemcount;

    public UserBasedCollaborativeFiltering() {
        // 读取CSV文件
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("D:\\graduate\\matrix1.csv"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 获取行数和列数
        int rows = lines.size() - 1;
        int cols = lines.get(0).split(",").length;

        rows /= 10;
        cols /= 5;

        this.userCount = rows + 1;
        this.itemcount = cols;

        movieIds = new int[this.itemcount];
        String[] ids = lines.get(0).split(",");
        for (int i = 0; i < this.itemcount; i ++ ) {
            movieIds[i] = Integer.parseInt(ids[i]);
        }

        // 初始化ratings矩阵
        this.ratings = new double[this.userCount][this.itemcount];
        this.similarities = new double[this.userCount][this.userCount];

        // 解析CSV文件并存储在ratings矩阵中
        for (int i = 1; i < this.userCount - 1; i++) {
            String[] values = lines.get(i).split(",");
            for (int j = 0; j < this.itemcount; j++) {
                this.ratings[i - 1][j] = Double.parseDouble(values[j]);
            }
        }
    }

    // 构造函数
    public UserBasedCollaborativeFiltering(double[][] ratings) {
        this.ratings = ratings;
        this.userCount = ratings.length;
        this.itemcount = ratings[0].length;
        this.similarities = new double[userCount][userCount];

        calculateSimilarities();
    }

    // 计算用户之间的相似度
    private double cosineSimilarity(int user1, int user2) {
        double dotProduct = 0;
        double norm1 = 0;
        double norm2 = 0;

        for (int i = 0; i < itemcount; i++) {
            if (ratings[user1][i] != 0 && ratings[user2][i] != 0) {
                dotProduct += ratings[user1][i] * ratings[user2][i];
                norm1 += ratings[user1][i] * ratings[user1][i];
                norm2 += ratings[user2][i] * ratings[user2][i];
            }
        }

        if (norm1 == 0 || norm2 == 0) {
            return 0;
        } else {
            return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
        }
    }

    // 计算所有用户之间的相似度
    public void calculateSimilarities() {
        for (int i = 0; i < userCount; i++) {
            for (int j = i; j < userCount; j++) {
                double similarity = cosineSimilarity(i, j);
                similarities[i][j] = similarity;
                similarities[j][i] = similarity;
            }
        }
    }

    // 获取与指定用户最相似的前N个用户
    private List<Integer> getTopNSimilarUsers(int userIndex, int n) {
        Map<Integer, Double> similarityMap = new HashMap<>();
        for (int i = 0; i < userCount; i++) {
            if (i != userIndex) {
                similarityMap.put(i, similarities[userIndex][i]);
            }
        }
        List<Integer> topNSimilarUsers = similarityMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return topNSimilarUsers;
    }

    // 基于用户的协同过滤推荐算法
    public Page<Movie> recommendItems(int userIndex, int current, int pageSize) {
        boolean existRecord = false;
        for (int j = 0; j < this.itemcount; j ++ ) {
            QueryWrapper<Rating> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userIndex);
            queryWrapper.eq("movie_id", movieIds[j]);

            Rating one = ratingService.getOne(queryWrapper);
            if (one != null) {
                existRecord = true;
                this.ratings[this.userCount - 1][j] = one.getRating();
            } else {
                this.ratings[this.userCount - 1][j] = 0;
            }
        }
        if (!existRecord) {
            Page<Movie> page = new Page<>(current, pageSize);
            QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("rating");
            return movieService.page(page, queryWrapper);
        }

        calculateSimilarities();

        userIndex = this.userCount - 1;

        List<Integer> topNSimilarUsers = getTopNSimilarUsers(userIndex, this.userCount / 10);
        Map<Integer, Double> recommendedItems = new HashMap<>();
        for (int i = 0; i < itemcount; i++) {
            if (ratings[userIndex][i] == 0) {
                double weightedRatingSum = 0;
                double similaritySum = 0;
                for (int similarUser : topNSimilarUsers) {
                    double similarity = similarities[userIndex][similarUser];
                    if (ratings[similarUser][i] != 0) {
                        weightedRatingSum += similarity * ratings[similarUser][i];
                        similaritySum += similarity;
                    }
                }
                if (similaritySum > 0) {
                    double predictedRating = weightedRatingSum / similaritySum;
                    recommendedItems.put(i, predictedRating);
                }
            }
        }

        List<Integer> recommendedItemIds = recommendedItems.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // 计算总记录数
        int total = recommendedItemIds.size();

        // 计算分页数据
        int fromIndex = (current - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, total);
        List<Integer> movieIds = recommendedItemIds.subList(fromIndex, toIndex);


        List<Movie> movies = new ArrayList<>();
        for (Integer movieId : movieIds) {
            Movie movie = movieService.getById(movieId);
            if (movie != null) {
                movies.add(movie);
            }
        }

        Page<Movie> moviePage = new Page<>(current, pageSize);
        moviePage.setRecords(movies);
        moviePage.setTotal(total);

        return moviePage;
    }

}
