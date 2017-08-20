package com.bluebrand.fieldium.core.parser;

import com.bluebrand.network.TradinosParser;

import org.json.JSONException;

public class EmptyParser implements TradinosParser<String> {


	@Override
	public String Parse(String jsonText) throws JSONException {
		String message ="";
		return message ; 
	}

}
