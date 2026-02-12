package com.xy.xyaicodemother.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xy.xyaicodemother.model.entity.App;
import com.xy.xyaicodemother.mapper.AppMapper;
import com.xy.xyaicodemother.service.AppService;
import org.springframework.stereotype.Service;

/**
 * 应用 服务层实现。
 *
 * @author <a>程序员咸鱼</a>
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App>  implements AppService{

}
