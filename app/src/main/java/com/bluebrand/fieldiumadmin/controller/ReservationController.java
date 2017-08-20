package com.bluebrand.fieldiumadmin.controller;

import android.content.Context;

import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.RequestMethod;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.core.network.TradinosRequest;

import java.util.List;
import java.util.Map;

import com.bluebrand.fieldiumadmin.API.APIModel;
import com.bluebrand.fieldiumadmin.API.URLBuilder;
import com.bluebrand.fieldiumadmin.Model.Reservation;
import com.bluebrand.fieldiumadmin.Parser.ReservationListParser;
import com.bluebrand.fieldiumadmin.Parser.ReservationParser;
import com.bluebrand.fieldiumadmin.Parser.StringParser;

/**
 * Created by r.desouki on 12/14/2016.
 */
public class ReservationController extends ParentController {
    public static ReservationController getInstance (Controller controller) {
        return new ReservationController(controller);
    }

    public ReservationController(Controller controller) {
        super(controller.getmContext(), controller.getmFaildCallback());
    }

    public ReservationController(Context context, FaildCallback faildCallback) {
        super(context, faildCallback);
    }

    public void getPendingReservations (SuccessCallback<List<Reservation>> successCallback) {
        String url= new URLBuilder(APIModel.bookings,"pending_bookings").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get,new ReservationListParser(), successCallback,getmFaildCallback()) ;
        authenticationRequired(request);
        request.Call();
    }

    public void getReservations (String company_id,SuccessCallback<List<Reservation>> successCallback) {
        String url= new URLBuilder(APIModel.bookings,"company_bookings").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get,new ReservationListParser(), successCallback,getmFaildCallback()) ;
        request.addParameter("company_id",company_id);
        authenticationRequired(request);
        request.Call();
    }

    public void approveReservation (int booking_id,SuccessCallback<Reservation> successCallback) {
        String url= new URLBuilder(APIModel.bookings,"approve").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get,new ReservationParser(), successCallback,getmFaildCallback()) ;
        request.addParameter("booking_id",String.valueOf(booking_id));
        authenticationRequired(request);
        request.Call();
    }

    public void declineReservation (int booking_id,SuccessCallback<String> successCallback) {
        String url= new URLBuilder(APIModel.bookings,"decline").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get,new StringParser(), successCallback,getmFaildCallback()) ;
        request.addParameter("booking_id",String.valueOf(booking_id));
        authenticationRequired(request);
        request.Call();
    }


    public void creatReservation (Map<String,String> Details, SuccessCallback<Reservation> successCallback) {
        String url= new URLBuilder(APIModel.bookings,"create_manually").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post,new ReservationParser(), successCallback,getmFaildCallback()) ;
        request.addParameter("field_id",Details.get("field_id"));
        request.addParameter("start",Details.get("start"));
        request.addParameter("duration",Details.get("duration"));
        request.addParameter("date",Details.get("date"));
        request.addParameter("notes",Details.get("notes"));
        request.addParameter("player_name",Details.get("player_name"));
        request.addParameter("player_phone",Details.get("player_phone"));
        request.addParameter("sms_option",Details.get("sms_option"));

        authenticationRequired(request);
        request.Call();
    }
}
