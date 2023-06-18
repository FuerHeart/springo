<template>
  <div class="main">
    <section class="container">
      <el-row type="flex" justify="space-between">

        <!-- 顶部过滤列表 -->
        <div class="flights-content">

          <!-- 过滤条件 -->
          <FlightsFilters
            :cacheFlight="cacheFlightsData"
            @setDataList="setDataList"/>
          <!--这个不一定是要叫:data 他可以是:anything-->

          <!-- 航班头部布局 -->
          <FlightsListHead/>

          <!--          <el-empty v-if="dataList.length === 0" description="您查询的 深圳(SZX) 至 北京(BJS)（出发日期：2023-05-02）-->
          <!--    的机票可能因无航班或航班座位已售完导致暂时无法查询到对应价格。 建议您更换旅行日期或旅行城市重新查询"></el-empty>-->

          <!-- 航班信息 -->
          <FlightsItem
            v-for="(item, index) in dataList"
            :key="index"
            :data="item"/>


          <!--          &lt;!&ndash;中转组合&ndash;&gt;-->
          <!--          <FlightSegmentGroup-->
          <!--            v-for="(item, index) in dataList"-->
          <!--            :key="index"-->
          <!--            :data="item"/>-->


          <!-- 停留时间过长提示 -->
          <el-dialog
            title="温馨提示"
            :visible.sync="showTimeoutMessage"
            width="35%"
            :close-on-click-modal="false"
            :show-close="false"
            :before-close="handleClose">
            <span>您的停留时间过长，航班及价格信息可能发生变动，正在为您查询最新价格。</span>
            <span slot="footer" class="dialog-footer">
    <el-button class="ps-button" type="primary" @click="resetTimer">我知道啦</el-button>
        </span>
          </el-dialog>


          <!-- 分页 -->
          <!-- size-change: 显示条数切换触发 -->
          <!-- current-change: 切换页数时候触发 -->
          <!-- current-page: 当前页数 -->
          <!-- total: 总条数 -->
          <el-pagination style="margin: 20px 0"
                         @size-change="handleSizeChange"
                         @current-change="handleCurrentChange"
                         :current-page="pageIndex"
                         :page-sizes="[10, 20, 30, 40, 50, 60, 70]"
                         :page-size="pageSize"
                         layout="total, sizes, prev, pager, next, jumper"
                         :total="total">
          </el-pagination>

        </div>

        <!-- 侧边栏 -->
        <FlightsAside/>
      </el-row>
    </section>
  </div>
</template>

<script>

import FlightsListHead from "@/components/air/flightsListHead.vue"
import FlightsItem from "@/components/air/flightsItem.vue"
import FlightSegmentGroup from "@/components/air/FlightSegmentGroup.vue"
import FlightsFilters from "@/components/air/flightsFilters.vue"
import FlightsAside from "@/components/air/flightsAside.vue"

export default {
  data() {
    return {
      // flightsData 航班的总数据
      flightsData: [],

      // 缓存对象，缓存对象一旦赋值之后不会被修改
      cacheFlightsData: [],

      pageIndex: 1, // 默认的当前页
      pageSize: 10, // 默认的条数
      total: 0, // 默认的总条数

      dataList: [], // 存当前页的数据

      directFlightsData: [],

      timeoutDuration: 60000, // 停留时间阈值，单位为毫秒
      showTimeoutMessage: false, // 是否显示停留时间过长的提示
    }
  },

  components: {
    FlightsListHead,
    FlightsItem,
    FlightSegmentGroup,
    FlightsFilters,
    FlightsAside
  },

  // watch是可以监听组件下所有属性
  watch: {
    $route() {
      // 请求机票列表
      this.getData();
    }
  },

  methods: {
    // 切换条数时候触发
    handleSizeChange(value) {
      this.pageSize = value;
      this.setDataList();
    },

    // 切换页数时候触发
    handleCurrentChange(value) {
      this.pageIndex = value;
      this.setDataList();
    },

    // 设置机票列表的数据
    // 给当前页面使用，没有传参数
    // 在过滤组件中调用时候会传过滤后的数组
    setDataList(arr) {
      if (arr) {
        this.flightsData = arr;
        // 初始化分页数据
        this.pageIndex = 1;
        this.total = arr.length;
      }

      // 在总列表中截取出当前页的数据
      this.dataList = this.flightsData.slice(
        (this.pageIndex - 1) * this.pageSize,
        this.pageIndex * this.pageSize
      )
    },

    // 获取列表的数据
    getData() {
      // 请求机票列表
      this.$axios({
        url: "/flight/single",
        method: "POST",
        data: {
          departure: this.$route.query.departureCode,
          arrival: this.$route.query.arrivalCode,
          departureDate: this.$route.query.departureDate,
        },
      }).then(res => {
        console.log(res.data)
        // 总数据，flightsData。flights是会修改的
        //Map转集合就是去 值的集 key这个可以完全不要的
        //加强for 默认是取 key集 直接想数组那样 map[key] 取值 然后 push 到数组就行
        let data = res.data.result;
        // console.log(data)
        for (let key in data) {
          this.flightsData.push(data[key])
        }

        //this.xx = directArr 这个你自己斟酌一下就OK
        // console.log(directArr)

        //this.flightsData = res.data.result;

        // this.flightsData = Object.assign(this.flightsData, directArr);
        // console.log("这是直达数据================↓")
        // this.directFlightsData = directArr
        // console.log(this.directFlightsData)

        // console.log("这是中转数据================↓")
        // console.log("这是总数据================↓")
        // 按长度排序，把直达航班放前面，中转航班放后面
        this.flightsData.sort((a, b) => a.length - b.length);
        // console.log(this.flightsData)


        // 缓存数据，一旦被赋值之后不能修改
        this.cacheFlightsData = [...this.flightsData]

        // 切割数据做分页，每10个切割
        this.dataList = this.flightsData.slice(0, 10);

        // console.log(this.dataList)

        // 初始化分页数据
        this.total = this.flightsData.length;
        this.pageIndex = 1;
      })
    },
    startTimer() {
      setTimeout(() => {
        this.showTimeoutMessage = true;
      }, this.timeoutDuration);
    },
    resetTimer() {
      clearTimeout(this.timerId);
      this.showTimeoutMessage = false;
      this.startTimer();
      this.getData();
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          // clearTimeout(this.timerId);
          // this.startTimer();
          done();
          console.log(done)
          console.log("确定")
        })
        .catch(_ => {
        });
    }
  },

  mounted() {
    // 请求机票列表
    this.getData();
    this.startTimer();
    // 获取token
    // const store = JSON.parse(localStorage.getItem("store"));
    // console.log(store.user.userInfo.token)

    // 自定义请求头
    // headers: {'token': store.user.userInfo.token},
    // console.log(this.$route.query)
  }
}
</script>

<style scoped lang="less">
//.main {
//  background-color: #eef1f6;
//}
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

.container {
  width: 1000px;
  margin: 0 auto;
  min-height: 100vh;
  padding-top: 20px;
}

.flights-content {
  width: 745px;
  font-size: 14px;
}

.aside {
  width: 240px;
}

.ps-button {
  width: 100%;
}
</style>
