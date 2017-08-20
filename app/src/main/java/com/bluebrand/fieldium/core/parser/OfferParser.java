package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.Company;
import com.bluebrand.fieldium.core.model.Game;
import com.bluebrand.fieldium.core.model.Offer;
import com.bluebrand.fieldium.core.model.Voucher;
import com.bluebrand.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Farah Etmeh Etmeh on 4/15/16.
 */
public class OfferParser implements TradinosParser<Offer> {

    @Override
    public Offer Parse(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        Offer offer = new Offer();
        offer.setValid(jsonObject.getInt("valid"));
        if (jsonObject.getInt("valid") == 1) {
            offer.setId(jsonObject.getInt("offer_id"));
//            voucher.setCode(jsonObject.getString("voucher"));
            offer.setType(jsonObject.optInt("voucher_type"));
            offer.setFrom_hour(jsonObject.optString("voucher_from_hour"));
            offer.setTo_hour(jsonObject.optString("voucher_to_hour"));
            offer.setStart_date(formattedDate(jsonObject.optString("start_date")));
            offer.setExpiry_date(formattedDate(jsonObject.optString("expiry_date")));
            offer.setDescription(jsonObject.optString("description_en"));
            offer.setValue(jsonObject.optInt("voucher_value"));
            offer.setSet_of_minutes(jsonObject.optInt("set_of_minutes"));
            offer.setBooked_hours(jsonObject.optInt("booked_hours"));
            ArrayList<Company> companies = new ArrayList<>();
       /*     JSONArray companiesArray = jsonObject.optJSONArray("companies");
            if (companiesArray != null)
                for (int i = 0; i < companiesArray.length(); i++) {
                    Company company = new Company();
                    company.setId(new JSONObject(companiesArray.getString(i)).getInt("company_id"));
                    company.setName(new JSONObject(companiesArray.getString(i)).getString("company_name"));
                    companies.add(company);
                }*/
            offer.setCompanies(companies);


            ArrayList<Game> games = new ArrayList<>();
     /*       JSONArray gamesArray = jsonObject.optJSONArray("games");
            if (gamesArray != null)
                for (int i = 0; i < gamesArray.length(); i++) {
                    Game game = new Game();
                    game.setId(new JSONObject(gamesArray.getString(i)).getInt("game_id"));
                    game.setName(new JSONObject(gamesArray.getString(i)).getString("game_name"));
                    games.add(game);
                }*/
            offer.setGames(games);
        }
        offer.setMessage(jsonObject.optString("message"));
        return offer;
    }

    public String formattedDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date d = null;
        try {
            d = formatter.parse(date);//catch exception
            return formatter.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }
}
