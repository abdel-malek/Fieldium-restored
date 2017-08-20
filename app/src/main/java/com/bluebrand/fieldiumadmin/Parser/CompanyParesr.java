package com.bluebrand.fieldiumadmin.Parser;

import com.tradinos.core.network.TradinosParser;

import org.json.JSONException;
import org.json.JSONObject;

import com.bluebrand.fieldiumadmin.Model.Company;
import com.bluebrand.fieldiumadmin.Model.Image;

/**
 * Created by r.desouki on 1/11/2017.
 */
public class CompanyParesr implements TradinosParser<Company> {

    @Override
    public Company Parse(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        return Parse(jsonObject);
    }


    public Company Parse(JSONObject jsonObject) throws JSONException {
        Company company = new Company();
        company.setID(jsonObject.getInt("company_id"));
        company.setName(jsonObject.getString("name"));
        company.setPhone(jsonObject.getString("phone"));
        company.setAddress(jsonObject.getString("address"));
        company.setDescription(jsonObject.getString("description"));
        company.setLongitude(jsonObject.getDouble("longitude"));
        company.setLatitude(jsonObject.getDouble("latitude"));
        company.setArea_id(jsonObject.getInt("area_id"));
        Image image=new Image();
        try {
            image.setUrl(jsonObject.getString("logo_url"));
        }catch (JSONException e){

        }

        try {
            image.setUrl(jsonObject.getString("image_url"));
        }catch (JSONException e){

        }
        image.setName(jsonObject.getString("logo"));
        company.setLogo(image);
        image=new Image();

        image.setName(jsonObject.getString("image"));
        company.setCover(image);
        return company;
    }
}