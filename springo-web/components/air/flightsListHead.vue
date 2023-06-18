<template>
  <el-row class="flight-title">
    <el-col :span="5">
      航空信息
    </el-col>
    <el-col :span="14">
      <el-row type="flex" justify="space-between">
        <el-col :span="16">
            <span style="margin-left: 20px" @click="handleDataChange">起飞时间 <i class="el-icon-caret-bottom"></i></span>
        </el-col>
        <el-col :span="9">
          到达时间
        </el-col>
      </el-row>
    </el-col>
    <el-col :span="5">
      价格
    </el-col>
  </el-row>
</template>

<script>
export default {
  components: {},
  props: [],
  data() {
    return {}
  },
  computed: {},
  watch: {},
  created() {
  },
  mounted() {
  },
  methods: {
    handleDataChange() {
      this.$notify({
        title: '警告',
        message: '这是一条警告的提示消息',
        type: 'warning'
      });
    },
  }
}
</script>

<style scoped lang="less">
.flight-title {
  padding: 0 15px;
  border: 1px #ddd solid;
  background: #f6f6f6;
  height: 38px;
  line-height: 38px;
  color: #666;
  font-size: 12px;
  margin-bottom: 10px;

  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
  -webkit-transition: top 80ms ease;
  -o-transition: top 80ms ease;
  transition: top 80ms ease;

  > div {
    text-align: center
  }
}
</style>
