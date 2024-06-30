package com.soft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soft.models.Inventory;
import com.soft.models.InventoryHistory;
import com.soft.models.Inventory_out;

@Repository
public interface InventoryHistoryRepository extends JpaRepository<InventoryHistory, Integer> {
	  List<InventoryHistory> findByInventoryId(Integer inventoryId);
	  List<InventoryHistory> findByInventoryOutId(Integer inventoryOutId);
	    List<InventoryHistory> findByInventory(Inventory inventory);
	    List<InventoryHistory> findByInventoryOut(Inventory_out inventoryOut);
	   
}
