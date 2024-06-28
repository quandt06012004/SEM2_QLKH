package com.soft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soft.models.InventoryHistory;

@Repository
public interface InventoryHistoryRepository extends JpaRepository<InventoryHistory, Integer> {
    List<InventoryHistory> findByInventoryId(Integer inventoryId);
}
