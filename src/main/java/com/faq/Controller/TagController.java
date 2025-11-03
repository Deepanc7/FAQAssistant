package com.faq.Controller;

import com.faq.DTO.TagResponseDTO;
import com.faq.Entity.Tag;
import com.faq.Service.ITagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final ITagService tagService;

    public TagController(ITagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagResponseDTO> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDTO> getTag(@PathVariable UUID id) {
        return tagService.getTagById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.createTag(tag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable UUID id, @RequestBody Tag updatedTag) {
        return ResponseEntity.ok(tagService.updateTag(id, updatedTag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
