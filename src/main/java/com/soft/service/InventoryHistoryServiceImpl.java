package com.soft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soft.models.InventoryHistory;
import com.soft.repository.InventoryHistoryRepository;

@Service
public class InventoryHistoryServiceImpl implements InventoryHistoryService {

    @Autowired
    private InventoryHistoryRepository inventoryHistoryRepository;

    @Override
    public List<InventoryHistory> findByInventoryId(Integer inventoryId) {
        return inventoryHistoryRepository.findByInventoryId(inventoryId);
    }

    @Override
    public void create(InventoryHistory history) {
        inventoryHistoryRepository.save(history);
    }
}
