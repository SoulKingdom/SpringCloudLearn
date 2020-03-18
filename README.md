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
-- sc-zuul（9013） Spring Cloud Zuul 网关，服务过滤，路由重试等功能  
-- sc-zipkin-server(9014) Spring Cloud Sleuth 服务监控  
-- sc-common Spring Cloud common 服务基础操作  
--- sc-util 服务工具类
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
   + Filter服务过滤：鉴权、流量转发、请求统计等等
      + 过滤器类型
       - PRE： 这种过滤器在请求被路由之前调用。我们可利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等。
       - ROUTING：这种过滤器将请求路由到微服务。这种过滤器用于构建发送给微服务的请求，并使用Apache HttpClient或Netfilx Ribbon请求微服务。
       - POST：这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等。
       - ERROR：在其他阶段发生错误时执行该过滤器。 除了默认的过滤器类型，Zuul还允许我们创建自定义的过滤器类型。例如，我们可以定制一种STATIC类型的过滤器，直接在Zuul中生成响应，而不将请求转发到后端的微服务。
      + 默认实现Filter
       - pre	-3	ServletDetectionFilter	标记处理Servlet的类型
       - pre	-2	Servlet30WrapperFilter	包装HttpServletRequest请求
       - pre	-1	FormBodyWrapperFilter	包装请求体
       - route	1	DebugFilter	标记调试标志
       - route	5	PreDecorationFilter	处理请求上下文供后续使用
       - route	10	RibbonRoutingFilter	serviceId请求转发
       - route	100	SimpleHostRoutingFilter	url请求转发
       - route	500	SendForwardFilter	forward请求转发
       - post	0	SendErrorFilter	处理有错误的请求响应
       - post	1000	SendResponseFilter	处理正常的请求响应
      + 自定义Filter 
   + 路由熔断
     - 处理服务请求异常情况
     - 异常反馈前端请求处理
     - Zuul 目前只支持服务级别的熔断，不支持具体到某个URL进行熔断。
### 功能实现
   1. 服务网关
      + 依赖spring-cloud-starter-zuul
      + 配置文件
        - 配置路径 zuul.routes.baidu.path=/baidu/**
        - 重定向路径 zuul.routes.baidu.url=http://www.baidu.com/
      + 开启注解@EnableZuulProxy，支持网关路由
      + 加入服务
        - 访问路径：zuul.routes.api-a.path=/producer/**
        - 服务转接：zuul.routes.api-a.serviceId  =spring-cloud-producer
        - 服务注册地址eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/
   2. Filter服务过滤
      + 自定义Filter
        - 继承ZuulFilter类，覆盖四个方法
          - filterType 定义filter的类型，有pre、route、post、error四种  
          - filterOrder 定义filter的顺序，数字越小表示顺序越高，越先执行
          - shouldFilter 表示是否需要执行该filter，true表示执行，false表示不执行
          - run filter需要执行的具体操作 
        - 路由熔斷
          - 定义类并且继承FallbackProvider 
          - 对熔断事件响应的信息编写
   3. 路由重试
      + 添加Spring Retry依赖
      + 配置文件中开启Zuul Retry
        _ zuul.retryable=true  是否开启重试功能
        - ribbon.MaxAutoRetries=2 对当前服务的重试次数
        - ribbon.MaxAutoRetriesNextServer=0 切换相同Server的次数
   4. Zuul高可用(不同的客戶端分配不同的zuul)
## Spring Cloud Sleuth和Zipkin进行分布式链路跟踪
### 介绍
主要有三部分：数据收集、数据存储和数据展示
### 功能点
   + Span：基本工作单元，例如，在一个新建的span中发送一个RPC等同于发送一个回应请求给RPC，span通过一个64位ID唯一标识，trace以另一个64位ID表示，span还有其他数据信息，比如摘要、时间戳事件、关键值注释(tags)、span的ID、以及进度ID(通常是IP地址) span在不断的启动和停止，同时记录了时间信息，当你创建了一个span，你必须在未来的某个时刻停止它。  
   + Trace：一系列spans组成的一个树状结构，例如，如果你正在跑一个分布式大数据工程，你可能需要创建一个trace。  
   + Annotation：用来及时记录一个事件的存在，一些核心annotations用来定义一个请求的开始和结束
     - cs - Client Sent -客户端发起一个请求，这个annotion描述了这个span的开始
     - sr - Server Received -服务端获得请求并准备开始处理它，如果将其sr减去cs时间戳便可得到网络延迟
     - ss - Server Sent -注解表明请求处理的完成(当请求返回客户端)，如果ss减去sr时间戳便可得到服务端需要的处理请求时间
     - cr - Client Received -表明span的结束，客户端成功接收到服务端的回复，如果cr减去cs时间戳便可得到客户端从服务端获取回复的所有所需时间 将Span和Trace在一个系统中使用Zipkin注解的过程图形化：
   
### 功能实现
   1. 建立zipkin-server服务
     + 踩坑点：SpringBoot2.0之后，会报Class path contains multiple SLF4J bindings. 架包冲突错误
       - 解决方式`<!--排除--><exclusions><exclusion><groupId>org.apache.logging.log4j</groupId>
                  <artifactId>log4j-slf4j-impl</artifactId></exclusion></exclusions>`
     + 踩坑点2：#zipkin启动报错无法访问的
        - 解决方法：配置文件增加 management.metrics.web.server.auto-time-requests: false
   2. 建立zipkin监控客户端 <artifactId>spring-cloud-starter-zipkin</artifactId>
   3. 进行监控
 ### 关于consul与eureka区别
 consul:一致性好，速度慢一点
 eureka:可用性好，服务注册快一点
 CAP定理：指的是在一个分布式系统中，Consistency（一致性）、 Availability（可用性）、Partition tolerance（分区容错性），三者不可同时获得。
 
 一致性（C）：在分布式系统中的所有数据备份，在同一时刻是否同样的值。（所有节点在同一时间的数据完全一致，越多节点，数据同步越耗时）
 
 可用性（A）：负载过大后，集群整体是否还能响应客户端的读写请求。（服务一直可用，而且是正常响应时间）
 
 分区容错性（P）：分区容忍性，就是高可用性，一个节点崩了，并不影响其它的节点（100个节点，挂了几个，不影响服务，越多机器越好）
 CAP理论：
 就是说在分布式存储系统中，最多只能实现上面的两点。而由于当前的网络硬件肯定会出现延迟丢包等问题，所以分区容忍性是我们必须需要实现的。所以我们只能在一致性和可用性之间进行权衡  
 ## Spring Cloud Gateway(代替zuul)  
 ### 介绍
  Spring Cloud Gateway 提供路由,Filter,安全，监控/指标，和限流等；  
  Gateway是简单有效的统一的 API 路由管理方式  
### 功能点  
  + Predicate 介绍
    - 通过时间匹配
    - 通过 Cookie 匹配
    - 通过 Header 属性匹配
    - 通过 Host 匹配
    - 通过请求方式匹配
    - 通过请求路径匹配
    - 通过请求参数匹配
    - 通过请求 ip 地址进行匹配
    - 组合使用
  + 服务网关 Spring Cloud GateWay 服务化和过滤器
  + Filter(过滤器)高级功能
    - 熔断
    - 限流
    - 重试
 ### 功能实现
   1. pom依赖
   2. 定义转发配置
      + yml文件转发路径配置 
   3. 遇到的注意点:
      - 在配置中predicates的- Path的路径匹配信息，一定要与调用的服务之前名称一致 
        - 可以通过配置StripPrefix 属性,修改请求路径 
          - - StripPrefix=1 过滤掉-Path中去掉1个字符串 /name/bar/foo后端匹配到的请求路径就会变成http://nameservice/foo。
          - PrefixPath Filter 是在 URL 路径前面添加一部分的前缀                                                
  #### 扩展功能
   + 熔断  
   
   + 限流
     - 采用redis进行限速
     - 增加redis架包   
     - 增加网关限流配置
       - filter 名称必须是 RequestRateLimiter
       - redis-rate-limiter.replenishRate：允许用户每秒处理多少个请求
       - redis-rate-limiter.burstCapacity：令牌桶的容量，允许在一秒钟内完成的最大请求数
       - key-resolver：使用 SpEL 按名称引用 bean
     - 设置限流策略 创建Config类
   + 重试
## 创建基础功能模块 SpringCommon
### 介绍
   Spring Cloud Common 提供了基本功能：通过Http外部接口调用,restTemplate内部调用；  
### 功能点一  sc-util 工具类集合
  + 通用工具类
    - http  通过http调用外部接口
    - request 获取request中的信息工具类
    - time 需要格式化的工具类
### 功能点二  sc-core 核心代码
  + 通用工具类
    - restfull  controller层响应信息
    - exception controller程序出错之后信息
### 功能点二  sc-base 基础符号
   + 通用工具类
    - constant  通用常量