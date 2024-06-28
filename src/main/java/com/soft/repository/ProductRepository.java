package com.soft.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soft.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.productName LIKE %:keyword%")
    List<Product> searchProduct(@Param("keyword") String keyword);
    
    @Query("SELECT p FROM Product p ORDER BY p.createdAt DESC")
    List<Product> findTopNProducts(Pageable pageable);

    
 // Products by male gender
    @Query("SELECT p FROM Product p WHERE p.genderForProduct = com.soft.models.GenderForProduct.MALE")
    List<Product> findMaleProducts();

    // Products by female gender
    @Query("SELECT p FROM Product p WHERE p.genderForProduct = com.soft.models.GenderForProduct.FEMALE")
    List<Product> findFemaleProducts();

    // Products by other genders
    @Query("SELECT p FROM Product p WHERE p.genderForProduct = com.soft.models.GenderForProduct.OTHER")
    List<Product> findOtherGenderProducts();

    Page<Product> findProductsByCategory(@Param("categoryId") Integer categoryId, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.category.id IN :categoryIds")
	Page<Product> findProductsByCategories(@Param("categoryIds") List<Integer> categoryIds, Pageable pageable);
}

