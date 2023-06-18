<template>
  <div class="container" ref="container" @click="handleContainerClick">
    <el-input
      v-model="searchText"
      placeholder="请输入城市名称"
      @focus="showTabs"
      @input="handleSearch"
      ref="input"
    ></el-input>

    <el-tabs
             v-model="activeTab"
             type="border-card"
             class="tabs-container"
             v-show="showTab"
             @tab-click="onTabClick"
    >
      <el-tab-pane label="热门城市" name="hot">
        <div class="tab-content">
          <el-row>
            <el-col v-for="city in hotCities" :key="city.id" :span="8">
              <div class="city-item" @click="selectCity(city.name)">{{ city.name }}</div>
            </el-col>
          </el-row>
        </div>
      </el-tab-pane>
      <el-tab-pane v-for="(group, index) in groupedCities" :label="index" :name="index" :key="index">
        <div class="tab-content">
          <el-row>
            <el-col v-for="city in group" :key="city.id" :span="8">
              <div class="city-item" @click="selectCity(city.name)">{{ city.name }}</div>
            </el-col>
          </el-row>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchText: '',
      showTab: false,
      activeTab: 'hot',
      hotCities: [
        {id: 1, name: '北京', code: 'BJ', pinyin: ''},
        {id: 2, name: '上海', code: 'AAT', pinyin: ''},
        {id: 3, name: '广州', code: 'AAT', pinyin: ''},
        // 其他热门城市...
      ],
      cities: [
        {id: 1, name: '阿勒泰', code: 'AAT', pinyin: 'aletai'},
        {id: 2, name: '北海', code: 'BHY', pinyin: 'beihai'},
        {id: 3, name: '长沙', code: 'CSX', pinyin: 'changsha'},
        {id: 3, name: '长沙', code: 'CSX', pinyin: 'changsha'},
        {id: 3, name: '长沙', code: 'CSX', pinyin: 'changsha'},
        {id: 4, name: '大同', code: 'DAT', pinyin: 'datong'},
        // 其他城市...
      ]
    };
  },
  mounted() {
    document.addEventListener('click', this.handleClickOutside);
  },
  beforeDestroy() {
    document.removeEventListener('click', this.handleClickOutside);
  },
  computed: {
    groupedCities() {
      // 根据城市名称的首字母进行分组
      const grouped = {};
      this.cities.forEach(city => {
        const firstLetter = city.code[0].toUpperCase();
        if (grouped[firstLetter]) {
          grouped[firstLetter].push(city);
        } else {
          grouped[firstLetter] = [city];
        }
      });
      return grouped;
    }
  },
  methods: {
    showTabs() {
      this.showTab = true;
    },
    handleClickOutside(event) {
      if (!this.$el.contains(event.target)) {
        this.showTab = false;
      }
    },
    onBlur(event) {
      if (!this.$refs.container.contains(event.relatedTarget)) {
        this.showTab = false;
      }
    },
    onTabClick() {
      this.$refs.input.focus();
    },
    selectCity(cityName) {
      this.searchText = cityName;
      console.log(cityName)
      this.showTab = false;
    },
    handleSearch() {
      // 处理搜索逻辑，可以在这里过滤城市列表等
    },
    handleContainerClick(event) {
      // 点击容器区域时，隐藏 el-tabs
      if (!this.$refs.container.contains(event.target)) {
        this.showTab = false;
      }
    }
  }
};
</script>

<style>
.container {
  width: 50%;
  margin: 250px auto;
  position: relative;
}

.tabs-container {
  position: absolute;
  top: 38px;
  width: 100%;
  margin-top: 20px;
}

.tab-content {
  padding: 20px;
}

.city-item {
  cursor: pointer;
  margin-bottom: 10px;
}
</style>
