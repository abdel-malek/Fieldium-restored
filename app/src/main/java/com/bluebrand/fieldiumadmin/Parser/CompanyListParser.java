package com.bluebrand.fieldiumadmin.Parser;

import com.bluebrand.fieldiumadmin.Model.Company;
import com.bluebrand.fieldiumadmin.Model.Field;
import com.tradinos.core.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by r.desouki on 12/15/2016.
 */
public class CompanyListParser implements TradinosParser<List<Company>> {

    public List<Company> Parse(String jsonText) throws JSONException {
        List<Company> companies = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonText);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            companies.add(new CompanyParesr().Parse(jsonObject));
        }
        return companies ;
    }
}