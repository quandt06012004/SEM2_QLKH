package com.soft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soft.models.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Integer>{
			
}
