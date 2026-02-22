package com.xy.xyaicodemother.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.xy.xyaicodemother.exception.BusinessException;
import com.xy.xyaicodemother.exception.ErrorCode;
import com.xy.xyaicodemother.exception.ThrowUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Set;

@Service
@Slf4j
public class ProjectDownloadServiceImpl implements ProjectDownloadService {

    /**
     * 需要过滤的文件和目录名称
     */
    private static final Set<String> IGNORED_NAMES = Set.of(
            "node_modules",
            ".git",
            "dist",
            "build",
            ".DS_Store",
            ".env",
            "target",
            ".mvn",
            ".idea",
            ".vscode"
    );

    /**
     * 需要过滤的文件扩展名
     */
    private static final Set<String> IGNORED_EXTENSIONS = Set.of(
            ".log",
            ".tmp",
            ".cache"
    );


    @Override
    public void downloadProjectAsZip(String projectPath, String downloadFileName, HttpServletResponse response) {
        // 基础校验
        ThrowUtils.throwIf(StrUtil.isBlank(projectPath), ErrorCode.PARAMS_ERROR, "项目路径不能为空");
        ThrowUtils.throwIf(StrUtil.isBlank(downloadFileName), ErrorCode.PARAMS_ERROR, "下载文件名不能为空");
        File projectDir = new File(projectPath);
        ThrowUtils.throwIf(!projectDir.exists(), ErrorCode.PARAMS_ERROR, "项目路径不存在");
        ThrowUtils.throwIf(!projectDir.isDirectory(), ErrorCode.PARAMS_ERROR, "项目路径不是一个目录");
        log.info("开始打包下载项目: {} -> {}.zip", projectPath, downloadFileName);
        // 设置 HTTP 响应头
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/zip");
        response.addHeader("Content-Disposition",
                String.format("attachment; filename=\"%s.zip\"", downloadFileName));
        // 定义文件过滤器
        FileFilter filter = file -> isPathAllowed(projectDir.toPath(), file.toPath());
        // 使用临时文件绕过 Hutool 的 OutputStream 目标为 null 的 Bug
        File tempZipFile = null;
        try {
            // 1. 在系统临时目录创建一个临时 zip 文件
            tempZipFile = File.createTempFile("project_download_", ".zip");

            // 2. 让 Hutool 打包到这个临时文件里（此时目标有具体文件，不会触发 null 报错）
            ZipUtil.zip(tempZipFile, StandardCharsets.UTF_8, false, filter, projectDir);

            // 3. (可选) 设置响应体大小，这样浏览器下载时能显示进度条！
            response.setContentLengthLong(tempZipFile.length());

            // 4. 将临时文件的内容拷贝到 HttpServletResponse 的输出流中
            try (FileInputStream fis = new FileInputStream(tempZipFile)) {
                cn.hutool.core.io.IoUtil.copy(fis, response.getOutputStream());
            }

            log.info("项目打包下载完成: {}", downloadFileName);
        } catch (Exception e) {
            log.error("项目打包下载异常", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "项目打包下载失败");
        } finally {
            // 5. 极其重要：务必在 finally 中删除临时文件，防止随着下载量增加撑爆服务器磁盘！
            if (tempZipFile != null && tempZipFile.exists()) {
                cn.hutool.core.io.FileUtil.del(tempZipFile);
            }
        }
    }

    /**
     * 校验路径是否允许包含在压缩包中
     *
     * @param projectRoot 项目根目录
     * @param fullPath    完整路径
     * @return 是否允许
     */
    private boolean isPathAllowed(Path projectRoot, Path fullPath) {
        // 获取相对路径
        Path relativePath = projectRoot.relativize(fullPath);
        // 检查路径中的每一部分是否符合要求
        for (Path part : relativePath) {
            String partName = part.toString();
            // 检查是否在忽略名称列表中
            if (IGNORED_NAMES.contains(partName)) {
                return false;
            }
            // 检查是否以忽略扩展名结尾
            if (IGNORED_EXTENSIONS.stream().anyMatch(ext -> partName.toLowerCase().endsWith(ext))) {
                return false;
            }
        }
        return true;
    }
}
