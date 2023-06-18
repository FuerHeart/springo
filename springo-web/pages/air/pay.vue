<template>
  <div class="container">
    <div class="main">
      <div class="pay-title">
        支付总金额 <span class="pay-price">￥ {{ order.amountPaid }}</span>
        <!--支付总金额 <span class="pay-price">￥ {{order.price}}</span>-->
      </div>
      <div class="pay-main">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>待支付订单：{{ order.orderNo }}</span>
            <el-button style="float: right; padding: 3px 0" type="text">查看详情</el-button>
          </div>
          <div class="order-item">
            <el-row :gutter="20">
              <el-col :span="16">
                <el-statistic :value="countDownTimer" format="HH:mm:ss" time-indices>
                  <template slot="prefix">剩余支付时间</template>
                  <template slot="suffix"> 若订单超时请重新订购</template>
                </el-statistic>
                <div class="flight-info-item">
                  <span>{{ order.departureAirportName }} {{ order.departureTerminal }}</span>
                  <img class="arrow-oneway" src="@/assets/images/icon_sprite.png" alt="">
                  <span>{{ order.arrivalAirportName }} {{ order.arrivalTerminal }}</span>
                  <div class="flight-data-item">
                    <span>{{ order.departureDatetime | dateFormat }}</span>
                    <span style="margin-left: 70px">{{ order.arrivalDatetime | dateFormat }}</span>
                  </div>
                </div>
              </el-col>
              <el-col :span="8">
                <div class="users">
                  乘机人： <span v-for="(item, index) in passengers" :key="index">{{ item.username }}&nbsp;&nbsp;</span>
                  <p> 联系人手机： +86 {{ order.contactPhone }}</p>
                </div>
              </el-col>
            </el-row>
            <el-row>
            </el-row>
          </div>
        </el-card>
        <el-row>
          <h3>支付方式</h3>
          <br>
          <el-col class="payment">
            <div class="alipay" @click="aliPay">
              <img src="@/assets/images/alipay.png">
            </div>

          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import dayjs from "dayjs";

export default {
  data() {
    return {
      order: {},
      passengers: "",
      // 检查付款的定时器对象
      timer: null,
      // 订单标题
      subject: "机票订单",
      // 产品码
      product_code: "FAST_INSTANT_TRADE_PAY",
      total_amount: "500",

      timerId: null, // 计时器ID
      countDownTimer: Date.now() + 1000 * 30 * 60, // 倒计时
    }
  },
  filters: {
    dateFormat(order) {
      return dayjs(order).format("YYYY年MM月DD日HH:mm")
    }
  },
  methods: {
    // 支付宝支付
    aliPay() {
      return this.$axios({
        url: "/pay/create",
        method: "GET",
        data: {
          out_trade_no: this.order.orderNo,
          total_amount: this.order.amountPaid,
          subject: this.subject,
          product_code: this.product_code
        },
        // headers: {
        //   Authorization: `Bearer ${this.$store.state.user.userInfo.token}`
        // }
      }).then(res => {
        // const {statusTxt} = res.data;
        console.log(res.data)
        document.write(res.data)

        if (statusTxt === "支付完成") {
          return true;
        } else {
          return false;
        }
      })
    },
  },

  destroyed() {
    // 当页面离开后停止定时器
    clearInterval(this.timer);
  },

  mounted() {
    // 因为本地的读取的时间是比组件加载慢，需要延时执行
    setTimeout(() => {
      this.$axios({
        url: `/orders/queryById?id=${this.$route.query.orderNo}`,
        headers: {
          token: this.$store.state.user.userInfo.token
        }
      }).then(res => {
        this.order = res.data.result[0]
        console.log(this.order)
        // // 价格保留两位小数点
        this.order.amountPaid = Number(this.order.amountPaid).toFixed(2);

        // passengers 转 JSON
        this.passengers = JSON.parse(this.order.passengers);

      });
    }, 100);
  }
}
</script>

<style scoped lang="less">
.container {
  background: #f5f5f5;
  padding: 30px 0;

  .main {
    width: 1000px;
    margin: 0 auto;

    .pay-title {
      text-align: right;

      span {
        font-size: 28px;
        color: orangered;
      }
    }

    .pay-main {
      background: #fff;
      margin-top: 10px;
      border-top: 5px orange solid;
      padding: 30px;

      /deep/ .el-card {
        width: 930px !important;
      }


      .order-item {
        margin-bottom: 18px;

        .flight-info-item {
          line-height: 30px;
          font-size: 16px;

          .arrow-oneway {
            width: 90px;

            span {
              position: absolute;
              font-size: 14px;
              font-weight: 400;
              color: #666;
              text-align: left;
              left: 0;
              width: 200px;
            }
          }

          .flight-data-item {
            font-size: 14px;
          }
        }

        .users {
          line-height: 30px;

          p {
            font-size: 16px;
            margin: 0;
            padding: 0;
          }
        }
      }

      .clearfix {
        z-index: 1;
      }

      .clearfix:before,
      .clearfix:after {
        display: table;
        content: "";
      }

      .clearfix:after {
        clear: both
      }

      .box-card {
        width: 480px;
      }

      h4 {
        font-size: 28px;
        font-weight: normal;
        margin-bottom: 10px;
      }

      .pay-qrcode {
        padding: 0 80px;
      }

      .payment-methods {
        text-align: center;
      }

      .qrcode {
        border: 1px #ddd solid;
        padding: 15px;
        height: fit-content;

        .pay-qr {
          width: 285px;
          height: 285px;

          img {
            width: 285px;
            height: 285px;

          }
        }

        #qrcode-stage {
          width: 200px;
          height: 200px;
          margin-bottom: 10px;
        }

        p {
          line-height: 2;
          text-align: center;
        }
      }

      .payment {
        display: flex;

        .alipay {
          width: 168px;
          height: 58px;
          border-radius: 5px;
          border: 1.5px solid rgba(41, 83, 142, .3);
          display: flex;
          align-items: center;
          justify-content: center;

          img {
            width: 100px;
          }

          &:hover {
            cursor: pointer;
          }
        }

      }
    }
  }
}

h3 {
  margin-top: 20px;
}
</style>
