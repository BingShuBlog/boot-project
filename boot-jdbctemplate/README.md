# SpringBoot集成JdbcTemplate

​       JdbcTemplate是我学习java的时候比较早使用的框架，这个框架上手比较容易，当时好像还没有Springboot当时学习的时候是在SpringMVC环境下学习的。

​     今天学习一下boot版本



## 一、项目结构

![1557301844026](https://github.com/BingShuBlog/boot-project/blob/master/boot-jdbctemplate/image/1557301844026.png)



## 二、代码

### 1.引入依赖

在Spring Boot开启JdbcTemplate很简单，只需要引入`spring-boot-starter-jdbc`依赖即可。JdbcTemplate封装了许多SQL操作 。

[官方文档](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html。)

```xml
	<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
	 </dependency>
```



### 2.DAO层代码

```java
package com.nbkj.bootjdbctemplate.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.nbkj.bootjdbctemplate.dao.UserDao;
import com.nbkj.bootjdbctemplate.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * UserDaoImpl 2019年5月7日10:26:40
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate JdbcTemplate;
    
    @Override
    public int add(User user) {
        //创建sql语句
        StringBuffer sql = new StringBuffer("insert into t_user(username,password,age) ");
        sql.append("values(:username,:password,:age) ");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                JdbcTemplate.getDataSource());
        return namedParameterJdbcTemplate.update(sql.toString(), new BeanPropertySqlParameterSource(user));

    }

    @Override
    public int update(User user) {
        String sql = "update t_user set username=?,password=?,age=?";
        Object[] param = { user.getUsername(), user.getPassword(), user.getAge() };
        int[] paramTypes = { Types.VARCHAR, Types.VARCHAR, Types.INTEGER };
        return JdbcTemplate.update(sql, param, paramTypes);

    }

    @Override
    public int deleteById(Long id) {
        String sql = "delete from t_user where id=?";
        Object[] param = { id };
        int[] paramTypes = { Types.BIGINT };
        return JdbcTemplate.update(sql, param, paramTypes);
    }

    @Override
    public List<Map<String, Object>> queryUserByListMap() {
        String sql = "select * from t_user";
        return JdbcTemplate.queryForList(sql);
    }

    @Override
    public User queryUserById(Long id) {
        String sql = "select * from t_user where id=?";
        Object[] param = { id };
        int[] paramTypes = { Types.BIGINT };
        List<User> query = JdbcTemplate.query(sql, param, paramTypes, (RowMapper<User>) (rs, rowNum) -> {
            //匿名函数转换用的是lambda
		   User  user=new User();
		   user.setId(rs.getLong("id"));
		   user.setAge(rs.getInt("age"));
		   user.setPassword(rs.getString("password"));
            user.setUsername(rs.getString("username"));
		   return user;
        });
        if(query!=null&&!query.isEmpty() ){
         return query.get(0);
        }else{
            return null;
        }
    }

}
```



### 3.如果想配置打印sql可以配置日志文件

   resources目录下配置logback.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <include resource="org/springframework/boot/logging/logback/base.xml"/>
    <!--显示日志-->
    <logger name="org.springframework.jdbc.core" additivity="false" level="DEBUG" >
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="FILE" />
    </logger>
</configuration>
```



## 三、jdbcTemplate防注入的方式

1.使用数组传值

​           ---例子中有用到

2.使用map

   

```java
 params 传map
 namedTemplate.queryForList(sql , params); 
```

3.使用Bean

   ---使用javabean传值





## 四、RowMapper 

1.jdbcTemplate中会用到rowMapp 这个其实就是个映射，将数据库查询出来的字段放到一个map中，

通过这个mapper将map转为javaBean等做处理

这个可以写到具体的类中，也可以写匿名函数，lambda等方式实现。

下面的是通过实现接口方式实现的

```java
class GenericMapper implements RowMapper<Map<String,String>> {
        @Override
        public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {

    //List<Map<String, String>> list = new ArrayList<Map<String, String>>();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            //while (rs.next()) {
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);
                    String value = rs.getString(columnName).replace(" ", "");
                    map.put(columnName, value);
            }
       return map;     
    }
 }
```





# 总结：

​        jdbcTemplate是初学者比较容易上手的持久成工具，我们可以深度学习一下他的模板模式等等，有助于自己的提高，将我们学到的东西转化为生产力。



###                                                        微笑拥抱每一天，做像向日葵般温暖的人。
