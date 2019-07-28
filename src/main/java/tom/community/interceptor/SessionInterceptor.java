package tom.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tom.community.mapper.LoginTicketMapper;
import tom.community.mapper.UserMapper;
import tom.community.model.*;
import tom.community.service.NotificationService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginTicketMapper loginTicketMapper;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private HostHolder hostHolder;

    @Override
    //在执行之前
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    if (users.size() != 0) {
                        request.getSession().setAttribute("user", users.get(0));//把user放到session里
                        Long unreadCount = notificationService.unreadCount(users.get(0).getId());
                        request.getSession().setAttribute("unreadCount", unreadCount);
                        hostHolder.setUsers(users.get(0));
                    }
                    break;
                } else if (cookie.getName().equals("ticket")) {
                    String ticket = cookie.getValue();
                    LoginTicketExample loginTicketExample = new LoginTicketExample();
                    loginTicketExample.createCriteria()
                            .andTicketEqualTo(ticket);
                    List<LoginTicket> loginTickets = loginTicketMapper.selectByExample(loginTicketExample);
                    if (loginTickets.size()==0||loginTickets.get(0).getExpired()<System.currentTimeMillis()||loginTickets.get(0).getStatus()!=0){
                        return true;
                    }
                    User user = userMapper.selectByPrimaryKey(loginTickets.get(0).getUserId());
                    if (user != null) {
                        request.getSession().setAttribute("user", user);//把user放到session里
                        Long unreadCount = notificationService.unreadCount(user.getId());
                        request.getSession().setAttribute("unreadCount", unreadCount);
                        hostHolder.setUsers(user);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        /*
        //页面渲染时可以加user
        if (modelAndView!=null){
            modelAndView.addObject("user",hostHolder.getUser());
        }*/
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
