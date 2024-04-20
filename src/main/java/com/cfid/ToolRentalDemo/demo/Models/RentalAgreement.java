package com.cfid.ToolRentalDemo.demo.Models;

import java.util.Date;

public class RentalAgreement {
	
	Tool tool;
	Integer rentalDays; 
	Date checkoutDate;
	Date dueDate; 
	Integer chargeDays; 
	Double preDiscountCharge;
	Double discountPercent;
	Double discountAmount; 
	Double finalCharge;
	
	public void printRentalAgreement() {
		//TODO: prints RA details per requirement
	}; 
	
	public RentalAgreement(Tool tool, Integer rentalDays, Date checkoutDate, Date dueDate, Integer chargeDays,
			Double preDiscountCharge, Double discountPercent, Double discountAmount, Double finalCharge) {
		super();
		this.tool = tool;
		this.rentalDays = rentalDays;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.chargeDays = chargeDays;
		this.preDiscountCharge = preDiscountCharge;
		this.discountPercent = discountPercent;
		this.discountAmount = discountAmount;
		this.finalCharge = finalCharge;
	}
	public Tool getTool() {
		return tool;
	}
	public void setTool(Tool tool) {
		this.tool = tool;
	}
	public Integer getRentalDays() {
		return rentalDays;
	}
	public void setRentalDays(Integer rentalDays) {
		this.rentalDays = rentalDays;
	}
	public Date getCheckoutDate() {
		return checkoutDate;
	}
	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Integer getChargeDays() {
		return chargeDays;
	}
	public void setChargeDays(Integer chargeDays) {
		this.chargeDays = chargeDays;
	}
	public Double getPreDiscountCharge() {
		return preDiscountCharge;
	}
	public void setPreDiscountCharge(Double preDiscountCharge) {
		this.preDiscountCharge = preDiscountCharge;
	}
	public Double getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(Double discountPercent) {
		this.discountPercent = discountPercent;
	}
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public Double getFinalCharge() {
		return finalCharge;
	}
	public void setFinalCharge(Double finalCharge) {
		this.finalCharge = finalCharge;
	} 
	
}
