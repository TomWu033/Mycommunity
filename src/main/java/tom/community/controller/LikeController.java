package tom.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tom.community.async.EventModel;
import tom.community.async.EventProducer;
import tom.community.async.EventType;
import tom.community.dto.LikeDTO;
import tom.community.dto.QuestionDTO;
import tom.community.dto.ResultDTO;
import tom.community.enums.CommentTypeEnum;
import tom.community.exception.CustomizeErrorCode;
import tom.community.model.Comment;
import tom.community.model.HostHolder;
import tom.community.model.User;
import tom.community.service.CommentService;
import tom.community.service.LikeService;
import tom.community.service.QuestionService;
import tom.community.util.JUtil;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LikeController {
    @Autowired
    private LikeService likeService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private EventProducer eventProducer;
    @Autowired
    private CommentService commentService;
    @Autowired
    private QuestionService questionService;

    @PostMapping("/like")
    @ResponseBody
    public Object like(@RequestBody LikeDTO likeDTO){
        User user = hostHolder.getUser();
        if (user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Long commentId = likeDTO.getCommentId();
        Comment comment=commentService.getComment(commentId);
        QuestionDTO questionDTO=questionService.getById(comment.getParentId());
        eventProducer.fireEvent(new EventModel(EventType.LIKE).setActorId(hostHolder.getUser().getId())
        .setEntityId(commentId).setEntityType(CommentTypeEnum.COMMENT.getType())
                .setExt("questionTitle",questionDTO.getTitle()).setEntityOwnerId(comment.getCommentator()));
        long likeCount=likeService.like(user.getId(), CommentTypeEnum.QUESTION,commentId);
        return ResultDTO.okOf(String.valueOf(likeCount));
    }

    @PostMapping("/dislike")
    @ResponseBody
    public Object dislike(@RequestParam(value = "commentId") Long commentId,
                       HttpServletRequest request){
        User user =(User) request.getSession().getAttribute("user");
        if (user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        long likeCount=likeService.dislike(user.getId(), CommentTypeEnum.QUESTION,commentId);
        return JUtil.getJSONString(0,String.valueOf(likeCount));
    }
}
