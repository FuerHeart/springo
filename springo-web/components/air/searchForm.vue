<template>
  <div class="search-form">

    <!-- 头部tab切换 -->
    <el-row class="search-tab" type="flex">
      <el-radio-group v-model="currentTab" @change="currentTabChange">
        <el-radio label="0">单程</el-radio>
        <el-radio label="1">往返</el-radio>
      </el-radio-group>

    </el-row>

    <el-form class="search-form-content" label-width="80px" ref="form">
      <el-form-item label="出发城市">
        <!-- fetch-suggestions: 获取搜索的建议 -->
        <!-- select: 选中下拉列表数据时候触发的事件 -->
        <el-autocomplete
          :fetch-suggestions="queryDepartureSearch"
          @select="handleDepartSelect"
          class="el-autocomplete"
          placeholder="请搜索出发城市"
          v-model="form.departure"

        ></el-autocomplete>
      </el-form-item>
      <!--  :focus="queryDepartureSearch"-->
      <!--城市标签页-->
      <!--      <el-tabs v-model="activeName" @tab-click="handleClick" :style="{display: leftVisible?'block':'none'}">-->
      <!--        <el-tab-pane label="热门" name="热门">-->
      <!--          <ul class="hot-city city-group">-->
      <!--            <li class="city-item start-city-item" v-for="(city, i) in hotCities" :key="i" @click="getCityName($event)">-->
      <!--              {{ city }}-->
      <!--            </li>-->
      <!--          </ul>-->
      <!--        </el-tab-pane>-->
      <!--        <el-tab-pane :label="labelKey" :name="labelKey" v-for="(value1, labelKey) in data" :key="labelKey">-->
      <!--          <div class="character-city-wrapper">-->
      <!--            <div class="character-city" v-for="(value2, key2) in value1" :key="key2">-->
      <!--              <div class="character">{{ key2 }}</div>-->
      <!--              <ul class="city-group">-->
      <!--                <li class="city-item start-city-item" v-for="(city, i) of value2" :key="i"-->
      <!--                    @click="getCityName($event)"-->
      <!--                >-->
      <!--                  {{ city }}-->
      <!--                </li>-->
      <!--              </ul>-->
      <!--            </div>-->
      <!--          </div>-->
      <!--        </el-tab-pane>-->
      <!--      </el-tabs>-->

      <el-form-item label="到达城市">
        <el-autocomplete
          :fetch-suggestions="queryArrivalSearch"
          @select="handleArrivalSelect"
          class="el-autocomplete"
          placeholder="请搜索到达城市"
          v-model="form.arrival"
        ></el-autocomplete>
      </el-form-item>

      <el-form-item label="出发日期" prop="departureDate">
        <!-- change 用户确认选择日期时触发 -->
        <el-date-picker
          v-model="form.departureDate"
          value-format="yyyy-MM-dd"
          clearable
          type="date"
          placeholder="请选择起始日期"
          style="width: 100%;"
          @change="handleDate"
        />
        <!--:picker-options="dateTimeFunc"-->
      </el-form-item>
      <el-form-item label="返回日期" prop="returnDate" v-show="currentTab == 2">
        <el-date-picker
          v-model="form.returnDatetime"
          value-format="yyyy-MM-dd"
          clearable
          type="date"
          placeholder="请选择返回日期"
          style="width: 100%;"
        />
        <!--                  :picker-options="dateTimeFunc"-->
      </el-form-item>
      <el-form-item label="">
        <el-button @click="handleSubmit"
                   icon="el-icon-search"
                   style="width:100%;"
                   type="primary">
          搜索
        </el-button>
      </el-form-item>
      <div class="reverse">
        <span @click="handleReverse">换</span>
      </div>
    </el-form>
  </div>
</template>

<script>
import moment from "moment";

export default {
  data() {
    return {
      currentTab: '0',
      // 表单的数据对象
      form: {
        departure: "", //出发城市
        departureCode: "", //出发城市代码
        arrival: "", //到达城市
        arrivalCode: "", //到达城市代码
        departureDate: "", //出发日期
        returnDatetime: "", //返回日期
      },
      disabledDate: "",
      // 禁用过去日期
      // dateTimeFunc: {
      //   disabledDate: time => {
      //     return time.getTime() <= Date.now() - 86400000;
      //   }
      // },
    }
  },
  methods: {
    // tab切换时触发
    currentTabChange: function (val) {
      if (val == 1) {
        this.$confirm('业务暂时不提供往返', '提示', {
          confirmButtonText: '确定',
          type: 'warning'
        })
        return this.currentTab = '0'
        // console.log(this.currentTab)
      }
    },
    loadAll() {
      return [
        {"value": "北京", "address": "BJS"},
        {"value": "上海", "address": "SHA"},
        {"value": "广州", "address": "CAN"},
        {"value": "成都", "address": "CTU"},
        {"value": "杭州", "address": "HGH"},
        {"value": "武汉", "address": "WUH"},
        {"value": "西安", "address": "SIA"},
        {"value": "重庆", "address": "CKG"},
        {"value": "青岛", "address": "TAO"},
        {"value": "长沙", "address": "CSX"},
        {"value": "南京", "address": "NKG"},
        {"value": "厦门", "address": "XMN"},
        {"value": "昆明", "address": "KMG"},
        {"value": "大连", "address": "DLC"},
        {"value": "天津", "address": "TSN"},
        {"value": "郑州", "address": "CGO"},
        {"value": "三亚", "address": "SYX"},
        {"value": "济南", "address": "TNA"},
        {"value": "福州", "address": "FOC"},
        {"value": "韶关", "address": "HSC"},
        {"value": "建三江", "address": "SJS"},
        {"value": "莎车", "address": "QSZ"},
        {"value": "若羌", "address": "RQA"},
        {"value": "菏泽", "address": "HZA"},
        {"value": "巴中", "address": "BZX"},
        {"value": "荆州", "address": "SHS"},
        {"value": "图木舒克", "address": "TWC"},
        {"value": "九江", "address": "JIU"},
        {"value": "芜湖", "address": "WHA"},
        {"value": "新源", "address": "NLT"},
        {"value": "陇南", "address": "LNL"},
        {"value": "甘孜", "address": "GZG"},
        {"value": "于田", "address": "YTW"},
        {"value": "岳阳", "address": "YYA"},
        {"value": "郴州", "address": "HCZ"},
        {"value": "信阳", "address": "XAI"},
        {"value": "松原", "address": "YSQ"},
        {"value": "玉林", "address": "YLX"},
        {"value": "大兴安岭", "address": "JGD"},
        {"value": "遵义", "address": "WMT"},
        {"value": "五大连池", "address": "DTU"},
        {"value": "西宁", "address": "XNN"},
        {"value": "玉树", "address": "YUS"},
        {"value": "格尔木", "address": "GOQ"},
        {"value": "德令哈", "address": "HDX"},
        {"value": "果洛", "address": "GMQ"},
        {"value": "深圳", "address": "SZX"},
        {"value": "花土沟", "address": "HTT"},
        {"value": "祁连", "address": "HBQ"},
        {"value": "乌鲁木齐", "address": "URC"},
        {"value": "海口", "address": "HAK"},
        {"value": "哈尔滨", "address": "HRB"},
        {"value": "贵阳", "address": "KWE"},
        {"value": "昭苏", "address": "ZFL"},
        {"value": "阿拉尔", "address": "ACF"},
        {"value": "沈阳", "address": "SHE"},
        {"value": "南宁", "address": "NNG"},
        {"value": "太原", "address": "TYN"},
        {"value": "长春", "address": "CGQ"},
        {"value": "兰州", "address": "LHW"},
        {"value": "朝阳", "address": "CHG"},
        {"value": "丹东", "address": "DDG"},
        {"value": "锦州", "address": "JNZ"},
        {"value": "鞍山", "address": "AOG"},
        {"value": "长海", "address": "CNI"},
        {"value": "白山", "address": "NBS"},
        {"value": "延吉", "address": "YNJ"},
        {"value": "通化", "address": "TNH"},
        {"value": "白城", "address": "DBC"},
        {"value": "鸡西", "address": "JXA"},
        {"value": "伊春", "address": "LDS"},
        {"value": "大庆", "address": "DQA"},
        {"value": "漠河", "address": "OHE"},
        {"value": "佳木斯", "address": "JMU"},
        {"value": "牡丹江", "address": "MDG"},
        {"value": "齐齐哈尔", "address": "NDG"},
        {"value": "黑河", "address": "HEK"},
        {"value": "抚远", "address": "FYJ"},
        {"value": "常州", "address": "CZX"},
        {"value": "南通", "address": "NTG"},
        {"value": "连云港", "address": "LYG"},
        {"value": "徐州", "address": "XUZ"},
        {"value": "盐城", "address": "YNZ"},
        {"value": "无锡", "address": "WUX"},
        {"value": "淮安", "address": "HIA"},
        {"value": "扬州", "address": "YTY"},
        {"value": "宁波", "address": "NGB"},
        {"value": "温州", "address": "WNZ"},
        {"value": "台州", "address": "HYN"},
        {"value": "舟山", "address": "HSN"},
        {"value": "义乌", "address": "YIW"},
        {"value": "衢州", "address": "JUZ"},
        {"value": "合肥", "address": "HFE"},
        {"value": "黄山", "address": "TXN"},
        {"value": "安庆", "address": "AQG"},
        {"value": "阜阳", "address": "FUG"},
        {"value": "池州", "address": "JUH"},
        {"value": "武夷山", "address": "WUS"},
        {"value": "泉州", "address": "JJN"},
        {"value": "龙岩", "address": "LCX"},
        {"value": "烟台", "address": "YNT"},
        {"value": "威海", "address": "WEH"},
        {"value": "潍坊", "address": "WEF"},
        {"value": "临沂", "address": "LYI"},
        {"value": "东营", "address": "DOY"},
        {"value": "济宁", "address": "JNG"},
        {"value": "南昌", "address": "KHN"},
        {"value": "南昌", "address": "RIZ"},
        {"value": "赣州", "address": "KOW"},
        {"value": "景德镇", "address": "JDZ"},
        {"value": "吉安", "address": "JGS"},
        {"value": "宜春", "address": "YIC"},
        {"value": "上饶", "address": "SQD"},
        {"value": "南阳", "address": "NNY"},
        {"value": "洛阳", "address": "LYA"},
        {"value": "宜昌", "address": "YIH"},
        {"value": "襄阳", "address": "XFN"},
        {"value": "恩施", "address": "ENH"},
        {"value": "神农架", "address": "HPG"},
        {"value": "张家界", "address": "DYG"},
        {"value": "怀化", "address": "HJJ"},
        {"value": "永州", "address": "LLF"},
        {"value": "常德", "address": "CGD"},
        {"value": "衡阳", "address": "HNY"},
        {"value": "桂林", "address": "KWL"},
        {"value": "北海", "address": "BHY"},
        {"value": "柳州", "address": "LZH"},
        {"value": "百色", "address": "AEB"},
        {"value": "梧州", "address": "WUZ"},
        {"value": "河池", "address": "HCJ"},
        {"value": "珠海", "address": "ZUH"},
        {"value": "揭阳", "address": "SWA"},
        {"value": "佛山", "address": "FUO"},
        {"value": "湛江", "address": "ZHA"},
        {"value": "梅州", "address": "MXZ"},
        {"value": "惠州", "address": "HUZ"},
        {"value": "万州", "address": "WXN"},
        {"value": "黔江", "address": "JIQ"},
        {"value": "丽江", "address": "LJG"},
        {"value": "迪庆", "address": "DIG"},
        {"value": "腾冲", "address": "TCZ"},
        {"value": "文山", "address": "WNH"},
        {"value": "临沧", "address": "LNJ"},
        {"value": "普洱", "address": "SYM"},
        {"value": "保山", "address": "BSD"},
        {"value": "芒市", "address": "LUM"},
        {"value": "昭通", "address": "ZAT"},
        {"value": "大理", "address": "DLU"},
        {"value": "西双版纳", "address": "JHG"},
        {"value": "宁蒗", "address": "NLH"},
        {"value": "拉萨", "address": "LXA"},
        {"value": "林芝", "address": "LZY"},
        {"value": "昌都", "address": "BPX"},
        {"value": "日喀则", "address": "PKZ"},
        {"value": "绵阳", "address": "MIG"},
        {"value": "西昌", "address": "XIC"},
        {"value": "甘孜", "address": "KGT"},
        {"value": "宜宾", "address": "YBP"},
        {"value": "达州", "address": "DZH"},
        {"value": "南充", "address": "NAO"},
        {"value": "攀枝花", "address": "PZI"},
        {"value": "九寨", "address": "JZH"},
        {"value": "泸州", "address": "LZO"},
        {"value": "稻城", "address": "DCY"},
        {"value": "广元", "address": "GYS"},
        {"value": "红原", "address": "AHJ"},
        {"value": "荔波", "address": "LLB"},
        {"value": "黎平", "address": "HZH"},
        {"value": "安顺", "address": "AVA"},
        {"value": "兴义", "address": "ACX"},
        {"value": "铜仁", "address": "TEN"},
        {"value": "遵义", "address": "ZYI"},
        {"value": "毕节", "address": "BFJ"},
        {"value": "凯里", "address": "KJH"},
        {"value": "六盘水", "address": "LPF"},
        {"value": "延安", "address": "ENY"},
        {"value": "榆林", "address": "UYN"},
        {"value": "安康", "address": "AKA"},
        {"value": "汉中", "address": "HZG"},
        {"value": "嘉峪关", "address": "JGN"},
        {"value": "敦煌", "address": "NDH"},
        {"value": "金昌", "address": "JIC"},
        {"value": "张掖", "address": "YZY"},
        {"value": "天水", "address": "THQ"},
        {"value": "庆阳", "address": "IQN"},
        {"value": "甘南", "address": "GXH"},
        {"value": "银川", "address": "INC"},
        {"value": "中卫", "address": "ZHY"},
        {"value": "固原", "address": "GYU"},
        {"value": "喀什", "address": "KHG"},
        {"value": "和田", "address": "HTN"},
        {"value": "库尔勒", "address": "KRL"},
        {"value": "克拉玛依", "address": "KRY"},
        {"value": "吐鲁番", "address": "TLQ"},
        {"value": "哈密", "address": "HMI"},
        {"value": "阿克苏", "address": "AKU"},
        {"value": "库车", "address": "KCA"},
        {"value": "布尔津", "address": "KJI"},
        {"value": "阿勒泰", "address": "AAT"},
        {"value": "博乐", "address": "BPL"},
        {"value": "塔城", "address": "TCG"},
        {"value": "伊宁", "address": "YIN"},
        {"value": "且末", "address": "IQM"},
        {"value": "富蕴", "address": "FYN"},
        {"value": "石家庄", "address": "SJW"},
        {"value": "秦皇岛", "address": "BPE"},
        {"value": "唐山", "address": "TVS"},
        {"value": "邯郸", "address": "HDG"},
        {"value": "运城", "address": "YCU"},
        {"value": "长治", "address": "CIH"},
        {"value": "吕梁", "address": "LLV"},
        {"value": "大同", "address": "DAT"},
        {"value": "忻州", "address": "WUT"},
        {"value": "呼和浩特", "address": "HET"},
        {"value": "临汾", "address": "LFQ"},
        {"value": "通辽", "address": "TGO"},
        {"value": "包头", "address": "BAV"},
        {"value": "海拉尔", "address": "HLD"},
        {"value": "赤峰", "address": "CIF"},
        {"value": "鄂尔多斯", "address": "DSN"},
        {"value": "二连浩特", "address": "ERL"},
        {"value": "阿尔山", "address": "YIE"},
        {"value": "乌海", "address": "WUA"},
        {"value": "锡林浩特", "address": "XIL"},
        {"value": "巴彦淖尔", "address": "RLK"},
        {"value": "满洲里", "address": "NZH"},
        {"value": "阿拉善左旗", "address": "AXF"},
        {"value": "阿拉善右旗", "address": "RHT"},
        {"value": "额济纳旗", "address": "EJN"},
        {"value": "张家口", "address": "ZQZ"},
        {"value": "乌兰浩特", "address": "HLH"},
        {"value": "扎兰屯", "address": "NZL"},
        {"value": "乌兰察布", "address": "UCB"},
        {"value": "霍林郭勒", "address": "HUO"},
        {"value": "承德", "address": "CDE"},
        {"value": "营口", "address": "YKH"},
        {"value": "十堰", "address": "WDS"},
        {"value": "三明", "address": "SQJ"},
        {"value": "琼海", "address": "BAR"},
        {"value": "三沙", "address": "XYI"},
        {"value": "沧源", "address": "CWJ"},
        {"value": "邵阳", "address": "WGN"},
        {"value": "澜沧", "address": "JMJ"},
        {"value": "石河子", "address": "SHF"}
      ];
    },
    // getCityName(e) {
    //   const target = e.target;
    //   // 保存获取到的城市名字
    //   let val = null;
    //   if (target.className.indexOf('start-city-item') !== -1) {
    //     const starting = this.$refs.startingRef;
    //     this.leftVisible = false
    //     this.rightVisible = true
    //     val = target.innerHTML
    //     this.$refs.startInputRef.value = val
    //     // 把点击城市名获取到的值赋值给默认值
    //     this.defaultSearchValue = val
    //     console.log(starting)
    //   }
    // },
    // 出发城市 触发的搜索建议
    queryDepartureSearch(queryString, cb) {
      // this.leftVisible = true
      const restaurants = this.restaurants;
      const results = queryString ? restaurants.filter(this.createDepartureFilter(queryString)) : restaurants;

      clearTimeout(this.timeout);
      this.timeout = setTimeout(() => {
        cb(results);
      }, 1000 * Math.random());
    },
    createDepartureFilter(queryString) {
      return (state) => {
        return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    // 触发的搜索建议
    // value 是输入框的值
    // cb 是回调函数是必须要执行，接收数组，数组内部必须是由对象组件，对象必须要value属性
    handleDepartSelect(item) {
      console.log(item);
      this.form.departure = item.value;
      this.form.departureCode = item.address;

      console.log(this.form.departureCode);
    },

    // 到达城市输入框获得焦点时触发
    // value 是选中的值，cb是回调函数，接收要展示的列表
    queryArrivalSearch(queryString, cb) {
      const restaurants = this.restaurants;
      const results = queryString ? restaurants.filter(this.createArrivalFilter(queryString)) : restaurants;

      clearTimeout(this.timeout);
      this.timeout = setTimeout(() => {
        cb(results);
      }, 3000 * Math.random());
    },
    createArrivalFilter(queryString) {
      return (state) => {
        return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    // 目标城市下拉选择时触发
    handleArrivalSelect(item) {
      console.log(item);

      this.form.arrival = item.value;
      this.form.arrivalCode = item.address;

      console.log(this.form.arrivalCode);
    },

    // 确认选择日期时触发
    handleDate(value) {
      this.form.departureDate = moment(value).format("YYYY-MM-DD");
    },

    // 触发和目标城市切换时触发
    handleReverse() {
      const {departure, departureCode, arrival, arrivalCode} = this.form;

      // 到达城市传给出发城市
      this.form.departure = arrival;
      this.form.departureCode = arrivalCode;

      // 把出发城市传给到达城市
      this.form.arrival = departure;
      this.form.arrivalCode = departureCode;
    },


    // 提交表单是触发
    handleSubmit() {

      // 自定义表单验证
      const rules = {
        departure: {
          value: this.form.departure,
          message: "请选择出发城市"
        },
        arrival: {
          value: this.form.arrival,
          message: "请选择到达城市"
        },
        departureDate: {
          value: this.form.departureDate,
          message: "请选择出发时间"
        }
      };

      // 验证变量哦，默认验证通过
      let valid = true;

      // 循环验证对象
      Object.keys(rules).forEach(v => {
        if (!valid) return;

        // 如果有一项数据不存在
        if (!rules[v].value) {
          this.$message.warning(rules[v].message);
          // 验证不通过
          valid = false;
        }
      })

      // 验证不通过，直接返回
      if (!valid) return;

      this.$router.push({
        path: "/air/flights",
        query: this.form
      });

      // 先获取本地的列表
      const localAirs = JSON.parse(localStorage.getItem("airs") || `[]`);

      // 把当前表单的值保存到本地
      localAirs.unshift(this.form);//把form放到localAirs第一个
      localStorage.setItem("airs", JSON.stringify(localAirs))
    },
    handleClick(tab, event) {
      console.log(tab, event);
    }
  },
  mounted() {
    this.restaurants = this.loadAll();
  }
}
</script>

<style lang="less" scoped>
.search-form {
  border: 2px solid #ff9d00;
  //border-top: none;
  width: 360px;
  height: 370px;
  box-sizing: border-box;
}

.search-tab {
  padding-left: 95px;
  padding-top: 20px;
}

.search-form-content {
  padding: 15px 50px 15px 15px;
  position: relative;

  .el-autocomplete {
    width: 100%;
  }

  .el-tabs {
    position: absolute;
    z-index: 999 !important;
    top: 75px;
    width: 625px;
    min-height: 200px;
    background-color: white;
    display: none;
    border: 1px solid #ddd;
    -webkit-box-shadow: 0 7px 22px -7px rgba(0, 0, 0, .34);
    box-shadow: 0 7px 22px -7px rgba(0, 0, 0, .34);
    border-radius: 6px;
    vertical-align: top;

    /deep/ .el-tabs__nav-scroll {
      background: white;
    }

    /deep/ .is-top:nth-child(2) {
      padding-left: 20px;
    }

    /deep/ .el-tab-pane {
      width: 605px;
      padding: 0 10px;

      .character-city-wrapper {
        padding-top: 24px;
        padding-left: 32px;

        .character-city {
          display: flex;
          margin-bottom: 10px;

          .character {
            width: 26px;
            line-height: 32px;
            vertical-align: top;
            font-size: 14px;
            font-weight: 600;
            padding-right: 10px;
          }
        }


      }
    }

    .city-group {
      display: inline-block;
      max-width: 570px;

      .city-item {
        float: left;
        padding: 0 2px 0 8px;
        font-size: 14px;
        color: #333333;
        cursor: pointer;
        width: 84px;
        height: 30px;
        line-height: 30px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }

      .city-item:hover {
        background: rgba(0, 134, 246, 0.05);
        color: #0086F6;
        border-radius: 5px;
      }
    }

  }
}

.reverse {
  position: absolute;
  top: 35px;
  right: 15px;

  &:after, &:before {
    display: block;
    content: "";
    position: absolute;
    left: -35px;
    width: 25px;
    height: 1px;
    background: #ccc;
  }

  &:after {
    top: 0;
  }

  &:before {
    top: 60px;
  }

  span {
    display: block;
    position: absolute;
    top: 20px;
    right: 0;
    font-size: 12px;
    background: #999;
    color: #fff;
    width: 20px;
    height: 20px;
    line-height: 18px;
    text-align: center;
    border-radius: 2px;
    cursor: pointer;

    &:after, &:before {
      display: block;
      content: "";
      position: absolute;
      left: 10px;
      width: 1px;
      height: 20px;
      background: #ccc;
    }

    &:after {
      top: -20px;
    }

    &:before {
      top: 20px;
    }

    &:hover {
      background-color: orange;
    }
  }
}
</style>
