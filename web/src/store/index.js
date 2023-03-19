import { createStore } from 'vuex'
import auth from './modules/auth'
import movies from "./modules/movies";

const store = createStore({
    modules: {
        auth,
        movies
    }
})

export default store
