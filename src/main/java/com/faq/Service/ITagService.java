package com.faq.Service;

import com.faq.DTO.TagResponseDTO;
import com.faq.Entity.Tag;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITagService {

    List<TagResponseDTO> getAllTags();

    Optional<TagResponseDTO> getTagById(UUID id);

    Tag createTag(Tag tag);

    Tag updateTag(UUID id, Tag updatedTag);

    void deleteTag(UUID id);
}
