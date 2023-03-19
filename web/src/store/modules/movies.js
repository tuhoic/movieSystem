import axios from 'axios'

export default {
    state: {
        movies: []
    },
    getters: {
        movies: state => state.movies
    },
    mutations: {
        setMovies(state, movies) {
            state.movies = movies
        }
    },
    actions: {
        async fetchMovies({ commit }) {
            try {
                const response = await axios.get('/api/movies')
                commit('setMovies', response.data)
            } catch (error) {
                console.error(error)
            }
        }
    }
}
