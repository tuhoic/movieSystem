import axios from "axios";

const state = {
    movies: [],
    searchTitle: "",
    currentPage: 1,
    pageSize: 20,
    total: 0,
}

const getters = {
    movies: state => state.movies,
    currentPage: state => state.currentPage,
    pageSize: state => state.pageSize,
    total: state => state.total
}

const actions = {
    async fetchMovies({ commit }) {
        const response = await axios.get(`/movie/search?title=${state.searchTitle}&current=${state.currentPage}&size=${state.pageSize}`)
        commit('setMovies', response.data.data.records)
        commit('setTotal', response.data.data.total)
    },
    async searchMovies({ commit }, searchQuery) {
        commit('setSearchTitle', searchQuery)
        commit('setCurrentPage', 1)
    },
    async handleCurrentChange({ commit }, currentPage) {
        commit('setCurrentPage', currentPage);
    }
}

const mutations = {
    setMovies: (state, movies) => (state.movies = movies),
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
