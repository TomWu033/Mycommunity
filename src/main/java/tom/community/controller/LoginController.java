package tom.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tom.community.mapper.LoginTicketMapper;
import tom.community.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @RequestMapping(value = "/reg/", method = RequestMethod.POST)
    public String register(Model model,
                           @RequestParam(value = "name") String name,
                           @RequestParam(value = "password") String password,
                           HttpServletResponse response) {
        try {
            Map<String, String> map = userService.register(name, password);
            //出现问题，返回给前端
            if (!map.containsKey("ticket")) {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            } else {
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");//可在同一应用服务器内共享方法
                response.addCookie(cookie);
                return "redirect:/";
            }
        } catch (Exception e) {
            return "error";
        }
    }

    @RequestMapping(value = "/login/", method = RequestMethod.POST)
    public String login(Model model,
                        @RequestParam(value = "name") String name,
                        @RequestParam(value = "password") String password,
                        @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme,
                        HttpServletResponse response) {
        try {
            Map<String, String> map = userService.login(name, password);
            //出现问题，返回给前端
            if (!map.containsKey("ticket")) {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            } else {
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");//可在同一应用服务器内共享方法
                response.addCookie(cookie);
                return "redirect:/";
            }
        } catch (Exception e) {
            return "error";
        }
    }

    @RequestMapping(value = "/reglogin", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        //删除Session
        request.getSession().removeAttribute("user");
        //删除cookie
        Cookie cookie1=new Cookie("token",null);
        cookie1.setMaxAge(0);
        response.addCookie(cookie1);
        Cookie cookie2=new Cookie("ticket",null);
        cookie2.setMaxAge(0);
        response.addCookie(cookie2);
       /* Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies) {
            if (cookie.getName().equals("ticket")) {
                String ticket = cookie.getValue();
                LoginTicketExample loginTicketExample = new LoginTicketExample();
                loginTicketExample.createCriteria()
                        .andTicketEqualTo(ticket);
                List<LoginTicket> loginTickets = loginTicketMapper.selectByExample(loginTicketExample);
                LoginTicket dbTicket=loginTickets.get(0);
                LoginTicket updateTicket = new LoginTicket();
                updateTicket.setStatus(1);
                LoginTicketExample example = new LoginTicketExample();
                example.createCriteria()
                        .andIdEqualTo(dbTicket.getId());
                loginTicketMapper.updateByExampleSelective(updateTicket,example);
            }
        }*/
        return "redirect:/";
    }
}
