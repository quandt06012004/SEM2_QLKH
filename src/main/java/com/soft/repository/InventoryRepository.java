package com.soft.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soft.models.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Integer>{
	 @Query("from Inventory i where i.lastUpdated BETWEEN :startDate AND :endDate")
	    Page<Inventory> findAllBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

}	
