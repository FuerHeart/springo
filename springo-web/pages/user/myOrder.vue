<template>
  <div class="content-body-container" style="padding-top: 0;">
    <div class="content-bar">
      <div class="container content-bar-body">
        <div><h2 class="content-title">我的订单</h2></div>
        <div class="bread-cumb-inner-container flex bread-cumb"></div>
      </div>
      <div class="page-container">
        <div class="bread-nav">
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item class="is-link" :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>我的订单</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="nav-bar">
          <el-row>
            <el-col :span="6">
              <div class="left-nav">
                <orderMenu/>
              </div>
            </el-col>
            <el-col class="right-item" :span="18">
              <div class="grid-content">
                <div class="select-item">
                  <div class="select-item-input">
                    <el-input placeholder="请输入内容" v-model="selectInput" class="input-with-select">
                      <el-select v-model="select" slot="prepend" placeholder="请选择">
                        <el-option label="订单号" value="1"></el-option>
                        <el-option label="票号" value="2"></el-option>
                        <el-option label="乘机人" value="3"></el-option>
                        <el-option label="手机号" value="4"></el-option>
                      </el-select>
                    </el-input>
                  </div>
                  <div class="block">
                    <el-date-picker
                      v-model="value2"
                      type="daterange"
                      align="right"
                      unlink-panels
                      range-separator="至"
                      start-placeholder="开始日期"
                      end-placeholder="结束日期"
                      :picker-options="pickerOptions">
                    </el-date-picker>
                  </div>
                  <div class="select-button">
                    <el-button type="primary" plain>查询订单</el-button>
                  </div>
                </div>
                <el-col class="tabs-item">
                  <el-tabs type="border-card">
                    <el-tab-pane label="全部订单">
                      <el-table
                        :data="tableData"
                        style="width: 100%">
                        <el-table-column
                          label="订单号">
                          <template slot-scope="scope">
                            <span>{{ scope.row.orderNo }}</span>
                          </template>
                        </el-table-column>
                        <el-table-column
                          label="订单金额">
                          <template slot-scope="scope">
                            <span>¥{{ scope.row.amountPaid }}</span>
                          </template>
                        </el-table-column>
                        <el-table-column
                          label="订单状态">
                          <template slot-scope="scope">
                            <span v-if="scope.row.status === 0">未支付</span>
                            <span v-if="scope.row.status === 1">已支付</span>
                            <span v-if="scope.row.status === 2">已取消</span>
                          </template>
                        </el-table-column>
                        <el-table-column label="操作">
                          <template slot-scope="scope">
                            <el-button
                              size="mini"
                              @click="handleEdit(scope.$index, scope.row)">查看订单
                            </el-button>
                            <br>
                            <el-button v-if="scope.row.status === 0"
                                       size="mini" style="margin-top: 5px"
                                       type="danger"
                                       @click="handleDelete(scope.$index, scope.row)">立即支付
                            </el-button>
                          </template>
                        </el-table-column>
                      </el-table>
                    </el-tab-pane>
                    <el-tab-pane label="等待支付">配置管理</el-tab-pane>
                    <el-tab-pane label="正在出票">角色管理</el-tab-pane>
                    <el-tab-pane label="交易完成">定时任务补偿</el-tab-pane>
                  </el-tabs>
                </el-col>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import orderMenu from "@/components/user/orderMenu.vue";
import FlightsItem from "~/components/air/flightsItem.vue";

export default {
  name: "myOrder",
  data() {
    return {
      selectInput: '',
      select: '1',
      value1: '',
      value2: '',
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      tableData: [],
      users: {}
    }
  },
  components: {
    FlightsItem,
    orderMenu,
  },
  methods: {
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    }
  },
  mounted() {
    // this.$axios({
    //   url: "/orders/getOrder",
    //   params: {
    //     userId: "4badc3038d6d2b964fe19561a3f8343a"
    //   },
    //   headers: {
    //     token: this.$store.state.user.userInfo.token
    //   }
    // }).then(res => {
    //   this.orderInfo = res.data.result;
    //   console.log(this.sales)
    // })

    setTimeout(() => {
      this.$axios({
        url: `/orders/getOrder?userId=${this.$store.state.user.userInfo.user.userId}`,
        headers: {
          token: this.$store.state.user.userInfo.token
        }
      }).then(res => {
        this.tableData = res.data.result
        console.log(this.tableData);
      });
    }, 100);
  },
}
</script>

<style scoped lang="less">
.content-body-container {
  background-color: #f4f4f4;

  .content-bar {
    background: url(../../assets/images/content-bar.png) no-repeat top;
    background-size: auto 230px;

    .container {
      width: 1000px;
      margin: auto;
    }

    .content-bar-body {
      height: 93px;
      display: -webkit-box;
      display: -ms-flexbox;
      display: flex;
      -webkit-box-pack: justify;
      -ms-flex-pack: justify;
      justify-content: space-between;
      -webkit-box-align: center;
      -ms-flex-align: center;
      align-items: center;
      position: relative;

      .content-title {
        font-size: 24px;
        font-weight: 500;
        display: inline-block;
        color: #fff;
        line-height: 33px;
      }
    }

    .page-container {
      position: relative;
      z-index: 3;
      width: 1000px;
      margin: 0 auto;

      .bread-nav {

        height: 45px;
        background: rgba(0, 0, 0, .2);
        padding-left: 40px;
        color: #fff;
        font-size: 14px;
        position: relative;


        .el-breadcrumb {
          line-height: 45px !important;

          /deep/ .el-breadcrumb__inner.is-link {
            font-weight: 700;
            text-decoration: none;
            transition: color .2s cubic-bezier(.645, .045, .355, 1);
            color: #BFA456 !important;
          }

          /deep/ .el-breadcrumb__inner {
            color: #BFA456 !important;
          }
        }
      }

      .nav-bar {
        .el-row {
          margin-bottom: 20px;
          background: #f4f4f4;

          .el-col {
            .left-nav {
              height: 73vh;
              width: 220px;
              background-color: white;
            }

            .right-item {
              margin-left: -20px;
            }
          }

          .el-col-6 {
            width: 22%;
          }
        }


        .grid-content {
          margin-left: 12px;
          height: 73vh;
          width: 758px;

          .select-item {
            padding-top: 25px;
            padding-left: 10px;
            background-color: white;
            display: flex;
            width: 758px;

            .select-item-input {
              width: 38%;
              height: 55px;

              .el-select {
                width: 100px;
              }

              .el-input {
                width: 270px;
              }

              .input-with-select {
                background-color: #fff;
              }
            }

            .select-button {
              margin-left: 12px;
            }
          }

          .tabs-item {
            margin-top: 10px;
            width: 768.5px;

            .operation {
              display: flex;
            }

            /deep/ .el-table__cell {
              padding: 12px 0;
              min-width: 0;
              box-sizing: border-box;
              text-overflow: ellipsis;
              vertical-align: middle;
              position: relative;
              text-align: center;
            }
          }
        }

        .row-bg {
          padding: 10px 0;
          background-color: #f9fafc;
        }
      }
    }

    .order-bg {
      background: #f4f4f4;
    }
  }
}


</style>
