<template>
  <div class="discover-page">
    <app-header />
    
    <div class="discover-hero">
      <div class="container">
        <h1 class="page-title">发现旅游景点</h1>
        <p class="page-subtitle">探索世界各地的精彩景点和独特体验</p>
        
        <div class="search-container">
          <el-input
            v-model="searchQuery"
            placeholder="搜索目的地、景点或活动"
            class="search-input"
            clearable
          >
            <template #prefix>
              <el-icon><search /></el-icon>
            </template>
          </el-input>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
        </div>
      </div>
    </div>
    
    <div class="filter-section container">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-select v-model="filters.region" placeholder="地区" class="filter-select">
            <el-option label="全部地区" value="" />
            <el-option label="亚洲" value="asia" />
            <el-option label="欧洲" value="europe" />
            <el-option label="北美洲" value="north_america" />
            <el-option label="南美洲" value="south_america" />
            <el-option label="非洲" value="africa" />
            <el-option label="大洋洲" value="oceania" />
          </el-select>
        </el-col>
        <el-col :span="6">
          <el-select v-model="filters.category" placeholder="景点类型" class="filter-select">
            <el-option label="全部类型" value="" />
            <el-option label="自然风光" value="nature" />
            <el-option label="历史古迹" value="history" />
            <el-option label="主题公园" value="theme_park" />
            <el-option label="博物馆" value="museum" />
            <el-option label="海滩度假" value="beach" />
            <el-option label="城市观光" value="city" />
          </el-select>
        </el-col>
        <el-col :span="6">
          <el-select v-model="filters.season" placeholder="最佳季节" class="filter-select">
            <el-option label="全部季节" value="" />
            <el-option label="春季" value="spring" />
            <el-option label="夏季" value="summer" />
            <el-option label="秋季" value="autumn" />
            <el-option label="冬季" value="winter" />
          </el-select>
        </el-col>
        <el-col :span="6">
          <el-select v-model="sortBy" placeholder="排序方式" class="filter-select">
            <el-option label="推荐排序" value="recommended" />
            <el-option label="热门程度" value="popularity" />
            <el-option label="评分最高" value="rating" />
          </el-select>
        </el-col>
      </el-row>
    </div>
    
    <div class="container main-content">
      <h2 class="section-title">热门目的地</h2>
      <el-row :gutter="20">
        <el-col :sm="12" :md="8" v-for="destination in destinations" :key="destination.id">
          <el-card class="destination-card" shadow="hover">
            <img :src="destination.image" class="destination-image" />
            <div class="destination-info">
              <h3>{{ destination.name }}</h3>
              <div class="destination-rating">
                <el-rate v-model="destination.rating" disabled :colors="['#ffd21e', '#ffd21e', '#ffd21e']" />
                <span>({{ destination.reviewCount || 0 }}条评论)</span>
              </div>
              <p>{{ destination.description }}</p>
              <el-button type="primary" size="small">了解更多</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
      
      <h2 class="section-title" style="margin-top: 60px;">推荐景点</h2>
      <el-row :gutter="20">
        <el-col :span="24" v-for="attraction in attractions" :key="attraction.id">
          <el-card class="attraction-card" shadow="hover">
            <div class="attraction-content">
              <div class="attraction-image-container">
                <img :src="attraction.image" class="attraction-image" />
                <div class="attraction-price" v-if="attraction.price">
                  ¥{{ attraction.price }}起
                </div>
              </div>
              <div class="attraction-details">
                <h3>{{ attraction.name }}</h3>
                <div class="attraction-meta">
                  <span class="location">{{ attraction.location }}</span>
                  <span class="category">{{ attraction.category }}</span>
                </div>
                <div class="attraction-rating">
                  <el-rate v-model="attraction.rating" disabled :colors="['#ffd21e', '#ffd21e', '#ffd21e']" />
                  <span class="rating-text">{{ attraction.rating.toFixed(1) }}</span>
                  <span class="reviews">({{ attraction.reviewCount }}条点评)</span>
                </div>
                <p class="attraction-description">{{ attraction.description }}</p>
                <div class="attraction-tags">
                  <el-tag v-for="(tag, index) in attraction.features" :key="index" size="small">{{ tag }}</el-tag>
                </div>
                <div class="attraction-actions">
                  <el-button type="primary" size="small">查看详情</el-button>
                  <el-button size="small">
                    <el-icon><star /></el-icon>
                    收藏
                  </el-button>
                </div>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      
      <div class="pagination-container">
        <el-pagination
          background
          layout="prev, pager, next"
          :total="50"
          :current-page="currentPage"
          @current-change="handlePageChange"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import AppHeader from '../components/AppHeader.vue'
import { Search, Star } from '@element-plus/icons-vue'

// 搜索相关
const searchQuery = ref('')
const handleSearch = () => {
  console.log('搜索查询:', searchQuery.value)
  // 实际应用中这里会调用API获取搜索结果
}

// 筛选相关
const filters = reactive({
  region: '',
  category: '',
  season: '',
})

const sortBy = ref('recommended')
const currentPage = ref(1)

const handlePageChange = (page) => {
  currentPage.value = page
  console.log('页面改变:', page)
  // 实际应用中这里会加载对应页的数据
}

// 目的地数据
const destinations = ref([
  {
    id: 1,
    name: '东京',
    description: '现代与传统并存的国际大都市，体验日本文化与现代科技的完美融合。',
    image: 'https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1374&q=80',
    rating: 4.7,
    reviewCount: 12453,
  },
  {
    id: 2,
    name: '巴黎',
    description: '浪漫之都，艺术、时尚与美食的代表，感受独特的法式生活方式。',
    image: 'https://images.unsplash.com/photo-1502602898657-3e91760cbb34?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1473&q=80',
    rating: 4.5,
    reviewCount: 10876,
  },
  {
    id: 3,
    name: '新加坡',
    description: '现代化的花园城市，多元文化与美食天堂，干净整洁的城市环境。',
    image: 'https://images.unsplash.com/photo-1565967511849-76a60a516170?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1471&q=80',
    rating: 4.6,
    reviewCount: 9234,
  }
])

// 景点数据
const attractions = ref([
  {
    id: 1,
    name: '长城',
    location: '中国 北京',
    category: '历史古迹',
    description: '中国古代伟大的防御工程，全长超过2万公里，历史跨越2000多年。登上长城可以欣赏到壮观的山脉景色。',
    image: 'https://images.unsplash.com/photo-1508804185872-d7badad00f7d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80',
    rating: 4.9,
    reviewCount: 12547,
    price: 150,
    features: ['世界遗产', '户外活动', '历史文化']
  },
  {
    id: 2,
    name: '富士山',
    location: '日本 静冈县',
    category: '自然风光',
    description: '日本的象征和最高峰，被誉为"日本第一山"，是世界文化遗产。四季景色各异，春樱秋枫，冬雪夏绿。',
    image: 'https://images.unsplash.com/photo-1578637387939-43c525550085?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80',
    rating: 4.8,
    reviewCount: 5268,
    price: 300,
    features: ['摄影胜地', '户外活动', '世界遗产']
  }
])
</script>

<style scoped>
.discover-page {
  width: 100%;
}

.discover-hero {
  background: linear-gradient(135deg, #1c92d2 0%, #f2fcfe 100%);
  padding: 60px 0;
  color: white;
  text-align: center;
  width: 100%;
  margin-top: 64px;
}

.container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.page-title {
  font-size: 2.5rem;
  margin-bottom: 16px;
  font-weight: 600;
}

.page-subtitle {
  font-size: 1.2rem;
  opacity: 0.9;
  max-width: 600px;
  margin: 0 auto 30px;
}

.search-container {
  max-width: 600px;
  margin: 0 auto;
  display: flex;
  gap: 10px;
}

.search-input {
  flex-grow: 1;
}

.filter-section {
  background-color: white;
  padding: 20px;
  margin-top: -30px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  position: relative;
  z-index: 10;
}

.filter-select {
  width: 100%;
}

.main-content {
  padding: 40px 0;
}

.section-title {
  font-size: 1.8rem;
  margin-bottom: 30px;
  font-weight: 600;
  text-align: center;
}

.destination-card {
  margin-bottom: 20px;
  overflow: hidden;
  height: 100%;
  transition: transform 0.3s, box-shadow 0.3s;
}

.destination-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
}

.destination-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
  display: block;
}

.destination-info {
  padding: 16px;
}

.destination-info h3 {
  margin-top: 0;
  font-size: 1.3rem;
  margin-bottom: 10px;
  font-weight: 600;
}

.destination-rating {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 12px;
}

.destination-rating span {
  font-size: 0.8rem;
  color: #909399;
}

.destination-info p {
  color: #606266;
  margin-bottom: 16px;
  line-height: 1.5;
}

.attraction-card {
  margin-bottom: 20px;
  overflow: hidden;
}

.attraction-content {
  display: flex;
}

.attraction-image-container {
  flex: 0 0 240px;
  position: relative;
}

.attraction-image {
  width: 240px;
  height: 180px;
  object-fit: cover;
}

.attraction-price {
  position: absolute;
  bottom: 12px;
  right: 12px;
  background-color: rgba(0, 0, 0, 0.7);
  color: white;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 0.8rem;
}

.attraction-details {
  flex: 1;
  padding: 16px;
}

.attraction-details h3 {
  margin-top: 0;
  font-size: 1.3rem;
  margin-bottom: 8px;
  font-weight: 600;
}

.attraction-meta {
  display: flex;
  gap: 16px;
  margin-bottom: 8px;
}

.attraction-meta span {
  font-size: 0.9rem;
  color: #606266;
}

.attraction-rating {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;
}

.rating-text {
  font-weight: 500;
  color: #ff9900;
}

.reviews {
  font-size: 0.8rem;
  color: #909399;
}

.attraction-description {
  color: #606266;
  margin-bottom: 12px;
  line-height: 1.6;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.attraction-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 16px;
}

.attraction-actions {
  display: flex;
  gap: 12px;
}

.pagination-container {
  margin-top: 40px;
  display: flex;
  justify-content: center;
}

@media (max-width: 768px) {
  .page-title {
    font-size: 2rem;
  }
  
  .page-subtitle {
    font-size: 1rem;
  }
  
  .search-container {
    flex-direction: column;
  }
  
  .attraction-content {
    flex-direction: column;
  }
  
  .attraction-image-container {
    width: 100%;
  }
  
  .attraction-image {
    width: 100%;
    height: 200px;
  }
}
</style> 