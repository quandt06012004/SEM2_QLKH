package com.soft.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.soft.models.Category;
import com.soft.models.Product;
import com.soft.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> getAll() {

		return this.productRepository.findAll();
	}

	@Override
	public Boolean create(Product product) {
		try {
			this.productRepository.save(product);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Product findById(Integer id) {

		return productRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Khong ton tai product co id " + id));
	}

	@Override
	public Boolean update(Product product) {
		try {
			this.productRepository.save(product);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean delete(Integer id) {
		try {
			this.productRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Page<Product> getAll(Integer pageNo,Integer sizePage) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo - 1, sizePage);
		return this.productRepository.findAll(pageable);
	}

	@Override
	public List<Product> searchProduct(String keyword) {
		// TODO Auto-generated method stub
		return this.productRepository.searchProduct(keyword);
	}

	@Override
	public Page<Product> searchProduct(String keyword, Integer pageNo, Integer sizePage) {
		// TODO Auto-generated method stub
		List list = this.productRepository.searchProduct(keyword);
		
		Pageable pageable = PageRequest.of(pageNo - 1, sizePage);
		
		Integer start = (int) pageable.getOffset();
		
		Integer end =(int) ( pageable.getOffset() + pageable.getPageSize() > list.size() ?  list.size() : pageable.getOffset() + pageable.getPageSize());
		
		list = list.subList(start, end);
		
		return new PageImpl<Product>(list, pageable, this.productRepository.searchProduct(keyword).size());

	}

	@Override
	public List<Product> getLatestNProducts(int count) {
		// TODO Auto-generated method stub
		Pageable  pageable = PageRequest.of(0, count); 
        return productRepository.findTopNProducts(pageable);
	}

	@Override
	public List<Product> findMaleProducts() {
		// TODO Auto-generated method stub
		return this.productRepository.findMaleProducts();
	}

	@Override
	public List<Product> findFemaleProducts() {
		// TODO Auto-generated method stub
		return this.productRepository.findFemaleProducts();
	}

	@Override
	public List<Product> findOtherGenderProducts() {
		// TODO Auto-generated method stub
		return this.productRepository.findOtherGenderProducts();
	}

	 @Override
	    public Page<Product> findProductsByCategory(Integer categoryId, int pageNo, int pageSize) {
	        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
	        return productRepository.findProductsByCategory(categoryId, pageable);
	    }

	    @Override
	    public Page<Product> findProductsByCategories(List<Integer> categoryIds, int pageNo, int pageSize) {
	        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
	        return productRepository.findProductsByCategories(categoryIds, pageable);
	    }

	

}
