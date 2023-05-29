<template>
  <top-bar></top-bar>
  <div class="movie-details">
    <div class="movie-poster">
      <img v-if="movie.fullSizeCoverUrl" :src="`/api/${movie.fullSizeCoverUrl}`" :style="{ width: '100%', height: '100%' }" @error="setDefaultMovieImage" alt="Movie Cover" />
    </div>
    <div class="movie-info">
      <h1>{{ movie.title }}</h1>
      <p>导演：{{ movie.director }}</p>
      <p v-if="movie.cast">主演：
        <span v-for="(actor, index) in displayedCast" :key="index" v-show="showMore || index < 3">
          {{ actor }}{{ index === displayedCast.length - 1 ? '' : '，' }}
        </span>
        <button class="btn-more" @click="toggleShowMore">
          {{ showMore ? '隐藏' : '显示更多' }}
        </button>
      </p>
      <p>类型：{{ movie.genre }}</p>
      <p>时长：{{ movie.duration }}分钟</p>
      <p>上映日期：{{ movie.releaseDate }}</p>
      <p>评分：{{ movie.rating }}/10</p>
      <div v-if="isAuthenticated" class="movie-action">
        <div>
          <el-button class="btn-like" @click="toggleLike">
            {{ isLiked ? '取消赞' : '赞' }} {{ likes }}
          </el-button>
          <el-button class="btn-collect" @click="toggleCollect">
            {{ isCollected ? '取消收藏' : '收藏' }} {{ collects }}
          </el-button>
        </div>
        <div class="rating-wrapper">
          <div class="star-rating">
            <span v-for="n in maxStars" :key="n" class="star" @click="setRating(n)" :class="{ 'rated': n <= currentRating }">
              <i class="fa fa-star"></i>
            </span>
            <div class="text">{{ this.currentRating }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- 评论模块开始 -->
  <div class="comments">
    <h2>用户评论</h2>
    <div class="comment-list">
      <div v-for="(comment, index) in comments" :key="index" class="comment">
        <div class="comment-header">
          <img class="avatar" :src="`/api/` + comment.headPortrait" @error="setDefaultHeadPortraitImage" alt="用户头像">
          <span>{{ comment.username }}</span>
          <span>{{ comment.createTime }}</span>
        </div>
        <div class="comment-content">{{ comment.content }}</div>
      </div>
    </div>
    <div class="new-comment">
      <textarea v-model="commentContent" placeholder="发表评论"></textarea>
      <button class="btn-comment" @click="isAuthenticated ? addComment() : redirectToLogin()">
        {{ isAuthenticated ? '提交评论' : '请登录' }}
      </button>
    </div>
  </div>
  <!-- 评论模块结束 -->
</template>

<script>

import axios from 'axios'
import TopBar from "@/components/Header.vue";
import {mapGetters} from "vuex";
import router from "@/router";

export default {
  name: "TestPage",
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
      currentRating: this.initialRating,
      commentContent: "",
      comments: [],
    }
  },
  methods: {
    setDefaultMovieImage(event) {
      event.target.src = require('../assets/default_movie_cover.jpg')
    },
    setDefaultHeadPortraitImage(event) {
      event.target.src = require('../assets/headPortrait.jpg')
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
    },
    toLogin()  {
      router.push("/")
    },
    getComments() {
      axios.get(`/comment/list?movieId=${this.$route.params.id}`).then((res) => {
        if (res.status === 200) {
          this.comments = res.data.data.records
        }
      })
    },
    addComment() {
      axios.post('/comment/add', {
        userId: this.user.id,
        movieId: this.movie.id,
        content: this.commentContent
      }).then((res) => {
        this.$message.success(res.data.message)
        if (res.status === 200) {
          this.commentContent = ""
          this.getComments()
        }
      })
    },
    toggleLike() {
      if (!this.isLiked) {
        axios.post(`/likes/add`, {
          userId: this.user.id,
          movieId: this.$route.params.id,
        }).then((response) => {
          this.$message.success(response.data.message)
          this.likeMovie()
        })
      } else {
        axios.post(`/likes/delete`, {
          userId: this.user.id,
          movieId: this.$route.params.id,
        }).then((response) => {
          this.$message.success(response.data.message)
          this.likeMovie()
        })
      }
    },
    toggleCollect() {
      if (!this.isCollected) {
        axios.post(`/favorites/add`, {
          userId: this.user.id,
          movieId: this.$route.params.id,
        }).then((response) => {
          this.$message.success(response.data.message)
          this.collectMovie()
        })
      } else {
        axios.post(`/favorites/delete`,{
          userId: this.user.id,
          movieId: this.movie.id,
        }).then((response) => {
          this.$message.success(response.data.message)
          this.collectMovie()
        })
      }
    },
    redirectToLogin() {
      router.push("/login")
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
    this.getComments();
    axios.get(`/movie/${movieId}`).then((response) => {
      this.movie = response.data.data
    })
    if (this.user != null) {
      axios.get(`/rating/get?userId=${this.user.id}&movieId=${movieId}`).then((response) => {
        if (response.status === 200) {
          if (response.data.code === 200) {
            this.currentRating = response.data.data.rating ? response.data.data.rating : 0
          }
        }
      });
      axios.get(`/likes/get?userId=${this.user.id}&movieId=${movieId}`).then((response) => {
        if (response.status === 200) {
          this.isLiked = response.data.data
        }
      })
      axios.get(`/favorites/get?userId=${this.user.id}&movieId=${movieId}`).then((response) => {
        if (response.status === 200) {
          this.isCollected = response.data.data
        }
      })
    }
    axios.get(`likes/list?movieId=${movieId}`).then((response) => {
      this.likes = response.data.data
    })
    axios.get(`favorites/list?movieId=${movieId}`).then((response) => {
      this.collects = response.data.data
    })

  },
}

</script>

<style scoped>
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

.comments {
  margin-top: 50px;
}

.comments h2 {
  margin-bottom: 20px;
  font-size: 28px;
}

.login-tip {
  margin-top: 20px;
  font-size: 24px;
}

.new-comment {
  margin-top: 20px;
}

.new-comment textarea {
  width: 100%;
  height: 100px;
  font-size: 20px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  resize: none;
}

.btn-comment {
  margin-top: 10px;
  font-size: 20px;
  padding: 10px 20px;
  border-radius: 5px;
  background-color: #4CAF50;
  color: white;
  text-decoration: none;
  cursor: pointer;
}

.btn-comment:hover {
  background-color: #3e8e41;
}

.comment {
  margin-top: 20px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 5px;
  font-size: 18px;
}

.comment-header img.avatar {
  margin-right: 10px;
}

.comment-header span:last-child {
  margin-left: auto;
}

.comment-content {
  font-size: 20px;
}

.avatar {
  width: 40px; /* 调整头像大小 */
  height: 40px;
  margin-right: 10px;
  border-radius: 50%;
}
</style>