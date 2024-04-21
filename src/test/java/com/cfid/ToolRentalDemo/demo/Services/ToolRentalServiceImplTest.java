package com.cfid.ToolRentalDemo.demo.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
@ExtendWith(MockitoExtension.class)
public class ToolRentalServiceImplTest {

	@InjectMocks
	ToolRentalService toolRentalService =new ToolRentalServiceImpl(); 
	
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
			toolRentalService.checkout(toolCode, checkoutDate,rentalDays,discount);
			
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
	 * Logic : 7.4 is holiday  , charge for 7.2,7.3 (2 Days)
	 * 
	 * Pre-discount : 1.99 * 2 = 3.98
	 * Discount: 3.98*10%= 0.398  = 0.4 rounded up
	 * Final Charge = 3.98-0.4 = 3.58
	 * 
	 * @throws ToolRentalException 
	 * 
	 * **/ 
	@Test
	public void checkout_testcase2() throws ToolRentalException {
		
		String toolCode ="LADW"; 
		LocalDate checkoutDate = LocalDate.of(2020, 7, 2);
		Integer rentalDays =3; 
		Integer discount =10;  
		
		Mockito.when(toolRentalRepo.getToolbyToolCode("LADW")).thenReturn(new Tool("LADW", "Ladder"		, "Werner"	,  new BigDecimal(1.99), true, true	, false));
		RentalAgreement rentalAgreement= toolRentalService.checkout(toolCode, checkoutDate,rentalDays,discount);

		BigDecimal finalCharge = new BigDecimal(3.58).setScale(2, RoundingMode.HALF_UP);
		
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
	 * Discount: 4.47*25%= 1.1175  = 1.12 rounded up
	 * Final Charge = 4.47- 1.12 = 3.35
	 * @throws ToolRentalException 
	 * 
	 * **/ 
	@Test
	public void checkout_testcase3() throws ToolRentalException {
		
		String toolCode ="CHNS"; 
		LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
		Integer rentalDays =5; 
		Integer discount =25;  
		Mockito.when(toolRentalRepo.getToolbyToolCode("CHNS")).thenReturn(new Tool("CHNS", "Chainsaw"		, "Stihl"	,  new BigDecimal(1.49), true, false	, true));
		RentalAgreement rentalAgreement= toolRentalService.checkout(toolCode, checkoutDate,rentalDays,discount);

		BigDecimal finalCharge =new BigDecimal (3.35).setScale(2, RoundingMode.HALF_UP);
		
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
	 * @throws ToolRentalException 
	 * 
	 * **/ 
	@Test
	public void checkout_testcase4() throws ToolRentalException {
		
		String toolCode ="JAKD"; 
		LocalDate checkoutDate = LocalDate.of(2015, 9, 3);
		Integer rentalDays =6; 
		Integer discount =0;  
		
		Mockito.when(toolRentalRepo.getToolbyToolCode("JAKD")).thenReturn(new Tool("JAKD", "Jackhammer"	, "DeWalt"	,  new BigDecimal(2.99), true, false	, false));
		RentalAgreement rentalAgreement= toolRentalService.checkout(toolCode, checkoutDate,rentalDays,discount);

		BigDecimal finalCharge = new BigDecimal(8.97).setScale(2, RoundingMode.HALF_UP);
		
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
	 * @throws ToolRentalException 
	 * 
	 * **/ 
	@Test
	public void checkout_testcase5() throws ToolRentalException {
		
		String toolCode ="JAKR"; 
		LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
		Integer rentalDays =9; 
		Integer discount =0;  
		
		Mockito.when(toolRentalRepo.getToolbyToolCode("JAKR")).thenReturn(new Tool("JAKR", "Jackhammer"	, "Ridgid"	,  new BigDecimal(2.99), true, false	, false));
		RentalAgreement rentalAgreement= toolRentalService.checkout(toolCode, checkoutDate,rentalDays,discount);

		BigDecimal finalCharge = new BigDecimal(17.94).setScale(2, RoundingMode.HALF_UP);
		
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
	 * @throws ToolRentalException 
	 * 
	 * **/ 
	@Test
	public void checkout_testcase6() throws ToolRentalException {
		
		String toolCode ="JAKR"; 
		LocalDate checkoutDate = LocalDate.of(2020, 7, 2);
		Integer rentalDays =4; 
		Integer discount =50;  
		
		Mockito.when(toolRentalRepo.getToolbyToolCode("JAKR")).thenReturn(new Tool("JAKR", "Jackhammer"	, "Ridgid"	,  new BigDecimal(2.99), true, false	, false));
		RentalAgreement rentalAgreement= toolRentalService.checkout(toolCode, checkoutDate,rentalDays,discount);

		BigDecimal finalCharge = new BigDecimal(1.49).setScale(2, RoundingMode.HALF_UP);
		
		assertEquals(rentalAgreement.getFinalCharge(), finalCharge);
			
	}
	
}
