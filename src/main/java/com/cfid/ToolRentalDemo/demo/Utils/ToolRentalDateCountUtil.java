package com.cfid.ToolRentalDemo.demo.Utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ToolRentalDateCountUtil {
	
	/**
	 * @return number of weekend days of given time period
	 * 
	 * **/
	public Integer weekendsDuringTime(LocalDate checkoutDate, LocalDate dueDate) {
		int weekendCount = 0;
		for (LocalDate i = checkoutDate; i.isBefore(dueDate) || i.isEqual(dueDate); i = i.plusDays(1)) {
			if (i.getDayOfWeek().equals(DayOfWeek.SATURDAY) || i.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
				weekendCount++;
			}
		}
		return weekendCount;
	}

	/**
	 * 	@return a Map that contains every holiday Date that is valid for given period
	 * 
	 *  total number of holidays of given date can be calculate from sum up all elements in map.
	 * 
	 *  Only consider independenceDay && laborDay
	 * 
	 **/
	public Map<String, List<LocalDate>> holidaysDuringTime(LocalDate checkoutDate, LocalDate dueDate) {

		Map<String, List <LocalDate>> holidayMap= new HashMap<>();
		
		List<LocalDate> laborDays 		= new ArrayList<>(); 
		List<LocalDate> independenceDays = new ArrayList<>(); 
		
		int howManyYearsNeedToBeChecked = dueDate.getYear() - checkoutDate.getYear() + 1;
		
		for (int i = 0; i < howManyYearsNeedToBeChecked; i++) {

			LocalDate laborDay = getLaborDay(checkoutDate.getYear() + i);
			if ((laborDay.isAfter(checkoutDate) || laborDay.isEqual(checkoutDate))
					&& (laborDay.isBefore(dueDate) || laborDay.isEqual(dueDate))) {
				laborDays.add(laborDay);
			}
			LocalDate independenceDay = LocalDate.of(checkoutDate.getYear() + i, 7, 4);
			if ((independenceDay.isAfter(checkoutDate) || independenceDay.isEqual(checkoutDate))
					&& (independenceDay.isBefore(dueDate) || independenceDay.isEqual(dueDate))) {
				independenceDays.add(independenceDay);
			}

		}
		
		if(!laborDays.isEmpty()) holidayMap.put("laborDays", laborDays);
		if(!independenceDays.isEmpty()) holidayMap.put("independenceDays", independenceDays);
		
		
		return holidayMap;
	}

	/**
	 * @retun LaborDay Date of given year. Can be move into Util
	 * 
	 **/
	public LocalDate getLaborDay(int year) {
		LocalDate date = LocalDate.of(year, 9, 1);
		while (!date.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
			date = date.plusDays(1);
		}
		return date;
	}
}
