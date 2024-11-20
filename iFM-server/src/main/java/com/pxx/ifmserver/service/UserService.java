package com.pxx.ifmserver.service;

import com.pxx.ifmserver.entity.dto.User;
import com.pxx.ifmserver.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {

    List<User> listUser();
    Result getUserByUserId(Integer userId);
    User getUserByUserEmail(String userEmail);
    User getUserByUserName(String userName);
    Result updateUserNameByUserId(Integer userId, String userName);
    Result updateUserProfileByUserId(Integer userId, String userProfile);
    Result updateUserPicurlByUserId(Integer userId, MultipartFile file);
    Result insertUser(User user);
    Result login(Integer userId,String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
