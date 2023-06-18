<template>
  <div class="content-bar">
    <div class="container">
      <div class="content-bar-body">
        <el-row>
          <el-col :span="8">
            <h2>添加乘客信息</h2>
            <span>(带*的为必填项)</span>
          </el-col>
          <el-col :span="8" offset="8">
            <div class="bread-cumb-inner-container flex bread-cumb">
              <el-steps space :active="1" align-center="center" finish-status="success">
                <el-step title="航班选择"></el-step>
                <el-step title="旅客信息"></el-step>
                <el-step title="支付出票"></el-step>
              </el-steps>
            </div>
          </el-col>
        </el-row>

      </div>
      <el-row type="flex" justify="space-between" :gutter="20">
        <el-col :span="16">
          <div>
            <!-- 订单表单 -->
            <OrderForm :data="infoData" @setAllPrice="setAllPrice"/>
          </div>
        </el-col>
        <el-col :span="12">
          <div>
            <!-- 侧边栏 -->
            <KeepAlive>
              <OrderAside :data="infoData" :allPrice="allPrice"/>
            </KeepAlive>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import OrderForm from "@/components/air/orderForm.vue";
import OrderAside from "@/components/air/orderAside.vue";

export default {
  components: {
    OrderForm,
    OrderAside
  },
  data() {
    return {
      // 保存订单机票信息
      infoData: this.$route.query,
      allPrice: 0,
    }
  },
  methods: {
    // 设置订单机票信息，给orderForm来调用的
    // setInfoData(data) {
    //   this.infoData = data;
    // },

    // 接收总价格，来自于orderForm
    setAllPrice(price) {
      this.allPrice = price;
    },

  },
  mounted() {
    console.log(this.$route.query)
    // const infoData = this.$route.query
    // console.log(this.infoData)
  }
}
</script>

<style lang="less" scoped>
.content-bar {
  background: #f4f4f4 url(../../assets/images/content-bar.png) no-repeat top;
  background-size: auto 230px;

  .container {
    width: 1000px;
    margin: 0 auto;

    .content-bar-body {
      height: 90px;

      h2 {
        font-size: 24px;
        font-weight: 500;
        display: inline-block;
        color: #fff;
        line-height: 90px;
      }

      span {
        color: #fff;
        margin-left: 10px;
        font-size: 12px;
      }

      .bread-cumb-inner-container {
        height: 100%;
        margin-top: 20px;
      }

      /deep/ .el-step__icon {
        background: transparent;
      }

      /deep/ .is-success {
        color: white;
        border-color: white;

      }

      /deep/ .is-text {
        border-radius: 50%;
        border: 2px solid;
        /* border-color: inherit; */
      }

      /deep/ .el-step__line {
        left: 60%;
        right: -40%;
      }

      /deep/ .is-process {
        color: #409EFF;

      }
    }
  }

  /*aside*/

  .aside {
    width: 350px;
    height: fit-content;
    border: 1px #ddd solid;
  }
}

</style>
