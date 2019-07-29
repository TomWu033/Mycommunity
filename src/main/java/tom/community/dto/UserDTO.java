package tom.community.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String avatarUrl;
    private Long followerCount;
    private Long followeeCount;
    private Boolean followed;
}
