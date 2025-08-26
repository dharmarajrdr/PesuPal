package com.pesupal.server.service.implementations.post;

import com.pesupal.server.factory.TrendingTagsAnalyserFactory;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.post.Tag;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.post.TagRepository;
import com.pesupal.server.service.interfaces.post.TagService;
import com.pesupal.server.service.interfaces.post.TrendingTagsAnalyser;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TagServiceImpl extends CurrentValueRetriever implements TagService {

    private final TagRepository tagRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final TrendingTagsAnalyserFactory trendingTagsAnalyserFactory;

    private final static Duration TRENDING_TAGS_CACHE_DURATION = Duration.ofHours(6);
    private final static String TRENDING_TAGS_KEY = "trending_tags";
    private final static String TRENDING_TAGS_ANALYSER_ALGORITHM = "MOST_POST";

    /**
     * Creates or retrieves a tag by its name.
     *
     * @param tagName
     * @return
     */
    @Override
    public Tag createOrGet(String tagName) {

        return tagRepository.findByName(tagName).orElseGet(() -> tagRepository.save(new Tag(tagName)));
    }

    /**
     * Get trending tags based on a window of recent activity.
     *
     * @param limit
     * @return
     */
    @Override
    public List<String> getTrendingTags(int limit) {

        OrgMember orgMember = getCurrentOrgMember();

        // Fetch tag ids from redis.
        String key = TRENDING_TAGS_KEY + "-" + orgMember.getOrg().getId();
        List<Object> tagIds = redisTemplate.opsForList().range(key, 0, limit - 1);
        if (tagIds != null && !tagIds.isEmpty()) {
            List<String> tags = new ArrayList<>();
            for (Object id : tagIds) {
                tagRepository.findById(Long.valueOf(id.toString())).ifPresent(tag -> tags.add(tag.getName()));
            }
            return tags;
        }

        // If not found in redis, analyse and store in redis.
        TrendingTagsAnalyser trendingTagsAnalyser = trendingTagsAnalyserFactory.getTrendingTagsAnalyser(TRENDING_TAGS_ANALYSER_ALGORITHM);
        List<Tag> newTrendingTags = trendingTagsAnalyser.analyseTrendingTags(orgMember.getOrg(), limit);
        List<Long> newTrendingTagIds = newTrendingTags.stream().map(Tag::getId).toList();
        redisTemplate.delete(key);  // Clear existing key if any.
        redisTemplate.opsForList().rightPushAll(key, newTrendingTagIds.toArray(new Long[0]));
        redisTemplate.expire(key, TRENDING_TAGS_CACHE_DURATION);
        return newTrendingTags.stream().map(Tag::getName).toList();
    }
}
