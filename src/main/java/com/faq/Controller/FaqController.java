package com.faq.Controller;

import com.faq.DTO.FaqResponseDTO;
import com.faq.Entity.Faq;
import com.faq.Service.AiService;
import com.faq.Service.IFaqService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/faqs")
public class FaqController {

    private final IFaqService faqService;

    private final AiService aiService;

    public FaqController(IFaqService faqService, AiService aiService) {
        this.faqService = faqService;
        this.aiService = aiService;
    }

    @PostMapping("/suggest-answer")
    public String suggestAnswer(@RequestParam String question) {
        return aiService.generateAnswer(question);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllFaqs(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("question").ascending());
        Page<FaqResponseDTO> faqPage = faqService.getAllFaqs(search, categoryId, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("data", faqPage.getContent());
        response.put("page", faqPage.getNumber());
        response.put("PageSize", faqPage.getSize());
        response.put("totalElements", faqPage.getTotalElements());
        response.put("totalPages", faqPage.getTotalPages());
        response.put("hasNext", faqPage.hasNext());
        response.put("hasPrevious", faqPage.hasPrevious());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<FaqResponseDTO> getFaqById(@PathVariable UUID id) {
        return faqService.getFaqById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FaqResponseDTO> createFaq(@RequestBody Faq faq) {
        FaqResponseDTO created = faqService.createFaq(faq);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FaqResponseDTO> updateFaq(
            @PathVariable UUID id,
            @RequestBody Faq updatedFaq) {
        FaqResponseDTO updated = faqService.updateFaq(id, updatedFaq);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaq(@PathVariable UUID id) {
        faqService.deleteFaq(id);
        return ResponseEntity.noContent().build();
    }
}
