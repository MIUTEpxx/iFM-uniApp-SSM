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
    Result checkEmail(String userEmail);
    User getUserByUserName(String userName);
    Result updateUserNameByUserId(Integer userId, String userName);
    Result updateUserProfileByUserId(Integer userId, String userProfile);
    Result updateUserPicurlByUserId(Integer userId, MultipartFile file);
    Result updateUserPassword(Integer userId, String userEmail, String password,  String code);
    Result updateUserEmail(Integer userId, String userEmail, String newEmail,  String code1,String code2);
    Result insertUser(User user,String code);
    Result loginPassword(Integer userId, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;
    Result loginEamil(String email, String code) throws UnsupportedEncodingException, NoSuchAlgorithmException;
    Result searchUser(String keyword);
    Result getVCode(String userEmail);
    boolean verifyEmail(String userEmail, String code);
}
