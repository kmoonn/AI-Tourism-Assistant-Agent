# AI-Tourism-Assistant-Agent

基于 Spring AI 和 Langchain4j 的旅游行程规划智能体

一个基于人工智能的旅游规划助手，帮助用户快速生成个性化的旅游攻略。通过简单的输入目的地、出行时间和个人偏好，AI会为您定制详细的旅行计划。

## 项目特点

- 🌍 **智能旅游规划**：根据目的地、时间和偏好自动生成旅游攻略
- 🎯 **个性化推荐**：考虑用户兴趣和偏好，提供定制化的旅游建议
- 📱 **响应式设计**：适配各种设备屏幕，提供良好的移动端体验
- 🖨️ **打印与分享**：支持打印旅游计划或在新标签页中查看分享
- ⚡ **快速生成**：高效的AI处理，快速生成详细的旅游规划
- 🌤️ **天气集成**：提供目的地实时天气信息，帮助旅行规划

## 项目结构

```
AI-Tourism-Assistant-Agent/
├── tourism-agent-client/    # 智能体客户端（后端核心）
├── tourism-weather-server/  # 和风天气 MCP 服务
└── tourism-agent-ui/        # 前端 UI 界面
```

## 子项目介绍

### tourism-agent-client

智能体客户端是项目的核心后端服务，负责处理用户请求并生成旅游规划。

- **主要功能**：
  - 基于用户输入生成个性化旅游行程
  - 与多种大语言模型集成（OpenAI、Anthropic、Google Gemini）
  - 提供RESTful API接口供前端调用
  - 集成MCP（Model Composition Protocol）服务

- **技术栈**：
  - Spring Boot 3.4.5
  - LangChain4j 1.0.1
  - Spring Web
  - Lombok

### tourism-weather-server

和风天气MCP服务是一个独立的微服务，负责提供目的地的天气数据。

- **主要功能**：
  - 提供目的地实时天气数据
  - 基于Spring AI的MCP服务实现
  - 集成和风天气API

- **技术栈**：
  - Spring Boot 3.4.6
  - Spring AI 1.1.0
  - Spring WebFlux
  - Hutool 5.8.29

### tourism-agent-ui

前端UI界面，为用户提供直观友好的操作界面。

- **主要功能**：
  - 用户输入界面（目的地、时间、偏好）
  - 展示生成的旅游攻略
  - 响应式设计，适配各种设备
  - 支持打印与分享功能

- **技术栈**：
  - Vue 3.5.13
  - Element Plus 2.10.2
  - Pinia 3.0.1
  - Vue Router 4.5.1
  - Axios 1.10.0
  - Dayjs 1.11.13
  - Vite 6.2.4

## 前端技术栈

- **前端框架**：Vue 3 + Vite
- **UI组件库**：Element Plus
- **状态管理**：Pinia
- **路由管理**：Vue Router
- **HTTP请求**：Axios
- **日期处理**：Day.js

## 后端技术栈

- **Spring AI**：Spring AI 是 Spring 框架的 AI 模块，提供了丰富的 AI 功能和集成，包括MCP服务实现
- **LangChain4j**：LangChain4j 是一个基于 Java 的 AI 框架，提供了丰富的 AI 功能和集成，支持多种大语言模型
- **Spring Boot**：Spring Boot 是 Spring 框架的快速开发框架，提供了丰富的功能和集成
- **Spring Web**：Spring Web 是 Spring 框架的 Web 模块，提供了丰富的 Web 功能和集成
- **Spring WebFlux**：Spring WebFlux 是 Spring 框架的响应式 Web 模块，提供了非阻塞的 Web 功能

## 项目依赖

- **和风天气 API**：和风天气 API 是和风天气提供的一个天气数据服务，提供了丰富的天气数据和集成
- **高德地图 MCP 服务**：高德地图 MCP 服务是高德地图提供的一个地图数据服务，提供了丰富的地图数据和集成
- **大语言模型**：支持OpenAI、Anthropic Claude、Google Gemini等多种大语言模型

## 系统架构

```
┌─────────────────┐      ┌─────────────────┐      ┌─────────────────┐
│                 │      │                 │      │                 │
│  tourism-agent- │      │  tourism-agent- │      │ tourism-weather-│
│       ui        │◄────►│     client      │◄────►│     server      │
│  (Vue 3 前端)    │      │ (Spring Boot)   │      │  (Spring AI)    │
│                 │      │                 │      │                 │
└─────────────────┘      └────────┬────────┘      └────────┬────────┘
                                  │                        │
                                  ▼                        ▼
                         ┌─────────────────┐      ┌─────────────────┐
                         │                 │      │                 │
                         │   大语言模型     │      │   和风天气 API  │
                         │  (OpenAI等)     │      │                 │
                         │                 │      │                 │
                         └─────────────────┘      └─────────────────┘
```

## 安装与运行

### 环境要求

- Java 21 或更高版本
- Maven 3.9.x 或更高版本
- Node.js 16.x 或更高版本
- npm 8.x 或更高版本

### 后端服务启动

1. 启动 tourism-agent-client

```bash
cd tourism-agent-client
mvn spring-boot:run
```

2. 启动 tourism-weather-server

```bash
cd tourism-weather-server
mvn spring-boot:run
```

### 前端服务启动

```bash
cd tourism-agent-ui
npm install
npm run dev
```

### 构建生产版本

```bash
cd tourism-agent-ui
npm run build
```

### 预览生产构建

```bash
cd tourism-agent-ui
npm run preview
```

## 使用方法

1. 在首页输入您的旅游目的地
2. 选择计划出行的日期
3. 添加您的旅游偏好和兴趣（如美食、历史、自然风光等）
4. 点击"生成旅游计划"按钮
5. 等待AI生成您的个性化旅游攻略
6. 查看生成的旅游计划，可以打印或在新标签页中查看

## 贡献指南

欢迎提交问题和改进建议！如果您想为项目做出贡献，请：

1. Fork本仓库
2. 创建您的特性分支 (`git checkout -b feature/amazing-feature`)
3. 提交您的更改 (`git commit -m 'Add some amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 开启一个Pull Request

## 许可证

[MIT License](LICENSE)
