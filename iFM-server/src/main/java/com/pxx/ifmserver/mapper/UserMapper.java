package com.pxx.ifmserver.mapper;

import com.pxx.ifmserver.entity.dto.Broadcast;
import com.pxx.ifmserver.entity.dto.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 获取所有用户的数据。
     * @return 返回一个包含所有用户数据的List<User>列表。
     */
    @Select("SELECT * FROM user")
    List<User> listUser();


    /**
     * 根据用户ID查询用户信息。
     * @param userId 用户ID，作为查询条件。
     * @return 返回一个User对象，包含用户的所有基本信息。
     */
/*    @Select("SELECT " +
            "user_id AS userId, " +
            "user_name AS userName, " +
            "user_password AS userPassword, " +
            "user_email AS userEmail, " +
            "user_picture AS userPicture " +
            "FROM user WHERE user_id = #{userId}")
    User getUserByUserId(Integer userId);*/


    /**
     * 根据用户ID查询用户信息。
     * @param userId 用户ID，作为查询条件。
     * @return 返回一个User对象，包含用户的所有基本信息。
     */
    @Select("SELECT * FROM user WHERE user_id = #{userId}")
    User getUserByUserId(Integer userId);


    /**
     * 根据邮箱查询用户信息。
     * @param userEmail 用户ID，作为查询条件。
     * @return 返回一个User对象，包含用户的所有基本信息。
     */
    @Select("SELECT * FROM user WHERE user_email = #{userEmail}")
    User getUserByUserEmail(String userEmail);


    /**
     * 根据昵称查询用户信息。
     * @param userName 用户ID，作为查询条件。
     * @return 返回一个User对象，包含用户的所有基本信息。
     */
    @Select("SELECT * FROM user WHERE user_name = #{userName}")
    User getUserByUserName(String userName);

    /**
     * 根据关键词获取用户
     * @param keyWord
     * @return
     */
    @Select("SELECT * FROM user WHERE user_name LIKE CONCAT('%', #{keyWord}, '%') " +
            "OR user_profile LIKE CONCAT('%', #{keyWord}, '%')")
    List<User> listUserByKeyWord(@Param("keyWord") String keyWord);



    /**
     * 添加新用户
     * @param user 新用户数据。
     * @return 表示受影响的行数。在执行插入操作时，这个值通常是1，表示一行被成功插入
     * useGeneratedKeys: 当设置为 true 时，这告诉 MyBatis 在执行插入操作后，尝试检索数据库自动生成的键（通常是自增的主键）。这个功能通常用于在插入记录后获取该记录的自增 userid。
     * keyProperty: 这个属性指定了应该将检索到的自动生成的键值设置到目标对象的哪个属性上。
     */
    @Insert({ "insert into user(user_password, user_name, user_email, user_picture) values(#{userPassword}, #{userName}, #{userEmail}, #{userPicture})" })
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(User user);

    /**
     * 根据用户ID更新对应用户头像路径。
     * @param userId 用户ID，作为查询条件。
     * @param userPicurl 作为新头像图片文件路径
     * @return 被修改的行数,期望为1,错误为0
     */
    @Update("UPDATE user SET user_picture = #{userPicurl} WHERE user_id = #{userId}")
    int updateUserPicurlByUserId(Integer userId, String userPicurl);

    /**
     * 根据用户ID更新对应用户邮箱。
     * @param userId 用户ID，作为查询条件。
     * @param userEmail 用户新邮箱
     * @return 被修改的行数,期望为1,错误为0
     */
    @Update("UPDATE user SET user_emali = #{userEmail} WHERE user_id = #{userId}")
    int updateUserEmailByUserId(Integer userId, String userEmail);

    /**
     * 根据用户ID更新对应用户昵称。
     * @param userId 用户ID，作为查询条件。
     * @param userName 用户新昵称
     * @return 被修改的行数,期望为1,错误为0
     */
    @Update("UPDATE user SET user_name = #{userName} WHERE user_id = #{userId}")
    int updateUserNameByUserId(Integer userId, String userName);

    /**
     * 根据用户ID更新对应用户昵称。
     * @param userId 用户ID，作为查询条件。
     * @param userProfile 用户新昵称
     * @return 被修改的行数,期望为1,错误为0
     */
    @Update("UPDATE user SET user_profile = #{userProfile} WHERE user_id = #{userId}")
    int updateUserProfileByUserId(Integer userId, String userProfile);

    /**
     * 根据用户ID更新对应用户密码。
     * @param userId 用户ID，作为查询条件。
     * @param userPassword 用户新密码
     * @return 被修改的行数,期望为1,错误为0
     */
    @Update("UPDATE user SET user_password = #{user_password} WHERE user_id = #{userId}")
    int updateUserPasswordByUserId(Integer userId, String userPassword);
}
