package tom.community.model;

public class LoginTicket {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column login_ticket.id
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column login_ticket.user_id
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column login_ticket.ticket
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    private String ticket;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column login_ticket.expired
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    private Long expired;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column login_ticket.status
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    private Integer status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column login_ticket.id
     *
     * @return the value of login_ticket.id
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column login_ticket.id
     *
     * @param id the value for login_ticket.id
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column login_ticket.user_id
     *
     * @return the value of login_ticket.user_id
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column login_ticket.user_id
     *
     * @param userId the value for login_ticket.user_id
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column login_ticket.ticket
     *
     * @return the value of login_ticket.ticket
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column login_ticket.ticket
     *
     * @param ticket the value for login_ticket.ticket
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    public void setTicket(String ticket) {
        this.ticket = ticket == null ? null : ticket.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column login_ticket.expired
     *
     * @return the value of login_ticket.expired
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    public Long getExpired() {
        return expired;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column login_ticket.expired
     *
     * @param expired the value for login_ticket.expired
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    public void setExpired(Long expired) {
        this.expired = expired;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column login_ticket.status
     *
     * @return the value of login_ticket.status
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column login_ticket.status
     *
     * @param status the value for login_ticket.status
     *
     * @mbg.generated Thu Aug 01 16:32:27 CST 2019
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}