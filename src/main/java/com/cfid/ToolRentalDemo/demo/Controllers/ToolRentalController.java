package com.cfid.ToolRentalDemo.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cfid.ToolRentalDemo.demo.Exceptions.ToolRentalException;
import com.cfid.ToolRentalDemo.demo.Models.CheckoutRequest;
import com.cfid.ToolRentalDemo.demo.Models.RentalAgreement;
import com.cfid.ToolRentalDemo.demo.Services.ToolRentalService;

@RestController
public class ToolRentalController {
	
	@Autowired
	ToolRentalService toolRentalService; 
	
	
	@GetMapping("toolrental/checkout")
	public RentalAgreement CheckoutTools(@RequestBody CheckoutRequest checkoutRequest ) throws ToolRentalException {
		return toolRentalService.checkout(checkoutRequest.getToolCode(), checkoutRequest.getCheckoutDate(), checkoutRequest.getRentalDays(), checkoutRequest.getDiscountPercent());
	}
}
