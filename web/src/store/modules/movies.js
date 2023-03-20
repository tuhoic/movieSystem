import axios from 'axios'

// axios.defaults.baseURL = 'http://localhost:8000'

const state = {
    movies: []
}

const getters = {
    movies: state => state.movies
}

const actions = {
    async fetchMovies({ commit }) {
        const response = await axios.get('/api/movies')
        commit('setMovies', response.data)
    },
    async searchMovies({ commit }, searchQuery) {
        const response = await axios.get(`/api/movies?title=${searchQuery}`)
        commit('setMovies', response.data)
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
