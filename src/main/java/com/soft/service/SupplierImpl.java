package com.soft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.soft.models.Suppliers;
import com.soft.repository.SupplierRepository;
@Service
public class SupplierImpl implements SupplierService{
	@Autowired
	private SupplierRepository supplierRepository;
	@Override
	public List<Suppliers> getAll() {
		// TODO Auto-generated method stub
		return this.supplierRepository.findAll();
	}

	@Override
	public Boolean create(Suppliers suppliers) {
		// TODO Auto-generated method stub
		try {
			this.supplierRepository.save(suppliers);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Suppliers findById(Integer id) {
		// TODO Auto-generated method stub
		return this.supplierRepository.findById(id).get();
	}

	@Override
	public Boolean update(Suppliers suppliers) {
		// TODO Auto-generated method stub
		try {
			this.supplierRepository.save(suppliers);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Suppliers> searchSuppliers(String keyword) {
		// TODO Auto-generated method stub
		return this.supplierRepository.searchSuppliers(keyword);
	}

	@Override
	public Boolean delete(Integer id) {
		// TODO Auto-generated method stub
		try {
			this.supplierRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Page<Suppliers> getAll(Integer pageNo) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo - 1, 2);
		return this.supplierRepository.findAll(pageable);
	}

	@Override
	public Page<Suppliers> searchSuppliers(String keyword, Integer pageNo) {
		// TODO Auto-generated method stub
		List list = this.supplierRepository.searchSuppliers(keyword);
		Pageable pageable = PageRequest.of(pageNo - 1, 2);
		Integer start = (int) pageable.getOffset();
		Integer end = (int) (pageable.getOffset() + pageable.getPageSize() > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<Suppliers>(list, pageable, this.supplierRepository.searchSuppliers(keyword).size());
	}

}
