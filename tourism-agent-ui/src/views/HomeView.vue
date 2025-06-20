<template>
  <div class="home-page">
    <app-header />
    
    <div class="hero-section">
      <div class="container">
        <div class="hero-content">
          <h1 class="main-title">游攻略</h1>
          <h2 class="subtitle">AI 帮你制作旅行攻略</h2>
          
          <div class="date-picker-container">
            <el-date-picker
              v-model="travelDateRange"
              type="daterange"
              range-separator=" - "
              start-placeholder="旅程开始日期"
              end-placeholder="旅程结束日期"
              format="YYYY/MM/DD"
              value-format="YYYY-MM-DD"
              :clearable="false"
              :disabled-date="disablePastDates"
            />
            <el-button type="primary" class="plan-btn" @click="createPlan">现在登录</el-button>
          </div>
        </div>
      </div>
    </div>
    
    <div class="features-section">
      <div class="container">
        <h2 class="section-title">旅游助手功能</h2>
        
        <div class="features-grid">
          <div class="feature-card">
            <el-icon class="feature-icon"><calendar /></el-icon>
            <h3>智能行程规划</h3>
            <p>AI根据您的时间和偏好自动生成最佳旅行路线</p>
          </div>
          
          <div class="feature-card">
            <el-icon class="feature-icon"><location /></el-icon>
            <h3>景点推荐</h3>
            <p>发现当地最受欢迎的景点和隐藏宝藏</p>
          </div>
          
          <div class="feature-card">
            <el-icon class="feature-icon"><food /></el-icon>
            <h3>美食推荐</h3>
            <p>品尝当地特色美食和人气餐厅</p>
          </div>
          
          <div class="feature-card">
            <el-icon class="feature-icon"><suitcase /></el-icon>
            <h3>一键生成攻略</h3>
            <p>自动生成详细的旅游攻略，可随时调整和分享</p>
          </div>
        </div>
      </div>
    </div>
    
    <div class="popular-destinations">
      <div class="container">
        <h2 class="section-title">热门旅游目的地</h2>
        
        <div class="destinations-grid">
          <div 
            v-for="destination in popularDestinations" 
            :key="destination.id" 
            class="destination-card"
            :style="{backgroundImage: `url(${destination.image})`}"
          >
            <div class="destination-info">
              <h3>{{ destination.name }}</h3>
              <p>{{ destination.description }}</p>
              <el-button type="primary" size="small" @click="exploreDestination(destination)">
                探索
              </el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { Calendar, Location, Food, Suitcase } from '@element-plus/icons-vue'
import AppHeader from '../components/AppHeader.vue'
import dayjs from 'dayjs'

// 旅行日期选择
const travelDateRange = ref([
  dayjs().format('YYYY-MM-DD'),
  dayjs().add(7, 'day').format('YYYY-MM-DD')
])

// 禁用过去的日期
const disablePastDates = (date) => {
  return date < dayjs().startOf('day')
}

// 创建旅行计划
const createPlan = () => {
  console.log('创建旅行计划，日期范围:', travelDateRange.value)
  // 实际应用中这里会导航到登录页或计划页
}

// 探索目的地
const exploreDestination = (destination) => {
  console.log('探索目的地:', destination.name)
  // 实际应用中这里会导航到目的地详情页
}

// 热门旅游目的地数据
const popularDestinations = ref([
  {
    id: 1,
    name: '北京',
    description: '探索中国的首都，体验古老文化与现代魅力',
    image: 'https://images.unsplash.com/photo-1508804185872-d7badad00f7d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80'
  },
  {
    id: 2,
    name: '上海',
    description: '现代与传统交融的国际大都市',
    image: 'https://images.unsplash.com/photo-1548919973-5cef591cdbc9?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80'
  },
  {
    id: 3,
    name: '香港',
    description: '东方之珠，购物天堂与美食之都',
    image: 'https://images.unsplash.com/photo-1506970845246-18f21d533b20?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80'
  },
  {
    id: 4,
    name: '成都',
    description: '休闲生活与美食之都，大熊猫的故乡',
    image: 'https://images.unsplash.com/photo-1558877316-c21513ac40d3?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80'
  },
  {
    id: 5,
    name: '西安',
    description: '古都风采，探索中国历史文化',
    image: 'https://images.unsplash.com/photo-1567358080869-dcbd6d0f4321?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1474&q=80'
  },
  {
    id: 6,
    name: '三亚',
    description: '热带海滨天堂，阳光沙滩与蓝色海洋',
    image: 'https://images.unsplash.com/photo-1570077188670-e3a8d69ac5ff?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80'
  }
])
</script>

<style scoped>
.home-page {
  padding-top: 64px; /* 为固定的header留出空间 */
  width: 100%;
}

.hero-section {
  background: linear-gradient(135deg, #1c92d2 0%, #f2fcfe 100%);
  padding: 100px 0;
  color: white;
  text-align: center;
  width: 100%;
}

.container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.hero-content {
  max-width: 800px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.main-title {
  font-size: 3.5rem;
  margin-bottom: 16px;
  font-weight: 700;
}

.subtitle {
  font-size: 1.5rem;
  margin-bottom: 40px;
  font-weight: 400;
  opacity: 0.9;
}

.date-picker-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
  max-width: 500px;
  width: 100%;
  margin: 0 auto;
}

@media (min-width: 768px) {
  .date-picker-container {
    flex-direction: row;
    align-items: center;
    justify-content: center;
  }
  
  .el-date-editor.el-input {
    width: 100%;
  }
}

.plan-btn {
  height: 40px;
  padding: 0 24px;
  font-weight: 500;
  font-size: 16px;
  min-width: 120px;
}

.features-section {
  padding: 80px 0;
  background-color: #f8f9fa;
  width: 100%;
}

.section-title {
  text-align: center;
  font-size: 2.2rem;
  margin-bottom: 60px;
  color: #333;
  font-weight: 600;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 30px;
}

.feature-card {
  background-color: white;
  border-radius: 12px;
  padding: 30px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
  text-align: center;
  transition: transform 0.3s, box-shadow 0.3s;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.feature-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
}

.feature-icon {
  font-size: 48px;
  color: #409EFF;
  margin-bottom: 20px;
}

.feature-card h3 {
  font-size: 1.2rem;
  margin-bottom: 12px;
  color: #333;
  font-weight: 600;
}

.feature-card p {
  color: #666;
  line-height: 1.6;
}

.popular-destinations {
  padding: 80px 0;
  width: 100%;
}

.destinations-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 24px;
}

.destination-card {
  height: 240px;
  border-radius: 12px;
  overflow: hidden;
  position: relative;
  background-size: cover;
  background-position: center;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;
}

.destination-card:hover {
  transform: scale(1.03);
}

.destination-info {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 20px;
  background: linear-gradient(to top, rgba(0, 0, 0, 0.8), rgba(0, 0, 0, 0));
  color: white;
}

.destination-info h3 {
  font-size: 1.3rem;
  margin-bottom: 8px;
  font-weight: 600;
}

.destination-info p {
  margin-bottom: 12px;
  opacity: 0.9;
  font-size: 0.95rem;
}

/* 日期选择器的样式优化 */
:deep(.el-input__wrapper) {
  width: 100%;
}

:deep(.el-date-editor--daterange) {
  width: 100%;
}
</style> 