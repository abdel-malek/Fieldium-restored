package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.Booking;
import com.bluebrand.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Player on 1/8/2017.
 */
public class BookingListParser implements TradinosParser<List<Booking>> {
    public List<Booking> Parse(String data) throws JSONException {
        JSONArray bookListJson = new JSONArray(data);
        List<Booking> bookingsList = new ArrayList<>() ;
        BookingParser bookingParser = new BookingParser() ;

        for (int i =0 ;i < bookListJson.length(); i++) {
            bookingsList.add(bookingParser.Parse(bookListJson.getString(i)));
        }

        return bookingsList;
    }
}
