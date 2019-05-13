# springboot AOP进行日志的记录

​         我们最初学习spring的时候，就学习ioc ,aop，今天我们学习一下使用aop ，用aop对日志进行记录。

## 一、我对aop的理解

   1.初学aop，这个东西显得高大上，刚学会面向对象，就来个面向切面，其实这些东西只是对java基础的补充。

   2.为啥要用aop,其实就是一个目的  (1)为了简单（懒）,(2)为了业务清晰。

   3.aop术语理解  

​           (1)**通知（Advice)**就是你想实现的功能，在你想用的地方用一下。

​        （2)**连接点（JoinPoint)**就是你可以使用的地方，方法前后，抛异常等地方。总之就是和方法有关的前前后后。

​          (3)**切入点（Pointcut)**你一个类有10个方法，你的连接点一大堆，但是你不想每个都织入，只想在具体的几个上执行。

​          (4)**切面（Aspect)**切面是通知和切入点的结合。现在发现了吧，没连接点什么事情，连接点就是为了让你好理解切点，搞出来的，明白这个概念就行了。通知说明了干什么和什么时候干（什么时候通过方法名中的before,after，around等就能知道），而切入点说明了在哪干（指定到底是哪个方法），这就是一个完整的切面定义。 

​          (5)**引入（introduction)**允许我们向现有的类添加新方法属性。这不就是把切面（也就是新方法属性：通知定义的） 

​          (6)**目标（target)** 引入中所提到的目标类，也就是要被通知的对象，也就是真正的业务逻辑，他可以在毫不知情的情况下，被咱们织入切面。而自己专注于业务本身的逻辑。 

​           (7)**代理(proxy)** 怎么实现整套aop机制的，都是通过代理，这个一会给细说。 

​           (8)**织入(weaving)**把切面应用到目标对象来创建新的代理对象的过程。有3种方式，spring采用的是运行时，为什么是运行时，后面解释。 



## 二、aop原理理解

　　spring用代理类包裹切面，把他们织入到Spring管理的bean中。也就是说代理类伪装成目标类，它会截取对目标类中方法的调用，让调用者对目标类的调用都先变成调用伪装类，伪装类中就先执行了切面，再把调用转发给真正的目标bean。

　　现在可以自己想一想，怎么搞出来这个伪装类，才不会被调用者发现（过JVM的检查，JAVA是强类型检查，哪里都要检查类型）。

　　1.实现和目标类相同的接口，我也实现和你一样的接口，反正上层都是接口级别的调用，这样我就伪装成了和目标类一样的类（实现了同一接口，咱是兄弟了），也就逃过了类型检查，到java运行期的时候，利用多态的后期绑定（所以spring采用运行时），伪装类（代理类）就变成了接口的真正实现，而他里面包裹了真实的那个目标类，最后实现具体功能的还是目标类，只不过伪装类在之前干了点事情（写日志，安全检查，事物等）。

　　这就好比，一个人让你办件事，每次这个时候，你弟弟就会先出来，当然他分不出来了，以为是你，你这个弟弟虽然办不了这事，但是他知道你能办，所以就答应下来了，并且收了点礼物（写日志），收完礼物了，给把事给人家办了啊，所以你弟弟又找你这个哥哥来了，最后把这是办了的还是你自己。但是你自己并不知道你弟弟已经收礼物了，你只是专心把这件事情做好。

　　顺着这个思路想，要是本身这个类就没实现一个接口呢，你怎么伪装我，我就压根没有机会让你搞出这个双胞胎的弟弟，那么就用第2种代理方式，创建一个目标类的子类，生个儿子，让儿子伪装我

　　2.生成子类调用，这次用子类来做为伪装类，当然这样也能逃过JVM的强类型检查，我继承的吗，当然查不出来了，子类重写了目标类的所有方法，当然在这些重写的方法中，不仅实现了目标类的功能，还在这些功能之前，实现了一些其他的（写日志，安全检查，事物等）。

　　这次的对比就是，儿子先从爸爸那把本事都学会了，所有人都找儿子办事情，但是儿子每次办和爸爸同样的事之前，都要收点小礼物（写日志），然后才去办真正的事。当然爸爸是不知道儿子这么干的了。这里就有件事情要说，某些本事是爸爸独有的(final的)，儿子学不了，学不了就办不了这件事，办不了这个事情，自然就不能收人家礼了。

　　前一种兄弟模式，spring会使用JDK的java.lang.reflect.Proxy类，它允许Spring动态生成一个新类来实现必要的接口，织入通知，并且把对这些接口的任何调用都转发到目标类。

　　后一种父子模式，spring使用CGLIB库生成目标类的一个子类，在创建这个子类的时候，spring织入通知，并且把对这个子类的调用委托到目标类。

　　相比之下，还是兄弟模式好些，他能更好的实现松耦合，尤其在今天都高喊着面向接口编程的情况下，父子模式只是在没有实现接口的时候，也能织入通知，应当做一种例外。



## 三、aop的使用场景。

1.  Authentication 权限
2. Caching 缓存
3. Context passing 内容传递
4. Error handling 错误处理
5. Lazy loading　懒加载
6. Debugging　　调试
7. logging, tracing, profiling and monitoring　记录跟踪　优化　校准
8. Performance optimization　性能优化
9. Persistence　　持久化
10. Resource pooling　资源池
11. Synchronization　同步
12. Transactions 事务



## 四、项目

 本项目，采用自定义注解，aop,反射等实现。具体查看源码

自定义注解

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyLog {
    String value() default "";
}
```

 

aop配置

```java

/**
 * MyLogAspect
 */
@Aspect
@Component
public class MyLogAspect {

    @Pointcut("@annotation(com.nbkj.bootaop.annotation.MyLog)")
    public void pointcut() {

    }

    @Around("pointcut()")
    public void around(ProceedingJoinPoint point) {
        // 秒表计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            // 执行方法
            point.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        stopWatch.prettyPrint(); // 执行时长(毫秒)
        long totalTimeMillis = stopWatch.getTotalTimeMillis(); // 获取用毫秒
        aopHandle(point,totalTimeMillis);
    }

    private void aopHandle(ProceedingJoinPoint point, Long time) {
        Signature signature = point.getSignature(); // 获取切入的方法
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        MyLogDo myLog = new MyLogDo();
        MyLog annotation = method.getAnnotation(MyLog.class);
        if (Objects.nonNull(annotation)) {
            myLog.setOperation(annotation.value());
        }
        // 获取请求方法名字
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        myLog.setMethod(className + "." + methodName);

        // 请求参数
        Object[] args = point.getArgs();
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = u.getParameterNames(method);
        if (Objects.nonNull(args) && Objects.nonNull(parameterNames)) {
            String params = "";
            for (int i = 0; i < args.length; i++) {
                params += "  " + parameterNames[i] + ": " + args[i];
            }
            myLog.setParams(params);
        }
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置ip
        myLog.setIp(IPUtils.getIpAddr(request));
        myLog.setUsername("BingShu");
        myLog.setTime(time);
        myLog.setCreateTime(new Date());

        System.out.println("===================");
     
        System.out.println(myLog.toString());
        System.out.println("===================");
    }

}
```





## 五、总结

项目下还使用了stopWatch等等用来计时，通过学习我对aop有所认识，希望对大家也能有所帮助。

大家也可以学习一下Objects工具类的使用

作者------------**冰叔** 

qq1058179540

wx:hsj179540