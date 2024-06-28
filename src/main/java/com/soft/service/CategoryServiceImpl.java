package com.soft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.soft.models.Category;
import com.soft.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getAll() {

		return this.categoryRepository.findAll();
	}

	@Override
	public Boolean create(Category category) {
		try {
			this.categoryRepository.save(category);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Category findById(Integer id) {

		return this.categoryRepository.findById(id).get();
	}

	@Override
	public Boolean update(Category category) {

		try {
			this.categoryRepository.save(category);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean delete(Integer id) {
		try {
			this.categoryRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Category> searchCategory(String keyword) {

		return this.categoryRepository.searchCategory(keyword);
	}

	@Override
	public Page<Category> getAll(Integer pageNo) {
		Pageable pageable = PageRequest.of(pageNo - 1, 5);
		return this.categoryRepository.findAll(pageable);
	}

	@Override
	public Page<Category> searchCategory(String keyword, Integer pageNo) {
		List list = this.searchCategory(keyword);
		
		Pageable pageable = PageRequest.of(pageNo - 1, 5);
		
		Integer start = (int) pageable.getOffset();
		
		Integer end =(int) ( pageable.getOffset() + pageable.getPageSize() > list.size() ?  list.size() : pageable.getOffset() + pageable.getPageSize());
		
		list = list.subList(start, end);
		
		return new PageImpl<Category>(list, pageable, this.searchCategory(keyword).size());
	}

}
