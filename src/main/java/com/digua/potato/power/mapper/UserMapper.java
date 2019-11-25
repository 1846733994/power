package com.digua.potato.power.mapper;

import com.digua.potato.power.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    @Update(value = "update user set name=#{name},token=#{token},account_id=#{accountId},gmt_modified=#{gmtModified} where id =#{id}")
    void update(User user);

    @Select("select * from user where id =#{id}")
    User selectById(Long id);

    @Select("select * from user where token = #{token}")
    User findByToken(String token);
    @Select("select * from user where account_id =#{accountId}")
    User findByAccountId(String accountId);
    @Update("update user set token=#{token},gmt_modified=#{gmtModified} where id =#{id}")
    void updateUserToken(User user);
}
