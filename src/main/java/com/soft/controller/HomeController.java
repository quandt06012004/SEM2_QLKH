package com.soft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.soft.models.Category;
import com.soft.models.Product;
import com.soft.service.CategoryService;
import com.soft.service.ProductService;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@GetMapping(value = {"/","/home"})
	public String home(Model model, @RequestParam(name = "count", defaultValue = "4") int count) {
	    List<Product> listProductnew = productService.getLatestNProducts(count);
	    model.addAttribute("listProductnew", listProductnew);
	    
	    List<Product> listProductMale = productService.findMaleProducts();
	    model.addAttribute("listProductMale", listProductMale);
	    
	    List<Product> listProductFemale = productService.findFemaleProducts();
	    model.addAttribute("listProductFemale", listProductFemale);
	    
	    List<Product> listProduct = this.productService.getAll(); // Đây là danh sách tất cả sản phẩm, có thể thay bằng phương thức khác nếu cần
	    model.addAttribute("listProduct", listProduct);
	    
	    return "index";
	}

	@GetMapping("/home/detail")
	public String detail(@RequestParam("id") Integer id, Model model, @RequestParam(name = "count", defaultValue = "4") int count) {
	    List<Product> listProductnew = productService.getLatestNProducts(count);
	    model.addAttribute("listProductnew", listProductnew);
		Product product = this.productService.findById(id);
		model.addAttribute("p", product);
		return "detailProduct";
	}
	@GetMapping("/home/shop")
	public String shop(@RequestParam(name = "categoryIds", required = false) List<Integer> categoryIds,
	                   @RequestParam(name = "page", defaultValue = "1") Integer page,
	                   Model model) {
	    List<Category> listcate = categoryService.getAll();
	    model.addAttribute("listcate", listcate);
	    
	    Page<Product> productPage;
	    if (categoryIds != null && !categoryIds.isEmpty()) {
	        productPage = productService.findProductsByCategories(categoryIds, page, 6); // Lấy sản phẩm theo danh sách categoryIds
	        model.addAttribute("listProduct", productPage.getContent());
	        model.addAttribute("currentPage", productPage.getNumber() + 1);
	        model.addAttribute("totalPages", productPage.getTotalPages());
	        return "shop :: #product-list"; // Trả về phần tử có id product-list trong template shop.html
	    } else {
	        productPage = productService.getAll(page, 6); // Lấy tất cả sản phẩm trang page, số lượng là 6 sản phẩm mỗi trang
	        model.addAttribute("listProduct", productPage.getContent());
	        model.addAttribute("currentPage", productPage.getNumber() + 1);
	        model.addAttribute("totalPages", productPage.getTotalPages());
	        return "shop"; // Trả về template shop.html
	    }
	}

	@GetMapping("/home/cart")
	public String cart(Model model) {
		return "cart";
	}
	
	
	 
}
