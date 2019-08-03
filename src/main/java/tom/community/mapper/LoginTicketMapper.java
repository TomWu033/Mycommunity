package tom.community.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import tom.community.model.LoginTicket;
import tom.community.model.LoginTicketExample;

public interface LoginTicketMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_ticket
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    long countByExample(LoginTicketExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_ticket
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    int deleteByExample(LoginTicketExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_ticket
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_ticket
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    int insert(LoginTicket record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_ticket
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    int insertSelective(LoginTicket record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_ticket
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    List<LoginTicket> selectByExampleWithRowbounds(LoginTicketExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_ticket
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    List<LoginTicket> selectByExample(LoginTicketExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_ticket
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    LoginTicket selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_ticket
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    int updateByExampleSelective(@Param("record") LoginTicket record, @Param("example") LoginTicketExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_ticket
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    int updateByExample(@Param("record") LoginTicket record, @Param("example") LoginTicketExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_ticket
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    int updateByPrimaryKeySelective(LoginTicket record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_ticket
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    int updateByPrimaryKey(LoginTicket record);
}