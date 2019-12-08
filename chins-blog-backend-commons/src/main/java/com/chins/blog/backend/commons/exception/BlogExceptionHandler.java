package com.chins.blog.backend.commons.exception;

import com.chins.blog.backend.commons.base.ResponseBase;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BlogExceptionHandler {

  /**
   * 启动应用后，被 @ExceptionHandler、@InitBinder、@ModelAttribute 注解的方法， 都会作用在 被 @RequestMapping 注解的方法上。
   *
   * @param binder 参数
   */
  @InitBinder
  public void initWebBinder(WebDataBinder binder) {

  }

  /**
   * 系统错误，未知错误
   *
   * @param ex 异常信息
   * @return 返回前端异常信息
   */
  @ExceptionHandler({Exception.class})
  public ResponseBase exception(Exception ex) {
    return ResponseBase.failed(ex);
  }

  /**
   * 自定义异常信息拦截
   *
   * @param ex 异常信息
   * @return 返回前端异常信息
   */
  @ExceptionHandler(BlogException.class)
  public ResponseBase myCustomizeException(BlogException ex) {
    return ResponseBase
        .failed(ex);
  }
}
