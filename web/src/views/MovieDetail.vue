<template>
  <top-bar></top-bar>
  <div class="movie-details">
    <div class="movie-poster">
      <img v-if="movie.fullSizeCoverUrl" :src="`/api/${movie.fullSizeCoverUrl}`" :style="{ width: '100%', height: '100%' }" @error="setDefaultImage" alt="Movie Cover" />
    </div>
    <div class="movie-info">
      <h1>{{ movie.title }}</h1>
      <p>导演: {{ movie.director }}</p>
      <p v-if="movie.cast">主演:
        <span v-for="(actor, index) in displayedCast" :key="index" v-show="showMore || index < 3">
          {{ actor }}{{ index === displayedCast.length - 1 ? '' : ', ' }}
        </span>
        <button class="btn-more" @click="toggleShowMore">
          {{ showMore ? '隐藏' : '显示更多' }}
        </button>
      </p>
      <p>类型: {{ movie.genre }}</p>
      <p>时长: {{ movie.duration }} 分钟</p>
      <p>上映日期: {{ movie.releaseDate }}</p>
      <p>评分: {{ movie.rating }}/10</p>
      <div v-if="isAuthenticated" class="movie-action">
        <div>
          <button class="btn-like" @click="toggleLike">
            {{ isLiked ? '取消赞' : '赞' }} {{ likes }}
          </button>
          <button class="btn-collect" @click="toggleCollect">
            {{ isCollected ? '取消收藏' : '收藏' }} {{ collects }}
          </button>
        </div>
        <div class="rating-wrapper">
          <div class="star-rating">
            <span v-for="n in maxStars" :key="n" class="star" @click="setRating(n)" :class="{ 'rated': n <= currentRating }">
              <i class="fa fa-star"></i>
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- 评论模块开始 -->
  <div class="comments">
    <h2>用户评论</h2>
    <div v-if="!isAuthenticated" class="login-tip">
      请<a @click="toLogin">登录</a>后发表评论
    </div>
    <div class="new-comment" v-if="isAuthenticated">
      <textarea v-model="commentContent" placeholder="发表评论"></textarea>
      <button class="btn-comment" @click="addComment">提交评论</button>
    </div>
    <div class="comment-list">
      <div v-for="(comment, index) in comments" :key="index" class="comment">
        <div class="comment-header">
          <span>{{ comment.userName }}</span>
          <span>{{ formatDate(comment.createTime) }}</span>
        </div>
        <div class="comment-content">{{ comment.content }}</div>
      </div>
    </div>
  </div>
  <!-- 评论模块结束 -->
</template>

<script>

import axios from 'axios'
import TopBar from "@/components/Header.vue";
import {mapGetters} from "vuex";

export default {
  components: { TopBar },
  props: {
    maxStars: {
      type: Number,
      default: 5
    },
    initialRating: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      movie: {},
      showMore: false,
      isLiked: false,
      isCollected: false,
      likes: 0,
      collects: 0,
      userRating: null,
      currentRating: this.initialRating
    }
  },
  methods: {
    setDefaultImage(event) {
      event.target.src = require('../assets/default_movie_cover.jpg')
    },
    toggleShowMore() {
      this.showMore = !this.showMore
    },
    likeMovie() {
      this.isLiked = !this.isLiked
      if (this.isLiked) {
        this.likes++
      } else {
        this.likes--
      }
    },
    collectMovie() {
      this.isCollected = !this.isCollected
      if (this.isCollected) {
        this.collects++
      } else {
        this.collects--
      }
    },
    setUserRating(rating) {
      this.userRating = rating
    },
    setRating(rating) {
      if (this.currentRating === 0) {
        this.currentRating = rating;
        // 发送 POST 请求将用户对电影的评分存储在服务器的 rating 表中
        axios.post('/rating/add', {
          userId: this.user.id,
          movieId: this.movie.id,
          rating: this.currentRating
        }).then((res) => {
          this.$message.success(res.data.message)
        })
      } else if (this.currentRating !== rating) {
        this.currentRating = rating
        // 发送 POST 请求将用户对电影的评分存储在服务器的 rating 表中
        axios.post('/rating/update', {
          userId: this.user.id,
          movieId: this.movie.id,
          rating: this.currentRating
        }).then((res) => {
          this.$message.success(res.data.message)
        })
      }
    }
  },
  computed: {
    ...mapGetters('auth', ["isAuthenticated", "user"]),
    displayedCast() {
      return this.movie.cast ? this.movie.cast.split(',') : []
    },
    ratingText() {
      if (this.userRating === null) {
        return "Rate this movie"
      } else {
        return "Your rating: ${this.userRating}"
      }
    },
    averageRating() {
      if (!this.movie.ratings) {
        return "N/A"
      } else {
        const total = this.movie.ratings.reduce((sum, rating) => sum + rating, 0)
        return (total / this.movie.ratings.length).toFixed(1)
      }
    },
  },
  mounted() {
    const movieId = this.$route.params.id
    axios.get(`/movie/${movieId}`).then((response) => {
      this.movie = response.data.data
    })
    if (this.user != null) {
      axios.get(`/rating/get?userId=${this.user.id}&movieId=${movieId}`).then((response) => {
        console.log(response)
        if (response.status === 200) {
          if (response.data.code === 200) {
            this.currentRating = response.data.data.rating ? response.data.data.rating : 0
          }
        }
      });
    }
  },
}
</script>

<style>
.movie-details {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}

.movie-poster {
  flex-basis: 40%;
  margin-right: 40px;

}

.movie-info {
  flex-basis: 60%;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.movie-action {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: flex-end;
  margin-top: 40px;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 50px;
}

.container img {
  width: 300px;
  height: 450px;
  object-fit: cover;
}

.container h1 {
  margin-bottom: 20px;
  font-size: 32px;
}

.container p {
  font-size: 20px;
  margin: 5px 0;
}

.btn-more {
  margin-left: 10px;
  font-size: 16px;
  padding: 10px 20px;
  border-radius: 5px;
  background-color: #4CAF50;
  color: white;
  text-decoration: none;
}

.btn-more:hover {
  background-color: #3e8e41;
}

.star-rating {
  display: inline-block;
  font-size: 0;
}
.star-rating .star {
  display: inline-block;
  font-size: 20px;
  cursor: pointer;
  color: #ccc;
}
.star-rating .star:hover,
.star-rating .star.rated {
  color: gold;
}

</style>