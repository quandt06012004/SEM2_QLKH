package com.soft.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.soft.models.Inventory;
import com.soft.models.InventoryHistory;
import com.soft.models.Product;
import com.soft.repository.InventoryRepository;

@Service
public class InventoryImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryHistoryService inventoryHistoryService;

    @Override
    public List<Inventory> getAll() {
        return this.inventoryRepository.findAll();
    }

	@Override
	public Boolean update(Inventory inventory) {
		// TODO Auto-generated method stub
	 try {
			this.inventoryRepository.save(inventory);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}


    @Override
    public Inventory findById(Integer id) {
        return this.inventoryRepository.findById(id).get();
    }

   
	@Override
	public Page<Inventory> getAll(Integer pageNo) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo - 1, 4);
		return this.inventoryRepository.findAll(pageable);
	}


    @Override
    public Boolean delete(Integer id) {
        try {
            this.inventoryRepository.delete(findById(id));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

	@Override
    public Page<Inventory> findAllBetweenDates(Date startDate, Date endDate, Pageable pageable) {
        return this.inventoryRepository.findAllBetweenDates(startDate, endDate, pageable);
    }
	

    @Override
    public Page<Inventory> searchInventory(String keyword, Integer pageNo) {
        return null;
    }

    @Override
    public List<Inventory> findByProduct(Product product) {
        return inventoryRepository.findByProduct(product);
    }

    @Override
    public List<InventoryHistory> getInventoryHistoryByInventoryId(Integer inventoryId) {
        return inventoryHistoryService.findByInventoryId(inventoryId);
    }

    private void saveInventoryHistory(Inventory inventory) {
        InventoryHistory history = new InventoryHistory();
        history.setQuantity(inventory.getQuantity());
        history.setDateUpdated(new Date());
        history.setInventory(inventory);
        inventoryHistoryService.create(history);
    }

    @Override
    public Boolean create(Inventory inventory) {
        try {
            this.inventoryRepository.save(inventory);
            saveInventoryHistory(inventory);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

	@Override
	public List<Inventory> searchInventory(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}
}
