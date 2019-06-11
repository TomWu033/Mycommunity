package tom.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tom.community.dto.QuestionDTO;
import tom.community.model.Question;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param(value="offset") Integer offset,@Param(value="size") Integer size);

    //查询页面总数
    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{userId} limit #{offset},#{size}")
    List<Question> listByUserId(@Param(value="userId") Integer userId,@Param(value="offset") Integer offset,@Param(value="size") Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param(value="userId") Integer userId);

    @Select("select * from question where id=#{id}")
    Question getById(@Param(value="id")Integer id);
}