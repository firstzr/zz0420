package com.cfid.ToolRentalDemo.demo.Services;

import java.time.LocalDate;

import com.cfid.ToolRentalDemo.demo.Exceptions.ToolRentalException;
import com.cfid.ToolRentalDemo.demo.Models.RentalAgreement;

public interface  ToolRentalService {
	public abstract RentalAgreement checkout(String toolCode, LocalDate date, Integer rentalDays, Integer discount) throws ToolRentalException;
}
