package com.group08.onlineShop.repository;

import com.group08.onlineShop.model.Category;
import com.group08.onlineShop.model.Product;
import com.group08.onlineShop.model.TypeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query("select p from Product p where"+
            " ((:category is null) or (p.category = :category)) and" +
            "((:typeProduct is null ) or (p.type = :typeProduct))" +
            "and (concat('%',lower(p.productName),'%') like concat('%',lower(:keyword),'%'))")
    List<Product> findAllByKeyword(String keyword, Category category, TypeProduct typeProduct);
    @Query("select p from Product p where p.id = :id")
    Product getProduct(Long id);
    @Query(
            "SELECT p FROM Product p WHERE (concat('%',lower(p.productName),'%') LIKE concat('%',lower(:keyword),'%')) "
    )
    List<Product> search(String keyword);

}
