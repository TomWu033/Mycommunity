package tom.community.model;

import lombok.Data;

@Data //lombok自动生成各种getter，setter
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private long gmtCreate;
    private long gmtModified;
    private String avatarUrl;


}
