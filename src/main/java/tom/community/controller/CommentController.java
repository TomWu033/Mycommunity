package tom.community.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import tom.community.dto.CommentCreateDTO;
import tom.community.dto.CommentDTO;
import tom.community.dto.ResultDTO;
import tom.community.enums.CommentTypeEnum;
import tom.community.exception.CustomizeErrorCode;
import tom.community.model.Comment;
import tom.community.model.User;
import tom.community.service.CommentService;
import tom.community.service.LikeService;
import tom.community.service.SensitiveService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private SensitiveService sensitiveService;


    @ResponseBody //可以自动把对象化为JSON传递到前端，RequestBody可以把JSON转为对象
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        //校验
        User user =(User) request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if(commentCreateDTO==null|| StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));//html标签转义
        comment.setContent(sensitiveService.filter(comment.getContent()));//敏感词过滤
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment,user);
        return ResultDTO.okOf();
    }

    @ResponseBody //可以自动把对象化为JSON传递到前端，RequestBody可以把JSON转为对象
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO comments(@PathVariable(name = "id") Long id){
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }
}
