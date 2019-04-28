package com.nbkj.mybatis.bootmybatis.mapper;

import java.util.List;

import com.nbkj.mybatis.bootmybatis.entity.User;
import com.nbkj.mybatis.bootmybatis.mapper.provider.UserProvider;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

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
                        @Result(property = "regTime", column = "reg_time"), @Result(property = "age", column = "age") })
        List<User> findAll();

        @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id") // 用于插入放回主键id
        @Insert("insert into t_user(user_name,pass_word,email,nick_name,age) values(#{userName},#{passWord},#{email},#{nickName},#{age})")
        int addUser(User user);

        @Update("update t_user set nick_name=#{nickName} where id=#{id}")
        int updateNickName(@Param("nickName") String nickName, @Param("id") Long id);

        @Delete("delete from t_user where id=#{id}")
        int deleteById(@Param("id") Long id);

        @Select("select * from t_user where id=#{id}")
        @ResultMap("user")
        User selectUserById(@Param("id") Long id);

        @SelectProvider(type = UserProvider.class, method = "selectAll")
        @ResultMap("user")
        List<User> selectAllProvider();

        
        /**
         * xml配置
         * @param userName
         * @return
         */
        User  selectUserByUserName(@Param("userName")String  userName);
}