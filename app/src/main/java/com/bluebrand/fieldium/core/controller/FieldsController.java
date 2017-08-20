package com.bluebrand.fieldium.core.controller;

import android.content.Context;

import com.bluebrand.fieldium.core.API.APIModel;
import com.bluebrand.fieldium.core.API.URLBuilder;
import com.bluebrand.fieldium.core.model.Availability;
import com.bluebrand.fieldium.core.model.AvailableTime;
import com.bluebrand.fieldium.core.model.Booking;
import com.bluebrand.fieldium.core.model.Company;
import com.bluebrand.fieldium.core.model.Field;
import com.bluebrand.fieldium.core.parser.AvailableTimeParser;
import com.bluebrand.fieldium.core.parser.CompanyParser;
import com.bluebrand.fieldium.core.parser.FieldListParser;
import com.bluebrand.fieldium.core.parser.FieldParser;
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
 * Created by Farah Etmeh on 4/15/16.
 */
public class FieldsController extends ParentController {


    public static FieldsController getInstance(Controller controller) {
        return new FieldsController(controller);
    }


    public FieldsController(Context context, FaildCallback faildCallback) {
        super(context, faildCallback);
    }

    public FieldsController(Controller controller) {
        super(controller.getmContext(), controller.getmFaildCallback());
    }

    public void ShowField(int fieldId, SuccessCallback<Field> successCallback) {
        String url = new URLBuilder(APIModel.fields, "show").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new FieldParser(), successCallback, getmFaildCallback());
        request.addParameter("field_id", String.valueOf(fieldId));
        addCountryToHeader(request);
        request.Call();
    }

    public void LoadFields(int companyId, int voucher_id, double longitude, double latitude, SuccessCallback<List<Field>> successCallback) {
        String url = new URLBuilder(APIModel.fields, "get_by_company").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new FieldListParser(), successCallback, getmFaildCallback());
        request.addParameter("company_id", String.valueOf(companyId));
        request.addParameter("longitude", String.valueOf(longitude));
        request.addParameter("latitude", String.valueOf(latitude));
        if (voucher_id != 0)
            request.addParameter("voucher_id", String.valueOf(voucher_id));
        addCountryToHeader(request);
        request.Call();
    }

    public void LoadFieldsWithTiming(int companyId, double longitude, double latitude, int game_id, Booking booking, SuccessCallback<List<Field>> successCallback) {
        String url = new URLBuilder(APIModel.fields, "get_by_company_with_timing").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new FieldListParser(), successCallback, getmFaildCallback());
        request.addParameter("company_id", String.valueOf(companyId));
        request.addParameter("longitude", String.valueOf(longitude));
        request.addParameter("latitude", String.valueOf(latitude));
        request.addParameter("game_type", String.valueOf(game_id));
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
        addCountryToHeader(request);
        request.Call();
    }

    public void CheckAvailability(int fieldId, String date, int game_id, SuccessCallback<Availability> successCallback) {
        String url = new URLBuilder(APIModel.fields, "check_availability").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new AvailableTimeParser(), successCallback, getmFaildCallback());
        request.addParameter("field_id", String.valueOf(fieldId));
        request.addParameter("date", date);
        request.addParameter("game_type", String.valueOf(game_id));
        addCountryToHeader(request);
        request.Call();
    }

    public void LoadFeaturedPlaces(SuccessCallback<List<Field>> successCallback) {
        String url = new URLBuilder(APIModel.fields, "get_featured_places").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new FieldListParser(), successCallback, getmFaildCallback());
        request.Call();
    }

    public void LoadCatalog(SuccessCallback<Company> successCallback) {
        String url = new URLBuilder(APIModel.categories, "load_catalog").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new CompanyParser(), successCallback, getmFaildCallback());
        request.Call();
    }


}

