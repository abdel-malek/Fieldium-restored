package com.bluebrand.fieldium.core.controller;

import android.content.Context;
import android.net.Uri;

import com.bluebrand.fieldium.core.API.APIModel;
import com.bluebrand.fieldium.core.API.URLBuilder;
import com.bluebrand.fieldium.core.model.City;
import com.bluebrand.fieldium.core.model.Booking;
import com.bluebrand.fieldium.core.model.Company;
import com.bluebrand.fieldium.core.model.Country;
import com.bluebrand.fieldium.core.model.Game;
import com.bluebrand.fieldium.core.model.HomeScreenModel;
import com.bluebrand.fieldium.core.model.Notification;
import com.bluebrand.fieldium.core.model.Voucher;
import com.bluebrand.fieldium.core.parser.AreasParser;
import com.bluebrand.fieldium.core.parser.BookingListParser;
import com.bluebrand.fieldium.core.parser.BookingParser;
import com.bluebrand.fieldium.core.parser.CompaniesListParser;
import com.bluebrand.fieldium.core.parser.CountriesParser;
import com.bluebrand.fieldium.core.parser.GamesListParser;
import com.bluebrand.fieldium.core.parser.HomeScreenBookingListParser;
import com.bluebrand.fieldium.core.parser.NotificationsListParser;
import com.bluebrand.fieldium.core.parser.VoucherParser;
import com.bluebrand.fieldium.core.parser.VouchersListParser;
import com.bluebrand.network.Controller;
import com.bluebrand.network.FaildCallback;
import com.bluebrand.network.RequestMethod;
import com.bluebrand.network.SuccessCallback;
import com.bluebrand.network.TradinosRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created on 4/15/16.
 */
public class BookingController extends ParentController {


    public static BookingController getInstance(Controller controller) {
        return new BookingController(controller);
    }


    public BookingController(Context context, FaildCallback faildCallback) {
        super(context, faildCallback);
    }

    public BookingController(Controller controller) {
        super(controller.getmContext(), controller.getmFaildCallback());
    }

    public void createBook(Booking booking, SuccessCallback<Booking> successCallback) {
        String url = new URLBuilder(APIModel.bookings, "create").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new BookingParser(), successCallback, getmFaildCallback());
        request.addParameter("field_id", String.valueOf(booking.getField().getId()));
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        SimpleDateFormat formatter1 = new SimpleDateFormat("kk:mm:ss", Locale.ENGLISH);
        try {
            request.addParameter("start", formatter1.format(formatter.parse(booking.getStartTime())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        request.addParameter("start", order.getNote());
        request.addParameter("game_type", String.valueOf(booking.getGame().getId()));
        request.addParameter("duration", booking.getDuration());
        request.addParameter("date", booking.getDate());
        request.addParameter("notes", booking.getNote());
        if (booking.getVoucher().getCode() != null && !booking.getVoucher().getCode().equals(""))
            request.addParameter("voucher", booking.getVoucher().getCode());
        authenticationRequired(request);
        addCountryToHeader(request);
        request.Call();
    }

    public void getMyBookings(SuccessCallback<List<Booking>> successCallback) {
        String url = new URLBuilder(APIModel.bookings, "my_bookings").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new BookingListParser(), successCallback, getmFaildCallback());
        authenticationRequired(request);
        addCountryToHeader(request);
        request.Call();
    }

    public void getMyNotifications(String token,SuccessCallback<List<Notification>> successCallback) {
        String url = new URLBuilder(APIModel.notifications, "get_notifications").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new NotificationsListParser(), successCallback, getmFaildCallback());
        request.addParameter("token", token);
        authenticationRequired(request);
        addCountryToHeader(request);
        request.Call();
    }

    public void getMyVouchers(SuccessCallback<List<Voucher>> successCallback) {
        String url = new URLBuilder(APIModel.vouchers, "my_vouchers").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new VouchersListParser(), successCallback, getmFaildCallback());
        authenticationRequired(request);
        addCountryToHeader(request);
        request.Call();
    }

    public void checkVoucher(String voucher, int field_id, String date, String duration, String start,int gameType, SuccessCallback<Voucher> successCallback) {
        String url = new URLBuilder(APIModel.vouchers, "check_validity").getURL()/*"http://fieldium2.tradinos.com/index.php/vouchers/check_validity/format/json?voucher=test&field_id=59&date=2017-08-16&duration=60&start=08:30 PM&game_type=1"*/;
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new VoucherParser(), successCallback, getmFaildCallback());
        request.addParameter("voucher", voucher);
        request.addParameter("field_id", String.valueOf(field_id));
        request.addParameter("date", date);
        request.addParameter("duration", duration);
        request.addParameter("start", start);
        request.addParameter("game_type", String.valueOf(gameType));
        authenticationRequired(request);
        addCountryToHeader(request);
        request.Call();
    }


    public void getBookDetails(int bookingId, SuccessCallback<Booking> successCallback) {
        String url = new URLBuilder(APIModel.bookings, "show").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new BookingParser(), successCallback, getmFaildCallback());
        request.addParameter("booking_id", String.valueOf(bookingId));
        authenticationRequired(request);
        addCountryToHeader(request);
        request.Call();
    }

    public void getHomeScreenModel1(SuccessCallback<HomeScreenModel> successCallback) {
        String url = new URLBuilder(APIModel.bookings, "upcoming_and_last_bookings").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new HomeScreenBookingListParser(), successCallback, getmFaildCallback());
        authenticationRequired(request);
        request.Call();
    }
    public void getHomeScreenModel(SuccessCallback<HomeScreenModel> successCallback) {
        String url = new URLBuilder(APIModel.site, "home").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new HomeScreenBookingListParser(), successCallback, getmFaildCallback());
        authenticationRequired(request);
//        request.addParameter("phone",phone);
//        request.addParameter("phone",);
        request.addParameter("os","android");
        addCountryToHeader(request);
        request.Call();
    }

    public void getAreas(SuccessCallback<List<City>> successCallback) {
        String url = new URLBuilder(APIModel.areas, "get_all").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new AreasParser(), successCallback, getmFaildCallback());
        request.Call();
    }

    public void getCountries(SuccessCallback<List<Country>> successCallback) {
        String url = new URLBuilder(APIModel.countries, "get_all").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new CountriesParser(), successCallback, getmFaildCallback());
        request.Call();
    }

    public void getgames(SuccessCallback<List<Game>> successCallback) {
        String url = new URLBuilder(APIModel.games, "get_all").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new GamesListParser(), successCallback, getmFaildCallback());
        addCountryToHeader(request);
        request.Call();
    }

 /*   public void Search(int area_id, String query, int game_id, String token, SuccessCallback<ArrayList<Company>> successCallback) {
        String url = new URLBuilder(APIModel.searches, "search").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new CompaniesListParser(), successCallback, getmFaildCallback());
        if (area_id != 0)
            request.addParameter("area_id", String.valueOf(area_id));
        request.addParameter("name", query);
        if (game_id != 0)
            request.addParameter("game_type", String.valueOf(game_id));
        request.addParameter("timing", String.valueOf(0));
        request.addParameter("token", token);
        request.Call();
    }*/

    public void Search(int area_id, String query, int game_id, Booking booking/*, int country_id*/, String token, SuccessCallback<ArrayList<Company>> successCallback) {
        String url = new URLBuilder(APIModel.searches, "search").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new CompaniesListParser(), successCallback, getmFaildCallback());
        request.addParameter("area_id", String.valueOf(area_id));
        if (!query.equals("")) {
            request.addParameter("name", String.valueOf(Uri.encode(query, "UTF-8")));
        } else {
            request.addParameter("game_type", String.valueOf(game_id));
            request.addParameter("country_id", String.valueOf(game_id));
            if (booking.getDate() != null) {
                request.addParameter("date", booking.getDate());
                if (booking.getDuration() != null && booking.getStartTime() != null) {
                    SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
                    SimpleDateFormat formatter1 = new SimpleDateFormat("kk:mm:ss", Locale.ENGLISH);
                    try {
                        request.addParameter("start", formatter1.format(formatter.parse(booking.getStartTime())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    request.addParameter("duration", booking.getDuration());
                    request.addParameter("timing", String.valueOf(1));
                } else request.addParameter("timing", String.valueOf(2));
            } else request.addParameter("timing", String.valueOf(0));
        }
        if (!token.equals(""))
            request.addParameter("token", token);
        addCountryToHeader(request);
        request.Call();
    }

}

