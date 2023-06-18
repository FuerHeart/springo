<template>
  <div class="header">

    <el-row class='main' justify="space-between" type="flex">

      <!-- logo -->
      <div class="logo">
        <nuxt-link to="/">
          <img alt="" src="../assets/images/logo.png">
        </nuxt-link>
      </div>

      <!-- 菜单 -->
      <el-row class="navs" type="flex">
        <nuxt-link to="/" icon="el-icon-search"><i class="el-icon-s-home" style="margin-right: 4px"></i>首页</nuxt-link>
        <nuxt-link to="/air/flightStatus" icon="el-icon-s-promotion"><i class="el-icon-s-promotion" style="margin-right: 4px"></i>航班动态</nuxt-link>
        <nuxt-link to="/air/SeatSelection" icon="el-icon-info"><i class="el-icon-info" style="margin-right: 4px"></i>在线选座</nuxt-link>
<!--        <nuxt-link to="/demo/demo" icon="el-icon-s-tools"><i class="el-icon-s-tools" style="margin-right: 4px"></i>Demo</nuxt-link>-->
      </el-row>

      <!-- 登录注册 -->
      <div class="header-right">

        <div v-if="$store.state.user.userInfo.token">
<!--        <div>-->

          <el-dropdown>
                        <span class="el-dropdown-link">
                            <img alt="" src="../assets/images/avatar.jpg">
                          {{ $store.state.user.userInfo.user.realName }}
                            <i class="el-icon-arrow-down el-icon--right"></i>
                        </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人中心</el-dropdown-item>
              <el-dropdown-item>
                <div @click="myOrder">我的订单</div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div @click="handleClick">退出</div>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>

        </div>

        <div class="login-link" v-else>
          <nuxt-link to="/user/login"> 会员登录 | 注册</nuxt-link>
        </div>

      </div>
    </el-row>
  </div>
</template>
<script>
export default {
  mounted() {
    // console.log(this.$store.state.user.userInfo);
  },
  methods: {
    handleClick() {
      // 清除本地的数据
      this.$store.commit("user/clearUserInfo");
      this.$message.success("退出成功");
    },
    myOrder() {
      this.$router.push("/user/myOrder")
      // this.$router.push("/air/order")
    }
  }
}
</script>

<style lang="less" scoped>

.header {
  height: 60px;
  line-height: 60px;
  border-bottom: 1px #ddd solid;
  box-shadow: 0 2px 2px #eee;

  .main {
    width: 1000px;
    margin: 0 auto;

    .logo {
      margin-top: 6px;
      margin-right: 20px;

      img {
        display: block;
        width: 147px;
        height: 49px;
      }
    }

    .navs {
      flex: 1;

      a {
        display: block;
        padding: 0 20px;
        height: 60px;
        box-sizing: border-box;
        font-family: "PingFang SC", sans-serif;

        &:hover {
          color: #409eff;
          border-bottom: 3px #409eff solid;
        }

        &:active {
          background: #409eff;
          color: #fff;
        }
      }

      // 高亮的样式
      .nuxt-link-exact-active {
        background: #409eff;
        color: #fff !important;
      }
    }

    // 登录 注册的样式
    .el-dropdown-link {
      img {
        width: 36px;
        height: 36px;
        border-radius: 100px;
        vertical-align: middle;
      }
    }

    .login-link {
      text-align: center;
      padding: 0 15px;
      line-height: 40px;
      font-size: 16px;
      border: 1px solid #BFA456;
      margin: 10px 0 !important;

      a {
        color: #9f8f5d;
      }
    }
  }
}

</style>
