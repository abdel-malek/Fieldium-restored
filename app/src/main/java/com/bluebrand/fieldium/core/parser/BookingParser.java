package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.Address;
import com.bluebrand.fieldium.core.model.Booking;
import com.bluebrand.fieldium.core.model.BookingStatus;
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
import java.util.Locale;

/**
 * Created by Player on 1/8/2017.
 */
public class BookingParser implements TradinosParser<Booking> {
    @Override
    public Booking Parse(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        Booking booking = new Booking();
        booking.setId(jsonObject.optInt("booking_id"));
        booking.setState(BookingStatus.getById(jsonObject.optInt("state_id")));
        booking.setDate(jsonObject.optString("date"));
        booking.setDuration(jsonObject.optString("duration"));
//        String total = jsonObject.optString("total");
//        if (total != null&&!total.equals(""))
        try {
            booking.setTotal(new BigDecimal(jsonObject.optString("total")));
        } catch (Exception e) {
            booking.setTotal(new BigDecimal(0));
        }

        booking.setCancelReason(jsonObject.optString("cancellation_reason"));
        booking.setNote(jsonObject.optString("notes"));
        booking.setStartTime(convertTime(jsonObject.optString("start")));
        Field field = new Field();
        Company company = new Company();
        Address company_address = new Address();
        company_address.setName(jsonObject.optString("address"));
        field.setId(jsonObject.optInt("field_id"));
        field.setName(jsonObject.optString("field_name"));
        company.setAddress(company_address);
        company.setId(jsonObject.optInt("company_id"));
        company.setLogoURL(jsonObject.optString("logo_url"));
        company.setName(jsonObject.optString("company_name"));

        JSONArray imagesJsonArray = jsonObject.optJSONArray("images");
        if (imagesJsonArray != null)
            if (imagesJsonArray != null && imagesJsonArray.length() != 0) {
                ImageParser imageParser = new ImageParser();
                company.setImageURL(imageParser.Parse(imagesJsonArray.getJSONObject(0)).getUrl());
            }
        field.setCompany(company);
        try {
            field.setHourRate(new BigDecimal(jsonObject.optString("hour_rate")));
        }catch (Exception e){
            field.setHourRate(new BigDecimal(0));
        }
        booking.setField(field);
        Game game = new Game();
        game.setName(jsonObject.optString("game_type_name"));
        game.setId(jsonObject.optInt("game_type_id"));
        booking.setGame(game);
        String hasVoucher=jsonObject.optString("voucher");
        if (hasVoucher != null &&!hasVoucher.equals("")) {
            Voucher voucher = new Voucher();
            voucher.setValue(jsonObject.optInt("voucher_value"));
            voucher.setType(jsonObject.optInt("voucher_type"));
            booking.setVoucher(voucher);
            booking.setSubTotal(jsonObject.optString("subtotal"));
        }

        booking.setArCurrency(jsonObject.optString("ar_currency_symbol",""));
        booking.setEnCurrency(jsonObject.optString("en_currency_symbol",""));
        return booking;
    }

    public String convertTime(String time) {
        String formattedTime = "";
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        SimpleDateFormat formatter1 = new SimpleDateFormat("kk:mm", Locale.ENGLISH);
        try {
            formattedTime = formatter.format(formatter1.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedTime;
    }
}
