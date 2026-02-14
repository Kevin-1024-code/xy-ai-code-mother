# App模块功能代码生成
_Exported on 2026/2/12 at GMT+8 23:09:32 from Cursor (2.2.44)_

---

**User**

请参考项目中已有的User模块的文件和代码风格，帮我根据下列需求，生成完整的App模块的代码。
需要的功能如下:
【用户】创建应用(须填写 initPrompt)
【用户]根据id修改自己的应用(目前只支持修改应用名称)
-【用户】根据id删除自己的应用
【用户】根据id查看应用详情
【用户】分页查询自己的应用列表(支持根据名称查询，每页最多 20个)
【用户】分页查询精选的应用列表(支持根据名称查询，每页最多20个)
【管理员】根据 id删除任意应用
【管理员】根据id更新任意应用(支持更新应用名称、应用封面、优先级)
【管理员】分页查询应用列表(支持根据除时间外的任何字段查询，每页数量不限)
【管理员】根据id查看应用详情

---

**Cursor**

I'll analyze the existing User module to understand the code style and structure, then generate a complete App module following the same patterns.

好的，我将参考 User 模块的代码风格，为你生成完整的 App 模块代码。让我开始创建所需的文件。

```java
package com.xy.xyaicodemother.model.dto.app;

import lombok.Data;

import java.io.Serializable;

/**
 * 应用创建请求
 */
@Data
public class AppAddRequest implements Serializable {

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用初始化的 prompt
     */
    private String initPrompt;

    private static final long serialVersionUID = 1L;
}

```

```java
package com.xy.xyaicodemother.model.dto.app;

import lombok.Data;

import java.io.Serializable;

/**
 * 应用更新请求（用户）
 */
@Data
public class AppUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 应用名称
     */
    private String appName;

    private static final long serialVersionUID = 1L;
}

```

```java
package com.xy.xyaicodemother.model.dto.app;

import lombok.Data;

import java.io.Serializable;

/**
 * 应用更新请求（管理员）
 */
@Data
public class AppUpdateByAdminRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用封面
     */
    private String cover;

    /**
     * 优先级
     */
    private Integer priority;

    private static final long serialVersionUID = 1L;
}

```

```java
package com.xy.xyaicodemother.model.dto.app;

import com.xy.xyaicodemother.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 应用查询请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AppQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用封面
     */
    private String cover;

    /**
     * 应用初始化的 prompt
     */
    private String initPrompt;

    /**
     * 代码生成类型（枚举）
     */
    private String codeGenType;

    /**
     * 部署标识
     */
    private String deployKey;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 创建用户id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}

```

```java
package com.xy.xyaicodemother.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 应用视图对象
 */
@Data
public class AppVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用封面
     */
    private String cover;

    /**
     * 应用初始化的 prompt
     */
    private String initPrompt;

    /**
     * 代码生成类型（枚举）
     */
    private String codeGenType;

    /**
     * 部署标识
     */
    private String deployKey;

    /**
     * 部署时间
     */
    private LocalDateTime deployedTime;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 创建用户id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;
}

```

```java
package com.xy.xyaicodemother.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.xy.xyaicodemother.model.dto.app.AppQueryRequest;
import com.xy.xyaicodemother.model.entity.App;
import com.xy.xyaicodemother.model.vo.AppVO;

import java.util.List;

/**
 * 应用 服务层。
 *
 * @author <a>程序员咸鱼</a>
 */
public interface AppService extends IService<App> {

    /**
     * 获取应用视图对象
     *
     * @param app 应用实体
     * @return 应用视图对象
     */
    AppVO getAppVO(App app);

    /**
     * 获取应用视图对象列表
     *
     * @param appList 应用列表
     * @return 应用视图对象列表
     */
    List<AppVO> getAppVOList(List<App> appList);

    /**
     * 根据查询条件构造数据查询参数
     *
     * @param appQueryRequest 查询请求
     * @return 查询包装器
     */
    QueryWrapper getQueryWrapper(AppQueryRequest appQueryRequest);

    /**
     * 校验应用参数
     *
     * @param app 应用实体
     * @param add 是否为创建操作
     */
    void validApp(App app, boolean add);

}

```

```java
package com.xy.xyaicodemother.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xy.xyaicodemother.exception.BusinessException;
import com.xy.xyaicodemother.exception.ErrorCode;
import com.xy.xyaicodemother.exception.ThrowUtils;
import com.xy.xyaicodemother.model.dto.app.AppQueryRequest;
import com.xy.xyaicodemother.model.entity.App;
import com.xy.xyaicodemother.mapper.AppMapper;
import com.xy.xyaicodemother.model.vo.AppVO;
import com.xy.xyaicodemother.service.AppService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 应用 服务层实现。
 *
 * @author <a>程序员咸鱼</a>
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {

    @Override
    public AppVO getAppVO(App app) {
        if (app == null) {
            return null;
        }
        AppVO appVO = new AppVO();
        BeanUtil.copyProperties(app, appVO);
        return appVO;
    }

    @Override
    public List<AppVO> getAppVOList(List<App> appList) {
        if (CollUtil.isEmpty(appList)) {
            return new ArrayList<>();
        }
        return appList.stream()
                .map(this::getAppVO)
                .collect(Collectors.toList());
    }

    @Override
    public QueryWrapper getQueryWrapper(AppQueryRequest appQueryRequest) {
        if (appQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = appQueryRequest.getId();
        String appName = appQueryRequest.getAppName();
        String cover = appQueryRequest.getCover();
        String initPrompt = appQueryRequest.getInitPrompt();
        String codeGenType = appQueryRequest.getCodeGenType();
        String deployKey = appQueryRequest.getDeployKey();
        Integer priority = appQueryRequest.getPriority();
        Long userId = appQueryRequest.getUserId();
        String sortField = appQueryRequest.getSortField();
        String sortOrder = appQueryRequest.getSortOrder();

        return QueryWrapper.create()
                .eq("id", id)
                .like("appName", appName)
                .like("cover", cover)
                .like("initPrompt", initPrompt)
                .eq("codeGenType", codeGenType)
                .eq("deployKey", deployKey)
                .eq("priority", priority)
                .eq("userId", userId)
                .orderBy(sortField, "ascend".equals(sortOrder));
    }

    @Override
    public void validApp(App app, boolean add) {
        ThrowUtils.throwIf(app == null, ErrorCode.PARAMS_ERROR);
        String appName = app.getAppName();
        String initPrompt = app.getInitPrompt();

        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StrUtil.isBlank(appName), ErrorCode.PARAMS_ERROR, "应用名称不能为空");
            ThrowUtils.throwIf(StrUtil.isBlank(initPrompt), ErrorCode.PARAMS_ERROR, "应用初始化 prompt 不能为空");
        }
        // 有参数则校验
        if (StrUtil.isNotBlank(appName)) {
            ThrowUtils.throwIf(appName.length() > 80, ErrorCode.PARAMS_ERROR, "应用名称过长");
        }
    }

}

```

```java
package com.xy.xyaicodemother.controller;

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.xy.xyaicodemother.annotation.AuthCheck;
import com.xy.xyaicodemother.common.BaseResponse;
import com.xy.xyaicodemother.common.DeleteRequest;
import com.xy.xyaicodemother.common.ResultUtils;
import com.xy.xyaicodemother.constant.UserConstant;
import com.xy.xyaicodemother.exception.BusinessException;
import com.xy.xyaicodemother.exception.ErrorCode;
import com.xy.xyaicodemother.exception.ThrowUtils;
import com.xy.xyaicodemother.model.dto.app.AppAddRequest;
import com.xy.xyaicodemother.model.dto.app.AppQueryRequest;
import com.xy.xyaicodemother.model.dto.app.AppUpdateByAdminRequest;
import com.xy.xyaicodemother.model.dto.app.AppUpdateRequest;
import com.xy.xyaicodemother.model.entity.User;
import com.xy.xyaicodemother.model.vo.AppVO;
import com.xy.xyaicodemother.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.xy.xyaicodemother.model.entity.App;
import com.xy.xyaicodemother.service.AppService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 应用 控制层。
 *
 * @author <a>程序员咸鱼</a>
 */
@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private AppService appService;

    @Autowired
    private UserService userService;

    // region 用户接口

    /**
     * 创建应用（用户）
     *
     * @param appAddRequest 应用创建请求
     * @param request       请求对象
     * @return 应用 id
     */
    @PostMapping("/add")
    public BaseResponse<Long> addApp(@RequestBody AppAddRequest appAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(appAddRequest == null, ErrorCode.PARAMS_ERROR);
        App app = new App();
        BeanUtil.copyProperties(appAddRequest, app);
        // 校验参数
        appService.validApp(app, true);
        // 获取当前登录用户
        User loginUser = userService.getLoginUser(request);
        app.setUserId(loginUser.getId());
        // 保存应用
        boolean result = appService.save(app);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(app.getId());
    }

    /**
     * 根据 id 删除应用（用户）
     *
     * @param deleteRequest 删除请求
     * @param request       请求对象
     * @return 是否删除成功
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteApp(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断应用是否存在
        App oldApp = appService.getById(id);
        ThrowUtils.throwIf(oldApp == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人可以删除
        if (!oldApp.getUserId().equals(loginUser.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 删除应用
        boolean result = appService.removeById(id);
        return ResultUtils.success(result);
    }

    /**
     * 更新应用（用户，仅支持修改应用名称）
     *
     * @param appUpdateRequest 应用更新请求
     * @param request          请求对象
     * @return 是否更新成功
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateApp(@RequestBody AppUpdateRequest appUpdateRequest, HttpServletRequest request) {
        if (appUpdateRequest == null || appUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        long id = appUpdateRequest.getId();
        // 判断应用是否存在
        App oldApp = appService.getById(id);
        ThrowUtils.throwIf(oldApp == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人可以修改
        if (!oldApp.getUserId().equals(loginUser.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 只允许修改应用名称
        App app = new App();
        app.setId(id);
        app.setAppName(appUpdateRequest.getAppName());
        // 校验参数
        appService.validApp(app, false);
        // 更新应用
        boolean result = appService.updateById(app);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取应用详情（用户）
     *
     * @param id 应用 id
     * @return 应用详情
     */
    @GetMapping("/get/vo")
    public BaseResponse<AppVO> getAppVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 获取当前登录用户
        User loginUser = userService.getLoginUser(request);
        // 查询应用
        App app = appService.getById(id);
        ThrowUtils.throwIf(app == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人可以查看
        if (!app.getUserId().equals(loginUser.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        return ResultUtils.success(appService.getAppVO(app));
    }

    /**
     * 分页查询自己的应用列表（用户）
     *
     * @param appQueryRequest 查询请求
     * @param request         请求对象
     * @return 应用列表
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<AppVO>> listMyAppVOByPage(@RequestBody AppQueryRequest appQueryRequest,
                                                        HttpServletRequest request) {
        ThrowUtils.throwIf(appQueryRequest == null, ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        // 限制每页最多 20 个
        long pageSize = appQueryRequest.getPageSize();
        ThrowUtils.throwIf(pageSize > 20, ErrorCode.PARAMS_ERROR, "每页最多 20 个");
        // 只查询当前用户的应用
        appQueryRequest.setUserId(loginUser.getId());
        long pageNum = appQueryRequest.getPageNum();
        // 构造查询条件
        QueryWrapper queryWrapper = appService.getQueryWrapper(appQueryRequest);
        Page<App> appPage = appService.page(Page.of(pageNum, pageSize), queryWrapper);
        // 转换为 VO
        Page<AppVO> appVOPage = new Page<>(pageNum, pageSize, appPage.getTotalRow());
        List<AppVO> appVOList = appService.getAppVOList(appPage.getRecords());
        appVOPage.setRecords(appVOList);
        return ResultUtils.success(appVOPage);
    }

    /**
     * 分页查询精选的应用列表（用户）
     *
     * @param appQueryRequest 查询请求
     * @return 应用列表
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<AppVO>> listAppVOByPage(@RequestBody AppQueryRequest appQueryRequest) {
        ThrowUtils.throwIf(appQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 限制每页最多 20 个
        long pageSize = appQueryRequest.getPageSize();
        ThrowUtils.throwIf(pageSize > 20, ErrorCode.PARAMS_ERROR, "每页最多 20 个");
        long pageNum = appQueryRequest.getPageNum();
        // 查询优先级大于 0 的应用（精选应用）
        QueryWrapper queryWrapper = appService.getQueryWrapper(appQueryRequest);
        queryWrapper.and(qw -> qw.gt("priority", 0));
        Page<App> appPage = appService.page(Page.of(pageNum, pageSize), queryWrapper);
        // 转换为 VO
        Page<AppVO> appVOPage = new Page<>(pageNum, pageSize, appPage.getTotalRow());
        List<AppVO> appVOList = appService.getAppVOList(appPage.getRecords());
        appVOPage.setRecords(appVOList);
        return ResultUtils.success(appVOPage);
    }

    // endregion

    // region 管理员接口

    /**
     * 根据 id 删除应用（管理员）
     *
     * @param deleteRequest 删除请求
     * @return 是否删除成功
     */
    @PostMapping("/delete/admin")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteAppByAdmin(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        // 判断应用是否存在
        App oldApp = appService.getById(id);
        ThrowUtils.throwIf(oldApp == null, ErrorCode.NOT_FOUND_ERROR);
        // 删除应用
        boolean result = appService.removeById(id);
        return ResultUtils.success(result);
    }

    /**
     * 更新应用（管理员）
     *
     * @param appUpdateByAdminRequest 应用更新请求
     * @return 是否更新成功
     */
    @PostMapping("/update/admin")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateAppByAdmin(@RequestBody AppUpdateByAdminRequest appUpdateByAdminRequest) {
        if (appUpdateByAdminRequest == null || appUpdateByAdminRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        App app = new App();
        BeanUtil.copyProperties(appUpdateByAdminRequest, app);
        // 校验参数
        appService.validApp(app, false);
        // 判断应用是否存在
        long id = appUpdateByAdminRequest.getId();
        App oldApp = appService.getById(id);
        ThrowUtils.throwIf(oldApp == null, ErrorCode.NOT_FOUND_ERROR);
        // 更新应用
        boolean result = appService.updateById(app);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 分页查询应用列表（管理员）
     *
     * @param appQueryRequest 查询请求
     * @return 应用列表
     */
    @PostMapping("/list/page/admin")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<AppVO>> listAppByPageAdmin(@RequestBody AppQueryRequest appQueryRequest) {
        ThrowUtils.throwIf(appQueryRequest == null, ErrorCode.PARAMS_ERROR);
        long pageNum = appQueryRequest.getPageNum();
        long pageSize = appQueryRequest.getPageSize();
        // 构造查询条件
        QueryWrapper queryWrapper = appService.getQueryWrapper(appQueryRequest);
        Page<App> appPage = appService.page(Page.of(pageNum, pageSize), queryWrapper);
        // 转换为 VO
        Page<AppVO> appVOPage = new Page<>(pageNum, pageSize, appPage.getTotalRow());
        List<AppVO> appVOList = appService.getAppVOList(appPage.getRecords());
        appVOPage.setRecords(appVOList);
        return ResultUtils.success(appVOPage);
    }

    /**
     * 根据 id 获取应用详情（管理员）
     *
     * @param id 应用 id
     * @return 应用详情
     */
    @GetMapping("/get/admin")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<App> getAppById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        App app = appService.getById(id);
        ThrowUtils.throwIf(app == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(app);
    }

    // endregion

}

```

完成！我已经参考 User 模块的代码风格，为你生成了完整的 App 模块代码。以下是创建的文件：

**DTO 类（请求对象）：**
- `AppAddRequest.java` - 用户创建应用请求（包含 appName 和 initPrompt）
- `AppUpdateRequest.java` - 用户更新应用请求（仅支持修改 appName）
- `AppUpdateByAdminRequest.java` - 管理员更新应用请求（支持修改 appName、cover、priority）
- `AppQueryRequest.java` - 应用查询请求（支持多字段查询）

**VO 类（视图对象）：**
- `AppVO.java` - 应用视图对象（用于数据脱敏返回）

**Service 层：**
- 更新了 `AppService.java` 接口，添加了业务方法
- 更新了 `AppServiceImpl.java` 实现类，实现了查询包装器、数据转换、参数校验等逻辑

**Controller 层：**
完全重写了 `AppController.java`，实现了所有需求的接口：

**用户接口：**
- `POST /app/add` - 创建应用（需填写 initPrompt）
- `POST /app/update` - 修改自己的应用（仅支持修改应用名称）
- `POST /app/delete` - 删除自己的应用
- `GET /app/get/vo` - 查看应用详情
- `POST /app/my/list/page/vo` - 分页查询自己的应用列表（支持名称查询，每页最多 20 个）
- `POST /app/list/page/vo` - 分页查询精选应用列表（优先级 > 0，每页最多 20 个）

**管理员接口：**
- `POST /app/delete/admin` - 删除任意应用
- `POST /app/update/admin` - 更新任意应用（支持更新名称、封面、优先级）
- `POST /app/list/page/admin` - 分页查询应用列表（支持多字段查询，每页数量不限）
- `GET /app/get/admin` - 查看应用详情

所有代码都遵循了项目的编码规范，包括权限校验、参数验证、异常处理等。

