package com._365d1.web.web.manager;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/8/13 16:42
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.common.utils.ResultFormat;
import com._365d1.web.config.minio.MinioResult;
import com._365d1.web.config.minio.MinioService;
import com._365d1.web.web.ManagerBase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "测试")
@RestController(value = ManagerBase.CRTL_PREFIX + "TestController")
@RequestMapping(value = "test")
public class TestController extends ManagerBase {

    @Autowired
    private MinioService minioService;

    @ApiOperation(value = "上传")
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Object upload(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "index") Integer index,
            @RequestParam(value = "total") Integer total,
            @RequestParam(value = "file") MultipartFile file
    ) {
        MinioResult result = minioService.uploadPart(name, index, total, file);
        if (result.getCode() == MinioResult.SUCCESS) {
            return ResultFormat.success("上传成功", result.getFile());
        } else if (result.getCode() == MinioResult.PART_SUCCESS) {
            return ResultFormat.response(201, String.format("分片 %d 上传成功", index), result.getFile());
        }
        return ResultFormat.error("上传失败");
    }

}
