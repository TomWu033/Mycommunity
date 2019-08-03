package tom.community.mapper;

import tom.community.model.Comment;
import tom.community.model.Feed;

import java.util.List;
//拉模式,查一批feed
public interface FeedExtMapper {
    List<Feed> selectUserFeeds(long maxId,List<Long> userIds,int count);
}