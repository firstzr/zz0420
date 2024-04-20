package com.cfid.ToolRentalDemo.demo.Repos;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cfid.ToolRentalDemo.demo.Models.Tool;

@Repository
public class ToolRentalRepo {
	
	//use as a database source. 
	public static Map<String, Tool> toolMap = new HashMap<String, Tool>() {
		
		private static final long serialVersionUID = 1L;

		{
        put("CHNS", new Tool("CHNS", "Chainsaw"		, "Stihl"	, 1.49, true, false	, true));
        put("LADW", new Tool("LADW", "Ladder"		, "Werner"	, 1.99, true, true	, false));
        put("JAKD", new Tool("JAKD", "Jackhammer"	, "DeWalt"	, 2.99, true, false	, false));
        put("JAKR", new Tool("JAKR", "Jackhammer"	, "Ridgid"	, 2.99, true, false	, false));
    	}
	}; 
	
	public Tool getToolbyToolCode(String toolCode) {
		return toolMap.get(toolCode);
	}
	
	
	
	
	
	
}
