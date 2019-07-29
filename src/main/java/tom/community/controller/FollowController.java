package tom.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tom.community.async.EventModel;
import tom.community.async.EventProducer;
import tom.community.async.EventType;
import tom.community.dto.*;
import tom.community.enums.CommentTypeEnum;
import tom.community.enums.EntityType;
import tom.community.exception.CustomizeErrorCode;
import tom.community.model.HostHolder;
import tom.community.model.Question;
import tom.community.model.User;
import tom.community.service.FollowService;
import tom.community.service.LikeService;
import tom.community.service.QuestionService;
import tom.community.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FollowController {
    @Autowired
    private FollowService followService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private EventProducer eventProducer;
    @Autowired
    private LikeService likeService;

    @PostMapping(value = "/followUser")
    @ResponseBody
    public Object followUser(@RequestBody PostDTO postDTO) {
        if (hostHolder.getUser() == null) {
            return "redirect:/reglogin";
        }
        //关注用户
        Long userId = postDTO.getEntityId();
        boolean ret = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId);
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                .setActorId(hostHolder.getUser().getId()).setEntityId(userId).setEntityType(EntityType.ENTITY_USER)
                .setEntityOwnerId(userId));
        followService.follow(hostHolder.getUser().getId(),EntityType.ENTITY_USER,userId);
        return ret?ResultDTO.okOf(String.valueOf(followService.getFollowerCount(EntityType.ENTITY_USER,hostHolder.getUser().getId())))
                :ResultDTO.errorOf(CustomizeErrorCode.FOLLOW_FAIL);
    }
    @PostMapping(value = "/unfollowUser")
    @ResponseBody
    public Object unfollowUser(@RequestBody PostDTO postDTO) {
        if (hostHolder.getUser() == null) {
            return "redirect:/reglogin";
        }
        //关注用户
        Long userId = postDTO.getEntityId();
        boolean ret = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId);
        eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW)
                .setActorId(hostHolder.getUser().getId()).setEntityId(userId).setEntityType(EntityType.ENTITY_USER)
                .setEntityOwnerId(userId));
        followService.unfollow(hostHolder.getUser().getId(),EntityType.ENTITY_USER,userId);
        return ret?ResultDTO.okOf(String.valueOf(followService.getFollowerCount(EntityType.ENTITY_USER,hostHolder.getUser().getId())))
                :ResultDTO.errorOf(CustomizeErrorCode.UNFOLLOW_FAIL);
    }

    @PostMapping(value = "/followQuestion")
    @ResponseBody
    public Object followQuestion(@RequestBody PostDTO postDTO) {
        if (hostHolder.getUser() == null) {
            return "redirect:/reglogin";
        }
        //关注问题
        Long questionId = postDTO.getEntityId();
        QuestionDTO q=questionService.getById(questionId);
        if (q==null){
            return ResultDTO.errorOf(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        boolean ret = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, questionId);
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                .setActorId(hostHolder.getUser().getId()).setEntityId(questionId).setEntityType(EntityType.ENTITY_QUESTION)
                .setEntityOwnerId(q.getCreator()));
        Map<String,Object> info=new HashMap<String,Object>();
        info.put("AvatarUrl",hostHolder.getUser().getAvatarUrl());
        info.put("name",hostHolder.getUser().getName());
        info.put("id",hostHolder.getUser().getId());
        info.put("count",followService.getFollowerCount(EntityType.ENTITY_QUESTION,questionId));
        followService.follow(hostHolder.getUser().getId(),EntityType.ENTITY_QUESTION,questionId);
        return ret?ResultDTO.okOf(info)
                :ResultDTO.errorOf(CustomizeErrorCode.FOLLOW_FAIL);
    }
    @PostMapping(value = "/unfollowQuestion")
    @ResponseBody
    public Object unfollowQuestion(@RequestBody PostDTO postDTO) {
        if (hostHolder.getUser() == null) {
            return "redirect:/reglogin";
        }
        //关注问题
        Long questionId = postDTO.getEntityId();
        QuestionDTO q=questionService.getById(questionId);
        if (q==null){
            return ResultDTO.errorOf(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        boolean ret = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, questionId);
        eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW)
                .setActorId(hostHolder.getUser().getId()).setEntityId(questionId).setEntityType(EntityType.ENTITY_QUESTION)
                .setEntityOwnerId(q.getCreator()));
        Map<String,Object> info=new HashMap<String,Object>();
        info.put("AvatarUrl",hostHolder.getUser().getAvatarUrl());
        info.put("name",hostHolder.getUser().getName());
        info.put("id",hostHolder.getUser().getId());
        info.put("count",followService.getFollowerCount(EntityType.ENTITY_QUESTION,questionId));
        followService.unfollow(hostHolder.getUser().getId(),EntityType.ENTITY_QUESTION,questionId);
        return ret?ResultDTO.okOf(info)
                :ResultDTO.errorOf(CustomizeErrorCode.UNFOLLOW_FAIL);
    }

    //获取粉丝和关注者
    @GetMapping(value = "/user/{uid}/followees/{action}")
    public String followees(@PathVariable("uid") long userId,
                            @PathVariable("action") String action,
                            Model model) {
        if ("users".equals(action)){
            List<Long> followeeIds=followService.getFollowees(EntityType.ENTITY_USER,userId,0,10);
            if (hostHolder.getUser()==null){
                return "redirect:/reglogin";
            }else{
                model.addAttribute("section","users");
                model.addAttribute("sectionName","关注的人");
                model.addAttribute("followees",getUsersInfo(hostHolder.getUser().getId(),followeeIds));
            }
        }
        else if("questions".equals(action)){
            List<Long> questionIds=followService.getFollowees(EntityType.ENTITY_QUESTION,userId,0,10);
            if (hostHolder.getUser()==null){
                return "redirect:/reglogin";
            }else{
                model.addAttribute("section","questions");
                model.addAttribute("sectionName","关注的问题");
                model.addAttribute("followquestions",getQuestionsInfo(hostHolder.getUser().getId(),questionIds));
            }
        }
        return "followees";
    }
    @GetMapping(value = "/user/{uid}/followers")
    public String followers(@PathVariable("uid") long userId,
                            Model model) {
        List<Long> followerIds=followService.getFollowers(EntityType.ENTITY_USER,userId,0,10);
        if (hostHolder.getUser()==null){
            return "redirect:/reglogin";
        }else{
            model.addAttribute("followers",getUsersInfo(hostHolder.getUser().getId(),followerIds));
        }
        return "followers";
    }

    private List<UserDTO> getUsersInfo(long localUserId,List<Long> userIds){
        List<UserDTO> userInfos=new ArrayList<>();
        for (Long uid:userIds){
            User user=userService.getUser(uid);
            if (user==null){
                continue;
            }
            UserDTO userDTO=new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setAvatarUrl(user.getAvatarUrl());
            userDTO.setFollowerCount(followService.getFollowerCount(EntityType.ENTITY_USER,uid));
            userDTO.setFolloweeCount(followService.getFolloweeCount(EntityType.ENTITY_USER,uid));
            if (localUserId!=0){
                userDTO.setFollowed(followService.isFollower(localUserId,EntityType.ENTITY_USER,uid));
            }else{
                userDTO.setFollowed(false);
            }
            userInfos.add(userDTO);
        }
        return userInfos;
    }
    private List<QuestionDTO> getQuestionsInfo(long localUserId,List<Long> questionIds){
        List<QuestionDTO> questionInfos=new ArrayList<>();
        for (Long qusetionId:questionIds){
            Question question=questionService.getQuestion(qusetionId);
            if (question==null){
                continue;
            }
            QuestionDTO questionDTO=new QuestionDTO();
            questionDTO.setId(question.getId());
            questionDTO.setTitle(question.getTitle());
            User user=userService.getUser(question.getCreator());
            questionDTO.setUser(user);
            questionDTO.setViewCount(question.getViewCount());
            questionDTO.setCommentCount(question.getCommentCount());
            questionDTO.setLikeCount(likeService.getLikeCount(CommentTypeEnum.QUESTION,question.getId()));
            questionDTO.setGmtCreate(question.getGmtCreate());
            questionDTO.setFollowerCount(followService.getFollowerCount(EntityType.ENTITY_QUESTION,question.getId()));
            if (localUserId!=0){
                questionDTO.setFollowed(followService.isFollower(localUserId,EntityType.ENTITY_QUESTION,question.getId()));
            }else{
                questionDTO.setFollowed(false);
            }
            questionInfos.add(questionDTO);
        }
        return questionInfos;
    }
}
