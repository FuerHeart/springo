<template>
  <div class="flight-segment-type-group">
    <el-card shadow="hover" style="margin-bottom: 3px">
      <div class="flight-item">
        <div @click="handleShow">
          <!-- 显示的机票信息 -->
          <el-row type="flex" align="middle" class="flight-info">
            <el-col :span="2">
              <div class="airline-item" style="margin-top: 8px;">
                <img :src="data[0].airlineLogo">
                <br>
                <img :src="data[1].airlineLogo">
              </div>
            </el-col>
            <el-col :span="7">
              <div class="airline-item">
                <span class="airline-name">{{ data[0].airlineName }}</span>
                <span class="flight-number">{{ data[0].flightNo }} {{ data[0].planeStyle }}({{
                    data[0].planeSize
                  }})</span>
              </div>
              <div class="airline-item">
                <span class="airline-name">{{ data[1].airlineName }}</span>
                <span class="flight-number">{{ data[1].flightNo }} {{ data[1].planeStyle }}({{
                    data[1].planeSize
                  }})</span>
              </div>
            </el-col>
            <el-col :span="12">
              <el-row type="flex" justify="space-between" class="flight-info-center">
                <el-col :span="8" class="flight-airport">
                  <strong>{{ data[0].departureDatetime | dateFormat }}</strong>
                  <span>{{ data[0].departureAirportName }}{{ data[0].departureTerminal }}</span>
                </el-col>
                <el-col :span="8" class="flight-time">
                  <div @mouseenter="handleMouseEnter" @mouseleave="handleMouseLeave" style="background: #26c28d">
                    <span>中转{{ timeConsuming }}</span>
                    <div class="arrow-transfer">
                      <img src="@/assets/images/arrow-transfer.png">
                      <span>转1次</span>
                    </div>
                    <span>转<span>{{ data[1].departureCityName }}{{
                        timeConsuminghm
                      }}</span></span>
                  </div>
                </el-col>
                <el-col :span="8" class="flight-airport">
                  <strong>{{ data[1].arrivalDatetime | dateFormat }}</strong>
                  <span>{{ data[1].arrivalAirportName }}{{ data[1].arrivalTerminal }}</span>
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="6" class="flight-info-right">
              ￥<span class="sell-price">700</span>起
            </el-col>
          </el-row>


          <div class="box-card" v-if="isCardShow == true">
            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet aperiam commodi culpa debitis dicta
            dolorum ea eos fuga id laborum, magni minus, obcaecati quasi quod repellendus saepe sequi tempore
            tenetur.
          </div>


        </div>

        <!--      <div class="flight-recommend" v-if="isShow">-->
        <!--        &lt;!&ndash; 隐藏的座位信息列表 &ndash;&gt;-->
        <!--        <el-row type="flex" justify="space-between" align="middle">-->
        <!--          <el-col :span="4">低价推荐</el-col>-->
        <!--          <el-col :span="20">-->
        <!--            <el-row type="flex" justify="space-between" align="middle" class="flight-sell"-->
        <!--                    v-for="(item, index) in cabinJSON.cabins" :key="index">-->
        <!--              <el-col :span="16" class="flight-sell-left">-->
        <!--                <span>{{ item.cabinName }}</span> | 退改签规则| 行李规定| 餐食说明| <span class="abbr">提供行程单</span>{{-->
        <!--                  item.supplierName-->
        <!--                }}-->
        <!--              </el-col>-->
        <!--              <el-col :span="5" class="price">-->
        <!--                ￥{{ item.cabinPrice.adultSalePrice }}-->
        <!--              </el-col>-->
        <!--              <el-col :span="3" class="choose-button">-->
        <!--                <el-button type="warning" size="mini" @click="handleToOrder(item)">-->
        <!--                  选定-->
        <!--                </el-button>-->
        <!--                <p>剩余：{{ item.seatLeftNum }}</p>-->
        <!--              </el-col>-->
        <!--            </el-row>-->
        <!--          </el-col>-->
        <!--        </el-row>-->
        <!--      </div>-->
      </div>
    </el-card>
  </div>

</template>

<script>
import dayjs from "dayjs";

export default {
  name: "FlightSegmentGroup",
  components: {},
  props: {
    // 数据
    data: {
      // 表示接收的类型必须是对象
      type: Array,
      // 默认是空对象
      default: []
    }
  },
  filters: {
    dateFormat(data) {
      return dayjs(data).format("HH:mm")
    },
  },
  data() {
    return {
      isCardShow: false,
    }
  },
  computed: {
    timeConsuming() {
      // 第一趟到达时间
      const departureDatetime = dayjs(this.data[1].arrivalDatetime).format("HH:mm")
      const arr = departureDatetime.split(":");
      // 第二趟起飞时间
      const arrivalDatetime = dayjs(this.data[0].departureDatetime).format("HH:mm")
      const dep = arrivalDatetime.split(":");

      if (arr[0] < dep[0]) {
        arr[0] += 24;
      }

      const countMin = (arr[0] * 60 + +arr[1]) - (dep[0] * 60 + +dep[1]);

      const hours = Math.floor(countMin / 60);
      const min = countMin % 60;

      return `${hours}时${min}分`;
    },
    timeConsuminghm() {
      // 第一趟到达时间
      const departureDatetime = dayjs(this.data[1].arrivalDatetime).format("HH:mm")
      const arr = departureDatetime.split(":");
      // 第二趟起飞时间
      const arrivalDatetime = dayjs(this.data[0].departureDatetime).format("HH:mm")
      const dep = arrivalDatetime.split(":");

      if (arr[0] < dep[0]) {
        arr[0] += 24;
      }

      const countMin = (arr[0] * 60 + +arr[1]) - (dep[0] * 60 + +dep[1]);

      const hours = Math.floor(countMin / 60);
      const min = countMin % 60;

      return `${hours}h${min}m`;
    }
  },
  watch: {},
  created() {
  },
  mounted() {
    console.log(this.data)
  },
  methods: {
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        // TODO 提交表单
      })
    },
    resetForm() {
      this.$refs['elForm'].resetFields()
    },
    handleMouseEnter() {
      this.isCardShow = true;
      console.log(this.isCardShow)
      console.log('鼠标移入')
    },
    handleMouseLeave() {
      this.isCardShow = false;
    }
  }
}
</script>

<style lang="less" scoped>
.flight-segment-type-group {
  margin-top: 10px;

  h3 {
    padding: 12px 24px;
    font-size: 18px;
    font-weight: 600;
    color: #333;
    border-radius: 8px 8px 0 0;
    display: inline-block;
    background-color: #eef1f6;
  }

  .desc {
    display: inline-block;
    margin-left: 11px;
    font-size: 14px;
    font-weight: 400;
    color: #0086f6;
    cursor: pointer;
  }
}

/deep/ .el-card__body {
  padding: 0;
}

/deep/ .is-hover-shadow {
  margin-bottom: 10px !important;
}

.flight-item {
  //border: 1px #ddd solid;
  //margin-bottom: 5px;
  height: 95px;

  .flight-segment-type-group {

    padding: 15px;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;

    img {
      width: 18px;
      height: 18px;
      overflow-clip-margin: content-box;
      overflow: clip;
    }

    .airline-item {
      display: block;
      align-items: flex-end;
      justify-content: left;
      line-height: 56/2px;

      .airline-name {
        font-size: 12px;
        color: #333333;
      }

      .flight-number {
        font-size: 12px;
        color: #0086f6;
      }
    }

    > div {
      &:first-child, &:last-child {
        text-align: center;
      }
    }
  }

  .flight-info-center {
    text-align: center;

    .flight-airport {
      strong {
        display: block;
        font-size: 30px;
        font-weight: normal;
        color: #333333;
      }

      span {
        font-size: 12px;
        color: #333333;
      }
    }

    .flight-time {
      display: block;
      align-items: center;
      justify-content: center;
      font-size: 12px;

      span {
        color: #999;
      }

      .arrow-transfer {
        margin-top: 4px;

        img {
          width: 105px;
          height: 22px;
        }

        span {
          width: 80%;
          display: inline-block;
          padding: 10px 0;
          color: #398efe;
          position: absolute;
          margin-left: 2px;
          left: 31px;
          top: 12px;
          font-size: 12px;
        }
      }

      .box-card {
        z-index: 999 !important;
        width: 480px;
        height: 500px;
        position: absolute;
        background: #26c28d;
      }
    }
  }

  .flight-info-right {
    font-size: 12px;
    color: #0086f6;

    .sell-price {
      font-size: 24px;
      margin: 0 2px;
    }
  }
}

.flight-recommend {
  background: #f6f6f6;
  border-top: 1px #eee solid;
  padding: 0 20px;

  .flight-sell {
    border-bottom: 1px #eee solid;
    padding: 10px 0;
    background-color: #f8fafb;

    &:last-child {
      border-bottom: none;
    }

    .flight-sell-left {
      font-size: 12px;
    }

    .price {
      font-size: 20px;
      color: orange;
    }

    .choose-button {
      text-align: center;
      color: #666;

      button {
        width: 100%;
        height: 35px;
        line-height: 35px;
        padding: 0 9px;
        font-size: 14px;
        font-weight: 600;
        border-radius: 5px;
        display: block;
        background-image: -webkit-gradient(linear, left top, right top, from(#ffa50a), to(#f70));
        background-image: -o-linear-gradient(left, #ffa50a, #f70);
        background-image: linear-gradient(90deg, #ffa50a, #f70);
        color: #fff;
        filter: progid:DXImageTransform.Microsoft.gradient(startColorstr="#ffa50a",endColorstr="#ff7700",GradientType=1);
      }
    }
  }
}
</style>
