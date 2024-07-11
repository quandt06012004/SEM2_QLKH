package com.soft.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soft.models.Category;
import com.soft.service.CategoryService;

import jakarta.validation.Valid;

@Controller

@RequestMapping("/admin")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/category")

	public String index(Model model, @RequestParam(name="keyword",defaultValue = "") String keyword,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {

		Page<Category> list = this.categoryService.getAll(pageNo);

		if(keyword != null) {
			list  = this.categoryService.searchCategory(keyword,pageNo);	
		}
		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("keyword", keyword);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("list", list);

		return "admin/category/index";
	}

	@GetMapping("/add-category")

	public String add(Model model) {
		Category category = new Category();
		category.setCategoryStatus(true);
		model.addAttribute("category", category);
		return "admin/category/add";
	}

	@PostMapping("/add-category")
	public String save(@Valid @ModelAttribute("category") Category category, BindingResult result, Model model) {
		 if (result.hasErrors()) {
	            return "admin/category/add";
	        }

	        if (this.categoryService.create(category)) {
	            return "redirect:/admin/category";
	        } else {
	            model.addAttribute("error", "An error occurred while saving the directory!");
	            return "admin/category/add";
	        }
	    }

	@GetMapping("/edit-category/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Category category = this.categoryService.findById(id);
		model.addAttribute("category", category);
		return "admin/category/edit";
	}

	@PostMapping("/edit-category")
	 public String update(@Valid @ModelAttribute("category") Category category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/category/edit";
        }

        if (this.categoryService.create(category)) {
            return "redirect:/admin/category";
        } else {
            model.addAttribute("error", "An error occurred while update the directory!");
            return "admin/category/edit";
        }
    }
	@GetMapping("/delete-category/{id}")
	public String delete(@PathVariable("id") Integer id) {
		if (this.categoryService.delete(id)) {

			return "redirect:/admin/category";

		} else {
			return "redirect:/admin/category";
		}
	}
}
