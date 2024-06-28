package com.soft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soft.models.Suppliers;

public interface SupplierRepository extends JpaRepository<Suppliers, Integer>{
	@Query("SELECT s FROM Suppliers s WHERE s.supplierName LIKE %:keyword%")
    List<Suppliers> searchSuppliers(@Param("keyword") String keyword);
}
