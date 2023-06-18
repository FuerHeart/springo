<template>
  <div class="aside">
    <div class="statement">
      <el-row type="flex" justify="space-between" class="statement-list">
        <el-col :span="8">
          <i class="iconfont iconweibiaoti-_huabanfuben" style="color:#409EFF;"></i>
          <span>航协认证</span>
        </el-col>
        <el-col :span="8">
          <i class="iconfont iconbaozheng" style="color:green;"></i>
          <span>出行保证</span>
        </el-col>
        <el-col :span="8">
          <i class="iconfont icondianhua" style="color:#409EFF;"></i>
          <span>7x24</span>
        </el-col>
      </el-row>
      <p class="service-tel">
        免费客服电话：19525999931转2
      </p>
    </div>

    <div class="history">
      <h5>历史查询</h5>
      <!--          <nuxt-link :to="{name: 'news', params: { newsId: 3306 }}">params传参</nuxt-link>-->
      <!-- 搜索记录的列表 -->
      <nuxt-link
        v-for="(item, index) in history" :key="index"
        :to="`/air/flights?departure=${item.departure}&departureCode=${item.departureCode}&arrival=${item.arrival}&arrivalCode=${item.arrivalCode}&departureDate=${item.departureDate}`">
        <el-row type="flex"
                justify="space-between"
                align="middle"
                class="history-item">
          <div class="air-info">
            <div class="to-from">{{ item.departure }} - {{ item.arrival }}</div>
            <p>{{ item.departureDate }}</p>
          </div>
          <span>选择</span>
        </el-row>
      </nuxt-link>
    </div>
    <div class="sales-banner">
      <img src="https://sales.mafengwo.net/img/85/b4/3d21c20b52233e2e6de679e770c80a55.jpeg" alt="">
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      history: []
    }
  },

  mounted() {
    // 加载本地的搜索记录
    this.history = JSON.parse(localStorage.getItem("airs") || `[]`)
    // console.log(this.history)
  }
}
</script>

<style scoped lang="less">
.statement {
  border: 1px #ddd solid;

  .statement-list {
    padding: 10px 0;

    > div {
      text-align: center;

      i {
        display: block;
        font-size: 40px;
      }

      span {
        font-size: 12px;
      }
    }
  }

  .service-tel {
    height: 32px;
    line-height: 32px;
    padding: 0 10px;
    background: #f6f6f6;
    font-size: 14px;
  }
}

.history {
  border: 1px #ddd solid;
  padding: 10px;
  margin-top: 10px;

  h5 {
    font-size: 16px;
    font-weight: normal;
    padding-bottom: 10px;
    border-bottom: 1px #eee solid;
  }

  .history-item {
    padding: 10px 0;
    font-size: 14px;
    border-bottom: 1px #eee solid;

    &:last-child {
      border: none;
    }

    .to-from {
      margin-bottom: 5px;
    }

    p {
      font-size: 12px;
      color: #666;
    }

    span {
      color: #fff;
      display: block;
      padding: 2px 10px;
      border-radius: 5px;
      font-size: 12px;
      cursor: pointer;
      font-weight: 600;
      background-image: -webkit-gradient(linear, left top, right top, from(#ffa50a), to(#f70));
      background-image: -o-linear-gradient(left, #ffa50a, #f70);
      background-image: linear-gradient(90deg, #ffa50a, #f70);
      color: #fff;
      filter: progid:DXImageTransform.Microsoft.gradient(startColorstr="#ffa50a",endColorstr="#ff7700",GradientType=1);
    }
  }
}

.sales-banner {
  margin-top: 10px;

  img {
    width: 240px;
    height: 135px;
  }
}
</style>
