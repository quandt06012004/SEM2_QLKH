package com.soft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	    
	     @Query("SELECT h FROM InventoryHistory h " +
	            "LEFT JOIN h.inventory i " +
	            "LEFT JOIN h.inventoryOut io " +
	            "LEFT JOIN i.product p1 " +
	            "LEFT JOIN io.product p2 " +
	            "WHERE p1.productName LIKE %:productName% OR p2.productName LIKE %:productName%")
	     List<InventoryHistory> findByProductName(@Param("productName") String productName);
	   
}
