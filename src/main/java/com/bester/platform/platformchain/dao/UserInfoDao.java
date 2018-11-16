package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author zhangqiang
 */
public interface UserInfoDao {

    /**
     * 根据userId查询
     *
     * @param userId
     * @return
     */
    UserInfoEntity selectById(@Param("userId") int userId);

    /**
<<<<<<< HEAD
     * 动态添加用户信息
     *
     * @param userInfoEntity
     * @return
     */
    int insertUserInfo(@Param("userInfo") UserInfoEntity userInfoEntity);
=======
     * 用户信息修改
     * @param userId
     * @param birth
     * @param userName
     * @param sex
     * @param phone
     * @param email
     * @param address
     * @param job
     * @return
     */
    int updateUserInfo(@Param("userId") int userId,
                       @Param("birth") Date birth,
                       @Param("userName") String userName,
                       @Param("sex") int sex,
                       @Param("phone") String phone,
                       @Param("email") String email,
                       @Param("address") String address,
                       @Param("job") String job);
>>>>>>> 20181106ljw

}
