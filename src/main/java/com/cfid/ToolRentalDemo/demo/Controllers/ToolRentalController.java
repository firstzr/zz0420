package com.cfid.ToolRentalDemo.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cfid.ToolRentalDemo.demo.Services.ToolRentalService;

@RestController
public class ToolRentalController {
	
	@Autowired
	ToolRentalService toolRentalService; 
	
	
	@GetMapping("toolrental/checkout")
	public String CheckoutTools() {
		return "good";
	}
}
