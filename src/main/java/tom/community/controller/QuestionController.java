package tom.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tom.community.dto.CommentDTO;
import tom.community.dto.QuestionDTO;
import tom.community.enums.CommentTypeEnum;
import tom.community.model.Comment;
import tom.community.model.Question;
import tom.community.model.User;
import tom.community.service.CommentService;
import tom.community.service.LikeService;
import tom.community.service.QuestionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Long id,
                           Model model,
                           HttpServletRequest request){
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        for (CommentDTO comment:comments){
            comment.setLikeCount(likeService.getLikeCount(CommentTypeEnum.QUESTION,comment.getId()));
            User user = (User)request.getSession().getAttribute("user");
            if (user==null){
                return "redirect:/reglogin";
            }else{
                comment.setLiked(likeService.getLikeStatus(user.getId(),CommentTypeEnum.QUESTION,comment.getId()));
            }
        }
        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}
