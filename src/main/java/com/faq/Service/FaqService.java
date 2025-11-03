package com.faq.Service;

import com.faq.DTO.FaqResponseDTO;
import com.faq.Entity.Category;
import com.faq.Entity.Faq;
import com.faq.Entity.Tag;
import com.faq.Mapper.FaqMapper;
import com.faq.Repository.CategoryRepository;
import com.faq.Repository.FaqRepository;
import com.faq.Repository.TagRepository;
import com.faq.Service.IFaqService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FaqService implements IFaqService {

    private final FaqRepository faqRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public FaqService(FaqRepository faqRepository,
                          CategoryRepository categoryRepository,
                          TagRepository tagRepository) {
        this.faqRepository = faqRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Page<FaqResponseDTO> getAllFaqs(String search, UUID categoryId, Pageable pageable) {
        Page<Faq> faqs;

        if (search != null && !search.isBlank()) {
            faqs = faqRepository.searchFaqs(search.trim(), pageable);
        } else if (categoryId != null) {
            faqs = faqRepository.findByCategory_Id(categoryId, pageable);
        } else {
            faqs = faqRepository.findAll(pageable);
        }

        return faqs.map(FaqMapper::toDto);
    }

    @Override
    public Optional<FaqResponseDTO> getFaqById(UUID id) {
        return faqRepository.findById(id).map(FaqMapper::toDto);
    }

    @Override
    public FaqResponseDTO createFaq(Faq faq) {
        validateFaq(faq);

        Faq saved = faqRepository.save(faq);
        return FaqMapper.toDto(saved);
    }

    @Override
    public FaqResponseDTO updateFaq(UUID id, Faq updatedFaq) {
        Faq existing = faqRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FAQ not found with id: " + id));

        if (updatedFaq.getQuestion() != null) existing.setQuestion(updatedFaq.getQuestion());
        if (updatedFaq.getAnswer() != null) existing.setAnswer(updatedFaq.getAnswer());
        if (updatedFaq.getCategory() != null) existing.setCategory(updatedFaq.getCategory());
        if (updatedFaq.getTags() != null) existing.setTags(updatedFaq.getTags());
        if (updatedFaq.getUpdatedBy() != null) existing.setUpdatedBy(updatedFaq.getUpdatedBy());

        Faq saved = faqRepository.save(existing);
        return FaqMapper.toDto(saved);
    }

    @Override
    public void deleteFaq(UUID id) {
        if (!faqRepository.existsById(id)) {
            throw new EntityNotFoundException("FAQ not found with id: " + id);
        }
        faqRepository.deleteById(id);
    }

    private void validateFaq(Faq faq) {
        if (faq.getQuestion() == null || faq.getQuestion().isBlank()) {
            throw new IllegalArgumentException("Question cannot be empty");
        }
        if (faq.getCategory() != null && faq.getCategory().getId() != null) {
            categoryRepository.findById(faq.getCategory().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Invalid category ID"));
        }
        if (faq.getTags() != null && !faq.getTags().isEmpty()) {
            Set<UUID> tagIds = faq.getTags().stream()
                    .map(Tag::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            if (!tagIds.isEmpty() && tagRepository.findAllById(tagIds).size() != tagIds.size()) {
                throw new EntityNotFoundException("One or more tags are invalid");
            }
        }
    }
}
