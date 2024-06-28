package com.soft.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soft.models.User;
import com.soft.models.UserRole;
import com.soft.repository.RoleRepository;
import com.soft.repository.UserRoleRepository;
import com.soft.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
    private RoleRepository roleRepository;
	@Autowired
    private UserRoleRepository userRoleRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@GetMapping("/login")
	public String logon() {
		return "admin/login";
	}
	
	 @GetMapping("/register")
	    public String register(Model model) {
	        model.addAttribute("user", new User());
	        return "admin/register";
	    }

	    @PostMapping("/register")
	    public String registerSubmit(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

	        if (result.hasErrors()) {
	            return "admin/register";
	        }

	        if (userService.findByUserName(user.getUserName()) != null) {
	            return "redirect:/register?error";
	        }

	        user.setEnabled(true);
	        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
	        userService.save(user);

	        UserRole userRole = new UserRole();
	        userRole.setUser(user);
	        userRole.setRole(roleRepository.findByName("admin"));
	        userRoleRepository.save(userRole);

	        redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công!");
	        return "redirect:/login";
	    }
	   
	    
}
