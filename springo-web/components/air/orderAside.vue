<template>
  <div class="aside">
    <div class="air-info">
      <el-row type="flex" justify="space-between" class="info-top">
        <div>{{ data.flightsData[0].departureDate }}</div>
        <div v-if="data.flightsData.length === 1">{{ data.flightsData[0].departureCityName }} -
          {{ data.flightsData[0].arrivalCityName }}
        </div>
        <div v-else>{{ data.flightsData[0].departureCityName }} - {{ data.flightsData[1].arrivalCityName }}</div>
      </el-row>
      <!--    直达航班    -->
      <el-row v-if="data.flightsData.length === 1"
              type="flex"
              justify="space-between"
              align="middle"
              class="info-step">
        <el-col :span="7" class="flight-airport">
          <strong>{{ data.flightsData[0].departureDatetime  | dateFormat }}</strong>
          <span>{{ data.flightsData[0].departureAirportName }}{{ data.flightsData[0].departureTerminal }}</span>
        </el-col>
        <el-col :span="12" class="flight-time">
          <span>--- {{ data.flightsData[0].grossTime }} ---</span>
          <span>{{ data.flightsData[0].airlineName }} {{
              data.flightsData[0].flightNo
            }} | {{ data.cabin.cabinName }}</span>
        </el-col>
        <el-col :span="7" class="flight-airport">
          <strong>{{ data.flightsData[0].arrivalDatetime | dateFormat }}</strong>
          <span>{{ data.flightsData[0].arrivalAirportName }}{{ data.flightsData[0].arrivalTerminal }}</span>
        </el-col>
      </el-row>
      <div v-else>
        <!--    第一趟航班    -->
        <el-row
          type="flex"
          justify="space-between"
          align="middle"
          class="info-step">
          <el-col :span="7" class="flight-airport">
            <strong>{{ data.flightsData[0].departureDatetime  | dateFormat }}</strong>
            <span>{{ data.flightsData[0].departureAirportName }}{{ data.flightsData[0].departureTerminal }}</span>
          </el-col>
          <el-col :span="12" class="flight-time">
            <span>--- {{ data.flightsData[0].grossTime }} ---</span>
            <span>{{ data.flightsData[0].airlineName }} {{
                data.flightsData[0].flightNo
              }} | {{ data.cabin.cabinName }}</span>
          </el-col>
          <el-col :span="7" class="flight-airport">
            <strong>{{ data.flightsData[0].arrivalDatetime | dateFormat }}</strong>
            <span>{{ data.flightsData[0].arrivalAirportName }}{{ data.flightsData[0].arrivalTerminal }}</span>
          </el-col>
        </el-row>
        <!--    第二趟航班    -->
        <el-row style="margin-top: 10px"
          type="flex"
          justify="space-between"
          align="middle"
          class="info-step">
          <el-col :span="7" class="flight-airport">
            <strong>{{ data.flightsData[1].departureDatetime  | dateFormat }}</strong>
            <span>{{ data.flightsData[1].departureAirportName }}{{ data.flightsData[1].departureTerminal }}</span>
          </el-col>
          <el-col :span="12" class="flight-time">
            <span>--- {{ data.flightsData[1].grossTime }} ---</span>
            <span>{{ data.flightsData[1].airlineName }} {{
                data.flightsData[1].flightNo
              }} | {{ data.cabin.cabinName }}</span>
          </el-col>
          <el-col :span="7" class="flight-airport">
            <strong>{{ data.flightsData[1].arrivalDatetime | dateFormat }}</strong>
            <span>{{ data.flightsData[1].arrivalAirportName }}{{ data.flightsData[1].arrivalTerminal }}</span>
          </el-col>
        </el-row>
      </div>
    </div>
    <el-row type="flex" justify="space-between" class="info-bar">
      <span>订单总价</span>
      <span>金额</span>
      <span>数量</span>
    </el-row>
    <el-row type="flex" justify="space-between" class="info-bar">
      <span>成人机票</span>
      <span>￥{{ data.cabin.cabinPrice.adultSalePrice }}</span>
      <span>x{{ passengersNumber }}</span>
    </el-row>
    <el-row type="flex" justify="space-between" class="info-bar">
      <span>机建</span>
      <span>¥{{ data.flightsData[0].airportTax }}</span>
      <span>x{{ passengersNumber }}</span>
    </el-row>
    <el-row type="flex" justify="space-between" class="info-bar">
      <span>燃油税</span>
      <span>¥{{ data.flightsData[0].fuelTax }}</span>
      <span>x{{ passengersNumber }}</span>
    </el-row>
    <el-row type="flex" justify="space-between" align="middle" class="info-bar">
      <span>应付总额：</span>
      <span class="price"><def class="d">￥</def>{{ allPrice | toFixed }}</span>
    </el-row>
  </div>
</template>

<script>
import ement from '@/plugins/ement'
import dayjs from "dayjs";

export default {
  components: {},
  props: {
    // 可以接收一个订单信息数据
    data: {
      type: Array,
      default: []
    },
    allPrice: {
      type: Number,
      default: 0
    },
    allGroupPrice: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      passengersNumber: 1
    }
  },
  computed: {},
  watch: {},
  created() {
    // 接收orderForm.vue传过来的乘机人数量
    ement.$on('find', val => {
      this.passengersNumber = val
    })
  },
  filters: {
    toFixed(price) {
      return Number(price).toFixed(0);
    },
    dateFormat(data) {
      return dayjs(data).format("HH:mm")
    },
  },
  mounted() {
  },
}
</script>

<style scoped lang="less">
/*aside*/
.aside {
  //width: 350px;
  height: fit-content;
  border: 1px #ddd solid;
  background-color: white;
  border-radius: 5px;
}

.air-info {
  padding: 10px;

  .info-top {
    margin-bottom: 10px;
    font-weight: 700;

    > div:last-child {
      font-size: 14px;
    }
  }

  .info-step {
    .flight-airport {
      text-align: center;

      strong {
        display: block;
        font-size: 22px;
        font-weight: normal;
      }

      span {
        font-size: 12px;
        color: #999;
      }
    }

    .flight-time {
      text-align: center;
      font-size: 12px;
      color: #999;

      span {
        display: block;
      }
    }
  }
}

.info-bar {
  border-top: 1px #ddd dashed;
  padding: 10px 15px;
  font-size: 14px;
  color: #666;
  text-align: center;

  span {
    width: 33%;
  }

  .price {
    font-size: 30px;
    color: #ff7d13;
    font-weight: 700;

    > def {
      font-size: 18px;
      font-style: normal;
    }
  }
}
</style>
