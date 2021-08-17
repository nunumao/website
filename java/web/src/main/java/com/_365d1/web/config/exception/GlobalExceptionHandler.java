package com._365d1.web.config.exception;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 全局异常处理
// +----------------------------------------------------------------------
// | 时　　间: 2020/1/2 1:21
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import com._365d1.common.utils.ResultFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 自定义异常处理
     *
     * @param e 异常
     * @return 提示信息
     */
    @ExceptionHandler(value = CustomException.class)
    public Object customExceptionHandler(CustomException e) {
        this.errorLog(e);
        return ResultFormat.error(e.getMessage());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Object missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        log.error("缺少参数异常:{}", e.getMessage());
        return ResultFormat.response(300, e.getMessage(), "");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Object methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        this.errorLog(e);
        return ResultFormat.error(e.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public Object runtimeExceptionHandler(Exception e) {
        this.errorLog(e);
        return ResultFormat.error("服务器繁忙，请稍候重试~");
    }

    @ExceptionHandler(value = Exception.class)
    public Object exceptionHandler(Exception e) {
        this.errorLog(e);
        return ResultFormat.response(500, "服务器未知错误，请稍候重试~", "");
    }

    private void errorLog(Exception e) {
        log.error("错误 ---> {}", e.getClass().getName());
        log.error("消息 ---> {}", e.getMessage());
        e.printStackTrace();
    }

}
