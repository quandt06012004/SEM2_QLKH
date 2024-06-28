package com.soft.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soft.models.Suppliers;
import com.soft.repository.SupplierRepository;
import com.soft.service.ProductService;
import com.soft.service.SupplierService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/admin")
public class SupplierController {
	@Autowired
	private ProductService productService;
	@Autowired
	private SupplierService supplierService;
	@GetMapping("/supplier")
	public String index(Model model, @RequestParam(name="keyword", defaultValue = "") String keyword, @RequestParam(name="pageNo", defaultValue = "1") Integer pageNo) {
		Page<Suppliers> list = this.supplierService.getAll(pageNo);
		if(keyword != null) {
			list = this.supplierService.searchSuppliers(keyword, pageNo);
		}
		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("keyword", keyword);
		model.addAttribute("ListSupplier", list);
		return "admin/supplier/index";
	}
	@GetMapping("/supplier-add")
	public String add(Model model) {
		Suppliers supplier = new Suppliers();
		supplier.setGender(true);
		model.addAttribute("suppliers", supplier);
		return "admin/supplier/add";
	}
	@PostMapping("/supplier-add")
	public String save(@ModelAttribute("suppliers") @Valid Suppliers suppliers,BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("suppliers", suppliers);
			return "/admin/supplier/add";
		}else {
			this.supplierService.create(suppliers);
			return "redirect:/admin/supplier";
			
		}
	}
	@GetMapping("/delete-supplier/{id}")
	public String delele(@PathVariable("id") Integer id) {
		if(this.supplierService.delete(id)) {
			return "redirect:/admin/supplier";
		}else {
			return "redirect:/admin/supplier";
		}
		
	}
	@GetMapping("/edit-supplier/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		Suppliers suppliers = this.supplierService.findById(id);
		model.addAttribute("suppliers", suppliers);
		return "/admin/supplier/edit";
	}
	@PostMapping("/edit-supplier")
	public String update(@ModelAttribute("suppliers") Suppliers suppliers, Model model) {
		if(this.supplierService.update(suppliers)) {
			return "redirect:/admin/supplier";
		}else {
			model.addAttribute("suppliers", suppliers);
			return "/admin/supplier/edit";
		}
		
	}
	
}
