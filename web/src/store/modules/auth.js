import axios from 'axios'

axios.defaults.baseURL = 'http://localhost:8000'

const state = {
    isAuthenticated: false,
    user: null
}

const getters = {
    isAuthenticated: state => state.isAuthenticated,
    user: state => state.user
}

const actions = {
    async login({ commit }) {
      try {
          const response = await axios.get('/api/users/login')
          commit('SET_USER', response.data)
      }  catch (error) {
          console.log(error)
      }
    },
    async logout({ commit }) {
        try {
            await axios.post('/api/auth/logout')
            commit('SET_USER', null)
        } catch (error) {
            console.log(error)
        }
    }
}

const mutations = {
    SET_USER(state, user) {
        state.user = user
    }
}

export default {
    state,
    getters,
    actions,
    mutations,
    namespaces: true
}
