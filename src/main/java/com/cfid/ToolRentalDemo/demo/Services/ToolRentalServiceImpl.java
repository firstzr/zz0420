package com.cfid.ToolRentalDemo.demo.Services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfid.ToolRentalDemo.demo.Exceptions.ToolRentalException;
import com.cfid.ToolRentalDemo.demo.Models.RentalAgreement;
import com.cfid.ToolRentalDemo.demo.Models.Tool;
import com.cfid.ToolRentalDemo.demo.Repos.ToolRentalRepo;
import com.cfid.ToolRentalDemo.demo.Utils.ToolRentalDateCountUtil;
import com.cfid.ToolRentalDemo.demo.Utils.ToolRentalValidator;

@Service
public class ToolRentalServiceImpl implements ToolRentalService {

	private static final Logger logger = LoggerFactory.getLogger(ToolRentalServiceImpl.class);

	@Autowired
	ToolRentalRepo toolRentalRepo;

	/**
	 * Major Service process checkout Logic
	 * 
	 * @param toolCode    - toolCode customer want to rent
	 * @param date        - date when checkout performed
	 * @param rentalDays- days customer wants to keep the tools for
	 * @param discount    - discount amount clerk wants to apply before final
	 *                    checkout
	 * @throws ToolRentalException when input beyond expectation
	 * 
	 * @return RentalAgreement
	 * 
	 **/
	@Override
	public RentalAgreement checkout(String toolCode, LocalDate checkoutDate, Integer rentalDays, Integer discount)
			throws ToolRentalException {

		logger.info("checking out for {}, on {} for {} days with discount {} % off", toolCode, checkoutDate, rentalDays,
				discount);
		// Validation
		ToolRentalValidator toolRentalValidator = new ToolRentalValidator();
		toolRentalValidator.validateDiscount(discount);
		toolRentalValidator.validateRentalDayCount(rentalDays);

		// Major Logic
		Tool tool = toolRentalRepo.getToolbyToolCode(toolCode);
		logger.info("Tool profile -> {}", tool.toString());
		
		Integer chargedDays =calculateChargeDays(tool, checkoutDate, rentalDays);
		logger.info("calculated Charge Days -> {} ", chargedDays);
		
		RentalAgreement rentalAgreement = buildRentalAgreement(tool,checkoutDate, rentalDays, discount,chargedDays);
		
		//print per requirement
		rentalAgreement.printRentalAgreement();
		
		return rentalAgreement;
	}

	private RentalAgreement buildRentalAgreement(Tool tool, LocalDate checkoutDate, Integer rentalDays,
			Integer discount, Integer chargedDays) {
		
			BigDecimal preDiscountCharge 	= tool.getDailyCharge().multiply(new BigDecimal(chargedDays));
			BigDecimal discountAmount		= preDiscountCharge.multiply(new BigDecimal(discount/100.0)).setScale(2, RoundingMode.HALF_UP);
			BigDecimal finalCharge			= preDiscountCharge.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
		RentalAgreement rentalAgreement = new RentalAgreement(
				tool, 
				rentalDays, 
				checkoutDate, 
				checkoutDate.plusDays(rentalDays-1), 
				chargedDays, 
				preDiscountCharge, 
				discount, 
				discountAmount, 
				finalCharge);
		
		return rentalAgreement;
	}

	private Integer calculateChargeDays(Tool tool, LocalDate checkoutDate, Integer rentalDays) {

		ToolRentalDateCountUtil toolRentalDateCountUtil = new ToolRentalDateCountUtil();

		Integer chargeDays = rentalDays;
		Boolean weekendCharge = tool.getWeekendCharge();
		Boolean holidayCharge = tool.getHolidayCharge();

		LocalDate dueDate = checkoutDate.plusDays(rentalDays-1);

		// 4 cases ignore weekday charge

		// case 1 : weekend t holiday t
		if (weekendCharge && holidayCharge) {
			logger.info("no free days applied");
			return chargeDays;
		}

		// case 2: weekend t holiday f
		else if (weekendCharge && !holidayCharge) {
			logger.info("free holiday charge applied");

			Map<String, List<LocalDate>> holidayMap = toolRentalDateCountUtil.holidaysDuringTime(checkoutDate, dueDate);

			// stream that returns total element count
			if (!holidayMap.isEmpty()) {
				chargeDays = rentalDays - holidayMap.values().stream().mapToInt(List::size).sum();
			}

			return chargeDays;
		}

		// case 3: weekend f holiday t
		else if (!weekendCharge && holidayCharge) {
			logger.info("free weekend charge applied");
			return rentalDays - toolRentalDateCountUtil.weekendsDuringTime(checkoutDate, dueDate);
		}

		// case 4: weekend f holiday f, holiday observation only applies to this case
		else {
			logger.info("All free days applied");
			chargeDays = rentalDays - toolRentalDateCountUtil.weekendsDuringTime(checkoutDate, dueDate);
			
			Map<String, List<LocalDate>> holidayMap = toolRentalDateCountUtil.holidaysDuringTime(checkoutDate, dueDate);

			if (!holidayMap.isEmpty()) {
				if (holidayMap.get("laborDays") != null) {
					chargeDays = chargeDays - holidayMap.get("laborDays").size();
				}
				// July 4th observation adjustment
				if (holidayMap.get("independenceDays") != null) {
					for (LocalDate i : holidayMap.get("independenceDays")) {
						if (!DayOfWeek.SATURDAY.equals(i.getDayOfWeek())
								&& !DayOfWeek.SUNDAY.equals(i.getDayOfWeek())) {
							chargeDays -= 1;
						} else {
							
							if (DayOfWeek.SATURDAY.equals(i.getDayOfWeek()) && !i.minusDays(1).isBefore(checkoutDate)) {
								chargeDays -=1;
							}
							if (DayOfWeek.SUNDAY.equals(i.getDayOfWeek()) && !i.plusDays(1).isAfter(dueDate)) {
								chargeDays -=1;
							}
						}
					}
				}
			}
			return chargeDays;
		}

	}

}
