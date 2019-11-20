# SpringCloudLearn
About SpringCloud structure and expend
## 注册中心Eureka的创建
### 功能点
   1. 服务注册与发现的组件，也就是服务注册中心
   2. 注册组件有（配置管理、服务发现、断路器、路由、微代理、事件总线、全局锁、决策竞选、分布式会话等）
   3. eureka集群处理：注册中心作为最为关键的服务，应该处于随时可以提供服务的状态，集群是最好的方式。
      - ureka通过互相注册的方式来实现高可用的部署
      - Eureke Server配置其他可用的serviceUrl就能实现高可用部署
#### 如何创建注册中心(eureka server)
   1. 创建一个maven主项目
   2. 创建eureka server的model
   3. 增加pom文件和增加@EnableEurekaServer注解
   4. 写yml配置文件(具体功能见yml注释)