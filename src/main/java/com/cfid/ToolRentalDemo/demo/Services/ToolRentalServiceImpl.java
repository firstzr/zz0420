package com.cfid.ToolRentalDemo.demo.Services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfid.ToolRentalDemo.demo.Models.RentalAgreement;
import com.cfid.ToolRentalDemo.demo.Repos.ToolRentalRepo;

@Service
public class ToolRentalServiceImpl implements ToolRentalService {

	@Autowired
	ToolRentalRepo toolRentalRepo;

	@Override
	public RentalAgreement checkout(String toolCode, LocalDate date, Integer rentalDays, Integer discount) {
		// TODO Auto-generated method stub
		return null;
	}


}
