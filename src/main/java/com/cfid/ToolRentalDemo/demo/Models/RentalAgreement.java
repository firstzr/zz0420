package com.cfid.ToolRentalDemo.demo.Models;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RentalAgreement {

	private static final Logger logger = LoggerFactory.getLogger(RentalAgreement.class);

	Tool tool;
	Integer rentalDays;
	LocalDate checkoutDate;
	LocalDate dueDate;
	Integer chargeDays;
	BigDecimal preDiscountCharge;
	Integer discountPercent;
	BigDecimal discountAmount;
	BigDecimal finalCharge;

	public void printRentalAgreement() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
		NumberFormat percentFormat = NumberFormat.getPercentInstance();
		logger.info("---- Print RentalAgreement per requirement----");
		logger.info("Tool code: " + this.tool.getToolCode());
		logger.info("Tool type: " + this.tool.getToolType());
		logger.info("Tool brand: " + this.tool.getBrand());
		logger.info("Rental days: " + this.rentalDays);
		logger.info("Check out date: " + this.checkoutDate.format(dateFormatter));
		logger.info("Due date: " + this.dueDate.format(dateFormatter));
		logger.info("Daily rental charge: " + currencyFormatter.format(this.tool.getDailyCharge()));
		logger.info("Charge days: " + this.chargeDays);
		logger.info("Pre-discount charge: " + currencyFormatter.format(this.preDiscountCharge));
		logger.info("Discount percent: " + percentFormat.format(this.discountPercent / 100.0));
		logger.info("Discount amount: " + currencyFormatter.format(this.discountAmount));
		logger.info("Final charge: " + currencyFormatter.format(this.finalCharge));
	}

	public RentalAgreement(Tool tool, Integer rentalDays, LocalDate checkoutDate, LocalDate dueDate, Integer chargeDays,
			BigDecimal preDiscountCharge, Integer discountPercent, BigDecimal discountAmount, BigDecimal finalCharge) {
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

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Integer getChargeDays() {
		return chargeDays;
	}

	public void setChargeDays(Integer chargeDays) {
		this.chargeDays = chargeDays;
	}

	public BigDecimal getPreDiscountCharge() {
		return preDiscountCharge;
	}

	public void setPreDiscountCharge(BigDecimal preDiscountCharge) {
		this.preDiscountCharge = preDiscountCharge;
	}

	public Integer getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Integer discountPercent) {
		this.discountPercent = discountPercent;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getFinalCharge() {
		return finalCharge;
	}

	public void setFinalCharge(BigDecimal finalCharge) {
		this.finalCharge = finalCharge;
	}

	public static Logger getLogger() {
		return logger;
	}

}
