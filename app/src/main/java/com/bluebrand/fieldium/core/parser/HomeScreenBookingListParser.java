package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.City;
import com.bluebrand.fieldium.core.model.Booking;
import com.bluebrand.fieldium.core.model.Game;
import com.bluebrand.fieldium.core.model.HomeScreenModel;
import com.bluebrand.fieldium.core.model.Offer;
import com.bluebrand.fieldium.core.model.Voucher;
import com.bluebrand.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Player on 1/8/2017.
 */
public class HomeScreenBookingListParser implements TradinosParser<HomeScreenModel> {
    public HomeScreenModel Parse(String data) throws JSONException {
        JSONObject homeScreenDataJson = new JSONObject(data);

        JSONArray lastBookingListJsonArray = homeScreenDataJson.optJSONArray("last_bookings");
        JSONArray upcomingBookingJsonArray = homeScreenDataJson.optJSONArray("upcoming_booking");
        JSONArray areasJsonArray = homeScreenDataJson.getJSONArray("areas");
        JSONArray gamesJsonArray = homeScreenDataJson.getJSONArray("games");
        JSONArray offersJsonArray = homeScreenDataJson.getJSONArray("offers");
        JSONArray vouchersJsonArray = homeScreenDataJson.optJSONArray("vouchers");
        List<Booking> lastBookingsList = new ArrayList<>();
        List<Booking> upcomingBookingsList = new ArrayList<>();
        List<Game> gamesList = new ArrayList<>();
        List<City> areasList = new ArrayList<>();
        List<Voucher> vouchersList = new ArrayList<>();
        List<Offer> offersList = new ArrayList<>();
        BookingParser bookingParser = new BookingParser();

        if (lastBookingListJsonArray != null)
            for (int i = 0; i < lastBookingListJsonArray.length(); i++) {
                lastBookingsList.add(bookingParser.Parse(lastBookingListJsonArray.getString(i)));
            }
        if (upcomingBookingJsonArray != null)
            if (upcomingBookingJsonArray.length() != 0)
                upcomingBookingsList.add(bookingParser.Parse(upcomingBookingJsonArray.getString(0)));
//        for(int i=0;i<areasJsonArray.length();i++)
//        {
//            areasList.add()
//        }
        AreasParser areasParser = new AreasParser();
        areasList = areasParser.Parse(areasJsonArray);
        GamesListParser gamesListParser = new GamesListParser();
        gamesList = gamesListParser.Parse(gamesJsonArray.toString());

        if (vouchersJsonArray != null) {
            VouchersListParser vouchersListParser = new VouchersListParser();
            vouchersList = vouchersListParser.Parse(vouchersJsonArray.toString());
        }

        OffersListParser offersListParser = new OffersListParser();
        offersList = offersListParser.Parse(offersJsonArray.toString());

        HomeScreenModel homeScreenModel = new HomeScreenModel();
        homeScreenModel.setLastBookings(lastBookingsList);
        homeScreenModel.setUpcomingBookings(upcomingBookingsList);
        homeScreenModel.setCities(areasList);
        homeScreenModel.setGames(gamesList);
        homeScreenModel.setVouchers(vouchersList);
        homeScreenModel.setOfferes(offersList);
        return homeScreenModel;
    }
}
