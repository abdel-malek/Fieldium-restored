package com.bluebrand.fieldiumadmin.controller;

import android.content.Context;

import com.bluebrand.fieldiumadmin.API.APIModel;
import com.bluebrand.fieldiumadmin.API.URLBuilder;
import com.bluebrand.fieldiumadmin.Model.FieldsReservationsReport;
import com.bluebrand.fieldiumadmin.Model.ReservationsReport;
import com.bluebrand.fieldiumadmin.Parser.FieldReservationsReportParser;
import com.bluebrand.fieldiumadmin.Parser.ReservationsReportParser;
import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.RequestMethod;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.core.network.TradinosRequest;

/**
 * Created by r.desouki on 12/15/2016.
 */
public class ReportsController extends ParentController {
    public static ReportsController getInstance (Controller controller) {
        return new ReportsController(controller);
    }

    public ReportsController(Controller controller) {
        super(controller.getmContext(), controller.getmFaildCallback());
    }

    public ReportsController(Context context, FaildCallback faildCallback) {
        super(context, faildCallback);
    }


    public void reservationsReport (int comp_id,String from,String to ,SuccessCallback<ReservationsReport> successCallback) {
        String url= new URLBuilder(APIModel.reports,"reservations_report").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get,new ReservationsReportParser(), successCallback,getmFaildCallback()) ;
        request.addParameter("company_id",String.valueOf(comp_id));
        request.addParameter("from_date",from);
        request.addParameter("to_date",to);

        authenticationRequired(request);
        request.Call();
    }

    public void fieldReservationsReport (int field_id,String from,String to ,SuccessCallback<FieldsReservationsReport> successCallback) {
        String url= new URLBuilder(APIModel.reports,"field_reservations_report").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get,new FieldReservationsReportParser(), successCallback,getmFaildCallback()) ;
        request.addParameter("field_id",String.valueOf(field_id));
        request.addParameter("from_date",from);
        request.addParameter("to_date",to);

        authenticationRequired(request);
        request.Call();
    }
}