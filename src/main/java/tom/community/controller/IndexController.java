package tom.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import tom.community.mapper.UserMapper;
import tom.community.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        //获取cookie，并与数据库的对比
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies) {
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user=userMapper.findByToken(token);
                if (user!=null){
                    request.getSession().setAttribute("user",user);//把user放到session里
                }
                break;
            }
        }
        return "index";
    }
}
