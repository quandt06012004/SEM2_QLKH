package com.soft.service;

import java.util.List;

import com.soft.models.Inventory;
import com.soft.models.InventoryHistory;
import com.soft.models.Inventory_out;

public interface InventoryHistoryService {
	 List<InventoryHistory> findByInventoryId(Integer inventoryId);
	 List<InventoryHistory> findByInventoryOutId(Integer inventoryId);
	    void create(InventoryHistory history);
	    // Phương thức mới
	    List<InventoryHistory> findByInventory(Inventory inventory);
	    List<InventoryHistory> findByInventory_out(Inventory_out inventory_out);
}