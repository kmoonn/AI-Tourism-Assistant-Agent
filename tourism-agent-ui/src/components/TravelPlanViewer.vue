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
        <el-button type="success" @click="openInNewTab">在新标签页打开</el-button>
      </div>
      
      <!-- 使用iframe显示HTML内容 -->
      <iframe v-if="iframeUrl" :src="iframeUrl" class="html-iframe" ref="htmlIframe"></iframe>
      
      <div v-if="!iframeUrl" class="debug-info">
        <p>调试信息: HTML内容存在但未创建iframe URL</p>
        <p>HTML内容长度: {{ htmlContent.length }}</p>
      </div>
    </div>
    
    <div v-else class="empty-container">
      <el-empty description="暂无旅行计划" />
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted, computed } from 'vue'
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

const htmlIframe = ref(null)
const iframeUrl = ref('')

// 监听htmlContent变化，创建Blob URL
watch(() => props.htmlContent, (newContent) => {
  console.log('TravelPlanViewer接收到HTML内容:', newContent ? newContent.substring(0, 100) + '...' : '无内容')
  
  if (newContent) {
    console.log('创建Blob URL');
    try {
      // 创建一个Blob对象，用于生成URL
      const blob = new Blob([newContent], { type: 'text/html' });
      
      // 如果之前有创建过URL，先释放它
      if (iframeUrl.value && iframeUrl.value.startsWith('blob:')) {
        URL.revokeObjectURL(iframeUrl.value);
      }
      
      // 创建新的URL
      iframeUrl.value = URL.createObjectURL(blob);
      console.log('Blob URL创建成功:', iframeUrl.value);
    } catch (error) {
      console.error('创建Blob URL时出错:', error);
    }
  } else {
    iframeUrl.value = '';
  }
}, { immediate: true })

// 组件卸载时，释放Blob URL
onMounted(() => {
  console.log('TravelPlanViewer组件挂载');
})

// 组件卸载时，释放Blob URL
const beforeUnmount = () => {
  if (iframeUrl.value && iframeUrl.value.startsWith('blob:')) {
    URL.revokeObjectURL(iframeUrl.value);
  }
}

// 打印行程（通过iframe打印）
const printPlan = () => {
  if (htmlIframe.value) {
    htmlIframe.value.contentWindow.print();
  } else {
    window.print();
  }
}

// 在新标签页中打开
const openInNewTab = () => {
  if (iframeUrl.value) {
    window.open(iframeUrl.value, '_blank');
  }
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

.html-iframe {
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  background-color: white;
  min-height: 700px;
  width: 100%;
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
}
</style> 