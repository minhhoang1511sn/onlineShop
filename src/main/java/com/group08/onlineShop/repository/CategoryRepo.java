package com.group08.onlineShop.repository;

import com.group08.onlineShop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    @Query("select c from Category c")
    List<Category> findAllCategory();
}
