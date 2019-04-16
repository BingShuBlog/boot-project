# Springboot项目

## 一、使用sts、idea 、vscode等编辑器创建项目

  创建方式可以自己百度，也可以咨询我 **wx :hsj179540**



## 二、基本目录结构

  

```java
src                                                         ----源码
  +- main                                                   ----main包
  |     +-java                                              ----java代码
  |    |     +-com                                         ----包名 （避免重复，一般都是域名倒写）
  |    |        +-nbkj                                       
  |    |           +-bootstart                              ----项目名称
  |    |                    DemoApplication.java            ----springboot主函数，启动类
  |    |
  |    +-resources
  |              application.properties 或者 application.yml          ----springboot配置文件
  |
  +-test 
       +-java                                              ----java代码
       |     +-com                                         ----包名 （避免重复，一般都是域名倒写）
       |        +-nbkj                                       
       |           +-bootstart                              ----项目名称
       |                    DemoApplication.java            ----springboot测试主函数，启动类
       |
       +-resources
              application.properties 或者 application.yml          ----测试springboot配置文件
  
pom.xml  maven pom文件，如需知道详细意思可以学习maven
```





## 三、运行主函数启动项目

​    运行DemoApplication.java中的主函数启动项目，如果端口冲突，可以kill端口，或者在配置文件中修改端口

   

## 四、启动日志

 

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.4.RELEASE)
2019-04-16 10:31:15.182  INFO 564 --- [           main] com.nbkj.bootstart.DemoApplication       : Starting DemoApplication on DESKTOP-A9021VQ with PID 564 (E:\vscode-pro\boot-project\boot-start\target\classes started by ABC in e:\vscode-pro\boot-project\boot-start)
2019-04-16 10:31:15.186  INFO 564 --- [           main] com.nbkj.bootstart.DemoApplication       : No active profile set, falling back to default profiles: default
2019-04-16 10:31:17.800  INFO 564 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8081 (http)
2019-04-16 10:31:17.853  INFO 564 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2019-04-16 10:31:17.854  INFO 564 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.17]
2019-04-16 10:31:18.044  INFO 564 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2019-04-16 10:31:18.045  INFO 564 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 2780 ms
2019-04-16 10:31:18.360  INFO 564 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2019-04-16 10:31:18.729  INFO 564 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8081 (http) with context path ''
2019-04-16 10:31:18.735  INFO 564 --- [           main] com.nbkj.bootstart.DemoApplication       : Started DemoApplication in 3.99 seconds (JVM running for 4.539)
```



## 五、访问项目

启动后 访问地址127.0.0.1:配置端口

当看到以下画面，访问成功

![1](https://github.com/BingShuBlog/boot-project/blob/dev/boot-start/image/1.png)





## 六、其他方式启动

   springboot项目也可以采用jar包的形式启动

   也可以采用部署到tomcat启动（以后学习）

   

```
项目根目录运行如下命令
mvn clean package
cd target 
java -jar *.jar  启动
```





## 七、本文档仅用来学习，切莫用于商业用途

作者------------**冰叔** 

qq1058179540

wx:hsj179540