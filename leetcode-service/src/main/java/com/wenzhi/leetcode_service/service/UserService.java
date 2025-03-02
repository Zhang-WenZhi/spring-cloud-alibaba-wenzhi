package com.wenzhi.leetcode_service.service;

import com.wenzhi.leetcode_service.entity.message.Response;
import com.wenzhi.leetcode_service.entity.vo.UserVO;

public interface UserService {
    public UserVO login(String username, String password);
}
