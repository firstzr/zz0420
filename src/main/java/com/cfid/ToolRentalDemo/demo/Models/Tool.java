package com.cfid.ToolRentalDemo.demo.Models;

import java.math.BigDecimal;

public class Tool {
	
	private String toolCode ; 
	private String toolType ;
	private String brand;
	private BigDecimal dailyCharge;
	private Boolean weekdayCharge;
	private Boolean weekendCharge;
	private Boolean holidayCharge;
	
	public Tool(String toolCode, String toolType, String brand, BigDecimal dailyCharge, Boolean weekdayCharge,
			Boolean weekendCharge, Boolean holidayCharge) {
		super();
		this.toolCode = toolCode;
		this.toolType = toolType;
		this.brand = brand;
		this.dailyCharge = dailyCharge;
		this.weekdayCharge = weekdayCharge;
		this.weekendCharge = weekendCharge;
		this.holidayCharge = holidayCharge;
	}
	
	public String getToolCode() {
		return toolCode;
	}
	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}
	public String getToolType() {
		return toolType;
	}
	public void setToolType(String toolType) {
		this.toolType = toolType;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public BigDecimal getDailyCharge() {
		return dailyCharge;
	}
	public void setDailyCharge(BigDecimal dailyCharge) {
		this.dailyCharge = dailyCharge;
	}
	public Boolean getWeekdayCharge() {
		return weekdayCharge;
	}
	public void setWeekdayCharge(Boolean weekdayCharge) {
		this.weekdayCharge = weekdayCharge;
	}
	public Boolean getWeekendCharge() {
		return weekendCharge;
	}
	public void setWeekendCharge(Boolean weekendCharge) {
		this.weekendCharge = weekendCharge;
	}
	public Boolean getHolidayCharge() {
		return holidayCharge;
	}
	public void setHolidayCharge(Boolean holidayCharge) {
		this.holidayCharge = holidayCharge;
	}

	@Override
	public String toString() {
		return "Tool [toolCode=" + toolCode + ", toolType=" + toolType + ", brand=" + brand + ", dailyCharge="
				+ dailyCharge + ", weekdayCharge=" + weekdayCharge + ", weekendCharge=" + weekendCharge
				+ ", holidayCharge=" + holidayCharge + "]";
	}
	
	
		
	
	
	
}
