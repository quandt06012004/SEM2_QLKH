package com.soft.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.soft.models.Suppliers;



public interface SupplierService {
	List<Suppliers> getAll();

	Boolean create(Suppliers suppliers);

	Suppliers findById(Integer id);

	Boolean update(Suppliers suppliers);
	List<Suppliers> searchSuppliers(String keyword);
	Boolean delete(Integer id);
	Page<Suppliers> getAll(Integer pageNo);
	Page<Suppliers> searchSuppliers(String keyword , Integer pageNo);
}
