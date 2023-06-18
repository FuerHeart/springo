# springo-web

以 Web 服务的方式实现一个为旅客提供国内航班查询与机票预订功能的系统。航班查询功能：支持在给定日期、出发地、目的地模式下的航班查询，查询结果中至包含航班的起降时间、剩余座位数、票价。提供中转航班查询。机票预订功能：支持对给定日期、航班机票的预订及预订后的机票改期退票功能；具备基本的座位库存控制能力，能正确处理多人并发预订机票时的库存扣减事务。

``` bash
# 安装依赖
$ yarn install

# 建议不要直接使用 cnpm 安装依赖，会有各种诡异的 bug。可以通过如下操作解决 npm 下载速度慢的问题
yarn install --registry=https://registry.npmmirror.com

# 启动服务
npm run dev

# build for production and launch server
$ yarn build
$ yarn start

# generate static project
$ yarn generate
```

浏览器访问 http://localhost:3000

## 前端技术栈

* Nuxtjs
* Vue
* Vue-router
* Element-ui
* Axios
* Token

## 后端技术栈

* Spring Boot
* MySQL
* Mybatis-Plus
* RocketMQ
* Redis

## 版本控制

该项目使用Git进行版本管理。您可以在repository参看当前可用版本。

## 演示截图

![首页](D:\Desktop\项目最新进展\前端\springo-web\assets\images\首页.png)

![航班列表](D:\Desktop\项目最新进展\前端\springo-web\assets\images\航班列表.png)

![添加乘机人](D:\Desktop\项目最新进展\前端\springo-web\assets\images\添加乘机人.png)