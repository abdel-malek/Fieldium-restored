package com.bluebrand.fieldiumadmin.Parser;

import com.tradinos.core.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.bluebrand.fieldiumadmin.Model.Reservation;

public class ReservationListParser implements TradinosParser<List<Reservation>> {
	
	public List<Reservation> Parse(String jsonText) throws JSONException {
		List<Reservation> bills = new ArrayList<>();
		JSONArray jsonArray = new JSONArray(jsonText);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i); 
			bills.add(new  ReservationParser().Parse(jsonObject));
		}
		return bills ; 
	}
}