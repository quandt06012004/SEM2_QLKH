package com.soft.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.soft.models.Inventory;
import com.soft.repository.InventoryRepository;
@Service
public class InventoryImpl implements InventoryService{
	@Autowired
	private InventoryRepository inventoryResitory;
	@Override
	public List<Inventory> getAll() {
		// TODO Auto-generated method stub
		return this.inventoryResitory.findAll();
	}

	@Override
	public Boolean create(Inventory inventory) {
		// TODO Auto-generated method stub
		try {
			this.inventoryResitory.save(inventory);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Inventory findById(Integer id) {
		// TODO Auto-generated method stub
		return this.inventoryResitory.findById(id).get();
	}

	@Override
	public Boolean update(Inventory inventory) {
		// TODO Auto-generated method stub
	 try {
			this.inventoryResitory.save(inventory);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Inventory> searchInventory(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delete(Integer id) {
		// TODO Auto-generated method stub
		try {
			this.inventoryResitory.delete(findById(id));
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Page<Inventory> getAll(Integer pageNo) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo - 1, 4);
		return this.inventoryResitory.findAll(pageable);
	}

	@Override
	public Page<Inventory> searchInventory(String keyword, Integer pageNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public Page<Inventory> findAllBetweenDates(Date startDate, Date endDate, Pageable pageable) {
        return this.inventoryResitory.findAllBetweenDates(startDate, endDate, pageable);
    }
	
	
	
}
