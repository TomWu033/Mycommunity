package tom.community.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tom.community.dto.AccessTokenDTO;
import tom.community.dto.GithubUser;
import tom.community.mapper.UserMapper;
import tom.community.model.LoginTicket;
import tom.community.model.LoginTicketExample;
import tom.community.model.User;
import tom.community.provider.GithubProvider;
import tom.community.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
public class AuthorizeController {

    @Autowired//自动将spring容器写好的实例加载到这
    private GithubProvider githubProvider;


    @Value("${github.client.id}")//读取application。properties的配置的值
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser!=null&&githubUser.getId()!=null){
            //登录成功，写cookies和session
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token",token));
            //写入cookies

            return "redirect:/";//callback后跳转到index
        }
        else {
            log.error("callback get github error,{}",githubUser);//追加日志，用户登录失败，上面的@sjf4j是lombok注解，直接添加log
            //登录失败，重新登录
            return "redirect:/";
        }
    }


}
