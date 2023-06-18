<template>
  <div class="footer-wrapper">
    <div class="footer">
      <el-row class="info-list">
        <el-col :span="6" :offset="1">
          <h5>航空路线推荐系统</h5>
          <p>上亿旅行者共同打造的"旅行神器"</p>
          <p><span>60,000</span> 多个全球旅游目的地</p>
          <p><span>600,000</span> 个细分目的地新玩法</p>
          <p><span>760,000,000</span> 次攻略下载</p>
          <p><span>38,000</span> 家旅游产品供应商</p>
        </el-col>
        <el-col :span="5">
          <h5>关于我们</h5>
          <p>隐私政策 商标声明</p>
          <p>服务协议 游记协议</p>
          <p>商城平台服务协议</p>
          <p>网络信息侵权通知指引</p>
          <p>闲云旅游旅游网服务监督员</p>
          <p>网站地图 加入我们</p>
        </el-col>
        <el-col :span="5">
          <h5>旅行服务</h5>
          <p>旅游攻略 机票预订</p>
          <p>旅游特价 国际租车</p>
          <p>旅游问答 旅游保险</p>
          <p>旅游指南 订火车票</p>
          <p>旅游资讯 APP下载</p>
        </el-col>
        <el-col :span="6" class="scan">
          <p>
            <img src="../assets/images/qr_sbm.jpg" alt="航空路线推荐系统">
          </p>
          航空路线推荐系统
          微信公众号
        </el-col>
      </el-row>

      <div class="licence">
        Copyright©2022-2025 曾家爱 版权所有 粤ICP备19980902号
      </div>
    </div>
  </div>
</template>

<script>
export default {}
</script>

<style scoped lang="less">
.footer-wrapper {
  background: #223b61;
  color: white;
  min-width: 1000px;
}

.footer {
  padding-top: 30px;
  margin: 0 auto;
  width: 1000px;
}

.info-list {
  h5 {
    font-weight: 400;
    font-size: 18px;
    margin-bottom: 10px;
    display: inline-block;
    height: 20px;
    line-height: 18px;
    vertical-align: middle;
  }

  p {
    font-size: 12px;
    line-height: 1.8;

    span {
      color: orange;
    }
  }
}

.scan {
  text-align: center;

  img {
    width: 140px;
    height: 140px;
    margin-bottom: 5px;
  }

  font-size: 12px;
}

.licence {
  border-top: 1px #666 solid;
  margin-top: 20px;
  padding: 50px 0;
  text-align: center;
  font-size: 12px;
}
</style>
