package com.wenzhi.leetcode_service.service.impl;

import com.wenzhi.leetcode_service.entity.UserEntity;
import com.wenzhi.leetcode_service.dao.UserDao;
import com.wenzhi.leetcode_service.entity.vo.UserVO;
import com.wenzhi.leetcode_service.service.UserService;
import com.wenzhi.leetcode_service.entity.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userMapper;
    private final PasswordEncoder passwordEncoder;

    // @Autowired
    public UserServiceImpl(UserDao userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserVO login(String username, String password) {
        // 查询用户
        Optional<UserEntity> userOpt = userMapper.findByUsername(username);
        UserEntity user = userOpt.orElseThrow(() -> new BusinessException(404, "用户不存在"));

        // 验证密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException(401, "密码错误");
        }

        // 转换为VO
        return convertToUserVO(user);
    }

    private UserVO convertToUserVO(UserEntity user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setPassword(null); // 清除敏感字段
        return userVO;
    }
}