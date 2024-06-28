package com.soft.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.soft.models.Category;
import com.soft.models.Product;

public interface ProductService {
	List<Product> getAll();

	Boolean create(Product product);

	Product findById(Integer id);

	Boolean update(Product product);
	List<Product> searchProduct(String keyword);
	Boolean delete(Integer id);
	Page<Product> getAll(Integer pageNo,Integer sizePage);
	Page<Product> searchProduct(String keyword , Integer pageNo, Integer sizePage);
	
	List<Product> getLatestNProducts(int n);
	List<Product> findMaleProducts();
	List<Product> findFemaleProducts();
	List<Product> findOtherGenderProducts();
	Page<Product> findProductsByCategory(Integer categoryId, int pageNo, int pageSize);
	Page<Product> findProductsByCategories(List<Integer> categoryIds, int pageNo, int pageSize);
}
