package tom.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"你找的问题不存在，换个问题试试?"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登录，请先登录后重试"),
    SYS_ERROR(2004,"服务过热了，请稍后重试！"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在了"),
    CONTENT_IS_EMPTY(2007,"输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008,"通知人信息错误"),
    NOTIFICATION_NOT_FOUND(2009,"通知消息不见了"),
    FILE_UPLOAD_FAIL(2010,"图片上传失败"),
    FOLLOW_FAIL(2011,"关注失败"),
    UNFOLLOW_FAIL(2012,"取消关注失败")
    ;

    private String message;
    private Integer code;


    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

}
