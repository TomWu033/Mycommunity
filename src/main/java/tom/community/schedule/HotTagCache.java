package tom.community.schedule;


import lombok.Data;
import org.springframework.stereotype.Component;
import tom.community.dto.HotTagDTO;

import java.util.*;

@Component
@Data
public class HotTagCache {
    private List<String> hots = new ArrayList<>();

    public void updateTags(Map<String, Integer> tags) {
        int max = 5;
        PriorityQueue<HotTagDTO> pq = new PriorityQueue<>(max);
        tags.forEach(
                (name, priority) -> {
                    HotTagDTO hotTagDTO = new HotTagDTO();
                    hotTagDTO.setName(name);
                    hotTagDTO.setPriority(priority);
                    if (pq.size() < max) {
                        pq.add(hotTagDTO);
                    } else {
                        HotTagDTO minHot = pq.peek();
                        if (hotTagDTO.compareTo(minHot) > 0) {
                            pq.poll();
                            pq.add(hotTagDTO);
                        }
                    }
                }
        );
        List<String> sortedTags = new ArrayList<>();
        HotTagDTO poll = pq.poll();
        while (poll != null) {
            sortedTags.add(0, poll.getName());
            poll = pq.poll();
        }
        hots=sortedTags;
    }
}
