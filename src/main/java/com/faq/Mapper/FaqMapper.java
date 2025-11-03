package com.faq.Mapper;

import com.faq.DTO.FaqResponseDTO;
import com.faq.Entity.Faq;
import com.faq.Entity.Tag;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class FaqMapper {

    private FaqMapper() {
    }

    public static FaqResponseDTO toDto(Faq faq) {
        if (faq == null) return null;

        FaqResponseDTO dto = new FaqResponseDTO();
        dto.setId(faq.getId());
        dto.setQuestion(faq.getQuestion());
        dto.setAnswer(faq.getAnswer());

        if (faq.getCategory() != null) {
            dto.setCategoryId(faq.getCategory().getId());
        }

        if (faq.getTags() != null) {
            Set<String> tagNames = faq.getTags().stream()
                    .map(Tag::getName)
                    .collect(Collectors.toSet());
            dto.setTags(tagNames);
        }

        if (faq.getCreatedBy() != null) {
            dto.setCreatedBy(faq.getCreatedBy().getId());
        }

        return dto;
    }

    public static Faq toEntity(FaqResponseDTO dto) {
        if (dto == null) return null;

        Faq faq = new Faq();
        faq.setId(dto.getId() != null ? dto.getId() : UUID.randomUUID());
        faq.setQuestion(dto.getQuestion());
        faq.setAnswer(dto.getAnswer());
        return faq;
    }
}
