package com.faq.Service;

import com.faq.Entity.Category;
import com.faq.Repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(UUID id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category createCategory(Category category) {
        validateCategory(category);

        if (categoryRepository.findByNameIgnoreCase(category.getName()).isPresent()) {
            throw new IllegalArgumentException("Category with name '" + category.getName() + "' already exists");
        }

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(UUID id, Category updatedCategory) {
        validateCategory(updatedCategory);

        return categoryRepository.findById(id)
                .map(existing -> {
                    if (!existing.getName().equalsIgnoreCase(updatedCategory.getName())
                            && categoryRepository.findByNameIgnoreCase(updatedCategory.getName()).isPresent()) {
                        throw new IllegalArgumentException("Category with name '" + updatedCategory.getName() + "' already exists");
                    }

                    existing.setName(updatedCategory.getName());
                    existing.setDescription(updatedCategory.getDescription());
                    return categoryRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    @Override
    public void deleteCategory(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    private void validateCategory(Category category) {
        if (category == null || !StringUtils.hasText(category.getName())) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
    }
}
