package com.soft.controller.admin;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soft.models.Category;
import com.soft.models.Role;
import com.soft.models.Suppliers;
import com.soft.models.User;
import com.soft.models.UserRole;
import com.soft.repository.CategoryRepository;
import com.soft.repository.ProductRepository;
import com.soft.repository.UserRepository;
import com.soft.service.CategoryService;
import com.soft.service.RoleService;
import com.soft.service.SupplierService;
import com.soft.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	 @Autowired
	    private CategoryRepository categoryRepository;
	    
	    @Autowired
	    private ProductRepository productRepository;
	    
	    @Autowired
	    private UserRepository userRepository;
	    
	    @Autowired
	    private PasswordEncoder passwordEncoder;
	  
	   
	    @Autowired
	    private UserService userService; 

//	    @Autowired
//		private SupplierService supplierService;
//	    @Autowired
//		private CategoryService categoryService;
	    
	    @Autowired
	    private RoleService roleService;
	    
	    
	    
	    
	    @RequestMapping
	    public String index() {
	    	return "redirect:/admin/";
	    }
	    @RequestMapping("/")
	    public String admin(Model model) {
	        long countCategory = categoryRepository.count();
	        long countProduct = productRepository.count();
	        long countUser = userRepository.count();

	        model.addAttribute("countCategory", countCategory);
	        model.addAttribute("countProduct", countProduct);
	        model.addAttribute("countUser", countUser);

	        return "admin/index";
	    }

	    @PreAuthorize("hasRole('admin')")
	    @GetMapping("/account/management")
	    public String accountManagement(Model model, @RequestParam(name="keyword",defaultValue = "") String keyword,
				@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
	        Page<User> listUsers = this.userService.getAll(pageNo); 
	        
	        
	        if(keyword != null) {
	        	listUsers  = this.userService.searchUser(keyword,pageNo);	
			}
			model.addAttribute("totalPage", listUsers.getTotalPages());
			model.addAttribute("keyword", keyword);
			model.addAttribute("currentPage", pageNo);
	        
	        model.addAttribute("listUsers", listUsers);
	        return "admin/account/management";
	    }
	    
	   
	    @PreAuthorize("hasRole('admin')")
	    @GetMapping("/account/profile")
	    public String getProfile(Model model, Principal principal) {
	        String userName = principal.getName();
	        User user = userService.findByUserName(userName);

	        if (user == null) {
	            throw new RuntimeException("User not found");
	        }

	        model.addAttribute("user", user); 
	        return "admin/account/profile";
	    }
	    
	    @PreAuthorize("hasRole('admin')")
	    @GetMapping("/account/change-password")
	    public String showChangePasswordPage() {
	        return "admin/account/change-password";
	    }
	    
	    @PreAuthorize("hasRole('admin')")
	    @PostMapping("/account/change-password")
	    public String changePassword(
	            @RequestParam("currentPassword") String currentPassword,
	            @RequestParam("newPassword") String newPassword,
	            @RequestParam("confirmPassword") String confirmPassword,
	            Principal principal,
	            Model model) {
	        String userName = principal.getName();
	        User user = userService.findByUserName(userName);

	        if (user == null) {
	            throw new RuntimeException("User not found");
	        }

	        if (!passwordEncoder.matches(currentPassword, user.getPassWord())) {
	            model.addAttribute("error", "Current password is incorrect");
	            return "admin/account/change-password";
	        }

	        if (!newPassword.equals(confirmPassword)) {
	            model.addAttribute("error", "New passwords do not match");
	            return "admin/account/change-password";
	        }

	        user.setPassWord(passwordEncoder.encode(newPassword));
	        userService.save(user);

	        model.addAttribute("message", "Password changed successfully");
	        return "redirect:/admin/account/profile";
	    }
	    @GetMapping("/account/delete")
	    public String getMethodName(@RequestParam("id") Long id) {
	    	 userService.deleteUserById(id);
	    	 return "redirect:/admin/account/management"; 
	    	 
	    }
	    @GetMapping("/account/edit")
	    public String editUserForm(@RequestParam("id") Long id, Model model) {
	        // Retrieve user by id
	        User user = userService.findById(id);
	        model.addAttribute("user", user);
	        return "admin/account/editAccount"; 
	   }

	    
	    @PostMapping("/account/edit")
	    public String updateUser(@ModelAttribute("user") User user, @RequestParam("roleId") Long roleId) {
	        Role newRole = roleService.getRoleById(roleId);
	        if (user.getUserRoles().size() > 0) {
	            UserRole userRole = user.getUserRoles().iterator().next();
	            userRole.setRole(newRole);
	        } else {
	            UserRole userRole = new UserRole();
	            userRole.setUser(user);
	            userRole.setRole(newRole);
	            user.getUserRoles().add(userRole);
	        }
	        userService.save(user);
	        return "redirect:/admin/account/management";
	    }
}
