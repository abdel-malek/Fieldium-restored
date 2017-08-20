package com.bluebrand.fieldiumadmin.Parser;

import com.bluebrand.fieldiumadmin.Model.ReservationsReport;
import com.tradinos.core.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by r.desouki on 1/31/2017.
 */
public class ReservationsReportParser implements TradinosParser<ReservationsReport> {
    @Override
    public ReservationsReport Parse(String text) throws JSONException {
        ReservationsReport reservationsReport=new ReservationsReport();
        try {
            JSONObject jsonObject = new JSONObject(text);
            reservationsReport=Parse(jsonObject);
        }catch (JSONException e){

        }
        return reservationsReport;
    }

    public ReservationsReport Parse(JSONObject jsonObject) throws JSONException {
        ReservationsReport reservationsReport = new ReservationsReport();

        reservationsReport.setAll_bookings_number(jsonObject.getInt("bookings_number"));
        reservationsReport.setAll_total(jsonObject.getInt("total"));
        JSONArray jsonArray = jsonObject.getJSONArray("details");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            int id = jsonObject1.getInt("field_id");
            String name = jsonObject1.getString("field_name");
            int booking_num = jsonObject1.getInt("bookings_number");
            int total = jsonObject1.getInt("total");
            reservationsReport.newReservationReport(id, name, booking_num, total);
        }

        return reservationsReport;
    }

}