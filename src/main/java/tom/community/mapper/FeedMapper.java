package tom.community.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import tom.community.model.Feed;
import tom.community.model.FeedExample;

public interface FeedMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feed
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    long countByExample(FeedExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feed
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    int deleteByExample(FeedExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feed
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feed
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    int insert(Feed record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feed
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    int insertSelective(Feed record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feed
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    List<Feed> selectByExampleWithRowbounds(FeedExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feed
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    List<Feed> selectByExample(FeedExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feed
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    Feed selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feed
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    int updateByExampleSelective(@Param("record") Feed record, @Param("example") FeedExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feed
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    int updateByExample(@Param("record") Feed record, @Param("example") FeedExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feed
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    int updateByPrimaryKeySelective(Feed record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feed
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    int updateByPrimaryKey(Feed record);
}