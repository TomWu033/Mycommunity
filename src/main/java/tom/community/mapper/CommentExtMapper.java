package tom.community.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import tom.community.model.Comment;
import tom.community.model.CommentExample;
import tom.community.model.Question;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}