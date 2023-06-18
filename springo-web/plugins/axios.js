import {Message} from "element-ui";
import bigint from "json-bigint"
// obj是一个nuxt对象
export default ({$axios, redirect}) => {

  // 监听axios请求的错误
  $axios.onError(res => {
    const {message, status} = res.response.data;

    // 如果是参数
    if (status === 400) {
      // this.$message
      Message.warning("服务器出错!");
    }

    // 未登录跳转到登录页
    if (status === 401 || status === 403) {
      redirect("/user/login");
    }
  });
  $axios.defaults.transformResponse = [
    function (data) {
      const json = bigint({
        storeAsString: true
      })
      return json.parse(data);
    }
  ]
}
