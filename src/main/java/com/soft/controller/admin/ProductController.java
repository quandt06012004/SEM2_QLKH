package com.soft.controller.admin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import com.soft.models.Category;
import com.soft.models.Inventory;
import com.soft.models.InventoryHistory;
import com.soft.models.Product;
import com.soft.models.Suppliers;
import com.soft.service.CategoryService;
import com.soft.service.InventoryService;
import com.soft.service.ProductService;
import com.soft.service.StorageService;
import com.soft.service.SupplierService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class ProductController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private StorageService storageService;
	@Autowired
	private ProductService productService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
    private InventoryService inventoryService;
	
	@GetMapping("/export-products")
	public ResponseEntity<byte[]> exportProductsToExcel() {
	    List<Product> products = productService.getAll(); 

	    
	    long totalQuantity = products.stream().mapToLong(Product::getTotalQuantity).sum();

	    try (Workbook workbook = new XSSFWorkbook()) {
	        Sheet sheet = workbook.createSheet("Products");

	        // Tiêu đề cột
	        String[] headers = {"ID", "Product Name", "Price", "Category", "Supplier", "Quantity"};
	        Row headerRow = sheet.createRow(0);
	        for (int i = 0; i < headers.length; i++) {
	            Cell cell = headerRow.createCell(i);
	            cell.setCellValue(headers[i]);
	        }

	        // Dữ liệu sản phẩm
	        int rowNum = 1;
	        for (Product product : products) {
	            Row row = sheet.createRow(rowNum++);
	            row.createCell(0).setCellValue(product.getId());
	            row.createCell(1).setCellValue(product.getProductName());
	            row.createCell(2).setCellValue(product.getPrice());
	            row.createCell(3).setCellValue(product.getCategory().getCategoryName());
	            row.createCell(4).setCellValue(product.getSuppliers().getSupplierName());
	            row.createCell(5).setCellValue(product.getTotalQuantity()); // Cột Quantity là cột thứ 5

	            
	            if (rowNum == products.size() + 1) {
	                Row totalRow = sheet.createRow(rowNum);
	                totalRow.createCell(4).setCellValue("Total Quantity:"); // Cột Category là cột thứ 4
	                totalRow.createCell(5).setCellValue(totalQuantity); // Cột Quantity là cột thứ 5
	            }
	        }

	        // xuất file Excel
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        workbook.write(outputStream);

	        HttpHeaders headers1 = new HttpHeaders();
	        headers1.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=products.xlsx");

	        return ResponseEntity.ok()
	                .headers(headers1)
	                .body(outputStream.toByteArray());
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return ResponseEntity.badRequest().build();
	}
	
	
	@GetMapping("/product")
    public String index(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword,
                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        Page<Product> list = this.productService.getAll(pageNo, 4);

        if (keyword != null) {
            list = this.productService.searchProduct(keyword, pageNo, 4);
        }

        
        for (Product product : list) {
            int totalQuantity = calculateTotalQuantity(product);
            product.setTotalQuantity(totalQuantity);
        }

        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("listProduct", list);

        return "admin/product/index";
    }

    
    private int calculateTotalQuantity(Product product) {
        int totalQuantity = 0;
        List<Inventory> inventories = inventoryService.findByProduct(product);
        for (Inventory inventory : inventories) {
            totalQuantity += inventory.getQuantity();
        }
        return totalQuantity;
    }

	@GetMapping("/product-add")
	public String add(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		List<Category> listCate = this.categoryService.getAll();
		model.addAttribute("listCate", listCate);
		
		List<Suppliers> listSupp = this.supplierService.getAll();
		model.addAttribute("listSupp", listSupp);
		
		return "admin/product/add";
	}

	@PostMapping("/product-add")
	public String save(Model model,@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, @RequestParam("fileImage") MultipartFile file) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("product", product);
			List<Category> listCate = this.categoryService.getAll();
			model.addAttribute("listCate", listCate);
			List<Suppliers> listSupp = this.supplierService.getAll();
			model.addAttribute("listSupp", listSupp);
			return "admin/product/add";
	    }
		
		this.storageService.store(file);
		String fileName = file.getOriginalFilename();
		product.setImage(fileName);
		if (this.productService.create(product)) {
			return "redirect:/admin/product";

		}else {
			List<Suppliers> listSupp = this.supplierService.getAll();
			model.addAttribute("listSupp", listSupp);
			List<Category> listCate = this.categoryService.getAll();
			model.addAttribute("listCate", listCate);
			return "admin/product/add";
		}
		
	}

	@GetMapping("/edit-product/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Product product = this.productService.findById(id);
		if (product == null) {
			model.addAttribute("error", "Product not found");
			return "redirect:/admin/product";
		}
		List<Category> listCate = this.categoryService.getAll();
		model.addAttribute("listCate", listCate);
		List<Suppliers> listSupp = this.supplierService.getAll();
		model.addAttribute("listSupp", listSupp);
		model.addAttribute("product", product);
		return "admin/product/edit";
	}
	
	 @GetMapping("/detail-product/{id}")
	    public String detail(Model model, @PathVariable("id") Integer id) {
	        Product product = productService.findById(id);
	        if (product == null) {
	            model.addAttribute("error", "Product not found");
	            return "redirect:/admin/product";
	        }
	        List<Category> listCate = categoryService.getAll();
	        model.addAttribute("listCate", listCate);
	        List<Suppliers> listSupp = supplierService.getAll();
	        model.addAttribute("listSupp", listSupp);
	        model.addAttribute("product", product);

	        
	        List<Inventory> inventories = inventoryService.findByProduct(product);
	        model.addAttribute("inventories", inventories);

	        // Lấy lịch sử Inventory cho từng Inventory và thêm vào model
	        for (Inventory inventory : inventories) {
	            List<InventoryHistory> history = inventoryService.getInventoryHistoryByInventoryId(inventory.getId());
	            inventory.setHistory(history);
	        }

	        return "admin/product/detail";
	    }


	@PostMapping("/edit-product")
	public String update(@ModelAttribute("product") Product product, @RequestParam("fileImage") MultipartFile file, Model model) {
	    if (!file.isEmpty()) {
	        try {
	            String fileName = file.getOriginalFilename();
	            this.storageService.store(file);
	            product.setImage(fileName);
	        } catch (Exception e) {
	            model.addAttribute("error", "Failed to store file " + file.getOriginalFilename());
	            model.addAttribute("product", product);
	            return "admin/product/edit";
	        }
	    } else {
	        Product existingProduct = this.productService.findById(product.getId());
	        if (existingProduct != null) {
	            product.setImage(existingProduct.getImage());
	        } else {
	            model.addAttribute("error", "Product not found");
	            model.addAttribute("product", product);
	            return "admin/product/edit";
	        }
	    }

	    if (this.productService.update(product)) {
	        return "redirect:/admin/product";
	    } else {
	        model.addAttribute("error", "Failed to update product");
	        model.addAttribute("product", product);
	        return "admin/product/edit";
	    }
	}

	@GetMapping("/delete-product/{id}")
	public String delete(@PathVariable("id") Integer id) {
		if (this.productService.delete(id)) {

			return "redirect:/admin/product";

		} else {
			return "redirect:/admin/product";
		}
	}
}
