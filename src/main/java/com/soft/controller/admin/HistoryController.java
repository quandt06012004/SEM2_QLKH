package com.soft.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.soft.models.InventoryHistory;
import com.soft.service.InventoryHistoryService;


@Controller
@RequestMapping("/admin")
public class HistoryController {
	@Autowired
	private InventoryHistoryService inventoryHistoryService;


	 @GetMapping("/historyAll")
	    public String getAllHistory(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
	        List<InventoryHistory> listHistory;
	        if (keyword != null && !keyword.isEmpty()) {
	            listHistory = inventoryHistoryService.findByProductName(keyword);
	        } else {
	            listHistory = inventoryHistoryService.getAllHistory();
	        }
	        model.addAttribute("listHistory", listHistory);
	        model.addAttribute("keyword", keyword); // để giữ lại từ khóa trong trường nhập liệu
	        return "admin/history/index";
	    }
	 
	 @GetMapping("/delete-history/{id}")
	 public String deleteHistory(@PathVariable("id") Integer id) {
	    if( inventoryHistoryService.deleteHistoryById(id)) {
	    	return "redirect:/admin/historyAll";
	    }else {
	    	return "redirect:/admin/historyAll?error=true";
	    }
	 }
}
