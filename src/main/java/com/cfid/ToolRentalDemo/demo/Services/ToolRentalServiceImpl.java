package com.cfid.ToolRentalDemo.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfid.ToolRentalDemo.demo.Models.RentalAgreement;
import com.cfid.ToolRentalDemo.demo.Repos.ToolRentalRepo;

@Service
public class ToolRentalServiceImpl implements ToolRentalService {

	@Autowired
	ToolRentalRepo toolRentalRepo;
	
	@Override
	public RentalAgreement checkout() {
		// TODO checkout Implementation
		return null;
	}
}
