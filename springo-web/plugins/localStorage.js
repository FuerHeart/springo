import createPersistedState from 'vuex-persistedstate'

export default ({store}) => {
    window.onNuxtReady(() => {
        createPersistedState({
            // 声明数据保存到本地时候的名称
            key: "store", 
        })(store)
    })
}