package com.ace.cms.rest.exception;

import com.ace.cms.enums.ErrorCode;
import com.ace.cms.exceptions.WebControllerException;
import com.ace.cms.rest.vo.JsonRestResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * RESTful 异常处理类<br>
 * 一个全局的异常处理切面类，用它来统一处理所有的异常行为。
 * 需要注意的是，在运行时从上往下依次调用每个异常处理方法，匹配当前异常类型是否与@ExceptionHandler注解所定义的异常相匹配，若匹配，则执行该方法，同时忽略后续所有的异常处理方法
 * ，最终会返回经JSON序列化后的Response对象。
 * 
 * 业务程序自定义异常:
 * 
 * <pre>
 * Exception                               HTTP Status Code  
 * BaseException                           可预期的业务异常    
 * BaseRTException                         不可预计运行时异常
 * </pre>
 * 
 * SpringMVC自定义异常对应的status code:
 * 
 * <pre>
 * Exception                               HTTP Status Code  
 * BindException                           400 (Bad Request)    参数不正确
 * </pre>
 * 
 * @author zb
 */
@Slf4j
@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class GlobalExceptionAdvice {


    /**
     * 
     * spring mvc 请求参数不正确
     * 
     * @param request
     * @param e
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<JsonRestResponseVo> missingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e);
        
        JsonRestResponseVo result = new JsonRestResponseVo();
        result.setCode(ErrorCode.PARMAS_HAS_ERROR.getCode());
        result.setMessage(e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ExceptionHandler(WebControllerException.class)
    public ResponseEntity<JsonRestResponseVo> webControllerException(HttpServletRequest request, WebControllerException e) {
        log.warn(e.getMessage(),e);
        JsonRestResponseVo result = new JsonRestResponseVo();
        result.setCode(e.getCode());
        result.setMessage(e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
