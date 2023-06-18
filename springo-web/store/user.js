// Vue.use(Vuex)

// new Vuex.Store({
//     state: {},
//     mutations: {},
//     actions: {}
// })

// store下的每个文件都是一个store的实例，不需要去手动创建
// 只需要暴露store的属性，（固定的写法）

// 由于nuxt项目会先在后端启动，调用window下api会报错，

// 需要使用第三方的插件来 【 把store的数据保存到本地 】，
// 该插件会自动判断当前的环境是服务端还是浏览器
export const state = () => ({
  userInfo: {
    token: "",
    user: {}
  }
});

export const mutations = {
  // 设置用户的数据的
  setUserInfo(state, data) {
    state.userInfo = data;
    // localStorage.setItem("userInfo", JSON.stringify(data));
  },

  // 清楚用户的数据
  clearUserInfo(state) {
    // 重置用户信息
    state.userInfo = {
      token: "",
      user: {}
    }
  }
};

// 存放公共的异步的方法
export const actions = {
  // resolve
  login({commit}, data) {

    return this.$axios({
      url: "/users/userLogin",
      method: "POST",
      data
    }).then(res => {
      console.log(res.data)
      // 在当前的模块下调用mutations可以不用模块名字
      // user/setUserInfo`写成setUserInfo
      commit("setUserInfo", res.data.result);
      // 调用外部的成功的回调函数
      // 在pormise函数中可以用过Promise.resolve来调用成功的回调函数
      Promise.resolve();

      //Promise.reject();
      //Promise.all()
    })
      .catch(res => {
        console.log(res);
      });
  },
}
