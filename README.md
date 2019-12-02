# SpringCloudLearn
## 一、微服务框架基本结构
- name(port)  
-- sc-eureka-server(9001) 单点注册中心  
-- sc-eureka-server-to(9002,9003)双点注册中心,也可以多点，集群的初始概念  
-- sc-eureka-client(9005)注册服务  
-- sc-client-to(9007)第二个注册服务  9005和9007进行负载均衡的作用,相同名称
-- sc-eureka-consumer(9006)通过feign进行服务调用服务  
-- sc-ribbon(9008)ribbon进行远程服务调用  
-- sc-hystrix-dashboard-turbine(9009)熔断器集群Turbine监控
-- sc-config-server(9010) Spring Cloud Config 配置中心
-- sc-config-client(9011) Spring Cloud Config 配置中心客户端   
-- sc-config-server(9012) Spring Cloud Config 配置中心集群 高可用
## 二、注册中心Eureka的创建
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
## 三、Spring cloud有两种服务调用方式
### ribbon+restTemplate远程调用
### 功能点
   1. 注册中心注册服务
   2. ribbon开启负载均衡
   3. 调用对应服务路径
### 如何创建
   1. 在注册中心创建ribbon服务
   2. @LoadBalanced方法在启动项开启负载均衡  
     - ````@LoadBalanced
            RestTemplate restTemplate() {
                return new RestTemplate();
            }````  
### feign远程调用（集成了ribbon）
### 功能点
   1. 注册中心注册服务
   2. @EnableFeignClients注解开启Feign的功能
   2. 通过@ FeignClient（“服务名”）匹配对应服务名称
   3. 调用服务名称对应服务
   4. feign集成ribbon服务，默认负载均衡
   5. 实现模块：sc-eureka-consumer
## 四、在开启服务中心之后，服务提供和调用
### 功能点
   1. 在注册中心注册服务
   2. 客户端去调用服务
   3. 对应角色：服务中心、服务提供者、服务消费者
### 如何创建服务
   1. 确保创建了注册中心
   2. 创建client模块
      + 配置pom文件
      + 配置yml文件
      + 设置启动类：启动类中添加@EnableDiscoveryClient注解，之后注册中心会有client服务 
   3. 创建consumer模块，对应feign进行远程调用
      + 配置pom，feign架包
      + 创建feign远程调用接口
      + controller调用远程方法
### feign为服务提供者提供了负载均衡处理
   1. 在client的基础上，在创建一个相同name不过端口号不同的client（sc-client-to）注意:yml的名称要相同才能负载均衡
   2. 然后开启调用者consumer进行feign调用，结果交替执行
## 五、Hystrix熔断器
作用：防止服务故障“雪崩”现象   
#### 功能点
   1. 断路器机制  (OPEN)->(HALF-OPEN)->(ClOSED)->(OPEND)
   2. Fallback 如果服务出现故障，返回一个固定值Fallback
   3. 资源隔离
   4. 熔断只是作用在服务调用这一端  
   5. Feign中已经依赖了Hystrix
### 如何实现熔断器Hystrix
#### Feign实现熔断器(sc-eureka-consumer)
   1. 配置文件：feign.hystrix.enabled=true
   2. 创建回调类
   3. feign接口添加fallback属性
### Ribbon实现熔断器(sc-ribbon)
   1. 启动类 加@EnableHystrix注解开启Hystrix
   2. ribbon的service方法添加@HystrixCommand注解
   3. 写熔断器fallback的方法
### 熔断器集成Hystrix Dashboard监控
注意：SpringBoot2.0以上版本@HystrixCommand找不到的手动添加对应依赖就行了<dependency> <groupId>com.netflix.hystrix</groupId> <artifactId>hystrix-javanica</artifactId> </dependency>
这个错误找了好久，版本比较高。需要所以Feign在整合的时候，因为添加@EnableCircuitBreaker会报错。  
高版本SpringBoot需要配置Dashboard才可以有监控。[https://blog.csdn.net/ddxd0406/article/details/79643059]  
  + 解决：Unable to connect to Command Metric Stream.
    - 在启动类中添加````@Bean
                  public ServletRegistrationBean getServlet(){
                      HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
                      ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
                      registrationBean.setLoadOnStartup(1);
                      registrationBean.addUrlMappings("/actuator/hystrix.stream");
                      registrationBean.setName("HystrixMetricsStreamServlet");
                      return registrationBean;
                  }````  
 ### 熔断器集群Turbine监控 
   + 添加pom依赖
   + 配置文件
     - spring.application.name=hystrix-dashboard-turbine
     - server.port=9009
     - turbine.appConfig=node01,node02
     - turbine.aggregator.clusterConfig= default
     - turbine.clusterNameExpression= new String("default")
     - eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/
   + 启动类增加注解@EnableTurbine，激活对Turbine的支持
   + 访问Turbine路径：http://localhost:9009/turbine.stream
## Spring Cloud Config 文件配置中心
### 功能点
  + 提供服务端和客户端支持
  + 集中管理各环境的配置文件
  + 配置文件修改之后，可以快速的生效
  + 可以进行版本管理
  + 支持大的并发查询
  + 支持各种语言
### 创建Spring Cloud Config
  + 添加pom文件
  + 写yml配置git,svn文件，本地文件等等
  + 增加@EnableConfigServer，激活对配置中心的支持
  + 访问路径
    - /{application}/{profile}[/{label}]
    - /{application}-{profile}.yml
    - /{label}/{application}-{profile}.yml
    - /{application}-{profile}.properties
    - /{label}/{application}-{profile}.properties
### client实现
  + 添加config client pom依赖
  + 配置application和bootstrap的yml文件
  + 使用@Value注解来获取server端参数的值
---- 注意：不能因为git，动态刷新git中的数据，只有在项目启动的时候，才会调用配置文件，需要重启服务。后面会有动态刷配置服务数据refresh;
           但是，配置文件每次刷新都要CMD通过手动curl -X POST http://localhost:9011/actuator/refresh来重新刷新配置文件
### 动态刷配置服务数据refresh
   1. 添加actuator依赖      
   2. 开启更新机制
      - 需要给加载变量的类上面加载@RefreshScope，在客户端执行/refresh的时候就会更新此类下面的变量值。
      - 配置文件application.properties添加以下配置
        - management.security.enabled： springboot 1.5.X 以上默认开通了安全认证，所以需要添加这个配置
        -  management.endpoints.web.exposure.include： springboot 2.x 默认只开启了info、health的访问，*代表开启所有访问
      - 手动刷新配置文件 curl -X POST http://localhost:9011/actuator/refresh  （9011属于客户端配置端口号）
### 配置中心服务化和高可用
   1. config server和client加入注册中心 实现服务化
   2. 创建新的config server 更改端口为9012 实现Sping Config 高可用
## Spring Cloud Bus 消息总线
### 功能点
  + 用于广播配置文件的更改
  + 服务之间的通讯
  + 目前唯一实现的方式是用AMQP消息代理作为通道
### 创建服务
  1. 添加依赖 spring-cloud-starter-bus-amqp和rabbitMQ
  2. 配置文件
  3. 改进通过config servce支持总线，进行更新配置
     - 通知消息总线curl -X POST http://localhost:9010/actuator/bus-refresh 
  4. 局部刷新
     - /bus/refresh?destination=customers:8000，这样消息总线上的微服务实例就会根据destination参数的值来判断是否需要要刷新。其中，customers:8000指的是各个微服务的ApplicationContext ID。
     - /bus/refresh?destination=customers:**，这样就可以触发customers微服务所有实例的配置刷新。
     - 等
   5. 跟踪总线 /trace
##Spring Cloud Zuul 
### 介绍
 Spring Cloud Zuul路由是微服务架构的不可或缺的一部分，提供动态路由，监控，弹性，安全等的边缘服务。  
 Zuul是Netflix出品的一个基于JVM路由和服务端的负载均衡器。  
 Zuul的主要功能是路由转发和过滤器。路由功能是微服务的一部分，比如／api/user转发到到user服务，/api/shop转发到到shop服务。zuul默认和Ribbon结合实现了负载均衡的功能。
### 功能点
   + 网关路由
   + 服务化
   + 网关默认路由规则：默认支持负载均衡、注册服务和转发服务
### 功能实现
   1. 服务网关
      + 依赖spring-cloud-starter-zuul
      + 配置文件
        - 配置路径 zuul.routes.baidu.path=/baidu/**
        - 重定向路径 zuul.routes.baidu.url=http://www.baidu.com/
      + 开启注解@EnableZuulProxy，支持网关路由
      + 加入服务
        - 访问路径：zuul.routes.api-a.path=/producer/**
        - 服务转接：zuul.routes.api-a.serviceId=spring-cloud-producer
        - 服务注册地址eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/