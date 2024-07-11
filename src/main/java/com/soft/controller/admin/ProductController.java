package com.soft.controller.admin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.soft.models.Category;
import com.soft.models.Inventory;
import com.soft.models.InventoryHistory;
import com.soft.models.Inventory_out;
import com.soft.models.Product;
import com.soft.models.Suppliers;
import com.soft.service.CategoryService;
import com.soft.service.InventoryOutService;
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
	@Autowired
	private InventoryOutService inventoryOutService;
	
	@GetMapping("/export-products")
	public ResponseEntity<byte[]> exportProductsToExcel() {
	    List<Product> products = productService.getAll();

	    int totalQuantity = 0;
	    for (Product product : products) {
	        int totalQuantityForProduct = calculateTotalQuantity(product);
	        product.setTotalQuantity(totalQuantityForProduct);
	        totalQuantity += totalQuantityForProduct;
	    }

	    try (Workbook workbook = new XSSFWorkbook()) {
	        Sheet sheet = workbook.createSheet("Products");

	        // Tạo dòng tiêu đề lớn
	        Row titleRow = sheet.createRow(0);
	        Cell titleCell = titleRow.createCell(0);
	        titleCell.setCellValue("List of products currently in stock");

	        // Định dạng dòng tiêu đề lớn
	        CellStyle titleCellStyle = workbook.createCellStyle();
	        Font titleFont = workbook.createFont();
	        titleFont.setBold(true);
	        titleFont.setFontHeightInPoints((short) 16);
	        titleCellStyle.setFont(titleFont);
	        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
	        titleCell.setCellStyle(titleCellStyle);

	        // Hợp nhất các ô cho tiêu đề lớn
	        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

	        // Tiêu đề cột
	        String[] headers = { "ID", "Product Name", "Price", "Category", "Supplier", "Quantity" };
	        Row headerRow = sheet.createRow(1);
	        CellStyle headerCellStyle = workbook.createCellStyle();
	        headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
	        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        headerCellStyle.setBorderBottom(BorderStyle.THIN);
	        headerCellStyle.setBorderLeft(BorderStyle.THIN);
	        headerCellStyle.setBorderRight(BorderStyle.THIN);
	        headerCellStyle.setBorderTop(BorderStyle.THIN);
	        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
	        Font headerFont = workbook.createFont();
	        headerFont.setBold(true);
	        headerCellStyle.setFont(headerFont);

	        for (int i = 0; i < headers.length; i++) {
	            Cell cell = headerRow.createCell(i);
	            cell.setCellValue(headers[i]);
	            cell.setCellStyle(headerCellStyle);
	        }

	        // Định dạng dữ liệu
	        CellStyle dataCellStyle = workbook.createCellStyle();
	        dataCellStyle.setBorderBottom(BorderStyle.THIN);
	        dataCellStyle.setBorderLeft(BorderStyle.THIN);
	        dataCellStyle.setBorderRight(BorderStyle.THIN);
	        dataCellStyle.setBorderTop(BorderStyle.THIN);

	        // Dữ liệu sản phẩm
	        int rowNum = 2;
	        for (Product product : products) {
	            Row row = sheet.createRow(rowNum++);
	            row.createCell(0).setCellValue(product.getId());
	            row.createCell(1).setCellValue(product.getProductName());
	            row.createCell(2).setCellValue(product.getPrice());
	            row.createCell(3).setCellValue(product.getCategory().getCategoryName());
	            row.createCell(4).setCellValue(product.getSuppliers().getSupplierName());
	            row.createCell(5).setCellValue(product.getTotalQuantity());

	            for (int i = 0; i < headers.length; i++) {
	                row.getCell(i).setCellStyle(dataCellStyle);
	            }
	        }

	        // Thêm dòng tổng số lượng vào dòng cuối cùng
	        Row totalRow = sheet.createRow(rowNum);
	        totalRow.createCell(4).setCellValue("Total Quantity:");
	        totalRow.createCell(5).setCellValue(totalQuantity);

	        CellStyle totalCellStyle = workbook.createCellStyle();
	        totalCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	        totalCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        totalCellStyle.setBorderBottom(BorderStyle.THIN);
	        totalCellStyle.setBorderLeft(BorderStyle.THIN);
	        totalCellStyle.setBorderRight(BorderStyle.THIN);
	        totalCellStyle.setBorderTop(BorderStyle.THIN);
	        totalCellStyle.setAlignment(HorizontalAlignment.CENTER);
	        totalRow.getCell(4).setCellStyle(totalCellStyle);
	        totalRow.getCell(5).setCellStyle(totalCellStyle);

	        // Tự động điều chỉnh kích thước cột
	        for (int i = 0; i < headers.length; i++) {
	            sheet.autoSizeColumn(i);
	        }

	        // Xuất file Excel
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        workbook.write(outputStream);

	        HttpHeaders headers1 = new HttpHeaders();
	        headers1.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listproduct.xlsx");

	        return ResponseEntity.ok()
	                .headers(headers1)
	                .body(outputStream.toByteArray());
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return ResponseEntity.badRequest().build();
	}
	
	
//	@GetMapping("/checkInventory/{productId}")
//	@ResponseBody
//	public ResponseEntity<String> checkInventory(@PathVariable("productId") Integer productId,
//	                                             @RequestParam("quantityOut") Integer quantityOut) {
//	    Product product = productService.findById(productId);
//	    int totalQuantity = product.getTotalQuantity();
//	    
//	    if (quantityOut > totalQuantity) {
//	        return ResponseEntity.badRequest().body("Số lượng xuất không được lớn hơn số lượng tồn kho hiện tại (" + totalQuantity + ")");
//	    } else {
//	        return ResponseEntity.ok("Số lượng hợp lệ");
//	    }
//	}



	private int calculateTotalQuantity(Product product) {
		 int totalQuantity = product.getTotalQuantity();
	        List<Inventory> inventories = inventoryService.findByProduct(product);
	        for (Inventory inventory : inventories) {
	            totalQuantity += inventory.getQuantity();
	        }
	        List<Inventory_out> inventoryOuts = inventoryOutService.findByProduct(product);
	        for (Inventory_out inventoryOut : inventoryOuts) {
	            totalQuantity -= inventoryOut.getQuantity_out();
	        }
	        return totalQuantity;
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
	public String save(Model model, @Valid @ModelAttribute("product") Product product, BindingResult bindingResult,
			@RequestParam("fileImage") MultipartFile file) {

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

		} else {
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

		// Tính toán tổng số lượng
		int totalQuantity = calculateTotalQuantity(product);
		product.setTotalQuantity(totalQuantity);

		model.addAttribute("product", product);

		List<Inventory> inventories = inventoryService.findByProduct(product);
		model.addAttribute("inventories", inventories);
		// Lấy lịch sử Inventory cho từng Inventory và thêm vào model
		for (Inventory inventory : inventories) {
			List<InventoryHistory> history = inventoryService.getInventoryHistoryByInventoryId(inventory.getId());
			inventory.setHistory(history);
		}
		
		 // Lấy danh sách Inventory_out liên quan đến sản phẩm này
	    List<Inventory_out> inventoryOuts = inventoryOutService.findByProduct(product);
	    model.addAttribute("inventoryOuts", inventoryOuts);

	    // Lấy lịch sử Inventory_out cho từng Inventory_out và thêm vào model
	    for (Inventory_out inventoryOut : inventoryOuts) {
	        List<InventoryHistory> historyOut = inventoryOutService.getInventoryHistoryByInventoryOutId(inventoryOut.getId());
	        inventoryOut.setHistory(historyOut);
	    }
		
		return "admin/product/detail";
	}

	@PostMapping("/edit-product")
	public String update(@ModelAttribute("product") Product product, @RequestParam("fileImage") MultipartFile file,
			Model model) {
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
			List<Category> listCate = this.categoryService.getAll();
			model.addAttribute("listCate", listCate);
			List<Suppliers> listSupp = this.supplierService.getAll();
			model.addAttribute("listSupp", listSupp);
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
