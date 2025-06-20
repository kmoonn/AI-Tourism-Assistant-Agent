<template>
  <div class="travel-plan-viewer">
    <div v-if="loading" class="loading-container">
      <el-skeleton :rows="15" animated />
      <div class="generating-message">
        <el-icon class="rotating"><loading /></el-icon>
        <p>AI正在为您生成个性化旅行攻略，这可能需要几分钟时间...</p>
      </div>
    </div>
    
    <div v-else-if="error" class="error-container">
      <el-result icon="error" title="生成失败" :sub-title="error">
        <template #extra>
          <el-button type="primary" @click="$emit('retry')">重试</el-button>
        </template>
      </el-result>
    </div>
    
    <div v-else-if="htmlContent" class="plan-content">
      <div class="plan-actions">
        <el-button type="primary" @click="printPlan">打印行程</el-button>
        <el-button @click="$emit('back')">返回编辑</el-button>
      </div>
      <div class="html-container" ref="htmlContainer"></div>
    </div>
    
    <div v-else class="empty-container">
      <el-empty description="暂无旅行计划" />
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { Loading } from '@element-plus/icons-vue'

const props = defineProps({
  loading: {
    type: Boolean,
    default: false
  },
  htmlContent: {
    type: String,
    default: ''
  },
  error: {
    type: String,
    default: ''
  }
})

defineEmits(['retry', 'back'])

const htmlContainer = ref(null)

// 监听htmlContent变化，更新内容
watch(() => props.htmlContent, (newContent) => {
  if (newContent && htmlContainer.value) {
    htmlContainer.value.innerHTML = newContent
  }
}, { immediate: true })

// 组件挂载后，如果有内容则显示
onMounted(() => {
  if (props.htmlContent && htmlContainer.value) {
    htmlContainer.value.innerHTML = props.htmlContent
  }
})

// 打印行程
const printPlan = () => {
  window.print()
}
</script>

<style scoped>
.travel-plan-viewer {
  width: 100%;
  min-height: 500px;
}

.loading-container {
  position: relative;
  min-height: 400px;
}

.generating-message {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  color: #409EFF;
  padding: 20px;
}

.rotating {
  animation: rotate 2s linear infinite;
  font-size: 24px;
  margin-bottom: 10px;
}

@keyframes rotate {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

.error-container {
  min-height: 400px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.plan-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.plan-actions {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.html-container {
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  padding: 20px;
  background-color: white;
  min-height: 500px;
}

.empty-container {
  min-height: 400px;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 打印样式 */
@media print {
  .plan-actions {
    display: none;
  }
  
  .html-container {
    border: none;
    padding: 0;
  }
}
</style> 