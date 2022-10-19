package top.xc27.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.xc27.entity.MyRuntimeException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(MyRuntimeException.class)
    public R<String> RuntimeException(MyRuntimeException e){
        e.printStackTrace();
        log.error("出现异常!" + "{" + e.getMessage() + "}");
        return R.error("出现异常!" + "{" + e.getMessage() + "}");
    }

    @ExceptionHandler(Exception.class)
    public R<String> Exception(Exception e){
        if(e instanceof DataIntegrityViolationException){
            e.printStackTrace();
            log.error(e.getMessage());
            return R.error("数据库新增操作异常!请查看日志!");
        }
        if(e instanceof SQLException){
            e.printStackTrace();
            log.error(e.getMessage());
            return R.error("数据库操作异常!请查看日志!");
        }
        e.printStackTrace();
        log.error("出现异常!" + "{" + e.getMessage() + "}");
        return R.error("出现异常!" + "{" + "请查看日志" + "}");
    }
}
