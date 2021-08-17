package com._365d1.web.web.manager;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/8/1 02:11
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.PathUtil;
import cn.hutool.core.util.IdUtil;
import com._365d1.common.utils.Pages;
import com._365d1.common.utils.Path;
import com._365d1.common.utils.ResultFormat;
import com._365d1.common.utils.SQLDatetime;
import com._365d1.model.File;
import com._365d1.service.FileService;
import com._365d1.web.utils.LoginUser;
import com._365d1.web.web.ManagerBase;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.regex.Matcher;

@Api(tags = "文件管理")
@RestController(value = ManagerBase.CRTL_PREFIX + "FileController")
@RequestMapping(value = "file")
public class FileController extends ManagerBase {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list(
            @ApiParam(value = "目录ID") @RequestParam(value = "folderId") String folderId,
            @ApiParam(value = "页码") @RequestParam(value = "page", defaultValue = "1") Integer page
    ) {
        QueryWrapper<File> query = new QueryWrapper<>();
        query.eq(File.FOLDER_ID, folderId);
        IPage<File> list = fileService.page(new Pages<>(page, this.pageSize), query);
        if (list.getRecords().size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "上传文件")
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Object upload(
            @RequestParam(value = "index") Integer index,
            @RequestParam(value = "total") Integer total,
            @RequestParam("file") MultipartFile file
    ) {
        if (file.isEmpty()) {
            return ResultFormat.error("上传失败,请选择文件");
        }
        try {
            String originalName = file.getOriginalFilename();
            String name = originalName.split("\\.")[0];
            String ext = originalName.split("\\.")[1];
            String path = Path.getAbsolutePath("upload");
            String userId = LoginUser.instance().userId();

            String fileName = "";
            long size = 0;

            if (index.equals(0) && total.equals(0)) {
                fileName = IdUtil.simpleUUID() + "." + ext;
                size = file.getSize();
                file.transferTo(new java.io.File(path + fileName));
            } else if (index < total) {
                String temp = Path.getAbsolutePath("upload/temp/") + name;
                java.io.File tempFile = new java.io.File(String.format("%s_%s_%s", temp, userId, index));
                file.transferTo(tempFile);
                return ResultFormat.response(201, "分片 " + index + " 上传成功", "");
            } else if (index.equals(total)) {
                fileName = IdUtil.simpleUUID() + "." + ext;
                String mergeFile = path + fileName;
                String temp = Path.getAbsolutePath("upload/temp/") + name;

                FileOutputStream outputStream = new FileOutputStream(mergeFile, true);
                for (int i = 0; i < total; i++) {
                    String part = String.format("%s_%s_%s", temp, userId, i);
                    java.io.File read = new java.io.File(part);
                    Long readLength = read.length();
                    size += readLength;
                    byte[] content = new byte[readLength.intValue()];
                    FileInputStream inputStream = new FileInputStream(read);
                    inputStream.read(content);
                    inputStream.close();
                    outputStream.write(content);
                }
                outputStream.flush();
                outputStream.close();
                // 合并完成后删除临时文件
                for (int i = 0; i < total; i++) {
                    java.io.File del = new java.io.File(String.format("%s_%s_%s", temp, userId, i));
                    if (del.exists() && del.isFile()) {
                        del.delete();
                    }
                }
            }

            // 插入数据库
            File f = new File();
            f.setFolderId(0);
            f.setName(name);
            f.setFileName(fileName);
            f.setPath(Path.convertSeparator("upload/"));
            f.setFileFullName(Path.convertSeparator("upload/") + fileName);
            f.setFileSize(size);
            f.setFileExt(ext);
            f.setCreateTime(SQLDatetime.time());
            fileService.save(f);
            return ResultFormat.success("上传成功", fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFormat.error("上传失败" + e.getMessage());
        }
    }


}
