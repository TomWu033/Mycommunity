package tom.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tom.community.model.User;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user1 (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user1 where token = #{token}")
    User findByToken(@Param("token")String token);//mybatis不是类需要加注解@param，类可以自动加

    @Select("select * from user1 where id = #{id}")
    User findById(@Param("id")Integer id);

    @Select("select * from user1 where account_id = #{account_id}")
    boolean findByAccount_Id(@Param("account_id")String account_id);
}
