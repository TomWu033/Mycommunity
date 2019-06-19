package tom.community.advice;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import tom.community.dto.ResultDTO;
import tom.community.exception.CustomizeErrorCode;
import tom.community.exception.CustomizeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    Object handle(Throwable e, Model model,
                  HttpServletRequest request,
                  HttpServletResponse response) {
        String contentType=request.getContentType();
        if("application/json".equals(contentType)){
            ResultDTO resultDTO;

            //返回JSON
            if(e instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            }
            else{
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR );
            }
            try{
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            }catch (IOException ioe){

            }
            return null;
        }
        else{
            //错误页面跳转
            //如果与自己定义的异常一致
            if(e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }
            //不知道的异常
            else{
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }

            return new ModelAndView("error");
        }
    }
}
