package tom.community.async;

public enum EventType {
    LIKE(3),
    REPLY_QUESTION(1),
    REPLY_COMMENT(2),
    LOGIN(5),
    MAIL(4),
    FOLLOW(6),
    UNFOLLOW(7);
    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
