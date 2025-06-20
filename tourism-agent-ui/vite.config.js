import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

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
      '/travel/chat': {
        target: 'http://localhost:8080/travel/chat',
        changeOrigin: true
      }
    },
    fs: {
      allow: ['..']
    }
  },
  publicDir: 'public'
})
