package com.pesupal.server.service.implementations;

import com.pesupal.server.model.post.Tag;
import com.pesupal.server.repository.TagRepository;
import com.pesupal.server.service.interfaces.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

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
}
