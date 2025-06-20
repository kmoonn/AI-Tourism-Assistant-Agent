<template>
  <div class="plan-page">
    <app-header />
    
    <div class="plan-hero">
      <div class="container">
        <h1 class="page-title">AI 帮你制作旅行攻略</h1>
        <p class="page-subtitle">输入旅行信息，让AI为您定制完美行程</p>
      </div>
    </div>
    
    <div class="plan-container container">
      <el-card v-if="!showPlanResult" class="trip-form-card">
        <template #header>
          <div class="card-header">
            <h2>创建您的旅行计划</h2>
          </div>
        </template>
        
        <el-form :model="tripForm" label-position="top">
          <el-form-item label="目的地" required>
            <el-input
              v-model="tripForm.destination"
              placeholder="请输入旅行目的地"
              class="full-width"
            />
          </el-form-item>
          
          <el-form-item label="旅行日期" required>
            <el-date-picker
              v-model="tripForm.dateRange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              class="full-width"
              :disabled-date="disablePastDates"
            />
          </el-form-item>
          
          <el-form-item label="旅行风格/兴趣偏好" required>
            <el-select
              v-model="tripForm.travelStyle"
              multiple
              placeholder="选择您的旅行风格"
              class="full-width"
            >
              <el-option label="历史文化" value="历史文化" />
              <el-option label="美食体验" value="美食体验" />
              <el-option label="自然风光" value="自然风光" />
              <el-option label="购物" value="购物" />
              <el-option label="冒险活动" value="冒险活动" />
              <el-option label="放松休闲" value="放松休闲" />
              <el-option label="亲子游" value="亲子游" />
              <el-option label="摄影" value="摄影" />
            </el-select>
          </el-form-item>
          
          <el-form-item label="特殊要求或偏好">
            <el-input
              v-model="tripForm.specialRequests"
              type="textarea"
              placeholder="有任何特殊要求请在此说明，如儿童友好、无障碍设施等"
              :rows="3"
            />
          </el-form-item>
          
          <el-form-item>
            <el-button type="primary" @click="generatePlan" class="generate-btn" :loading="isGenerating">生成旅行攻略</el-button>
          </el-form-item>
        </el-form>
      </el-card>
      
      <div v-if="showPlanResult" class="plan-result-container">
        <el-card class="plan-result-card">
          <template #header>
            <div class="card-header">
              <h2>{{ tripForm.destination }}旅行攻略</h2>
              <span class="trip-duration">{{ formatDateRange }}</span>
            </div>
          </template>
          
          <travel-plan-viewer
            :loading="isGenerating"
            :html-content="planHtmlContent"
            :error="planError"
            @retry="generatePlan"
            @back="showPlanResult = false"
          />
        </el-card>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { ElMessage } from 'element-plus'
import dayjs from 'dayjs'
import AppHeader from '../components/AppHeader.vue'
import TravelPlanViewer from '../components/TravelPlanViewer.vue'
import { generateTravelPlan, getTravelHtml } from '../services/api'

// 表单数据
const tripForm = ref({
  destination: '',
  dateRange: [
    dayjs().add(1, 'day').toDate(),
    dayjs().add(5, 'day').toDate()
  ],
  travelStyle: [],
  specialRequests: ''
})

// 禁用过去的日期
const disablePastDates = (date) => {
  return date < dayjs().startOf('day')
}

// 生成计划相关状态
const isGenerating = ref(false)
const showPlanResult = ref(false)
const planHtmlContent = ref('')
const planError = ref('')

// 格式化日期范围显示
const formatDateRange = computed(() => {
  if (!tripForm.value.dateRange || !tripForm.value.dateRange[0] || !tripForm.value.dateRange[1]) {
    return '';
  }
  
  const start = dayjs(tripForm.value.dateRange[0]).format('YYYY年MM月DD日')
  const end = dayjs(tripForm.value.dateRange[1]).format('YYYY年MM月DD日')
  return `${start} 至 ${end}`
})

// 生成旅行计划
const generatePlan = async () => {
  // 表单验证
  if (!tripForm.value.destination) {
    ElMessage.error('请输入目的地')
    return
  }
  
  if (!tripForm.value.dateRange || !tripForm.value.dateRange[0] || !tripForm.value.dateRange[1]) {
    ElMessage.error('请选择旅行日期')
    return
  }
  
  if (tripForm.value.travelStyle.length === 0) {
    ElMessage.error('请选择至少一项旅行风格/兴趣偏好')
    return
  }
  
  // 设置状态
  isGenerating.value = true
  showPlanResult.value = true
  planHtmlContent.value = ''
  planError.value = ''
  
  try {
    // 准备参数
    const destination = tripForm.value.destination
    const travelDates = `${dayjs(tripForm.value.dateRange[0]).format('YYYY年MM月DD日')} 至 ${dayjs(tripForm.value.dateRange[1]).format('YYYY年MM月DD日')}`
    const preferences = tripForm.value.travelStyle.join('、') + (tripForm.value.specialRequests ? '、' + tripForm.value.specialRequests : '')
    
    // 开发环境：直接获取预设的HTML内容
    if (import.meta.env.DEV) {
      try {
        const html = await getTravelHtml()
        planHtmlContent.value = html
        isGenerating.value = false
      } catch (error) {
        planError.value = error.message || '获取旅游规划失败，请重试'
        isGenerating.value = false
      }
      return
    }
    
    // 生产环境：调用API生成旅游规划
    await generateTravelPlan(destination, travelDates, preferences)
    
    // 轮询获取生成的HTML文件，最多尝试10次
    let retries = 0
    const maxRetries = 10
    const interval = 10000 // 10秒检查一次
    
    const checkHtml = async () => {
      try {
        const html = await getTravelHtml()
        planHtmlContent.value = html
        isGenerating.value = false
        clearInterval(checkInterval)
      } catch (error) {
        retries++
        if (retries >= maxRetries) {
          planError.value = '获取旅游规划超时，请重试'
          isGenerating.value = false
          clearInterval(checkInterval)
        }
      }
    }
    
    // 开始轮询
    const checkInterval = setInterval(checkHtml, interval)
    
    // 立即执行一次检查
    await checkHtml()
    
  } catch (error) {
    console.error('生成旅游规划失败:', error)
    planError.value = error.message || '生成旅游规划失败，请重试'
    isGenerating.value = false
  }
}
</script>

<style scoped>
.plan-page {
  padding-top: 64px;
}

.plan-hero {
  background: linear-gradient(135deg, #1c92d2 0%, #f2fcfe 100%);
  padding: 60px 0;
  color: white;
  text-align: center;
}

.page-title {
  font-size: 2.5rem;
  margin-bottom: 16px;
}

.page-subtitle {
  font-size: 1.2rem;
  opacity: 0.9;
  max-width: 600px;
  margin: 0 auto;
}

.plan-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px 20px;
}

.trip-form-card {
  width: 100%;
  max-width: 800px;
  margin: 0 auto;
}

.plan-result-container {
  width: 100%;
}

.plan-result-card {
  margin-bottom: 30px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-header h2 {
  font-size: 1.5rem;
  margin: 0;
  color: #303133;
}

.trip-duration {
  color: #409EFF;
  font-weight: 500;
}

.full-width {
  width: 100%;
}

.generate-btn {
  width: 100%;
  height: 44px;
  font-size: 16px;
  font-weight: 500;
}
</style> 