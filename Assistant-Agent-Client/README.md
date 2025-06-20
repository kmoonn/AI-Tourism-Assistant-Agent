## 一个智能旅行规划助手：能够整合多种信息源：包括**小红书的热门攻略**、**实时天气预报**以及**高德地图的路线规划**去生成一份详尽的旅行计划并以美化后的HTML页面呈现出来。

### 最终HTML效果：

[![image](https://github.com/user-attachments/assets/8829dd3a-dcae-40a6-9f56-2c9b14777de8)](https://linux.do/uploads/default/original/4X/8/8/1/881a0bb14909e67f530185383e8f7655dd4d22b5.jpeg)



### 使用教程：


1. **申请一个高德地图的ApiKey,用作远程SSE链接官方的MCP服务**：https://lbs.amap.com/
2. **下载一个获取小红书内容的mcp服务，根据项目中的步骤安装相关依赖和cookie**：https://github.com/jobsonlook/xhs-mcp
3. **拉取WeatherMcpServer项目并启动，或者注释掉天气服务的相关mcp注入代码，项目地址：** https://github.com/Cooosin/WeatherMcpServer/tree/master

- 修改配置文件：

```yaml
# 这里填写大模型的ApiKey和模型
llm:
  apiKey: "YOUR_GEMINI_API_KEY"
  modelName: "gemini-2.5-flash"

# 这里填写高德地图的MCP-URL
mcp:
  gaoDe:
    url: "https://mcp.amap.com/sse?key=YOUR_GAODE_API_KEY"
```
二、接口调用:
`curl --location 'http://localhost:8182/travel/chat?content=xxx`

prompt示例：
```tex
你好，我需要你为我策划一份详尽的日本的旅游攻略。
以下是我的具体需求，请仔细阅读并融入到最终攻略中：
1. 核心旅行参数：
 预算范围： 5000-6000
 行程时长： 5天～6天
 期望出行时间： 6月20日-6月25日
 出发城市： 广州
 出行人群： 一对夫妇
 主要交通方式 ：飞机
 目的地内部交通： 以公共交通为主
 偏好城市：大阪
2. 行程风格与偏好：
 行程节奏： 轻松舒适
 住宿要求：
 类型：性价比高的精品酒店
 位置：交通便利
3.餐饮偏好：
 主要品尝：当地特色菜  / 街头小吃 
 口味要求： 无特殊要求 
 必去景点/活动 ：环球影城，去买化妆品和性价比的物品
 兴趣偏好 ： 无
```


