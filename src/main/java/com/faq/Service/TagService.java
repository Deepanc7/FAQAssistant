package com.faq.Service;

import com.faq.DTO.TagResponseDTO;
import com.faq.Entity.Tag;
import com.faq.Repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TagService implements ITagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<TagResponseDTO> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(tag -> new TagResponseDTO(tag.getId(), tag.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TagResponseDTO> getTagById(UUID id) {
        return tagRepository.findById(id)
                .map(tag -> new TagResponseDTO(tag.getId(), tag.getName()));
    }

    @Override
    public Tag createTag(Tag tag) {
        validateTag(tag);

        if (tagRepository.findByNameIgnoreCase(tag.getName()).isPresent()) {
            throw new IllegalArgumentException("Tag with name '" + tag.getName() + "' already exists");
        }

        return tagRepository.save(tag);
    }

    @Override
    public Tag updateTag(UUID id, Tag updatedTag) {
        validateTag(updatedTag);

        return tagRepository.findById(id)
                .map(existing -> {
                    if (!existing.getName().equalsIgnoreCase(updatedTag.getName())
                            && tagRepository.findByNameIgnoreCase(updatedTag.getName()).isPresent()) {
                        throw new IllegalArgumentException("Tag with name '" + updatedTag.getName() + "' already exists");
                    }

                    existing.setName(updatedTag.getName());
                    return tagRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + id));
    }

    @Override
    public void deleteTag(UUID id) {
        if (!tagRepository.existsById(id)) {
            throw new RuntimeException("Tag not found with id: " + id);
        }
        tagRepository.deleteById(id);
    }

    private void validateTag(Tag tag) {
        if (tag == null || !StringUtils.hasText(tag.getName())) {
            throw new IllegalArgumentException("Tag name cannot be empty");
        }
    }
}
