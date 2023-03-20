import axios from "axios";

const state = {
    movies: []
}

const getters = {
    movies: state => state.movies
}

const actions = {
    async fetchMovies({ commit }, page) {
        const response = await axios.get(`/movie/page?current=${page.current}&pageSize=${page.size}`)
        commit('setMovies', response.data.data.records)
    },
    async searchMovies({ commit }, searchQuery) {
        const response = await axios.get(`/movies/search?title=${searchQuery}`)
        commit('setMovies', response.data.data)
    }
}

const mutations = {
    setMovies: (state, movies) => (state.movies = movies)
}

export default {
    state,
    getters,
    actions,
    mutations,
    namespaced: true
}
