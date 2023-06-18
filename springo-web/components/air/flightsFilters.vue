<template>
  <div class="filters">
    <el-row type="flex" class="filters-top" justify="space-between" align="middle">
      <el-col :span="8">
        单程：{{ this.$route.query.departure }} - {{ this.$route.query.arrival }} /
        {{ this.$route.query.departureDate }}
      </el-col>
      <el-col :span="4">
        <el-select size="mini" v-model="airport" placeholder="起飞机场" @change="handleAirport">
          <el-option
            v-for="(item, index) in departureAirportName"
            :key="index"
            :label="item"
            :value="item"
          >
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="4">
        <el-select size="mini" v-model="flightTimes" placeholder="起飞时间" @change="handleFlightTimes">
          <el-option
            v-for="(item, index) in departureDatetime"
            :key="index"
            :label="item"
            :value="item"
          >
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="4">
        <el-select size="mini" v-model="company" placeholder="航空公司" @change="handleCompany">
          <el-option
            v-for="(item, index) in companies"
            :key="index"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="4">
        <el-select size="mini" v-model="airSize" placeholder="机型" @change="handleAirSize">
          <el-option
            v-for="(item, index) in ariSizeList"
            :key="index"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
      </el-col>
    </el-row>
    <div class="filter-cancel">
      筛选：
      <el-button
        type="primary"
        round
        plain
        size="mini"
        @click="handleFiltersCancel">
        撤销
      </el-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      airport: "",        // 机场
      flightTimes: "",    // 出发时间
      company: "",        // 航空公司
      airSize: "",        // 机型大小

      // flights: {},

      // 过滤航班
      companies: [],
      departureAirportName: [],
      departureDatetime: [],
      ariSizeList: [],
    }
  },

  // props: {
  //   cacheFlight: []
  // },

  props: {
    cacheFlight: {
      type: Array,
      default: []
    }
  },

  watch: {
    cacheFlight(cur, old) {
      if (cur != null) {
        this.cacheFlight = cur
      }
      // console.log("这里是子组件的值↓========================")
      // console.log(this.cacheFlight)

      let newArr = []
      let departureAirportName = []
      let departureDatetime = []
      let ariSizeListArr = []

      this.cacheFlight.map(e => {
        // 起飞城市
        if (!departureAirportName.includes(e[0].departureAirportName)) {
          departureAirportName.push(e[0].departureAirportName)
        }
        // 起飞时间
        if (!departureDatetime.includes(e[0].departureDatetime)) {
          departureDatetime.push(e[0].departureDatetime)
        }
        // 航空公司
        if (!newArr.includes(e[0].airlineName)) {
          newArr.push(e[0].airlineName)
        }
        // 机型
        if (!ariSizeListArr.includes(e[0].planeSize)) {
          ariSizeListArr.push(e[0].planeSize)
        }
      })

      /*let map = new Map();
      this.cacheFlight.forEach(item =>{
        if (!map.has(item.airlineName)){
          map.set(item.airlineName,true)
          newArr.push(item.airlineName)
        }
      })*/

      /*let obj = {}
      this.cacheFlight.forEach(e =>{
        if (!obj[e.airlineName]){
          obj[e.airlineName] = true
          newArr.push(e.airlineName)
        }
      })*/
      //这三个方式选一个就行 都可以过滤去重

      this.companies = newArr
      this.departureAirportName = departureAirportName
      this.ariSizeList = ariSizeListArr
    }
  },
  methods: {
    // 选择机场时候触发
    handleAirport(value) {
      // this.data是缓存的大数据对象，不会被修改
      const arr = this.cacheFlight.filter(v => {
        return v[0].departureAirportName === value;
      });
      // 触发修改机票列表的方法 setDataList
      this.$emit("setDataList", arr);
    },

    // 选择出发时间时候触发
    handleFlightTimes(value) {
      // this.data是缓存的大数据对象，不会被修改
      const arr = this.cacheFlight.filter(v => {
        // 开始的小时数字
        const start = +v.arrivalDatetime.split(":")[0];

        return value.from <= start && value.to > start;
      });

      // 触发修改机票列表的方法 setDataList
      this.$emit("setDataList", arr);
    },

    // 选择航空公司时候触发
    handleCompany(value) {
      console.log(value)
      // this.data是缓存的大数据对象，不会被修改
      const arr = this.cacheFlight.filter(v => {
        return v[0].airlineName === value;
      });

      // 触发修改机票列表的方法 setDataList
      this.$emit("setDataList", arr);
    },

    // 选择机型时候触发
    handleAirSize(value) {
      // this.data是缓存的大数据对象，不会被修改
      const arr = this.cacheFlight.filter(v => {
        return v[0].planeSize === value;
      });

      // 触发修改机票列表的方法 setDataList
      this.$emit("setDataList", arr);
    },

    // 撤销条件时候触发
    handleFiltersCancel() {
      this.airport = "";
      this.flightTimes = "";
      this.company = "";
      this.airSize = "";
      // 触发修改机票列表的方法 setDataList
      this.$emit("setDataList", this.cacheFlight);
    },
  },
  mounted() {
  }
}
</script>

<style scoped lang="less">
.filters {
  margin-bottom: 20px;
}

.filters-top {
  > div {
    /deep/ .el-select {
      margin-left: 10px;
    }
  }
}

.filter-cancel {
  margin-top: 10px;
}
</style>
