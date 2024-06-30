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
import com.soft.models.Inventory_out;
import com.soft.models.Product;
import com.soft.repository.InventoryOutRepository;
@Service
public class InventoryOutImpl implements InventoryOutService{
	@Autowired
	private InventoryOutRepository inventoryOutRepository;
	 @Autowired
	 private InventoryHistoryService inventoryHistoryService;

	@Override
	public List<Inventory_out> getAll() {
		// TODO Auto-generated method stub
		return this.inventoryOutRepository.findAll();
	}

	@Override
	public Boolean create(Inventory_out inventory_out) {
		// TODO Auto-generated method stub
	
	     try {
	    	 this.inventoryOutRepository.save(inventory_out);
	            saveInventoryHistory(inventory_out);
	            return true;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return false;
	}

	@Override
	public Inventory_out findById(Integer id) {
		// TODO Auto-generated method stub
		return this.inventoryOutRepository.findById(id).get();
	}

	@Override
	public Boolean update(Inventory_out inventory_out) {
		// TODO Auto-generated method stub
		try {
			this.inventoryOutRepository.save(inventory_out);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Inventory_out> searchInventory_out(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delete(Integer id) {
		// TODO Auto-generated method stub
		try {
			this.inventoryOutRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Page<Inventory_out> getAll(Integer pageNo) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo - 1, 4);
		return this.inventoryOutRepository.findAll(pageable);
	}

	@Override
	public Page<Inventory_out> searchInventory_out(String keyword, Integer pageNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Inventory_out> findAllBetweenDates(Date startDate, Date endDate, Pageable pageable) {
		// TODO Auto-generated method stub
		return this.inventoryOutRepository.findAllBetweenDates(startDate, endDate, pageable);
	}

	@Override
	public List<Inventory_out> findByProduct(Product product) {
		// TODO Auto-generated method stub
		return this.inventoryOutRepository.findByProduct(product);
	}

	@Override
	public List<InventoryHistory> getInventoryHistoryByInventoryOutId(Integer inventoryOutId) {
		// TODO Auto-generated method stub
		return inventoryHistoryService.findByInventoryOutId(inventoryOutId);
	}
	private void saveInventoryHistory(Inventory_out inventory_out) {
		 InventoryHistory history = new InventoryHistory();
	        history.setQuantity(inventory_out.getQuantity_out());
	        history.setDateUpdated(new Date());
	        history.setInventoryOut(inventory_out);
	        inventoryHistoryService.create(history);
    }

	@Override
	public int getTotalQuantity(Integer productId) {
		// TODO Auto-generated method stub
		return 0;
	}

//	@Override
//	public int getTotalQuantity(Integer productId) {
//		// TODO Auto-generated method stub
//		  int totalQuantity = 0;
//
//	        // Tính tổng số lượng tồn kho bằng cách lấy số lượng tồn và trừ đi số lượng đã xuất
//	        List<Inventory_out> inventories_out = InventoryOutService.findById(productId);
//	        for (Inventory inventory : inventories_out) {
//	            totalQuantity += inventory.getQuantity();
//	        }
//
//	        List<Inventory_out> inventoryOuts = inventoryOutService.findByProductId(productId);
//	        for (Inventory_out inventoryOut : inventoryOuts) {
//	            totalQuantity -= inventoryOut.getQuantity_out();
//	        }
//
//	        return totalQuantity;
//	}
}
