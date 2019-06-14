package tom.community.advice;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import tom.community.exception.CustomizeException;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model) {
        //如果与自己定义的异常一致
        if(e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());
        }
        //不知道的异常
        else{
            model.addAttribute("message","服务过热了，请稍后重试！");
        }

        return new ModelAndView("error");
    }
}
