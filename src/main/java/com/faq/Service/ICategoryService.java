package com.faq.Service;

import com.faq.Entity.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICategoryService {

    List<Category> getAllCategories();

    Optional<Category> getCategoryById(UUID id);

    Category createCategory(Category category);

    Category updateCategory(UUID id, Category updatedCategory);

    void deleteCategory(UUID id);
}
