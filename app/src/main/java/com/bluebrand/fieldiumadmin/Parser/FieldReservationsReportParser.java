package com.bluebrand.fieldiumadmin.Parser;

import com.bluebrand.fieldiumadmin.Model.FieldsReservationsReport;
import com.tradinos.core.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by r.desouki on 1/31/2017.
 */
public class FieldReservationsReportParser implements TradinosParser<FieldsReservationsReport> {
    @Override
    public FieldsReservationsReport Parse(String text) throws JSONException {
        FieldsReservationsReport fieldsReservationsReport = new FieldsReservationsReport();
        try {
            JSONObject jsonObject=new JSONObject(text);
            FieldsReservationsReport.setTotal(jsonObject.getInt("total"));
            JSONArray jsonArray =jsonObject.getJSONArray("details");
            fieldsReservationsReport= Parse(jsonArray);

        }catch (JSONException e){

        }
        return fieldsReservationsReport;
    }

    public FieldsReservationsReport Parse(JSONArray jsonArray) throws JSONException {
        FieldsReservationsReport fieldsReservationsReport = new FieldsReservationsReport();

        for (int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            int id=jsonObject.getInt("field_id");
            String field_name=jsonObject.getString("field_name");
            int booking_id=jsonObject.getInt("booking_id");
            String date=jsonObject.getString("date");
            String start=jsonObject.getString("start");
            int duration=jsonObject.getInt("duration");
            int player_id=jsonObject.getInt("player_id");
            String player_name=jsonObject.getString("player_name");
            Boolean manually= jsonObject.getInt("manually") != 0;
            int total=jsonObject.getInt("total");

            fieldsReservationsReport.newFieldReservationsReport(id,field_name,booking_id,date,start,duration,player_id,player_name,manually,total);
        }
        return fieldsReservationsReport;
    }
}