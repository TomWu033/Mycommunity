package tom.community.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class FeedDTO {
    private Long id;
    private Long gmtCreate;
    private Long userId;
    private Integer type;
    private Map<String,String> info=new HashMap<>();
}
