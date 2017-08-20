package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.Address;
import com.bluebrand.fieldium.core.model.Company;
import com.bluebrand.network.TradinosParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

/**
 * Created by Farah Etmeh Etmeh on 4/15/16.
 */
public class CompanyParser implements TradinosParser<Company> {

    @Override
    public Company Parse(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        Company company = new Company();
        company.setId(jsonObject.getInt("company_id"));
        company.setName(jsonObject.getString("name"));
        company.setDescription(jsonObject.optString("description"));
        company.setImageURL(jsonObject.optString("image_url"));
        company.setLogoURL(jsonObject.optString("logo_url"));
        company.setFieldCount(jsonObject.optInt("fields_number"));

        try {
            company.setPriceStartFrom(new BigDecimal(jsonObject.optString("starts_from")));
        } catch (Exception e) {company.setPriceStartFrom(new BigDecimal(0));

        }
        Address address = new Address();
        address.setName(jsonObject.optString("address"));
        address.setLongitude(jsonObject.optDouble("longitude"));
        address.setLatitude(jsonObject.optDouble("latitude"));
        company.setAddress(address);

        return company;
    }
}
