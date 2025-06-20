import axios from 'axios';

// 创建axios实例
const apiClient = axios.create({
  baseURL: 'http://localhost:8080', // 后端API地址
  headers: {
    'Content-Type': 'application/json'
  },
  timeout: 300000 // 设置超时时间为5分钟，因为生成行程可能需要较长时间
});

// 生成旅游规划的API函数
export async function generateTravelPlan(destination, travelDates, preferences) {
  // 构建prompt文本
  const prompt = `
你好，我需要你为我策划一份详尽的${destination}的旅游攻略。
以下是我的具体需求，请仔细阅读并融入到最终攻略中：
1. 核心旅行参数：
 期望出行时间：${travelDates}
 目的地具体名称：${destination}
2. 行程风格与偏好：
 兴趣偏好：${preferences}
`;

  try {
    // 调用后端API
    const response = await apiClient.get('/travel/chat', {
      params: { content: prompt }
    });
    return response.data;
  } catch (error) {
    console.error('生成旅游规划失败:', error);
    throw error;
  }
}

// 获取生成的HTML文件内容
export async function getTravelHtml() {
  try {
    // 开发环境使用本地示例文件
    if (import.meta.env.DEV) {
      // 使用fetch直接获取public目录下的文件
      const response = await fetch('/travel.html');
      if (!response.ok) {
        throw new Error('获取旅游规划HTML失败');
      }
      return await response.text();
    } else {
      // 生产环境从后端获取
      const response = await fetch('/travel.html');
      if (!response.ok) {
        throw new Error('获取旅游规划HTML失败');
      }
      return await response.text();
    }
  } catch (error) {
    // 如果获取失败，使用默认内容
    console.error('获取旅游规划HTML失败，使用备用内容:', error);
    
    // 返回一个简单的错误提示HTML
    return `
    <!DOCTYPE html>
    <html>
    <head>
      <meta charset="UTF-8">
      <title>加载失败</title>
      <style>
        body { font-family: 'Microsoft YaHei', sans-serif; text-align: center; padding: 50px; }
        .error-container { max-width: 600px; margin: 0 auto; }
        h1 { color: #f56c6c; }
        p { font-size: 16px; line-height: 1.6; }
        .retry-btn { 
          background-color: #409eff; 
          color: white; 
          border: none; 
          padding: 10px 20px; 
          border-radius: 4px; 
          cursor: pointer; 
          font-size: 16px; 
          margin-top: 20px;
        }
      </style>
    </head>
    <body>
      <div class="error-container">
        <h1>旅游规划加载失败</h1>
        <p>很抱歉，无法加载旅游规划内容。这可能是因为后端服务未启动或网络连接问题。</p>
        <p>您可以尝试刷新页面或稍后再试。</p>
        <button class="retry-btn" onclick="window.location.reload()">重试</button>
      </div>
    </body>
    </html>
    `;
  }
} 