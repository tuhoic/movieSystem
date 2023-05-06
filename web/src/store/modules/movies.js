import axios from "axios";

const state = {
    movies: [],
    currentMovie: true,
    searchTitle: "",
    currentPage: 1,
    pageSize: 20,
    total: 0,
}

const getters = {
    movies: state => state.movies,
    currentMovie: state => state.currentMovie,
    searchTitle: state => state.searchTitle,
    currentPage: state => state.currentPage,
    pageSize: state => state.pageSize,
    total: state => state.total
}

const actions = {
    async fetchMovies({ commit }) {
        const response = await axios.get(`/movie/search?title=${state.searchTitle}&current=${state.currentPage}&pageSize=${state.pageSize}`)
        commit('setMovies', response.data.data.records)
        commit('setTotal', response.data.data.total)
    },
    async searchMovies({ commit }, searchQuery) {
        commit('setSearchTitle', searchQuery)
        commit('setCurrentPage', 1)
        commit('setCurrentMovie', true)
    },
    async handleCurrentChange({ commit }, currentPage) {
        commit('setCurrentPage', currentPage);
    },
    async recommendations({ commit }, userId) {
        commit('setCurrentMovie', false)
        const response = await axios.get(`/movie/recommendations?userId=${userId}&current=${state.currentPage}&pageSize=${state.pageSize}`);
        commit('setMovies', response.data.data.records)
        commit('setTotal', response.data.data.total)
    }
}

const mutations = {
    setMovies: (state, movies) => (state.movies = movies),
    setCurrentMovie: (state, currentPage) => (state.currentMovie = currentPage),
    setSearchTitle: (state, searchTile) => (state.searchTitle = searchTile),
    setCurrentPage: (state, currentPage) => (state.currentPage = currentPage),
    setPageSize: (state, pageSize) => (state.pageSize = pageSize),
    setTotal: (state, total) => (state.total = total),
}

export default {
    state,
    getters,
    actions,
    mutations,
    namespaced: true
}
