package com.faq.Service;

import com.faq.DTO.FaqResponseDTO;
import com.faq.Entity.Faq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IFaqService {

    Page<FaqResponseDTO> getAllFaqs(String search, UUID categoryId, Pageable pageable);

    Optional<FaqResponseDTO> getFaqById(UUID id);

    FaqResponseDTO createFaq(Faq faq);

    FaqResponseDTO updateFaq(UUID id, Faq updatedFaq);

    void deleteFaq(UUID id);
}
