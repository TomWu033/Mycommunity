package tom.community.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tom.community.mapper.LoginTicketMapper;
import tom.community.mapper.UserMapper;
import tom.community.model.LoginTicket;
import tom.community.model.User;
import tom.community.model.UserExample;
import tom.community.util.Md5Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 解决已有用户登录创建新的id导致不一致的问题
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginTicketMapper loginTicketMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size()==0){
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }
        else{
            //更新
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
        }
    }
    //注册功能
    public Map<String,String> register(String name, String password){
        Map<String,String> map=new HashMap<>();
        if (StringUtils.isBlank(name)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return map;
        }
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andNameEqualTo(name);
        List<User> users=userMapper.selectByExample(userExample);
        if (users.size()!=0){
            map.put("msg","用户名已经被注册");
            return map;
        }
        User user = new User();
        user.setName(name);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));//生成随机的UUID并截取5个字符作为盐
        user.setAvatarUrl("https://avatars1.githubusercontent.com/u/50907578?v=4");
        user.setPassword(Md5Util.MD5(password+user.getSalt()));
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        userMapper.insert(user);
        UserExample userExample1 = new UserExample();
        userExample1.createCriteria()
                .andNameEqualTo(name);
        List<User> users1=userMapper.selectByExample(userExample);
        String ticket = addLoginTicket(users1.get(0).getId());
        map.put("ticket",ticket);
        return map;
    }

    //登录功能
    public Map<String, String> login(String name, String password) {
        Map<String,String> map=new HashMap<>();
        if (StringUtils.isBlank(name)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return map;
        }
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andNameEqualTo(name);
        List<User> users=userMapper.selectByExample(userExample);
        if (users.size()==0){
            map.put("msg","用户名不存在");
            return map;
        }
        //判断密码
        if (!Md5Util.MD5(password+users.get(0).getSalt()).equals(users.get(0).getPassword())){
            map.put("msg","密码错误");
            return map;
        }
        String ticket = addLoginTicket(users.get(0).getId());
        map.put("ticket",ticket);
        return map;
    }

    //写入cookie的ticket
    public String addLoginTicket(long userId){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        loginTicket.setExpired(System.currentTimeMillis()+7*24*60*60*1000);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketMapper.insert(loginTicket);
        return loginTicket.getTicket();
    }

    public String getUser(long actorId) {
        User user=userMapper.selectByPrimaryKey(actorId);
        return user.getName();
    }
}
