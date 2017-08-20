package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.Address;
import com.bluebrand.fieldium.core.model.Amenity;
import com.bluebrand.fieldium.core.model.Company;
import com.bluebrand.fieldium.core.model.Field;
import com.bluebrand.fieldium.core.model.Game;
import com.bluebrand.fieldium.core.model.Image;
import com.bluebrand.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Farah Etmeh on 4/15/16.
 */
public class FieldParser implements TradinosParser<Field> {

    public Field Parse(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        Field field = new Field();
        field.setId(jsonObject.getInt("field_id"));
        field.setName(jsonObject.getString("name"));
        field.setPhone(jsonObject.getString("phone"));
        field.setHourRate(new BigDecimal(jsonObject.getString("hour_rate")));
        field.setOpenTime(jsonObject.getString("open_time"));
        field.setCloseTime(jsonObject.getString("close_time"));
        field.setDescription(jsonObject.getString("description"));
        field.setArea_x(jsonObject.optDouble("area_x"));
        field.setArea_y(jsonObject.optDouble("area_y"));
        field.setMaxCapacity(jsonObject.getInt("max_capacity"));

        Company company=new Company();
        company.setName(jsonObject.getString("company_name"));
        Address companyAddress=new Address();
        companyAddress.setLongitude(jsonObject.getDouble("longitude"));
        companyAddress.setLatitude(jsonObject.getDouble("latitude"));
        companyAddress.setName(jsonObject.optString("address"));
        company.setAddress(companyAddress);
        company.setLogoURL(jsonObject.optString("logo_url"));
        field.setCompany(company);

        JSONArray amenitiesJsonArray = jsonObject.optJSONArray("amenities");
        if (amenitiesJsonArray != null) {
            AmenityParser amenityParser = new AmenityParser();
            ArrayList<Amenity> amenity = new ArrayList<>();
            for (int i = 0; i < amenitiesJsonArray.length(); i++) {
                amenity.add(amenityParser.Parse(amenitiesJsonArray.getJSONObject(i)));
            }
            field.setAmenities(amenity);
        }

        JSONArray imagesJsonArray = jsonObject.optJSONArray("images");
        if (imagesJsonArray != null) {
            ImageParser imageParser = new ImageParser();
            ArrayList<Image> images = new ArrayList<>();
            for (int i = 0; i < imagesJsonArray.length(); i++) {
                images.add(imageParser.Parse(imagesJsonArray.getJSONObject(i)));
            }
            field.setImages(images);
        }
        JSONArray gamesJsonArray = jsonObject.optJSONArray("games");
        if (gamesJsonArray != null) {
            GameParser gameParser = new GameParser();
            ArrayList<Game> games = new ArrayList<>();
            for (int i = 0; i < gamesJsonArray.length(); i++) {
                games.add(gameParser.Parse(gamesJsonArray.getJSONObject(i)));
            }
            field.setGames(games);
        }
        return field;
    }

}
