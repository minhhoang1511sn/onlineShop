package com.group08.onlineShop.service.impl;

import com.group08.onlineShop.dto.CategoryDTO;
import com.group08.onlineShop.exception.AppException;
import com.group08.onlineShop.model.Category;
import com.group08.onlineShop.repository.CategoryRepo;
import com.group08.onlineShop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;

    @Override
    public Category findById(Long id) {
        Optional<Category> category = categoryRepo.findById(id);
        return category.orElse(null);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAllCategory();
    }

    @Override
    public Category save(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryName(categoryDTO.getCategoryName());
        return categoryRepo.save(category);

    }

    @Override
    public Category updateCategory(CategoryDTO categoryDTO) {
        Category categoryUpdate = findById(categoryDTO.getId());
        if (categoryUpdate != null) {
            categoryUpdate.setCategoryName(categoryDTO.getCategoryName());

            return categoryRepo.save(categoryUpdate);
        } else
            throw new AppException(404, "Comment ID not found");

    }

    @Override
    public Boolean deleteCategory(Long categoryId) {
        Category categoryDelete = findById(categoryId);
        if (categoryDelete != null) {
            categoryRepo.deleteById(categoryDelete.getId());
            return true;
        } else
            return false;
    }
}
