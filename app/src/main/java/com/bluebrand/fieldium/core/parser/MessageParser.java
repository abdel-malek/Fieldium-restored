package com.bluebrand.fieldium.core.parser;

import com.bluebrand.network.TradinosParser;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageParser implements TradinosParser<String> {


	@Override
	public String Parse(String jsonText) throws JSONException {
		String message =new JSONObject(jsonText).optString("message","");
		return message ; 
	}

}
