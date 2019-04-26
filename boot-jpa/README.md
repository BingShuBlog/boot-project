# String data jpa基本配置

1. Java Persistence API（Java 持久层 API）：用于对象持久化的 API

2. 作用：使得应用程序以统一的方式访问持久层

3. 前言中提到了 Hibernate，那么JPA 与 Hibernate究竟是什么关系呢：

   1）JPA 是 Hibernate 的一个抽象，就像 JDBC 和 JDBC 驱动的关系

   2）JPA 是一种 ORM 规范，是 Hibernate 功能的一个子集 (既然 JPA 是规范，Hibernate 对 JPA 进行了扩展，那么说 JPA 是 Hibernate 的一个子集不为过)

   3）Hibernate 是 JPA 的一个实现

4. JPA 包括三个方面的技术：

   1）ORM 映射元数据，支持 XML 和 **JDK 注解**两种元数据的形式

   2）JPA 的 API

   3）查询语言：JPQL

## 一、创建springboot项目

   项目创建就不说了，spring data jpa基本用heibernate实现，heibernate学的比较好的同学应该

很好上手，jpa做简单的增删改查再好不过了，基本不用自己去实现代码了，还有一些高级查询方法，需要自己去探索，我们以后会说到。



## 二、pom文件

 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.3.RELEASE</version>
		<relativePath/>
		<!-- lookup parent from repository -->
	</parent>
	<groupId>com.nbkj</groupId>
	<artifactId>boot-jpa</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>boot-jpa</name>
	<description>this boot is for jpa</description>

	<properties>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```



## 三、创建数据库

​        sql文件位于sql文件夹



## 四、配置文件

​    

```properties
# 端口
server.port=8086


# 数据库连接
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/boot-jpa
spring.datasource.username=xxx
spring.datasource.password=123456
spring.datasource.tomcat.max-active=50
spring.datasource.tomcat.max-idle=10
spring.datasource.tomcat.min-idle=10
spring.datasource.tomcat.initial-size=10
spring.datasource.tomcat.max-wait=10000
spring.datasource.tomcat.validation-query=SELECT 1
spring.datasource.tomcat.test-while-idle=true
spring.datasource.tomcat.time-between-eviction-runs-millis=10000
spring.datasource.tomcat.jdbc-interceptors=ConnectionState;SlowQueryReport(threshold=500)

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
#数据库方言
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect

```



## 五、编写代码

jpa主要有entity 和持久层 entity（dto）与数据库表对应，idea也可以根据数据库一键生成

**举例**

### （1）entity

```java
package com.nbkj.bootjpa.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * User 用户
 */
@Entity
@Table(name = "t_user")
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "pass_word")
    private String password;

    @Column(name = "age")
    private Integer age;

    @Column(name = "reg_time")
    private Timestamp createTime;

    @Column(name = "email")
    private String email;

    @Column(name = "nick_name")
    private String nickName;
}
```



###  (2)Repository  

​    基本实现了增删改查 JpaRepository或者  CrudRepository 

  

```java
package com.nbkj.bootjpa.repository;

import com.nbkj.bootjpa.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository User持久层
 * 
 * @author : BingShu
 * @date : 2019年4月8日13:48:29
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 通过用户名查询用户
     * 
     * @param username
     * @return
     */
    User findByUsername(String username);
}
```



## 六、JPA 的基本注解

1. **@Entity**  修饰实体类，指明该类将映射到指定的数据表 

2. **@Table**  当实体类与映射的数据库表名不同名时需要使用 @Table 注解，该注解与 @Entity 注解并列使用，使用其**name 属性**指明数据库的表名 

3. **@Id**  标识该属性为主键，一般标注在该属性的 getter 方法上

4. **@GeneratedValue**  标注主键的生成策略通过其 strategy 属性   

   –IDENTITY   主键自增

   –AUTO    默认自动选择

   –SEQUENCE  通过序列产生主键  一般oracle 通过 @SequenceGenerator 注解指定序列名 

   –TABLE  通过表产生主键 

    

5. **@Basic**  没有标注的，也可以省略

6. **@Column**  当实体的属性与其映射的数据表的列不同名时使用  与数据库表字段对应

      **name**   字段名字

      **unique**  唯一约束

      **nullable**  是否为空

7. **@Transient**  标注此注解后在创建数据表的时候将会忽略该属性

8. **@Temporal**  向数据库映射日期（Date）属性时用来调整映射的精度





# 七、sql的使用

## 1.原生sql的使用

   

```java
/**
     * 查询所有用户的用户信息和地址 返回类型为数组方式查询但是sql中不能有重复的列
     * 
     * @return
     */
    @Query(value = "SELECT tu.* ,ta.province,ta.city,ta.street from t_user tu LEFT OUTER JOIN       t_address ta on tu.id =ta.user_id", nativeQuery = true)
    List<Object[]> selectAllUserAndAddress();
```



## 2 .对象sql的使用

```java
    /**
     * 返回类型为map查询所有数据
     * @return
     */
    @Query(value = "select new map(tu,ta) from User tu LEFT OUTER JOIN Address ta on tu.id           =ta.userId")
    List<Map<String, Object>> selectAllUserForMap(); 
```

