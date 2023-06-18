<template>
  <div class="main">
    <div class="air-column">
      <el-form class="member-info" :model="dynamicValidateForm" ref="dynamicValidateForm">
        <!-- 乘机人每一组数据 -->
        <div class="member-info-item"
             v-for="(item, index) in dynamicValidateForm.passengers"
             :key="index">
          <el-form-item
            label=姓名
            :prop="'passengers.' + index + '.username'"
            :rules="[{ required: true, message: '姓名不能为空，请您输入姓名后再预订', trigger: 'blur'}]">
            <el-input placeholder="与登机证件姓名保持一致" v-model="item.username"></el-input>
          </el-form-item>

          <el-form-item class="idCard"
                        label="证件号码"
                        :prop="'passengers.' + index + '.idCard'"
                        :rules="[{ required: true, message: '乘机人证件号必填,请您输入乘机人证件号后再预订', trigger: 'blur'},
                        { pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,message: '请正确输入18位身份证号码',trigger: 'blur'}]">
            <el-input
              placeholder="登机证件号码"
              class="input-with-select"
              v-model="item.idCard">
              <el-select v-model="dynamicValidateForm.dcumentType" slot="prepend" placeholder="请选择">
                <el-option label="身份证" value="1"></el-option>
                <el-option label="军官证" value="3"></el-option>
                <el-option label="士兵证" value="4"></el-option>
                <el-option label="台胞证" value="5"></el-option>
                <el-option label="港澳台居住证" value="7"></el-option>
                <el-option label="其他" value="8"></el-option>
              </el-select>
            </el-input>
          </el-form-item>

          <!-- 删除乘机人 -->
          <span class="delete-user" @click="handleDeleteUser(index)">-</span>

        </div>
        <el-button class="add-member" type='primary' @click="handleAddUsers">新增乘机人</el-button>

        <el-divider></el-divider>
        <div class="contact">
          <h2>联系人</h2>
          <el-form-item class="contact-item" label="姓名" prop="contactName" label-width="65px"
                        :rules="[{ required: true, message: '请填写联系人姓名'}]">
            <el-input v-model="dynamicValidateForm.contactName" placeholder="联系人姓名" clearable
                      style="width: 250px"></el-input>
          </el-form-item>

          <el-form-item class="contact-item" label="手机号" prop="contactPhone" label-width="65px"
                        :rules="[{required: true,message: '请输入手机号',trigger: 'blur'},
                                 {pattern: /^1(3|4|5|7|8|9)\d{9}$/,message: '手机号格式错误',trigger: 'blur'}]">
            <el-input v-model="dynamicValidateForm.contactPhone" placeholder="请填写联系人手机号" clearable
                      style="width: 250px"></el-input>
          </el-form-item>

          <el-button type="primary" class="submit" @click="handleSubmit('dynamicValidateForm')">提交订单</el-button>
        </div>
      </el-form>

    </div>


    <input type="hidden" :value="allPrice"/>
  </div>
</template>

<script>
import ement from '@/plugins/ement'

export default {
  props: {
    // 可以接收一个订单信息数据
    data: {
      type: Object,
      default: {}
    },
  },

  data() {
    return {
      dynamicValidateForm: {
        // 乘机人列表
        passengers: [{
          username: '',     // 姓名
          idCard: '',       // 登机证件号码
        }],
        dcumentType: "1",   // 证件类型
        contactName: "",    // 联系人
        contactPhone: "",   // 联系电话
      },
      // 机票信息的总数据
      infoData: {
        seat_infos: {}
      },
      passengersNumber: 1
    }
  },

  computed: {
    cabinJSON() {
      return JSON.parse(this.data.cabin)
    },
    // 直达的航班票价计算
    allPrice() {
      let price = 0;

      // 机票的单价
      price += this.data.cabin.cabinPrice.adultSalePrice;

      // 机建
      price += this.data.flightsData[0].airportTax;

      // 燃油税
      price += this.data.flightsData[0].fuelTax;

      // 乘机人数量
      price *= this.dynamicValidateForm.passengers.length;

      this.$emit('setAllPrice', price)

      return price;
    },
  },

  methods: {
    // 添加乘机人
    handleAddUsers() {
      this.dynamicValidateForm.passengers.push({
        username: "",
        idCard: ""
      })
      // 把乘机人数量传给orderAside.vue组件
      this.passengersNumber++
      ement.$emit("find", this.passengersNumber)
    },
    // 移除乘机人
    handleDeleteUser(index) {
      // 移除乘机人
      this.dynamicValidateForm.passengers.splice(index, 1);
      // 把乘机人数量传给orderAside.vue组件
      this.passengersNumber--
      ement.$emit("find", this.passengersNumber)
    },

    // 提交订单
    handleSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {

          // console.log(this.data)
          const store = JSON.parse(localStorage.getItem("store"));

          const passengers = JSON.stringify(this.dynamicValidateForm.passengers)

          const data = {
            passengers: passengers,
            contactName: this.dynamicValidateForm.contactName,
            contactPhone: this.dynamicValidateForm.contactPhone,
            flightId: this.data.flightsData[0].flightId,
            userId: store.user.userInfo.user.userId,
            amountPaid: this.allPrice
          }
          // console.log(data);
          this.$axios({
            url: "/orders/add",
            responseType: "json",
            method: "POST",
            data,
            headers: {
              token: this.$store.state.user.userInfo.token
            }
          }).then(res => {
            if (res.data.code === 604) {
              this.$confirm('乘机人  ' + res.data.result + '  已预订过相同时间的航班。', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
              }).then(() => {

              }).catch(() => {

              });
            } else {
              console.log(res.data.result.order.orderNo)
              this.$message.success("正在生成订单，请稍后...");
              const orderNo = res.data.result.order.orderNo
              console.log(orderNo)
              console.log(res.data)
              this.$router.push({
                path: "/air/pay",
                query: {
                  orderNo
                }
              })
            }
          })
        } else {
          return false;
        }
      });
    }
  },
  mounted() {
  }
}
</script>

<style scoped lang="less">
.main {
  background-color: white;
  border-radius: 5px !important;

  .air-column {
    margin-bottom: 20px;
    padding: 10px 20px 5px;
    background-color: white;
    border-radius: 5px;
  }

  .air-column h2 {
    margin-bottom: 20px;
    font-size: 22px;
    font-weight: normal;
  }

  /deep/ .el-select .el-input {
    width: 130px;
  }

  .input-with-select {
    width: 590px;
  }

  .input-with-select /deep/ .el-input-group__prepend {
    background-color: #fff;
  }

  .member-info /deep/ .el-form-item {
    margin-bottom: 0;
  }

  .member-info-item {
    //border-bottom: 1px #eee dashed;
    //padding-bottom: 20px;
    margin-bottom: 10px;
    position: relative;

    .idCard {
      margin-top: 20px;
    }

    &:first-child {
      .delete-user {
        display: none;
      }
    }
  }

  .add-member {
    margin-top: 30px;
  }

  .delete-user {
    display: block;
    background: #ddd;
    width: 16px;
    height: 16px;
    font-size: 14px;
    text-align: center;
    line-height: 16px;
    color: #fff;
    cursor: pointer;
    border-radius: 50px;
    position: absolute;
    right: 1px;
    top: 57%;
  }

  .insurance {
    > div {
      margin-top: 10px;
    }
  }

  .insurance-item {
    margin-bottom: 20px;
  }

  .contact {
    margin-top: 30px;

    .contact-item {
      height: 60px;
    }

    /deep/ .el-input {
      width: 50%;
    }
  }

  .submit {
    margin: 50px auto;
    display: block;
    width: 250px;
    height: 50px;
  }
}
</style>
