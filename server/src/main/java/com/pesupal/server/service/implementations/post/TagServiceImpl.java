package com.pesupal.server.service.implementations.post;

import com.pesupal.server.dto.response.post.TagDto;
import com.pesupal.server.factory.TrendingTagsAnalyserFactory;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.post.Tag;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.projections.PostTagProjection;
import com.pesupal.server.repository.post.PostTagRepository;
import com.pesupal.server.repository.post.TagRepository;
import com.pesupal.server.service.interfaces.post.TagService;
import com.pesupal.server.service.interfaces.post.TrendingTagsAnalyser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TagServiceImpl extends CurrentValueRetriever implements TagService {

    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final TrendingTagsAnalyserFactory trendingTagsAnalyserFactory;

    private final static String TRENDING_TAGS_KEY = "trending:tags";
    private final static String TRENDING_TAGS_ANALYSER_ALGORITHM = "MOST_POST";
    private final static Duration TRENDING_TAGS_CACHE_DURATION = Duration.ofHours(6);

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
        String key = TRENDING_TAGS_KEY + ":{" + orgMember.getOrg().getId() + "}";
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

        if (newTrendingTags.isEmpty()) {
            return List.of();
        }

        List<String> newTrendingTagIds = newTrendingTags.stream().map(tag -> tag.getId().toString()).toList();
        try {
            redisTemplate.delete(key);  // Clear existing key if exists.
            redisTemplate.opsForList().rightPushAll(key, newTrendingTagIds.toArray(new String[0]));
            redisTemplate.expire(key, TRENDING_TAGS_CACHE_DURATION);
        } catch (Exception ignored) {
        }
        return newTrendingTags.stream().map(Tag::getName).toList();
    }

    /**
     * Get all tags with pagination.
     *
     * @param size
     * @param page
     * @return
     */
    @Override
    public List<TagDto> getAllTags(int size, int page) {

        OrgMember orgMember = getCurrentOrgMember();
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        List<TagDto> tagDtos = new ArrayList<>();

        Page<PostTagProjection> postTagPageable = postTagRepository.findAllByPost_OrgOrderByCount(orgMember.getOrg(), pageable);
        for (PostTagProjection projection : postTagPageable.getContent()) {
            tagDtos.add(TagDto.fromPostTagProjection(projection));
        }
        return tagDtos;
    }
}
