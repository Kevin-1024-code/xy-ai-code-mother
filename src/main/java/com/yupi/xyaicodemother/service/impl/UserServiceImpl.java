package com.yupi.xyaicodemother.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.yupi.xyaicodemother.model.entity.User;
import com.yupi.xyaicodemother.mapper.UserMapper;
import com.yupi.xyaicodemother.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户 服务层实现。
 *
 * @author <a>程序员咸鱼</a>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>  implements UserService{

}
