<template>
  <el-card shadow="hover" style="margin-bottom: 3px">
    <div class="flight-item">
      <div @click="handleShow">
        <!-- 显示直达的机票信息 -->
        <el-row type="flex" align="middle" class="flight-info" v-if="data.length === 1">
          <el-col :span="2">
            <img :src="data[0].airlineLogo" style="margin-right: 5px">
          </el-col>
          <el-col :span="6">
            <span class="airline-name">{{ data[0].airlineName }}</span>
            <br>
            <span class="flight-number">{{ data[0].flightNo }} {{
                data[0].planeStyle
              }}({{ data[0].planeSize }})</span>
          </el-col>
          <el-col :span="12">
            <el-row type="flex" justify="space-between" class="flight-info-center">
              <el-col :span="8" class="flight-airport">
                <strong>{{ data[0].departureDatetime | dateFormat }}</strong>
                <span>{{ data[0].departureAirportName }} {{ data[0].departureTerminal }}</span>
              </el-col>
              <el-col :span="8" class="flight-time">
                <img src="@/assets/images/arrow-oneway.png">
                <span>{{ rankTime }}</span>
              </el-col>
              <el-col :span="8" class="flight-airport">
                <strong>{{ data[0].arrivalDatetime | dateFormat }}</strong>
                <span>{{ data[0].arrivalAirportName }} {{ data[0].arrivalTerminal }}</span>
              </el-col>
            </el-row>
          </el-col>
          <el-col :span="6" class="flight-info-right">
            ￥<span class="sell-price">{{ data[0].priceFare }}</span>起
          </el-col>
        </el-row>
        <!-- 显示中转的机票信息 -->
        <el-row v-else type="flex" align="middle" class="flight-segment-type-group">
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
                <span>{{ data[0].departureAirportName }} {{ data[0].departureTerminal }}</span>
              </el-col>
              <el-col :span="8" class="flight-time">
                <!--中转航班详情窗口-->
                <el-popover class="is-card-show"
                            placement="top"
                            width="600"
                            trigger="hover">
                  <div class="detail-contents">
                    <div class="flight-item-contents">
                      <div class="segment-header">
                            <span class="airline-line">
                              {{ data[0].departureCityName }}<span class="arrow"></span>{{ data[1].arrivalCityName }}
                            </span>
                        <div class="duration">总时长{{ totalDuration }}</div>
                      </div>
                      <div class="flight-item ">
                        <div class="airline">
                          <img class="logo" :src="data[0].airlineLogo" alt="">
                          <span class="name">{{ data[0].airlineName }} {{ data[0].flightNo }}</span>
                          <span class="delay-time">平均到达时间17:27</span>
                          <el-popover class="info"
                                      placement="right-start"
                                      width="240"
                                      trigger="hover">
                            <div class="info-des">
                              <p>廊桥率：100.00%</p>
                              <p>准点率：航班最近30次准点出发/到达情况。</p>
                              <p>廊桥率：航班停靠航站楼廊桥的概率。</p>
                            </div>
                            <el-button slot="reference">说明</el-button>
                          </el-popover>
                        </div>
                        <div class="airline-detail">
                          <div class="trip-box">
                            <div class="depart">
                              <span class="dot"></span>
                              <span class="date-time ">{{ data[0].departureDate }}</span>
                              <span class="time">{{ data[0].departureDatetime | dateFormat }}</span>&nbsp;&nbsp;
                              <span class="airport">
                                <div>{{ data[0].departureAirportCode }} {{
                                    data[0].departureAirportName
                                  }}&nbsp; {{ data[0].departureTerminal }}</div>
                              </span>
                            </div>
                            <div class="fly-time">飞行 {{ data[0].grossTime }}</div>
                            <div class="arrive">
                              <span class="dot"></span>
                              <span class="date-time">{{ data[0].arrivalDate }}</span>
                              <span class="time">{{ data[0].arrivalDatetime | dateFormat }}</span>&nbsp;&nbsp;
                              <span class="airport">
                                <div>{{ data[0].arrivalAirportCode }} {{
                                    data[0].arrivalAirportName
                                  }}&nbsp;{{ data[0].arrivalTerminal }}</div>
                              </span>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="detail-drop-down">
                        <div class="main-info">
                          <el-collapse accordion>
                            <el-collapse-item>
                              <template slot="title">
                                <i class="header-icon el-icon-info"></i>
                                <span class="city">{{ data[1].departureCityName }}&nbsp;中转</span>
                                <span class="stay-time">停留{{ timeConsuming }}</span>&nbsp;
                              </template>
                              <div class="detail-content">
                                <div class="detail-item">
                                  <span class="name bad">跨天中转</span>
                                  <span class="detail-info">
                              <span
                                class="detail-info">航班中转跨天，您可选择在机场候机室小憩，或提前休息室酒店候机。如在机场候机，请提前确认转机机场开放时间。</span>
                            </span>
                                </div>
                                <div class="detail-item">
                                  <span class="name bad">预订说明</span>
                                  <span
                                    class="detail-info"><span>本产品为航司特惠产品，如遇航司系统原因，导致出票失败，携程将会为您自动取消订单并全额退款。</span></span>
                                </div>
                              </div>
                            </el-collapse-item>
                          </el-collapse>
                        </div>
                      </div>
                      <div class="flight-item last">
                        <div class="airline">
                          <img class="logo" :src="data[1].airlineLogo" alt="">
                          <span class="name">{{ data[1].airlineName }} {{ data[1].flightNo }}</span>
                          <span class="delay-time">平均到达时间15:43</span>
                          <el-popover class="info"
                                      placement="right-start"
                                      width="240"
                                      trigger="hover">
                            <div class="info-des">
                              <p>廊桥率：100.00%</p>
                              <p>准点率：航班最近30次准点出发/到达情况。</p>
                              <p>廊桥率：航班停靠航站楼廊桥的概率。</p>
                            </div>
                            <el-button slot="reference">说明</el-button>
                          </el-popover>
                        </div>
                        <div class="airline-detail">
                          <div class="trip-box">
                            <div class="depart">
                              <span class="dot"></span>
                              <span class="date-time hightlight">{{ data[0].departureDate }}</span>
                              <span class="time">{{ data[1].departureDatetime | dateFormat }}</span>&nbsp;&nbsp;
                              <span class="airport">
                                <div>{{ data[1].departureAirportCode }} {{
                                    data[1].departureAirportName
                                  }}&nbsp; {{ data[1].departureTerminal }}</div>
                              </span>
                            </div>
                            <div class="fly-time">飞行 {{ data[1].grossTime }}</div>
                            <div class="arrive">
                              <span class="dot"></span>
                              <span class="date-time">{{ data[0].arrivalDate }}</span>
                              <span class="time">{{ data[1].arrivalDatetime | dateFormat }}</span>&nbsp;&nbsp;
                              <span class="airport">
                                <div>{{ data[1].arrivalAirportCode }} {{
                                    data[1].arrivalAirportName
                                  }}&nbsp;{{ data[1].arrivalTerminal }}</div>
                              </span>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>

                  <el-button slot="reference">
                    <span class="tips-top">中转{{ timeConsuming }}</span>
                    <div class="arrow-transfer">
                      <img src="@/assets/images/arrow-transfer.png">
                      <span>转1次</span>
                    </div>
                    <span class="tips-bottom">转{{ data[1].departureCityName }}{{ timeConsuminghm }}</span>
                  </el-button>
                </el-popover>
              </el-col>
              <el-col :span="8" class="flight-airport">
                <strong>{{ data[1].arrivalDatetime | dateFormat }}</strong>
                <span>{{ data[1].arrivalAirportName }} {{ data[1].arrivalTerminal }}</span>
              </el-col>
            </el-row>
          </el-col>
          <el-col :span="6" class="flight-info-right">
            ￥<span class="sell-price">700</span>起
          </el-col>
        </el-row>
      </div>
      <div class="flight-recommend" v-if="isShow">
        <!-- 隐藏的座位信息列表 -->
        <el-row type="flex" justify="space-between" align="middle">
          <el-col :span="4">低价推荐</el-col>
          <el-col :span="20">
            <el-row type="flex" justify="space-between" align="middle" class="flight-sell"
                    v-for="(item, index) in cabinJSON.cabins" :key="index">
              <el-col :span="16" class="flight-sell-left">
                <span>{{ item.cabinName }}</span> | 退改签规则| 行李规定| 餐食说明| <span
                class="abbr">提供行程单</span>
              </el-col>
              <el-col :span="5" class="price">
                ￥{{ item.cabinPrice.adultSalePrice }}
              </el-col>
              <el-col :span="3" class="choose-button">
                <el-button type="warning" size="mini" @click="handleToOrder(data,item)">
                  选定
                </el-button>
                <p>剩余：{{ item.seatLeftNum }}</p>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
      </div>
    </div>
  </el-card>


</template>

<script>

import dayjs from "dayjs";

export default {
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
  data() {
    return {
      isShow: false,
      isCardShow: false,
      gridData: this.data
    }
  },
  filters: {
    dateFormat(data) {
      return dayjs(data).format("HH:mm")
    },
  },
  computed: {
    cabinJSON() {
      return JSON.parse(this.data[0].cabin)
    },
    // 直达航班总时长
    rankTime() {
      // 到达时间
      const arr = dayjs(this.data[0].arrivalDatetime).format("HH:mm").split(":");
      // 出发时间
      const dep = dayjs(this.data[0].departureDatetime).format("HH:mm").split(":");

      if (arr[0] < dep[0]) {
        arr[0] = parseInt(arr[0]) + 24;
      }

      const countMin = (arr[0] * 60 + +arr[1]) - (dep[0] * 60 + +dep[1]);

      const hours = Math.floor(countMin / 60);
      const min = countMin % 60;

      return `${hours}时${min}分钟`;
    },
    // 中转航班中转时长
    timeConsuming() {
      // 第二趟起飞时间
      const arr = dayjs(this.data[1].departureDatetime).format("HH:mm").split(":");
      // 第一趟到达时间
      const dep = dayjs(this.data[0].arrivalDatetime).format("HH:mm").split(":");

      if (arr[0] < dep[0]) {
        arr[0] += 24;
      }

      const countMin = (arr[0] * 60 + +arr[1]) - (dep[0] * 60 + +dep[1]);

      const hours = Math.floor(countMin / 60);
      const min = countMin % 60;

      return `${hours}时${min}分`;
    },
    // 中转航班中转时长
    timeConsuminghm() {
      // 第二趟起飞时间
      const arr = dayjs(this.data[1].departureDatetime).format("HH:mm").split(":");
      // 第一趟到达时间
      const dep = dayjs(this.data[0].arrivalDatetime).format("HH:mm").split(":");

      if (arr[0] < dep[0]) {
        arr[0] += 24;
      }

      const countMin = (arr[0] * 60 + +arr[1]) - (dep[0] * 60 + +dep[1]);

      const hours = Math.floor(countMin / 60);
      const min = countMin % 60;

      return `${hours}h${min}m`;
    },
    // 中转航班总时长
    totalDuration() {
      // 第二趟到达时间
      const arr = dayjs(this.data[1].arrivalDatetime).format("HH:mm").split(":");
      // 第一趟出发时间
      const dep = dayjs(this.data[0].departureDatetime).format("HH:mm").split(":");

      if (arr[0] < dep[0]) {
        arr[0] += 24;
      }

      const countMin = (arr[0] * 60 + +arr[1]) - (dep[0] * 60 + +dep[1]);

      const hours = Math.floor(countMin / 60);
      const min = countMin % 60;

      return `${hours}时${min}分`;
    }
  },
  watch: {},
  created() {
  },
  methods: {
    // 切换隐藏座位信息
    handleShow() {
      this.isShow = !this.isShow;
    },
    // 跳转到订单页
    handleToOrder(data, item) {

      console.log("这是航班数据:", data);
      console.log("这是座位数据:", item);

      let tokenTrue = true;

      // 获取用户token
      this.store = JSON.parse(localStorage.getItem("store") || `[]`)
      const token = this.store.user.userInfo.token

      //判断token是否为空
      if (token == []) {
        tokenTrue = false;
      }

      // 验证不通过，直接登录
      if (!tokenTrue) {
        this.$router.push({
          path: "/user/login",
        });
        this.$message({
          message: '请先登录哦！',
          type: 'warning'
        });
        return;
      }

      // this.$router.push({
      //   path: "/air/order",
      //   query: {
      //     id: this.data.id, //航班ID
      //     seat_xid: item.seat_xid // 座位ID
      //   }
      // })
      this.$router.push({
        path: "/air/order",
        query: {
          flightsData: eval(JSON.stringify(data)), // 航班信息 这个是数组
          cabin: item             // 选择座位的信息 这个是对象
        }
      })
    },
  },
  mounted() {
    // console.log("====================")
    // seatInfos 转 JSON
    // const seatInfos = JSON.parse(this.data.seatInfos);
    // console.log(seatInfos);
    // console.log(this.cabinJSON[0].cabins)

    // 获取用户token
    // this.store = JSON.parse(localStorage.getItem("store") || `[]`)
    // const token = this.store.user.userInfo.token
    // console.log(token);
  }
}
</script>

<style scoped lang="less">
/deep/ .el-card__body {
  padding: 0;
}

/deep/ .is-hover-shadow {
  margin-bottom: 10px !important;
}

.flight-item {
  //border: 1px #ddd solid;
  //margin-bottom: 5px;

  .flight-info {
    padding: 15px;
    cursor: pointer;

    .airline-name {
      font-size: 14px;
      color: #333333;
    }

    .flight-number {
      font-size: 12px;
      color: #0086f6;
    }

    > div {
      &:first-child, &:last-child {
        text-align: center;
      }
    }
  }

  // 中转航班
  .flight-segment-type-group {
    padding: 15px;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 95px;


    .airline-item {
      display: block;
      align-items: flex-end;
      justify-content: left;
      line-height: 56 / 2px;


      img {
        width: 18px;
        height: 18px;
        overflow-clip-margin: content-box;
        overflow: clip;
      }

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

    .flight-info-center {
      text-align: center;
      margin-left: -20px;

      .flight-airport {

        strong {
          display: block;
          font-size: 24px;
          font-weight: normal;
          color: #333333;
        }

        span {
          font-size: 12px;
          color: #333333;
        }
      }

      .flight-time {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 12px;

        .is-card-show {
          height: 65px;
          margin-top: -10px;

          /deep/ .el-button {
            height: 48px;
            display: inline-block;
            white-space: nowrap;
            cursor: pointer;
            border: none;
            //color: #606266;
            -webkit-appearance: none;
            text-align: center;
            padding: 0;
            font-size: 12px;

            &:hover {
              background: none;
            }

            &:focus {
              background: none;
            }

            .arrow-transfer {
              margin-top: 20px;

              img {
                width: 105px;
                height: 22px;
              }

              span {
                width: 50%;
                display: inline-block;
                padding: 10px 0;
                color: #398efe;
                position: absolute;
                left: 74px;
                top: 17px;
                font-size: 12px;
              }
            }

            .tips-top {
              width: 80%;
              margin-top: 8px;
            }

            .tips-bottom {
              margin-top: 44px;
              width: 80%;
            }
          }
        }
      }
    }
  }


  .flight-info-center {
    padding-left: 15px;
    text-align: center;

    .flight-airport {
      strong {
        display: block;
        font-size: 24px;
        font-weight: normal;
        color: #333333;
      }

      span {
        font-size: 12px;
        color: #333333;
      }
    }

    .flight-time {
      display: flex;
      align-items: center;
      justify-content: center;

      span {
        width: 86%;
        color: #666666;
        position: absolute;
        left: 31px;
        top: 1px;
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

    &:last-child {
      border-bottom: none;
    }

    .flight-sell-left {
      font-size: 12px;

      span {
        color: green;
      }

      .abbr {
        cursor: help;
        color: black;
        border-bottom: 1px dotted #666;

        &:hover {
          position: absolute;
          background: url("assets/images/travel-itinerary.png");
        }
      }
    }

    .price {
      font-size: 20px;
      color: orange;
    }

    .choose-button {
      text-align: center;
      color: #666;

      //button {
      //  display: block;
      //  width: 100%;
      //  margin-bottom: 5px;
      //}

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

      //button {
      //  background-image: -o-linear-gradient(280deg, #26c28d 0, #00a876 100%);
      //  background-image: linear-gradient(170deg, #26c28d, #00a876);
      //  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr="#26C28D",endColorstr="#00A876",GradientType=1);
      //  border: #00a876
      //}


    }
  }
}

.detail-contents {
  height: auto;

  .flight-item-contents {
    padding: 15px;

    .segment-header {
      display: inline-flex;
      line-height: 30px;

      .airline-line {
        font-size: 20px;
        color: #333;
        font-weight: 600;
        margin-right: 16px;

        .arrow {
          margin: 0 4px;
          width: 12px;
          height: 4px;
          display: inline-block;
          vertical-align: middle;
          background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAwAAAAECAYAAAC6Jt6KAAAABGdBTUEAALGPC/xhBQAAAFJJREFUCB1jYMADzl5/NvPq//9syEoYz15/2oAsgMz+/5+hnpGR4ThQT7CJluhzkBzjmWtP/yMrwspmZHzOxMAcbKQpfpw4DSCTGRh+MTAy5gIAEoka8NTjwToAAAAASUVORK5CYII=);
          background-size: 100% 100%;
        }
      }

      .duration {
        font-size: 20px;
        color: #999;
      }
    }

    .flight-item {
      width: 100%;
      padding-bottom: 16px;
      padding-top: 20px;

      .airline {
        display: inline-flex;
        line-height: 20px;

        .logo {
          width: 21px;
          height: 21px;
        }

        .name {
          font-size: 14px;
          color: #354047;
          margin-left: 8px;
        }

        .delay-time {
          margin-left: 8px;
          color: #999;
        }

        .info {
          margin-left: 8px;
          cursor: pointer;
          position: relative;

          p {
            font-size: 12px;
            background: #26c28d;
          }

          /deep/ .el-button {
            display: inline-block;
            white-space: nowrap;
            cursor: pointer;
            color: #0086f6;
            border: none;
            -webkit-appearance: none;
            text-align: center;
            padding: 0;
            font-size: 12px;
            line-height: 16px;

            &:hover {
              background: none;
            }

            &:focus {
              background: none;
            }
          }
        }
      }

      .airline-detail {
        width: 100%;
        display: inline-block;
        vertical-align: top;

        .trip-box {
          .depart {
            vertical-align: middle;
            line-height: 18px;
            position: relative;
            display: inline-flex;

            .dot {
              width: 6px;
              height: 6px;
              display: inline-block;
              background: #99aeca;
              position: absolute;
              bottom: 6px;
            }

            .date-time {
              margin-left: 22px;
              margin-right: 8px;
            }

            .time {
              font-size: 14px;
              color: #354047;
            }

            .airport {
              max-width: 300px;
              word-wrap: break-word;
              word-break: break-all;
            }

            .hightlight {
              color: #f60;
            }
          }

          .arrive {
            vertical-align: middle;
            line-height: 18px;
            position: relative;
            display: inline-flex;

            .dot {
              width: 6px;
              height: 6px;
              display: inline-block;
              background: #99aeca;
              position: absolute;
              bottom: 6px;
            }

            .date-time {
              margin-left: 22px;
              margin-right: 8px;
            }

            .time {
              font-size: 14px;
              color: #354047;
            }

            .airport {
              max-width: 300px;
              word-wrap: break-word;
              word-break: break-all;
            }
          }

          .fly-time {
            position: relative;
            border-left: 6px solid #99aeca;
            padding: 17px 0 17px 16px;
            font-size: 12px;
            color: #999;
            line-height: 52px;
          }
        }
      }
    }

    .detail-drop-down {
      padding: 8px 0;

      .main-info {
        line-height: 14px;
        vertical-align: middle;
        position: relative;
        font-size: 12px;

        .city {
          font-size: 12px;
          color: #333;
          margin-left: 5px;
        }

        .stay-time {
          margin-left: 8px;
          margin-right: 13px;
        }

        .detail-content {
          display: block;
          padding-left: 13px;

          .detail-item {
            margin-top: 8px;
            line-height: 14px;

            .bad {
              color: #f60;
            }

            .detail-info {
              font-size: 12px;
              color: #999;
              margin-left: 8px;

              .detail-info {
                font-size: 12px;
                color: #999;
                margin-left: 8px;
              }
            }
          }
        }
      }
    }
  }
}

.info-des {
  font-size: 12px;
  color: #666666;
}
</style>
