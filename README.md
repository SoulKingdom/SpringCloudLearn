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
   5. 注册中心双节点集群(Eureka集群),重点:注册中心，相互注册[https://blog.csdn.net/xcbeyond/article/details/81503484]
      + 操作：配置三个配置文件
        - application.yml
        - application-eureka-server1.yml
        - application-eureka-server2.yml
      + 分别配置对方的地址作为Eureka Client进行相互注册。由于采用了参数配置eureka.instance.hostname及http://eureka-server1的写法，则需要在进行hosts的配置
        - 在window在C:\Windows\System32\drivers\etc\hosts中配置  
        127.0.0.1 eureka-server1  
        127.0.0.1 eureka-server2  
      + EurekaServerToApplication 添加@EnableEurekaServer注解，开启对EurekaServer的支持，即：作为Eureka服务端。
      + 集群：启动启动参数--spring.profiles.active=eureka-server1来指定对应配置环境
        - idea配置：打开右上角运行的EditConfiguration 配置active prifiles 配置启动项目
        - 注意：要保证注册中心多个启动，只启动一个会出现另一个注册中心无法连接的错误，不影响运行。
      + 高可用验证
        - 如果出现其中一个节点宕机,另外一个中心正常运行，注册不会存在问题，只是宕机的服务会变成 unavailable-replicas；
   