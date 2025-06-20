<template>
  <header class="app-header">
    <div class="container">
      <div class="logo">
        <router-link to="/">
          <img src="../assets/logo.svg" alt="旅游助手" />
        </router-link>
      </div>
      <nav class="main-nav">
        <router-link to="/" class="nav-item">首页</router-link>
        <router-link to="/discover" class="nav-item">发现</router-link>
        <router-link to="/plan" class="nav-item">攻略</router-link>
      </nav>
      <div class="user-actions">        
        <router-link to="/login" class="login-btn">登录 / 注册</router-link>
      </div>
    </div>
  </header>
</template>

<style scoped>
.app-header {
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 16px 0;
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 100;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo img {
  height: 32px;
}

.main-nav {
  display: flex;
  gap: 24px;
}

.nav-item {
  color: #606266;
  text-decoration: none;
  font-weight: 500;
  padding: 8px 0;
  position: relative;
  transition: color 0.3s;
}

.nav-item:hover, .nav-item.router-link-active {
  color: #409EFF;
}

.nav-item.router-link-active:after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 2px;
  background-color: #409EFF;
}

.user-actions {
  display: flex;
  align-items: center;
  gap: 20px;
}

.lang-selector {
  display: flex;
  align-items: center;
  gap: 4px;
  cursor: pointer;
  font-size: 14px;
  color: #606266;
}

.login-btn {
  background-color: #409EFF;
  color: white;
  padding: 8px 16px;
  border-radius: 4px;
  text-decoration: none;
  transition: background-color 0.3s;
}

.login-btn:hover {
  background-color: #66b1ff;
}

@media (max-width: 768px) {
  .main-nav {
    display: none;
  }
}
</style> 