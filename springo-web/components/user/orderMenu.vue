<template>
  <el-row class="tac">
    <el-col>
      <el-menu
        default-active="1"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose">
        <el-menu-item index="1">
          <i class="el-icon-s-order"></i>
          <span>我的订单</span>
        </el-menu-item>
        <el-menu-item index="2">
          <i class="el-icon-s-finance"></i>
          <span slot="title">我的报销凭证</span>
        </el-menu-item>
        <el-menu-item index="3">
          <i class="el-icon-s-ticket"></i>
          <span slot="title">我的卡卷</span>
        </el-menu-item>
        <el-submenu index="4">
          <template slot="title">
            <i class="el-icon-user-solid"></i>
            <span>个人中心</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index="4-1">会员等级</el-menu-item>
            <el-menu-item index="4-2">里程积分</el-menu-item>
            <el-menu-item index="4-3">账户管理</el-menu-item>
            <el-menu-item index="4-4">常用乘机人</el-menu-item>
            <el-menu-item index="4-5">常用联系人</el-menu-item>
            <el-menu-item index="4-6">常用接送机人</el-menu-item>
            <el-menu-item index="4-7">常用地址</el-menu-item>
          </el-menu-item-group>
        </el-submenu>

      </el-menu>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "orderMenu",
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>

<style scoped lang="less">
.el-row {
  width: 220px;
}

.el-menu-item {
  height: 50px;
  line-height: 50px;
  padding: 0 20px;
  /* min-width: 200px; */
}
</style>
