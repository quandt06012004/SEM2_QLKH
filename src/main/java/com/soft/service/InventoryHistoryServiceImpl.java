package com.soft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soft.models.Inventory;
import com.soft.models.InventoryHistory;
import com.soft.models.Inventory_out;
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

	@Override
	public List<InventoryHistory> findByInventory(Inventory inventory) {
		// TODO Auto-generated method stub
		return inventoryHistoryRepository.findByInventory(inventory);
	}

	@Override
	public List<InventoryHistory> findByInventory_out(Inventory_out inventoryOut) {
		// TODO Auto-generated method stub
		return inventoryHistoryRepository.findByInventoryOut(inventoryOut);
	}

	@Override
	public List<InventoryHistory> findByInventoryOutId(Integer inventoryOutId) {
		// TODO Auto-generated method stub
		 return inventoryHistoryRepository.findByInventoryOutId(inventoryOutId);
	}
}
