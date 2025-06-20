<template>
  <div class="login-page">
    <app-header />
    
    <div class="auth-container">
      <div class="auth-card">
        <el-tabs v-model="activeTab" class="auth-tabs">
          <el-tab-pane label="登录" name="login">
            <h2 class="auth-title">欢迎回来</h2>
            <p class="auth-subtitle">登录您的账号，探索更多旅行可能</p>
            
            <el-form :model="loginForm" :rules="loginRules" ref="loginFormRef" @submit.prevent="handleLogin">
              <el-form-item prop="email">
                <el-input 
                  v-model="loginForm.email" 
                  placeholder="邮箱" 
                  prefix-icon="message"
                />
              </el-form-item>
              
              <el-form-item prop="password">
                <el-input 
                  v-model="loginForm.password" 
                  placeholder="密码" 
                  prefix-icon="lock" 
                  show-password
                />
              </el-form-item>
              
              <div class="remember-forgot">
                <el-checkbox v-model="loginForm.remember">记住我</el-checkbox>
                <el-button text @click="forgotPassword">忘记密码？</el-button>
              </div>
              
              <el-button type="primary" native-type="submit" class="auth-button" :loading="loading">
                登录
              </el-button>
            </el-form>
            
            <div class="divider">
              <span>或使用以下方式登录</span>
            </div>
            
            <div class="social-login">
              <el-button class="social-button wechat">
                <i class="fab fa-weixin"></i> 微信登录
              </el-button>
              <el-button class="social-button qq">
                <i class="fab fa-qq"></i> QQ登录
              </el-button>
            </div>
          </el-tab-pane>
          
          <el-tab-pane label="注册" name="register">
            <h2 class="auth-title">创建账号</h2>
            <p class="auth-subtitle">注册成为会员，获取专属旅行方案</p>
            
            <el-form :model="registerForm" :rules="registerRules" ref="registerFormRef" @submit.prevent="handleRegister">
              <el-form-item prop="username">
                <el-input 
                  v-model="registerForm.username" 
                  placeholder="用户名" 
                  prefix-icon="user"
                />
              </el-form-item>
              
              <el-form-item prop="email">
                <el-input 
                  v-model="registerForm.email" 
                  placeholder="邮箱" 
                  prefix-icon="message"
                />
              </el-form-item>
              
              <el-form-item prop="password">
                <el-input 
                  v-model="registerForm.password" 
                  placeholder="密码" 
                  prefix-icon="lock" 
                  show-password
                />
              </el-form-item>
              
              <el-form-item prop="confirmPassword">
                <el-input 
                  v-model="registerForm.confirmPassword" 
                  placeholder="确认密码" 
                  prefix-icon="lock" 
                  show-password
                />
              </el-form-item>
              
              <el-form-item prop="agreement">
                <el-checkbox v-model="registerForm.agreement">
                  我已阅读并同意<el-button text type="primary">服务条款</el-button>和<el-button text type="primary">隐私政策</el-button>
                </el-checkbox>
              </el-form-item>
              
              <el-button type="primary" native-type="submit" class="auth-button" :loading="loading">
                注册
              </el-button>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </div>
      
      <div class="auth-image">
        <div class="image-overlay">
          <h2>探索世界的奇妙之旅</h2>
          <p>加入我们，获取专业旅行规划和独家优惠</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { User, Message, Lock } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import AppHeader from '../components/AppHeader.vue'

const router = useRouter()
const activeTab = ref('login')
const loading = ref(false)

// 登录表单
const loginFormRef = ref(null)
const loginForm = reactive({
  email: '',
  password: '',
  remember: false
})

const loginRules = {
  email: [
    { required: true, message: '请输入邮箱地址', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6个字符', trigger: 'blur' }
  ]
}

// 注册表单
const registerFormRef = ref(null)
const registerForm = reactive({
  username: '',
  email: '',
  password: '',
  confirmPassword: '',
  agreement: false
})

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== registerForm.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const registerRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度在2-20个字符之间', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱地址', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ],
  agreement: [
    { 
      validator: (rule, value, callback) => {
        if (!value) {
          callback(new Error('请同意服务条款和隐私政策'))
        } else {
          callback()
        }
      }, 
      trigger: 'change' 
    }
  ]
}

// 登录处理
const handleLogin = async () => {
  if (!loginFormRef.value) return
  
  await loginFormRef.value.validate((valid) => {
    if (!valid) return
    
    loading.value = true
    
    // 模拟API调用
    setTimeout(() => {
      loading.value = false
      ElMessage.success('登录成功')
      router.push('/')
    }, 1500)
  })
}

// 注册处理
const handleRegister = async () => {
  if (!registerFormRef.value) return
  
  await registerFormRef.value.validate((valid) => {
    if (!valid) return
    
    loading.value = true
    
    // 模拟API调用
    setTimeout(() => {
      loading.value = false
      ElMessage.success('注册成功，请登录')
      activeTab.value = 'login'
      
      // 清空表单
      registerForm.username = ''
      registerForm.email = ''
      registerForm.password = ''
      registerForm.confirmPassword = ''
      registerForm.agreement = false
    }, 1500)
  })
}

// 忘记密码
const forgotPassword = () => {
  ElMessage.info('重置密码功能即将上线')
}
</script>

<style scoped>
.login-page {
  padding-top: 64px;
  min-height: 100vh;
  background-color: #f8f9fa;
}

.auth-container {
  display: flex;
  max-width: 1000px;
  margin: 60px auto;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
}

.auth-card {
  background-color: white;
  padding: 40px;
  width: 50%;
}

.auth-tabs {
  width: 100%;
}

.auth-title {
  font-size: 1.8rem;
  font-weight: 600;
  margin-bottom: 8px;
  color: #303133;
}

.auth-subtitle {
  color: #606266;
  margin-bottom: 24px;
}

.remember-forgot {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.auth-button {
  width: 100%;
  height: 44px;
  font-size: 16px;
  margin-top: 10px;
}

.divider {
  position: relative;
  text-align: center;
  margin: 30px 0;
}

.divider:before {
  content: '';
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;
  height: 1px;
  background-color: #ebeef5;
  z-index: 1;
}

.divider span {
  position: relative;
  background-color: white;
  padding: 0 16px;
  color: #909399;
  font-size: 14px;
  z-index: 2;
}

.social-login {
  display: flex;
  gap: 16px;
}

.social-button {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.social-button.wechat {
  background-color: #07c160;
  color: white;
  border: none;
}

.social-button.qq {
  background-color: #12b7f5;
  color: white;
  border: none;
}

.auth-image {
  width: 50%;
  background-image: url('https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80');
  background-size: cover;
  background-position: center;
  position: relative;
}

.image-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.7));
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 40px;
  color: white;
}

.image-overlay h2 {
  font-size: 2rem;
  margin-bottom: 16px;
}

.image-overlay p {
  font-size: 1.1rem;
  opacity: 0.9;
}

@media (max-width: 768px) {
  .auth-container {
    flex-direction: column-reverse;
    margin: 20px;
  }
  
  .auth-card, .auth-image {
    width: 100%;
  }
  
  .auth-image {
    height: 200px;
  }
  
  .image-overlay {
    padding: 20px;
  }
  
  .image-overlay h2 {
    font-size: 1.5rem;
  }
}
</style> 