# Springboot基本配置

# 一、banner配置

### 1.banner

​    springboot1.x的时候就支持banner的配置，升级到2.x banner可以配置成图片，当启动的时候，打印在控制台。

​      具体将自己的banner放在resources目录图片或者banner.txt,这样启动主程序就可以打印出来了



### 2.源码解析

#####  （1）接口

   具体调用时创建SpringApplication->run方法调用  Banner printedBanner = printBanner(environment);

```java
//函数式接口注解这个注解有且只能有一个抽象方法（不包括静态方法，默认方法,和Object中的方法，推荐使用lambda实现）
@FunctionalInterface
public interface Banner {
	//输出方法
    void printBanner(Environment environment, Class<?> sourceClass, PrintStream out);
	
    //枚举
    enum Mode {
		OFF,  //关闭
		CONSOLE,  //控制台打印
		LOG       //log文件打印
	}

}
```

  

##### （2）实现类

​     具体代码位于spring-boot-*release* .jar中

​     包org.springframework.boot包中

​     有兴趣的可以研究一下

```
SpringBootBanner
ImageBanner
ResourceBanner
```



##### （3）三种模式配置

```java
public static void main(String[] args) {
        SpringApplication application=new SpringApplication(WxMpDemoApplication.class);
        application.setBannerMode(Banner.Mode.OFF);  //具体可以在主函数设置
        application.run(args);
        //SpringApplication.run(WxMpDemoApplication.class, args);
    }
```



##### （4）自己也可以实现Banner接口，写自己的逻辑







## 二、boot全局配置文件

###   1.application.properties 或者application.yml

   这个文件是springboot的配置文件，大部分配置信息需要配置在这两个文件

   具体配置已经实现的代码可以，可以查看文档（常用的jdbc,port等）这些配置信息基本都有默认配置

  springboot正是利用这一点，简化了配置操作。

  yml,和properties两种区别可以查看资料<https://blog.csdn.net/u013551585/article/details/80196052> 

###   

### 2.源码实现（如何加载）

1. 服务启动调用:SpringApplication.run

   ```java
   @SpringBootApplication
   public class WxMpDemoApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(WxMpDemoApplication.class, args);
       }
   }
   ```

   

2. 创建默认的环境参数：ConfigurableEnvironment

   ```java
   public ConfigurableApplicationContext run(String... args) {
   		StopWatch stopWatch = new StopWatch();   //秒表，记录运行时间
   		stopWatch.start();
   		ConfigurableApplicationContext context = null;
   		Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
   		configureHeadlessProperty();
            //获取执行监听器实例
   		SpringApplicationRunListeners listeners = getRunListeners(args);
   		listeners.starting();
   		try {
                //创建全局系统参数实例
   			ApplicationArguments applicationArguments = new DefaultApplicationArguments(
   					args);
                //创建    ConfigurableEnvironment 并触发ApplicationEnvironmentPreparedEvent事件 
               //加载配置的核心地方，spring启动首要做的事情
   			ConfigurableEnvironment environment = prepareEnvironment(listeners,
   					applicationArguments); 
   			configureIgnoreBeanInfo(environment);
   			Banner printedBanner = printBanner(environment);
   			context = createApplicationContext();
   			exceptionReporters = getSpringFactoriesInstances(
   					SpringBootExceptionReporter.class,
   					new Class[] { ConfigurableApplicationContext.class }, context);
   			prepareContext(context, environment, listeners, applicationArguments,
   					printedBanner);
   			refreshContext(context);
   			afterRefresh(context, applicationArguments);
   			stopWatch.stop();
   			if (this.logStartupInfo) {
   				new StartupInfoLogger(this.mainApplicationClass)
   						.logStarted(getApplicationLog(), stopWatch);
   			}
   			listeners.started(context);
   			callRunners(context, applicationArguments);
   		}
   		catch (Throwable ex) {
   			handleRunFailure(context, ex, exceptionReporters, listeners);
   			throw new IllegalStateException(ex);
   		}
   
   		try {
   			listeners.running(context);
   		}
   		catch (Throwable ex) {
   			handleRunFailure(context, ex, exceptionReporters, null);
   			throw new IllegalStateException(ex);
   		}
   		return context;
   	}
   ```

   

3. 触发事件：ApplicationEnvironmentPreparedEvent

   ```java
   private ConfigurableEnvironment prepareEnvironment(
   			SpringApplicationRunListeners listeners,
   			ApplicationArguments applicationArguments) {
   		// Create and configure the environment
           //创建一个配置环境信息，当是web环境时创建StandardServletEnvironment实例，非web环境时创建StandardEnvironment实例
   		ConfigurableEnvironment environment = getOrCreateEnvironment();
   		configureEnvironment(environment, applicationArguments.getSourceArgs());
        //核心事件触发方法，此方法执行后会执行所有监听ApplicationEnvironmentPreparedEvent事件的监听器，这里我们是跟踪application.properties文件的加载，就查看ConfigFileApplicationListener监听器都做了什么工作
   		listeners.environmentPrepared(environment);
   		bindToSpringApplication(environment);
   		if (this.webApplicationType == WebApplicationType.NONE) {
   			environment = new EnvironmentConverter(getClassLoader())
   					.convertToStandardEnvironmentIfNecessary(environment);
   		}
   		ConfigurationPropertySources.attach(environment);
   		return environment;
   	}
   ```

4. ConfigFileApplicationListener

   ```java
   public void onApplicationEvent(ApplicationEvent event) {
           //从此处可以看到当事件为ApplicationEnvironmentPreparedEvent时，执行onApplicationEnvironmentPreparedEvent方法
           if (event instanceof ApplicationEnvironmentPreparedEvent) {
               onApplicationEnvironmentPreparedEvent(
                       (ApplicationEnvironmentPreparedEvent) event);
           }
           if (event instanceof ApplicationPreparedEvent) {
               onApplicationPreparedEvent(event);
           }
       }
   ```

   

5. onApplicationEnvironmentPreparedEvent 

   ```java
   private void onApplicationEnvironmentPreparedEvent(
               ApplicationEnvironmentPreparedEvent event) {
           //此处通过SpringFactoriesLoader加载EnvironmentPostProcessor所有扩展   
           List<EnvironmentPostProcessor> postProcessors = loadPostProcessors();
           //因为此监听器同样是EnvironmentPostProcessor的扩展实例，所以在此处将自己加入集合
           postProcessors.add(this);
           AnnotationAwareOrderComparator.sort(postProcessors);
           //遍历所有的EnvironmentPostProcessor扩展调用postProcessEnvironment
           //当然我们跟踪是application.properties所以主要查看当前实例的postProcessEnvironment方法
           for (EnvironmentPostProcessor postProcessor : postProcessors) {
               postProcessor.postProcessEnvironment(event.getEnvironment(),
                       event.getSpringApplication());
           }
       }
   ```

   

6. postProcessEnvironment 

   ```java
   @Override
       public void postProcessEnvironment(ConfigurableEnvironment environment,
               SpringApplication application) {
           //此处添加配置信息到environment实例中，此方法完成后就将application.properties加载到环境信息中
           addPropertySources(environment, application.getResourceLoader());
           configureIgnoreBeanInfo(environment);
           bindToSpringApplication(environment, application);
       }
   ```

   

7. addPropertySources 

   ```java
   protected void addPropertySources(ConfigurableEnvironment environment,
               ResourceLoader resourceLoader) {
           //这里先添加一个Random名称的资源到环境信息中  
           RandomValuePropertySource.addToEnvironment(environment);
           //通过Loader加载application.properties并将信息存入环境信息中
           new Loader(environment, resourceLoader).load();
       }
   ```

8. load 

   ```java
   public void load() {
   //创建一个资源加载器，spring boot默认支持PropertiesPropertySourceLoader，YamlPropertySourceLoader两种配置文件的加载
               this.propertiesLoader = new PropertySourcesLoader();
               this.activatedProfiles = false;
               //加载配置profile信息，默认为default
               ..........此处省略
               while (!this.profiles.isEmpty()) {
                   Profile profile = this.profiles.poll();
                   //遍历所有查询路径，默认路径有：classpath:/,classpath:/config/,file:./,file:./config/
                   for (String location : getSearchLocations()) {
                   //这里不仅仅是加载application.properties，当搜索路径不是以/结束，默认认为是文件名已存在的路径
                       if (!location.endsWith("/")) {
                           // location is a filename already, so don't search for more
                           // filenames
                           load(location, null, profile);
                       }
                       else {
                            //遍历要加载的文件名集合，默认为application
                           for (String name : getSearchNames()) {
                               load(location, name, profile);
                           }
                       }
                   }
                   this.processedProfiles.add(profile);
               }
   
               //将加载完成的配置信息全部保存到环境信息中共享
               addConfigurationProperties(this.propertiesLoader.getPropertySources());
           }
   ```

   

9. load 

   ```java
   private void load(String location, String name, Profile profile) {
               //此处根据profile组装加载的文件名称以及资源所放置的组信息
               String group = "profile=" + (profile == null ? "" : profile);
               if (!StringUtils.hasText(name)) {
                   // Try to load directly from the location
                   loadIntoGroup(group, location, profile);
               }
               else {
                
                       // Also try the profile-specific section (if any) of the normal file
                       loadIntoGroup(group, location + name + "." + ext, profile);
                   }
               }
           }
   ```

   

10. loadIntoGroup 

    ```java
    private PropertySource<?> doLoadIntoGroup(String identifier, String location,
                    Profile profile) throws IOException {
                Resource resource = this.resourceLoader.getResource(location);
                PropertySource<?> propertySource = null;
                if (resource != null && resource.exists()) {
                    String name = "applicationConfig: [" + location + "]";
                    String group = "applicationConfig: [" + identifier + "]";
                    //资源加载核心方法，此处有两个实现，当后缀为，xml或者properties调用PropertiesPropertySourceLoader
                    //当后缀为yml或者yaml时，调用YamlPropertySourceLoader
                    
                    propertySource = this.propertiesLoader.load(resource, 
                }
            
                return propertySource;
            }
    ```

    

11. PropertiesPropertySourceLoader

    ```java
    @Override
        public PropertySource<?> load(String name, Resource resource, String profile)
                throws IOException {
            if (profile == null) {
                //此处调用PropertiesLoaderUtils工具类加载本地文件
                Properties properties = PropertiesLoaderUtils.loadProperties(resource);
                if (!properties.isEmpty()) {
                    return new PropertiesPropertySource(name, properties);
                }
            }
            return null;
        }
    ```

    

12. 到此application.properties就真正的加载并共享到环境信息中，供系统其它地方调用。 

    整个过程主要使用spring boot 内置的ConfigFileApplicationListener监听器监听ApplicationEnvironmentPreparedEvent事件完成对application.properties加载以及设置。

    

    ### 3.具体使用

    1.application.properties 配置属性如下

     

    ```properties
    bingshu.blog.name=https://blog.csdn.net/BingShuBlog
    bingshu.blog.title=BingShu
    ```

    

    2.定义一个PropertiesBean，通过`@Value("${属性名}")`来加载配置文件中的属性值： 

    ```java
    @Component
    @Data  // lombok插件
    public class PropertiesBean {
    	
        @Value("${bingshu.blog.name}")
        private String name;
        
        @Value("${bingshu.blog.title}")
        private String title;
        
    }
    ```

​       

​   3.当属性很多的时候可以用Class封装

​  @EnableConfigurationProperties({ConfigBean.class}) 主函数类加入该注解

```java
@ConfigurationProperties(prefix="bingshu.blog")
public class ConfigBean {
    private String name;
    private String title;
    // get,set略
}
```

  

 4.属性的引用在application.properties配置文件中，各个属性可以相互引用，如下： 

​      

```properties
bingshu.blog.name=https://blog.csdn.net/BingShuBlog
bingshu.blog.title=BingShu
bingshu.blog.allname=${bingshu.blog.name}-----------${bingshu.blog.title}
```





5.可以配置多个配置文件，启动动态选择

  application-dev.properties 

  application-prod .properties 



  

```shell
mvn clean package  打包
cd target
java -jar xxx.jar ----spring.profiles.active=dev 启动dev

```



# 额外源码知识

1.spring  **StopWatch**的使用