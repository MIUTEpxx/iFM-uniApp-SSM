package com.pxx.ifmserver.controller;

import com.pxx.ifmserver.result.Result;
import com.pxx.ifmserver.service.SearchHistoryService;
import com.pxx.ifmserver.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/searchHistory")
public class SearchHistoryController {
    @Autowired
    private SearchHistoryService searchHistoryService;

    /**
     * 获取用户搜索历史记录
     * @param userId 用户id
     * @param req
     * @param resp
     * @return searchHistoryList
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @GetMapping("/getSearchHistory")
    public Result getSearchHistory(
            @RequestParam Integer userId,
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("error","Token安全令牌失效,请重新登录");
            return new Result(false,20005,"处理失败",data);
        }
        return searchHistoryService.listSearchHistoryByUserId(userId);
    }

    /**
     * 保存用户搜索记录,并删除超出20条的最早的搜索记录
     * @param userId 用户id
     * @param keyword 搜索内容
     * @param req
     * @param resp
     * @return searchHistoryList
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/saveSearchHistory")
    public Result saveSearchHistory(
            @RequestParam Integer userId,@RequestParam String keyword,
            HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("error","Token安全令牌失效,请重新登录");
            return new Result(false,20005,"处理失败",data);
        }
        return searchHistoryService.saveSearchHistory(userId,keyword);
    }

    /**
     * 删除用户搜索记录
     * @param userId 用户id
     * @param searchHistoryId 搜索记录id
     * @param req
     * @param resp
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @DeleteMapping("/deleteSearchHistory")
    public Result deleteSearchHistory(
            @RequestParam Integer userId,@RequestParam Integer searchHistoryId,
            HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("error","Token安全令牌失效,请重新登录");
            return new Result(false,20005,"处理失败",data);
        }
        return searchHistoryService.deleteSearchHistoryBySearchHistoryId(userId,searchHistoryId);
    }

    /**
     * 删除用户所有的搜索记录
     * @param userId
     * @param req
     * @param resp
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @DeleteMapping("/deleteAll")
    public Result deleteAll(
            @RequestParam Integer userId,
            HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException, NoSuchAlgorithmException {
        // 检验Token
        String newToken = TokenUtil.verifyToken(req, resp,userId);
        if(newToken==null){
            Map<String, Object> data = new HashMap<>();
            data.put("error","Token安全令牌失效,请重新登录");
            return new Result(false,20005,"处理失败",data);
        }
        return searchHistoryService.deleteSearchHistoryByUserId(userId);
    }
}
