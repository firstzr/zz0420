package com.cfid.ToolRentalDemo.demo.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.cfid.ToolRentalDemo.demo.Exceptions.ToolRentalException;
import com.cfid.ToolRentalDemo.demo.Models.RentalAgreement;
import com.cfid.ToolRentalDemo.demo.Models.Tool;
import com.cfid.ToolRentalDemo.demo.Repos.ToolRentalRepo;


/** 
 * Test case for ToolRentalService Checkout Logic
 * As per requirement, Does NOT cover below case : 
 *	 
 *	Customer checkout on 7.2.2020 for 2 days for a JackHammer. 
 * 
 * **/
public class ToolRentalServiceImplTest {

	@InjectMocks
	ToolRentalService toolRentalService; 
	
	@Mock 
	ToolRentalRepo toolRentalRepo; 
	
	/**
	 * Case Scenario:  
	 * 
	 * Discount invalid.Expecting exception
	 *  
	 * **/ 
	@Test
	public void checkout_testcase1() {
		
		String toolCode ="JAKR"; 
		LocalDate checkoutDate = LocalDate.of(2015, 9, 3);
		Integer rentalDays =5; 
		Integer discount =101;  
		
		
		Exception exception = assertThrows(ToolRentalException.class, () -> {
			RentalAgreement rentalAgreement= toolRentalService.checkout(toolCode, checkoutDate,rentalDays,discount);
			
        });

        String expectedMessage = "Discount percent is not in the range 0-100";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
		
	}
	
	/**
	 * Case Scenario:  
	 * tool code : LADW  
	 * Tool profile: 	"LADW"
	 * 					Daily charge 1.99, 
	 * 					Weekday Charge:True, 
	 * 					Weekend Charge:True, 
	 * 					Holiday Charge:False
	 * 
	 * Action: Checkout on 7.2.2020 Thurday for 3 days , 10% off 
	 * 
	 * Logic : 7.4 is holiday lays in Sat, so free on 7.3 , charge for 7.2,7.4 (2 Days)
	 * 
	 * Pre-discount : 1.99 * 2 = 3.98
	 * Discount: 3.98*10%= 0.398  = 0.4 rounded up
	 * Final Charge = 3.98-0.4 = 3.58
	 * 
	 * **/ 
	@Test
	public void checkout_testcase2() {
		
		String toolCode ="LADW"; 
		LocalDate checkoutDate = LocalDate.of(2020, 7, 2);
		Integer rentalDays =3; 
		Integer discount =10;  
		
		RentalAgreement rentalAgreement= toolRentalService.checkout(toolCode, checkoutDate,rentalDays,discount);

		Double finalCharge = 3.58;
		
		assertEquals(rentalAgreement.getFinalCharge(), finalCharge);
			
	}
	
	/**
	 * Case Scenario:  
	 * tool code : CHNS  
	 * Tool profile: 	"CHNS"
	 * 					Daily charge 1.49, 
	 * 					Weekday Charge:True, 
	 * 					Weekend Charge:False, 
	 * 					Holiday Charge:True
	 * 																  
	 * Action: Checkout on 7.2.2015 Thurday for 5 days , 25% off     
	 * 
	 * Logic : charge for July 2,3,6th - 3days
	 *  
	 * Pre-discount : 1.49 * 3 = 4.47
	 * Discount: 4.47*25%= 1.1175  = 0.12 rounded up
	 * Final Charge = 4.47- 0.12 = 4.35
	 * 
	 * **/ 
	@Test
	public void checkout_testcase3() {
		
		String toolCode ="CHNS"; 
		LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
		Integer rentalDays =5; 
		Integer discount =25;  
		
		RentalAgreement rentalAgreement= toolRentalService.checkout(toolCode, checkoutDate,rentalDays,discount);

		Double finalCharge = 4.35;
		
		assertEquals(rentalAgreement.getFinalCharge(), finalCharge);
			
	}
	
	/**
	 * Case Scenario:  
	 * tool code : JAKD  
	 * Tool profile: 	"JAKD"
	 * 					Daily charge 2.99, 
	 * 					Weekday Charge:True, 
	 * 					Weekend Charge:False, 
	 * 					Holiday Charge:False
	 * 																  
	 * Action: Checkout on 9/3/15 Thurday for 6 days , 0% off          Sep. 3 4 5 6 7 8 
	 * 														dayofweek       4 5 6 7 1 2 	
	 * Logic : charge for Sep 3,4,8th - 3days
	 *  
	 * Pre-discount : 2.99 * 3 = 8.97
	 * 
	 * Final Charge = 8.97
	 * 
	 * **/ 
	@Test
	public void checkout_testcase4() {
		
		String toolCode ="JAKD"; 
		LocalDate checkoutDate = LocalDate.of(2015, 9, 3);
		Integer rentalDays =6; 
		Integer discount =0;  
		
		RentalAgreement rentalAgreement= toolRentalService.checkout(toolCode, checkoutDate,rentalDays,discount);

		Double finalCharge = 8.97;
		
		assertEquals(rentalAgreement.getFinalCharge(), finalCharge);
			
	}
	
	/**
	 * Case Scenario:  
	 * tool code : JAKR  
	 * Tool profile: 	"JAKR"
	 * 					Daily charge 2.99, 
	 * 					Weekday Charge:True, 
	 * 					Weekend Charge:False, 
	 * 					Holiday Charge:False
	 * 																  
	 * Action: Checkout on 7/2/15 Thurday for 9 days , 0% off          July. 2 3 4 5 6 7 8 9 10 
	 * 														dayofweek        4(5 6 7)1 2 3 4 5	 
	 * Logic : charge for 6 days 
	 *  
	 * Pre-discount : 2.99 * 6 = 17.94
	 * 
	 * Final Charge = 17.94
	 * 
	 * **/ 
	@Test
	public void checkout_testcase5() {
		
		String toolCode ="JAKR"; 
		LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
		Integer rentalDays =9; 
		Integer discount =0;  
		
		RentalAgreement rentalAgreement= toolRentalService.checkout(toolCode, checkoutDate,rentalDays,discount);

		Double finalCharge = 17.94;
		
		assertEquals(rentalAgreement.getFinalCharge(), finalCharge);
			
	}
	
	/**
	 * Case Scenario:  
	 * tool code : JAKR  
	 * Tool profile: 	"JAKR"
	 * 					Daily charge 2.99, 
	 * 					Weekday Charge:True, 
	 * 					Weekend Charge:False, 
	 * 					Holiday Charge:False
	 * 																  
	 * Action: Checkout on 7/2/20 Thurday for 4 days , 50% off          July. 2 3 4 5 
	 * 														dayofweek         4(5 6 7)
	 * Logic : charge for 1 days 
	 *  
	 * Pre-discount : 2.99 * 1 = 2.99
	 * Discount: 2.99*0.5 = 1.495 = 1.5 rounded up 
	 * Final Charge 2.99-1.5 = 1.49 
	 * 
	 * **/ 
	@Test
	public void checkout_testcase6() {
		
		String toolCode ="JAKR"; 
		LocalDate checkoutDate = LocalDate.of(2020, 7, 2);
		Integer rentalDays =4; 
		Integer discount =50;  
		
		RentalAgreement rentalAgreement= toolRentalService.checkout(toolCode, checkoutDate,rentalDays,discount);

		Double finalCharge = 1.49;
		
		assertEquals(rentalAgreement.getFinalCharge(), finalCharge);
			
	}
	
}
