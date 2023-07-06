package com.example.pintuan.service.impl;

import com.example.pintuan.entity.User;
import com.example.pintuan.mapper.UserMapper;
import com.example.pintuan.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  用户服务实现类
 * </p>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
