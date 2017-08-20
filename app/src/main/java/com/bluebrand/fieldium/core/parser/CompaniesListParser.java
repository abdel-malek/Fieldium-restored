package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.Company;
import com.bluebrand.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by b.srour on 6/27/2016.
 */
public class CompaniesListParser implements TradinosParser<ArrayList<Company>> {
    @Override
    public ArrayList<Company> Parse(String data) throws JSONException {

        CompanyParser companyParser = new CompanyParser();
        ArrayList<Company> companies =new ArrayList<>();

        JSONArray companyArray = new JSONArray(data);
        for (int i = 0; i < companyArray.length(); i++) {
            companies.add(companyParser.Parse(companyArray.getString(i)));
        }
        return companies;
    }
}
