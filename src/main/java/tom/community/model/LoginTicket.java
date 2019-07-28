package tom.community.model;

public class LoginTicket {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column LOGIN_TICKET.ID
     *
     * @mbg.generated Sat Jul 20 16:11:54 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column LOGIN_TICKET.USER_ID
     *
     * @mbg.generated Sat Jul 20 16:11:54 CST 2019
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column LOGIN_TICKET.TICKET
     *
     * @mbg.generated Sat Jul 20 16:11:54 CST 2019
     */
    private String ticket;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column LOGIN_TICKET.EXPIRED
     *
     * @mbg.generated Sat Jul 20 16:11:54 CST 2019
     */
    private Long expired;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column LOGIN_TICKET.STATUS
     *
     * @mbg.generated Sat Jul 20 16:11:54 CST 2019
     */
    private Integer status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column LOGIN_TICKET.ID
     *
     * @return the value of LOGIN_TICKET.ID
     *
     * @mbg.generated Sat Jul 20 16:11:54 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column LOGIN_TICKET.ID
     *
     * @param id the value for LOGIN_TICKET.ID
     *
     * @mbg.generated Sat Jul 20 16:11:54 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column LOGIN_TICKET.USER_ID
     *
     * @return the value of LOGIN_TICKET.USER_ID
     *
     * @mbg.generated Sat Jul 20 16:11:54 CST 2019
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column LOGIN_TICKET.USER_ID
     *
     * @param userId the value for LOGIN_TICKET.USER_ID
     *
     * @mbg.generated Sat Jul 20 16:11:54 CST 2019
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column LOGIN_TICKET.TICKET
     *
     * @return the value of LOGIN_TICKET.TICKET
     *
     * @mbg.generated Sat Jul 20 16:11:54 CST 2019
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column LOGIN_TICKET.TICKET
     *
     * @param ticket the value for LOGIN_TICKET.TICKET
     *
     * @mbg.generated Sat Jul 20 16:11:54 CST 2019
     */
    public void setTicket(String ticket) {
        this.ticket = ticket == null ? null : ticket.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column LOGIN_TICKET.EXPIRED
     *
     * @return the value of LOGIN_TICKET.EXPIRED
     *
     * @mbg.generated Sat Jul 20 16:11:54 CST 2019
     */
    public Long getExpired() {
        return expired;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column LOGIN_TICKET.EXPIRED
     *
     * @param expired the value for LOGIN_TICKET.EXPIRED
     *
     * @mbg.generated Sat Jul 20 16:11:54 CST 2019
     */
    public void setExpired(Long expired) {
        this.expired = expired;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column LOGIN_TICKET.STATUS
     *
     * @return the value of LOGIN_TICKET.STATUS
     *
     * @mbg.generated Sat Jul 20 16:11:54 CST 2019
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column LOGIN_TICKET.STATUS
     *
     * @param status the value for LOGIN_TICKET.STATUS
     *
     * @mbg.generated Sat Jul 20 16:11:54 CST 2019
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}