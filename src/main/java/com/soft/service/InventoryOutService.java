package com.soft.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.soft.models.InventoryHistory;
import com.soft.models.Inventory_out;
import com.soft.models.Product;

public interface InventoryOutService {
	List<Inventory_out> getAll();

	Boolean create(Inventory_out inventory_out);

	Inventory_out findById(Integer id);

	Boolean update(Inventory_out inventory_out);

	List<Inventory_out> searchInventory_out(String keyword);

	Boolean delete(Integer id);

	Page<Inventory_out> getAll(Integer pageNo);
	Page<Inventory_out> searchInventory_out(String keyword , Integer pageNo);
	
	
	public Page<Inventory_out> findAllBetweenDates(Date startDate, Date endDate, Pageable pageable);


	List<Inventory_out> findByProduct(Product product);
	
	List<InventoryHistory> getInventoryHistoryByInventoryId(Integer inventoryOutId);
}
