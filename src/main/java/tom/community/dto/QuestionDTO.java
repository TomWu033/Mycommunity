package tom.community.dto;

import lombok.Data;
import tom.community.model.User;

@Data
public class QuestionDTO {
    //与question模型一样，但多一个关联
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private long gmtCreate;
    private long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
