import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'node:path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    proxy: {
      // 代理/travel/chat接口到后端服务器
      '/travel/chat': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    },
    fs: {
      // 允许访问项目根目录之外的文件
      allow: ['..']
    }
  },
  // 不使用外部public目录，使用项目内的public目录
  publicDir: 'public'
})
