package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.City;
import com.bluebrand.fieldium.core.model.Country;
import com.bluebrand.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Player on 12/22/2016.
 */
public class CountriesParser implements TradinosParser<ArrayList<Country>> {
    @Override
    public ArrayList<Country> Parse(String text) throws JSONException {
        JSONArray jsonArray = new JSONArray(text);
        return Parse(jsonArray);
    }

    public ArrayList<Country> Parse(JSONArray jsonarray) throws JSONException {
        ArrayList<Country> countries = new ArrayList<>();
        if (jsonarray != null) {
            for (int i = 0; i < jsonarray.length(); i++) {
                Country country = new Country();
                country.setId(jsonarray.getJSONObject(i).getInt("country_id"));
                country.setAr_name(jsonarray.getJSONObject(i).optString("ar_name"));
                country.setEn_name(jsonarray.getJSONObject(i).optString("en_name"));
                country.setArabicCurrency(jsonarray.getJSONObject(i).optString("ar_currency_symbol"));
                country.setEnglishCurrency(jsonarray.getJSONObject(i).optString("en_currency_symbol"));
                country.setDialingCode(jsonarray.getJSONObject(i).optString("dialing_code"));
                country.setImageUrl(jsonarray.getJSONObject(i).optString("image_url"));
                countries.add(country);
            }
        }
        return countries;
    }
}
