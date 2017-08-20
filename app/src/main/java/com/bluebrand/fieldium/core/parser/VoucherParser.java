package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.Address;
import com.bluebrand.fieldium.core.model.Company;
import com.bluebrand.fieldium.core.model.Field;
import com.bluebrand.fieldium.core.model.Game;
import com.bluebrand.fieldium.core.model.Voucher;
import com.bluebrand.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Farah Etmeh Etmeh on 4/15/16.
 */
public class VoucherParser implements TradinosParser<Voucher> {

    @Override
    public Voucher Parse(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        Voucher voucher = new Voucher();
        voucher.setValid(jsonObject.getInt("valid"));
        if (jsonObject.getInt("valid") == 1) {

            voucher.setId(jsonObject.getInt("voucher_id"));
            voucher.setCode(jsonObject.getString("voucher"));
            voucher.setType(jsonObject.optInt("type"));
            voucher.setFrom_hour(jsonObject.optString("from_hour"));
            voucher.setTo_hour(jsonObject.optString("to_hour"));
            voucher.setStart_date(formattedDate(jsonObject.optString("start_date")));
            voucher.setExpiry_date(formattedDate(jsonObject.optString("expiry_date")));
            voucher.setDescription(jsonObject.optString("description"));
            voucher.setValue(jsonObject.optInt("value"));
            ArrayList<Company> companies = new ArrayList<>();
            JSONArray companiesArray = jsonObject.optJSONArray("companies");
            if (companiesArray != null)
                for (int i = 0; i < companiesArray.length(); i++) {
                    Company company = new Company();
                    company.setId(new JSONObject(companiesArray.getString(i)).getInt("company_id"));
                    company.setName(new JSONObject(companiesArray.getString(i)).getString("company_name"));
                    companies.add(company);
                }
            voucher.setCompanies(companies);


            ArrayList<Game> games = new ArrayList<>();
            JSONArray gamesArray = jsonObject.optJSONArray("games");
            if (gamesArray != null)
                for (int i = 0; i < gamesArray.length(); i++) {
                    Game game = new Game();
                    game.setId(new JSONObject(gamesArray.getString(i)).getInt("game_type_id"));
                    game.setName(new JSONObject(gamesArray.getString(i)).getString("game_name"));
                    game.setImageUrl(new JSONObject(gamesArray.getString(i)).getString("image_url"));
                    games.add(game);
                }
            voucher.setGames(games);
        }
        voucher.setMessage(jsonObject.optString("message"));
        return voucher;
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
