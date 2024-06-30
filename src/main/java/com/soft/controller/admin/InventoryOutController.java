package com.soft.controller.admin;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soft.models.Inventory_out;
import com.soft.models.Product;
import com.soft.service.InventoryOutService;
import com.soft.service.ProductService;

@Controller
@RequestMapping("/admin")
public class InventoryOutController {
	@Autowired
	private InventoryOutService inventoryOutService;
	@Autowired
	private ProductService productService;
	
	@GetMapping("/inventory-out")
	public String inventory_out(Model model,
	                        @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
	                        @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
	                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {

	    Page<Inventory_out> page;
	    if (startDate != null && endDate != null) {
	        // Nếu có startDate và endDate, thực hiện tìm kiếm theo khoảng thời gian
	        Pageable pageable = PageRequest.of(pageNo - 1, 4); 
	        page = inventoryOutService.findAllBetweenDates(startDate, endDate, pageable);
	    } else {
	        // Nếu không có startDate và endDate, lấy tất cả dữ liệu
	        page = inventoryOutService.getAll(pageNo);
	    }
	    model.addAttribute("startDate", startDate);
	    model.addAttribute("endDate", endDate);
	    
	    // Thêm các attribute cần thiết cho giao diện Thymeleaf
	    model.addAttribute("listInventory_out", page.getContent()); 
	    model.addAttribute("currentPage", pageNo); 
	    model.addAttribute("totalPage", page.getTotalPages()); 

	    return "admin/inventory_out/index";
	}

	@GetMapping("/inventory-out-add")
	public String add(Model model) {
		Inventory_out inventory_out = new Inventory_out();
		model.addAttribute("inventory_out", inventory_out);
		List<Product> listProduct = this.productService.getAll();
		model.addAttribute("listProduct", listProduct);
		return "admin/inventory_out/add";
	}
	
	@PostMapping("/inventory-out-add")
	public String save(@ModelAttribute("inventory_out") Inventory_out inventory_out, Model model) {
		//TODO: process POST request
		if(this.inventoryOutService.create(inventory_out)) {
			return "redirect:/admin/inventory-out";
		}
		else {
			List<Product> listProduct = this.productService.getAll();
			model.addAttribute("listProduct", listProduct);
			return "admin/inventory_out/add";
		}
	}
	
	@GetMapping("/delete-inventory-out/{id}")
	public String delete(@PathVariable("id") Integer id, Model model) {
		if(this.inventoryOutService.delete(id)) {
			return "redirect:/admin/inventory-out";
		}else {
			return "redirect:/admin/inventory-out";
		}
	}
	
	@GetMapping("/edit-inventory-out/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Inventory_out inventory_out = this.inventoryOutService.findById(id);
		if (inventory_out == null) {
			model.addAttribute("error", "inventory out not found");
			return "redirect:/admin/inventory-out";
		}
		model.addAttribute("inventory_out", inventory_out);
		List<Product> listProduct = this.productService.getAll();
		model.addAttribute("listProduct", listProduct);
		
		return "admin/inventory_out/edit";
	}
	
	
	@PostMapping("/edit-inventory-out")
	public String update(@ModelAttribute("inventory_out") Inventory_out inventory_out,  Model model) {
	  
	        

	    if (this.inventoryOutService.update(inventory_out)) {
	        return "redirect:/admin/inventory-out";
	    } else {
	        model.addAttribute("error", "Failed to update inventory");
	        model.addAttribute("inventory", inventory_out);
	        return "admin/inventory_out/edit";
	    }
	}
	
}
