package com.cfid.ToolRentalDemo.demo.Repos;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cfid.ToolRentalDemo.demo.Models.Tool;

@Repository
public class ToolRentalRepo {
	
	//use as a database source. 
	private final static Map<String, Tool> toolMap = new HashMap<String, Tool>() {
		
		private static final long serialVersionUID = 1L;

		{
        put("CHNS", new Tool("CHNS", "Chainsaw"		, "Stihl"	,  new BigDecimal(1.49), true, false, true));
        put("LADW", new Tool("LADW", "Ladder"		, "Werner"	,  new BigDecimal(1.99), true, true	, false));
        put("JAKD", new Tool("JAKD", "Jackhammer"	, "DeWalt"	,  new BigDecimal(2.99), true, false, false));
        put("JAKR", new Tool("JAKR", "Jackhammer"	, "Ridgid"	,  new BigDecimal(2.99), true, false, false));
    	}
	}; 
	
	public Tool getToolbyToolCode(String toolCode) {
		return toolMap.get(toolCode);
	}
	
	
	
	
	
	
}
