package com.soft.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soft.models.Inventory_out;
import com.soft.models.Product;

public interface InventoryOutRepository extends JpaRepository<Inventory_out, Integer>{
	 @Query("from Inventory_out i where i.lastUpdated BETWEEN :startDate AND :endDate")
	    Page<Inventory_out> findAllBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);
	 	List<Inventory_out> findByProduct(Product product);
}
