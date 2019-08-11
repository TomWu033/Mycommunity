package tom.community.schedule;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tom.community.mapper.QuestionMapper;
import tom.community.model.Question;
import tom.community.model.QuestionExample;

import java.util.*;

@Component
@Slf4j
public class HotTagTasks {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate=1000*60*60*3)
    public void hotTagSchedule() {
        int offset=0;
        int limit=5;
        log.info("HotTagSchedule start {}", new Date());
        List<Question> list=new ArrayList<>();
        Map<String, Integer> priorities = new HashMap<>();
        while(offset==0||list.size()==limit){
            list=questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,limit));
            for (Question question : list) {
                String[] tags= StringUtils.split(question.getTag(),",");
                for (String tag : tags) {
                    Integer priority = priorities.get(tag);
                    if (priority!=null){
                        priorities.put(tag,priority+3+question.getCommentCount()+question.getLikeCount());
                    }
                    else{
                        priorities.put(tag,3+question.getCommentCount()+question.getLikeCount());
                    }
                }
            }
            offset+=limit;
        }
        hotTagCache.updateTags(priorities);
        log.info("HotTagSchedule stop {}", new Date());
    }
}
