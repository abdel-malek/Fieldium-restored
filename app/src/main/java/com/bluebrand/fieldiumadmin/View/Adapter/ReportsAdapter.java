package com.bluebrand.fieldiumadmin.View.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bluebrand.fieldiumadmin.Model.FieldsReservationsReport;
import com.bluebrand.fieldiumadmin.Model.ReportItem;
import com.bluebrand.fieldiumadmin.Model.ReservationsReport;
import com.bluebrand.fieldiumadmin.R;

import java.util.List;

/**
 * Created by r.desouki on 1/10/2017.
 */
public class ReportsAdapter extends ArrayAdapter<ReportItem> {

    ListView listView;
    List<ReportItem> reportItems;
    int position;
    Context context;
    public ReportsAdapter(Context context, int resource, List<ReportItem> reportItems, ListView listView) {
        super(context, resource);
        this.reportItems = reportItems;
        this.listView = listView;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        this.position = position;
        View view = ((LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.report_item, null);

        ReportItem reportItem = getItem(position);
        if(reportItem instanceof ReservationsReport.ReservationReport){
            TextView L1TextView=(TextView) view.findViewById(R.id.L1TextView);
            TextView L2TextView=(TextView) view.findViewById(R.id.L2TextView);
            TextView L3TextView=(TextView) view.findViewById(R.id.L3TextView);
            TextView L4TextView=(TextView) view.findViewById(R.id.L4TextView);
            TextView L5TextView=(TextView) view.findViewById(R.id.L5TextView);
            TextView L6TextView=(TextView) view.findViewById(R.id.L6TextView);

            L1TextView.setVisibility(View.VISIBLE);
            L2TextView.setVisibility(View.VISIBLE);
            L3TextView.setVisibility(View.VISIBLE);
            L4TextView.setVisibility(View.GONE);
            L5TextView.setVisibility(View.GONE);
            L6TextView.setVisibility(View.GONE);


            ReservationsReport.ReservationReport reservationReport=(ReservationsReport.ReservationReport)reportItem;
            L1TextView.setText(reservationReport.getField_name());
            L2TextView.setText(String.valueOf(reservationReport.getBookings_number()));
            L3TextView.setText(String.valueOf(reservationReport.getTotal())+" AED");
        }
        else{
            TextView L1TextView=(TextView) view.findViewById(R.id.L1TextView);
            TextView L2TextView=(TextView) view.findViewById(R.id.L2TextView);
            TextView L3TextView=(TextView) view.findViewById(R.id.L3TextView);
            TextView L4TextView=(TextView) view.findViewById(R.id.L4TextView);
            TextView L5TextView=(TextView) view.findViewById(R.id.L5TextView);
            TextView L6TextView=(TextView) view.findViewById(R.id.L6TextView);

            L1TextView.setVisibility(View.VISIBLE);
            L2TextView.setVisibility(View.VISIBLE);
            L3TextView.setVisibility(View.VISIBLE);
            L4TextView.setVisibility(View.VISIBLE);
            L5TextView.setVisibility(View.VISIBLE);
            L6TextView.setVisibility(View.GONE);

            FieldsReservationsReport.FieldReservationsReport fieldReservationsReport=(FieldsReservationsReport.FieldReservationsReport)reportItem;
            L1TextView.setText(String.valueOf(fieldReservationsReport.getBooking_id()));
            L2TextView.setText(fieldReservationsReport.getPlayer_name());
            L3TextView.setText(fieldReservationsReport.getString_date());
            L4TextView.setText(fieldReservationsReport.getManually()?"Yes":"No");
            L5TextView.setText(String.valueOf(fieldReservationsReport.getTotal())+" AED");

        }

        if(position%2!=0){
            view.findViewById(R.id.background).setBackgroundColor(Color.parseColor("#dcdcdc"));
        }

        return view;
    }

    @Override
    public ReportItem getItem(int position) {
        return reportItems.get(position);
    }

    @Override
    public int getCount() {
        return reportItems.size();
    }

    public List<ReportItem> getFields() {
        return reportItems;
    }

    public void setFields(List fields) {
        this.reportItems = fields;
    }

}
