package com.soft.service;

import java.util.List;

import com.soft.models.InventoryHistory;

public interface InventoryHistoryService {
    List<InventoryHistory> findByInventoryId(Integer inventoryId);

    void create(InventoryHistory history);
}