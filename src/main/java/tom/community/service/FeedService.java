package tom.community.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tom.community.mapper.FeedExtMapper;
import tom.community.mapper.FeedMapper;
import tom.community.model.Feed;

import java.util.List;

/**
 * 读取feed流
 */
@Service
@Slf4j
public class FeedService {
    @Autowired
    private FeedMapper feedMapper;
    @Autowired
    private FeedExtMapper feedExtMapper;

    //把我关联地所有人的feed都取得，拉模式
    public List<Feed> getUserFeeds(long maxId,List<Long> userIds,int count){
        return feedExtMapper.selectUserFeeds(maxId,userIds,count);
    }

    public void addFeed(Feed feed){
        int ret=feedMapper.insert(feed);
        if (ret<=0){
            log.error("feed流插入错误");
        }
    }

    //推模式
    public Feed getById(long id){
        return feedMapper.selectByPrimaryKey(id);
    }
}
