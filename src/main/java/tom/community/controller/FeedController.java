package tom.community.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tom.community.dto.FeedDTO;
import tom.community.enums.EntityType;
import tom.community.model.Feed;
import tom.community.model.HostHolder;
import tom.community.service.*;
import tom.community.util.RedisKeyUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class FeedController {
    @Autowired
    private FeedService feedService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private FollowService followService;
    @Autowired
    private JedisService jedisService;


    //拉模式
    @GetMapping("/pullfeeds")
    public String getPullFeeds(Model model){
        long localUserId=hostHolder.getUser()==null?0:hostHolder.getUser().getId();
        List<Long> followees=new ArrayList<>();
        if (localUserId==0){
            return "redirect:/reglogin";
        }
        else{
            followees=followService.getFollowees(EntityType.ENTITY_USER,localUserId,Integer.MAX_VALUE);
        }
        List<Feed> feeds=feedService.getUserFeeds(Long.MAX_VALUE,followees,10);
        List<FeedDTO> feedDTOS=new ArrayList<>();
        for(Feed feed:feeds){
            FeedDTO feedDTO=new FeedDTO();
            feedDTO.setId(feed.getId());
            feedDTO.setGmtCreate(feed.getGmtCreate());
            feedDTO.setType(feed.getType());
            feedDTO.setUserId(feed.getUserId());
            Map<String,String> map=JSONObject.parseObject(feed.getData(),new TypeReference<Map<String,String>>(){});
            feedDTO.setInfo(map);
            feedDTOS.add(feedDTO);
        }
        model.addAttribute("feeds",feedDTOS);
        return "feeds";
    }
    @GetMapping("/pushfeeds")
    public String getPushFeeds(Model model){
        long localUserId=hostHolder.getUser()==null?0:hostHolder.getUser().getId();
        if (localUserId==0){
            return "redirect:/reglogin";
        }
        List<String> feedIds=jedisService.lrange(RedisKeyUtil.getTimelineKey(localUserId),0,10);
        List<Feed> feeds=new ArrayList<>();
        List<FeedDTO> feedDTOS=new ArrayList<>();
        for (String feedId:feedIds){
            Feed feed = feedService.getById(Long.parseLong(feedId));
            if (feed==null){
                continue;
            }
            feeds.add(feed);
        }
        for(Feed feed:feeds){
            FeedDTO feedDTO=new FeedDTO();
            feedDTO.setId(feed.getId());
            feedDTO.setGmtCreate(feed.getGmtCreate());
            feedDTO.setType(feed.getType());
            feedDTO.setUserId(feed.getUserId());
            Map<String,String> map=JSONObject.parseObject(feed.getData(),new TypeReference<Map<String,String>>(){});
            feedDTO.setInfo(map);
            feedDTOS.add(feedDTO);
        }
        model.addAttribute("feeds",feedDTOS);
        return "feeds";
    }
}
