package tom.community.dto;

import lombok.Data;
import tom.community.model.User;

@Data
public class QuestionDTO {
    //与question模型一样，但多一个关联
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Long likeCount;
    private User user;
    private Long followerCount;
    private Boolean followed;
}
