package com.cfid.ToolRentalDemo.demo.Utils;

import com.cfid.ToolRentalDemo.demo.Exceptions.ToolRentalException;

public class ToolRentalValidator {
	
	public Boolean validateRentalDayCount(Integer rentalDayCount) throws ToolRentalException {
		if (rentalDayCount <1 ) throw new ToolRentalException("Rental day count is not 1 or greater");
		return true; 
	}

	public Boolean validateDiscount(Integer discount) throws ToolRentalException {
		if (discount>100 || discount < 0 ) throw new ToolRentalException("Discount percent is not in the range 0-100"); 
		return true; 
	}
	
	
	
}
