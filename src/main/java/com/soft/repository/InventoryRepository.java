package com.soft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soft.models.Inventory;
import com.soft.models.Product;

public interface InventoryRepository extends JpaRepository<Inventory, Integer>{
	List<Inventory> findByProduct(Product product);
}
