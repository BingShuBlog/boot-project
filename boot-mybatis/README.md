# SpringBoot Mybaits学习

## 一、首先搭建最基本的springboot项目

​    1.具体不多说了,不懂的可以私聊我。



## 二、引入mybatis

  1.添加依赖

```xml
<dependency>
	<groupId>org.mybatis.spring.boot</groupId>
	<artifactId>mybatis-spring-boot-starter</artifactId>
	<version>2.0.1</version>
</dependency>
```

2.数据驱动自己选择

3.我用的是HikariDataSource（具体是什么，类似druid的连接池（说是比较快，但是我自己还没有测试，以后测试一下吧））

4.配置文件 

```properties
#端口配置
server.port=8086

#数据库配置
# jdbc_config 数据源配置
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/boot-mybatis?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull
spring.datasource.username=root
spring.datasource.password==xxxx
# Hikari 配置文件
spring.datasource.type=com.zaxxer.hikari.HikariDataSource
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.maximum-pool-size=15
spring.datasource.hikari.auto-commit=true
spring.datasource.hikari.idle-timeout=30000
spring.datasource.hikari.pool-name=DatebookHikariCP
spring.datasource.hikari.max-lifetime=1800000
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.connection-test-query=SELECT 1
```



## 三、编写代码

1.目录结构

```java
com.nbkj.mybatis.bootmybatis
                       +-controller
                             UserController.java
                       +-entity
                             User.java
                       +-mapper
                             UserMapper.java
                       +-service
                             UserService.java
                           impl
                             UserServiceImpl.java
```



2.代码编写（这个项目展示了用注解的方式）

  主要注解有

- `@Insert`      插入注解   举例:  @Insert("select * from t_user") 
- `@Update`      更新注解  举例:  @Update("select * from t_user") 
- `@Delete`      删除注解  举例:  @Delete("select * from t_user") 
- `@Select`      查询注解  举例:  @Select("select * from t_user") 
- `@SelectProvider `   复杂动态sql使用
- `@UpdateProvider`
- `@DeleteProvider `
- `@InsertProvider ` 
- `@Results `      返回结果集映射 这个可以定义一个相同的，下面的结果集一样的话直接适应即可
- `@ResultMap`  直接使用 `@Results `定义好的结果集

```java
/**
 * UserMapper mapper持久层
 */
@Mapper
public interface UserMapper {

        @Select("select * from t_user")
        @Results(id = "user", value = { @Result(property = "id", column = "id"),
                        @Result(property = "userName", column = "user_name"),
                        @Result(property = "passWord", column = "pass_word"),
                        @Result(property = "email", column = "email"),
                        @Result(property = "nickName", column = "nick_name"),
                        @Result(property = "regTime", column = "reg_time"), @Result(property =           "age", column = "age") })
        List<User> findAll();
        
         // 用于插入返回主键id
        @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
        @Insert("insert into t_user(user_name,pass_word,email,nick_name,age) values(#                   {userName},#{passWord},#{email},#{nickName},#{age})")
        int addUser(User user);

        @Update("update t_user set nick_name=#{nickName} where id=#{id}")
        int updateNickName(@Param("nickName") String nickName, @Param("id") Long id);

        @Delete("delete from t_user where id=#{id}")
        int deleteById(@Param("id") Long id);

        @Select("select * from t_user where id=#{id}")
        @ResultMap("user")
        User selectUserById(@Param("id") Long id);
         
        //复杂sql使用
        @SelectProvider(type = UserProvider.class, method = "selectAll")
        @ResultMap("user")
        List<User> selectAllProvider();
}
```



UserProvider

```java
/**
 * UserProvider 通过Provider写复杂动态sql
 */
public class UserProvider {

    public String selectAll() {
        return new SQL() {
            {
                SELECT(" * ");
                FROM("t_user");
            }
        }.toString();
    }
}
```



## 四、sql显示配置

  如果开发中想显示sql

  可以在配置文件中配置相关日志级别

  

```properties
#日志配置
logging.level.com.nbkj.mybatis.bootmybatis.mapper=debug
```



## 五、配置xml开发

1.配置文件配置位置

```properties
#mybatis xml配置
mybatis.config-location=classpath:mybatis-config.xml
mybatis.mapper-locations=classpath:mapper/*.xml
```

2.注解和xml可以混合使用





# 六、有问题或者补充可以加我微信...

#                      wx:  hsj179540