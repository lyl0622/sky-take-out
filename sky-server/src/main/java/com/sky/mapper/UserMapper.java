package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {

    @Select("select * from user where openid=#{openid}")
    User getOpenid(String openid);

    /**
     * 插入用户表
     * @param user
     */
    void insert(User user);

    /**
     * 根据用户id查询
     * @param userId
     * @return
     */
    @Select("select * from user where id = #{id}")
    User getById(Long userId);

    /**
     * 统计用户
     * @param map
     * @return
     */
    Integer getUserReport(Map map);
}
