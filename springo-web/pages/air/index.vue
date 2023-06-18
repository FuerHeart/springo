<template>
  <section class="container">
    <h2 class="air-title"><span class="iconfont iconfeiji"></span>
      <i>国内机票</i>
    </h2>

    <!-- 搜索广告栏 -->
    <el-row type="flex" justify="space-between">
      <!-- 搜索表单 -->
      <SearchForm/>

      <!-- banner广告 -->
      <div class="sale-banner">
        <!-- <img src="http://157.122.54.189:9093/images/pic_sale.jpeg"> -->
        <img src="@/assets/images/pic_sale.jpeg">
      </div>
    </el-row>

    <el-row v-if="history.length">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>您搜索过的路线</span>
          <el-button @click="empty" style="float: right; padding: 3px 0" type="text"><i
            class="el-icon-delete-solid"></i>清空历史
          </el-button>
        </div>
        <nuxt-link
          v-for="(item, index) in history"
          :key="index"
          :to="`/air/flights?departure=${item.departure}&departureCode=${item.departureCode}&arrival=${item.arrival}&arrivalCode=${item.arrivalCode}&departureDatetime=${item.departureDatetime}`">
          <el-col :span="6" :offset="6" type="flex"
                  justify="space-between"
                  align="middle"
                  class="history-item">
            <div class="air-info">
              <div class="to-from">{{ item.departure }} - {{ item.arrival }}</div>
              <div class="to-date">{{ item.departureDatetime }}</div>
            </div>
          </el-col>
        </nuxt-link>
      </el-card>
    </el-row>

    <h2 class="air-sale-title">
      <span class="iconfont icontejiajipiao"></span>
      <i>为您推荐</i>
    </h2>

    <!--  新轮播  -->
    <el-carousel :interval="4000" type="card" height="235px">
      <el-carousel-item v-for="(item, index) in sales" :key="index">
        <nuxt-link
          :to="`/air/flights?departure=${item.departure}&departureCode=${item.departureCode}&arrival=${item.arrival}&arrivalCode=${item.arrivalCode}&departureDatetime=${item.departureDatetime}`">
          <div class="carousel-box">
            <img :src="item.cover"/>
            <el-row class="layer-item" type="flex" justify="space-between">
              <div class="title-org">
                <div class="title-org-des">{{ item.departCity }}-{{ item.destCity }}</div>
                <div class="title-org-date">{{ item.departDate }}</div>
              </div>
              <span class="title-price">￥{{ item.price }}<span class="title-option-des">起</span></span>
            </el-row>
          </div>
        </nuxt-link>
      </el-carousel-item>
    </el-carousel>

    <!-- 广告 -->
    <el-row type="flex" class="statement">
      <el-col :span="8">
        <i class="iconfont iconweibiaoti-_huabanfuben" style="color:#409EFF;"></i>
        <span>100%航协认证</span>
      </el-col>
      <el-col :span="8">
        <i class="iconfont iconbaozheng" style="color:green;"></i>
        <span>出行保证</span>
      </el-col>
      <el-col :span="8">
        <i class="iconfont icondianhua" style="color:#409EFF;"></i>
        <span>7x24小时服务</span>
      </el-col>
    </el-row>

  </section>
</template>

<script>
import SearchForm from "@/components/air/searchForm.vue";

export default {
  data() {
    return {
      // sales: [],
      history: [],
      sales: [
        {
          cover: 'https://pic.c-ctrip.com/flight/fuzzy/WUH/640.jpg',
          departure: '广州',
          departureCode: "CAN",
          arrival: '昆明',
          arrivalCode: "KMG",
          departDate: '2023-4-20',
          price: '579'
        },
        {
          cover: 'https://pic.c-ctrip.com/flight/fuzzy/HET/640.jpg',
          departure: '北京',
          departureCode: "BJS",
          arrival: '西安',
          arrivalCode: "SIA",
          departDate: '2023-4-21',
          price: '846'
        },
        {
          cover: 'https://pic.c-ctrip.com/flight/fuzzy/SHA/640.jpg',
          departure: '成都',
          departureCode: "CTU",
          arrival: '昆明',
          arrivalCode: "KMG",
          departDate: '2023-4-29',
          price: '789'
        },
      ],
    }
  },

  components: {
    SearchForm,
  },

  mounted() {
    // 加载本地的搜索记录
    this.history = JSON.parse(localStorage.getItem("airs") || `[]`)
    // 请求推荐列表的数据
    this.$axios({
      url: "/airs/sale"
    }).then(res => {
      const {data} = res.data;
      this.sales = data;
    })
  },
  methods: {
    empty() {
      localStorage.removeItem("airs");
      this.history = [];
    }
  }
}
</script>

<style scoped lang="less">
.sale-banner {
  > img {
    width: 620px;
    height: 370px;
  }
}

.air-sale {
  border: 1px #ddd solid;
  padding: 20px;
  margin-bottom: 50px;

  .air-sale-pic {
    > div {
      width: 225px;
      height: 140px;
      position: relative;
      overflow: hidden;

      img {
        width: 100%;
      }

      .layer-bar {
        position: absolute;
        bottom: 0;
        left: 0;
        background-color: rgba(34, 59, 97, .8);
        color: #fff;
        height: 30px;
        line-height: 30px;
        width: 100%;
        box-sizing: border-box;
        padding: 0 15px;
        font-size: 14px;

        .title-option-des {
          font-size: 5px !important;
        }

        //position: absolute;
        //bottom: 0;
        //left: 0;
        //right: 0;
        //height: 60px;
        //-webkit-transition: .5s;
        //-o-transition: .5s;
        //transition: .5s;

        span:last-child {
          font-size: 18px;
        }
      }
    }
  }
}

.air-sale-group {
  margin-top: 20px;
  padding-top: 8px;
  border-right: 1px #eee solid;

  &:last-child {
    border-right: none;
  }

  .air-sale-row {
    font-size: 12px;
    color: #666;
    margin-bottom: 8px;

    .air-sale-price {
      color: orange;
      font-size: 20px;
    }
  }
}

.container {
  width: 1000px;
  margin: 0 auto;
}

.air-title {
  margin: 15px 0;
  font-size: 20px;
  font-weight: normal;
  color: orange;

  span {
    font-size: 20px;
  }
}

.statement {
  margin: 15px 0;
  border: 1px #ddd solid;
  background: #f5f5f5;
  height: 58px;
  padding: 10px 0;
  box-sizing: border-box;

  > div {
    text-align: center;
    line-height: 38px;
    border-right: 1px #ddd solid;

    &:last-child {
      border-right: none;
    }

    * {
      vertical-align: middle;
    }

    i {
      font-size: 30px;
    }
  }
}

.air-sale-title {
  margin: 15px 0;
  font-size: 20px;
  font-weight: normal;
  color: orange;

  span {
    font-size: 20px;
  }
}

.history-item {
  width: 10%;
  font-size: 16px;
  border-bottom: 1px #eee solid;
  font-weight: bolder;
  line-height: 20px;
  margin: 10px 0;

  &:last-child {
    border: none;
  }

  .to-from {
    margin-bottom: 5px;
  }

  .to-date {
    font-size: 12px;
    color: #999;
    font-weight: normal;
  }

  span {
    color: #fff;
    display: block;
    padding: 2px 10px;
    background: orange;
    border-radius: 4px;
    font-size: 12px;
    cursor: pointer;
  }
}


.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.clearfix span {
  color: orange;
}

.box-card {
  width: 100%;
  margin-top: 20px;
}

/deep/ .el-card__body, .el-main {
  padding: 0 10px;
}

.history-item:hover {
  border-radius: 10px;
  color: orange !important;
}

.el-carousel__item {
  .carousel-box {
    position: relative;
    width: 100%;
    height: 100%;
    overflow: hidden;

    img {
      position: absolute;
      width: 100%;
      height: 100%;
      object-fit: cover;
      object-position: center center;
      transition: all .5s;

      &:hover {
        transform: scale(1.2);
      }
    }

    &::after {
      content: '';
      width: 100%;
      height: 27%;
      position: absolute;
      left: 0;
      bottom: 0;
      background-color: rgba(34, 59, 97, .8);
      transition: .5s;
    }

    &:hover::after {
      height: 100%;
      transition: all .5s;
      -webkit-transition: .5s;
      -o-transition: .5s;
    }

    &:hover .title-org-des {
      position: absolute;
      height: 100%;
      width: 100%;
      bottom: 120px;
      text-align: left;
      transition: all 5s !important;
    }

    &:hover .title-org-date {
      position: absolute;
      width: 100%;
      height: 100%;
      bottom: 85px;
      text-align: left;
    }

    .layer-item {
      position: absolute;
      width: 100%;
      height: 60px;
      left: 0;
      bottom: 0;
      color: white;
      padding: 0 50px;
      z-index: 999;

      .title-org {
        .title-org-des {
          font-size: 24px;
          font-weight: bolder;
        }

        .title-org-date {
          font-size: 16px;
        }
      }

      .title-price {
        font-size: 36px;
      }
    }
  }
}
</style>
