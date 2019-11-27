# SpringCloudLearn
## 一、微服务框架基本结构
- name(port)  
-- sc-eureka-server(9001) 单点注册中心  
-- sc-eureka-server-to(9002,9003)双点注册中心,也可以多点，集群的初始概念  
-- sc-eureka-client(9005)注册服务  
-- sc-client-to(9007)第二个注册服务  9005和9007进行负载均衡的作用,相同名称
-- sc-eureka-consumer(9006)通过feign进行服务调用服务  
-- sc-ribbon(9008)ribbon进行远程服务调用  
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