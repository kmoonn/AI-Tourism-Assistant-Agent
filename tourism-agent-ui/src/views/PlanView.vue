<template>
  <div class="plan-page">
    <app-header />
    
    <div class="plan-hero">
      <div class="container">
        <h1 class="page-title">AI 智能体帮你制作旅行攻略</h1>
        <p class="page-subtitle">输入您的旅行计划，让AI为您定制完美行程</p>
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
              <h2>您的旅行攻略已生成，请查收~</h2>
              <br>
              <span class="trip-duration">{{ formatDateRange }}</span>
            </div>
            <span style="color: #999;"> 由于MCP不稳定以及ApiKey限制，仅显示示例数据。<br>
              如需查看完整攻略，请点击"在新标签页打开"。</span>
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
  
  console.log('开始生成旅行计划，showPlanResult:', showPlanResult.value)
  
  try {
    // 首先尝试调用API生成旅游规划
    try {
      await generateTravelPlan(tripForm.value.destination, `${dayjs(tripForm.value.dateRange[0]).format('YYYY年MM月DD日')} 至 ${dayjs(tripForm.value.dateRange[1]).format('YYYY年MM月DD日')}`, tripForm.value.travelStyle.join('、') + (tripForm.value.specialRequests ? '、' + tripForm.value.specialRequests : ''))
      console.log('旅游规划API调用成功')
    } catch (apiError) {
      console.warn('API调用失败，将使用本地示例文件:', apiError)
    }
    
    // 无论API调用成功与否，都尝试获取HTML内容
    try {
      // 尝试获取HTML内容
      const html = await getTravelHtml()
      console.log('获取到的HTML内容:', html.substring(0, 100) + '...')
      
      // 如果获取失败或内容为空，使用硬编码的HTML
      if (!html || html.trim() === '') {
        console.log('获取的HTML为空，使用硬编码的HTML');
        planHtmlContent.value = `
        <!DOCTYPE html>
        <html>
        <head>
          <meta charset="UTF-8">
          <title>上海旅行攻略</title>
          <style>
            body { font-family: 'Microsoft YaHei', sans-serif; }
            .container { max-width: 800px; margin: 0 auto; padding: 20px; }
            h1 { color: #409EFF; }
          </style>
        </head>
        <body>
          <div class="container">
            <h1>上海旅行攻略</h1>
            <p>这是一个测试用的旅行攻略HTML内容</p>
            <h2>行程安排</h2>
            <ul>
              <li>第一天：外滩、南京路</li>
              <li>第二天：迪士尼乐园</li>
              <li>第三天：田子坊、豫园</li>
            </ul>
          </div>
        </body>
        </html>
        `;
      } else {
        planHtmlContent.value = html;
      }
      
      isGenerating.value = false
      console.log('设置完HTML内容，showPlanResult:', showPlanResult.value, '内容长度:', planHtmlContent.value.length)
    } catch (error) {
      console.error('获取HTML内容失败:', error)
      planError.value = error.message || '获取旅游规划失败，请重试'
      isGenerating.value = false
    }
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