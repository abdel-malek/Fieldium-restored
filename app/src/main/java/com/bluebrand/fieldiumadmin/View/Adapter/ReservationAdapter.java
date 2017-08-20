package com.bluebrand.fieldiumadmin.View.Adapter;

/**
 * Created by r.desouki on 5/18/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.bluebrand.fieldiumadmin.Model.Reservation;
import com.bluebrand.fieldiumadmin.MyApplication;
import com.bluebrand.fieldiumadmin.R;
import com.bluebrand.fieldiumadmin.View.ApprovedReservationsActivity;
import com.bluebrand.fieldiumadmin.View.MainActivity;


public class ReservationAdapter extends ArrayAdapter<Reservation> {

    ListView listView;
    List<Reservation> reservations=new ArrayList<>();
    int position;
    Context context;

    public ReservationAdapter(Context context, int resource, List<Reservation> reservations,ListView listView) {
        super(context, resource);
        this.reservations = reservations;
        this.listView=listView;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        this.position=position;
        View view = ((LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.reservation_item, null);

        Reservation reservation = getItem(position);
        TextView CutomerName_textView=(TextView)view.findViewById(R.id.CutomerName_textView);
        TextView reservTime_textView=(TextView)view.findViewById(R.id.reservTime_textView);
        TextView reservDate_textView=(TextView)view.findViewById(R.id.reservDate_textView);
        TextView reservField_textView=(TextView)view.findViewById(R.id.reservField_textView);
        TextView reservID_textView=(TextView)view.findViewById(R.id.reservID_textView);

        //((TextView)view.findViewById(R.id.CompanyName_textView)).setText(reservation.getCompany_name());
        CutomerName_textView.setText(String.valueOf(reservation.getPlayer_Name()));
        reservID_textView.setText(String.valueOf(reservation.getBooking_id()));
        reservTime_textView.setText(reservation.getStart_string());
        reservDate_textView.setText(reservation.getDate_string());
        if(context instanceof MainActivity)
        {
            reservField_textView.setText(reservation.getCompany_name()+", "+reservation.getField_name());
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    ((MainActivity)context).ReservationDialog(getItem(position),position);
                }
            });
        }
        else
        {
            reservField_textView.setText(reservation.getCompany_name()+", "+reservation.getField_name());
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    ((ApprovedReservationsActivity)context).ReservationDialog(getItem(position));
                }
            });
        }


        return view;
    }



    @Override
    public Reservation getItem(int position) {
        return reservations.get(position);
    }

    @Override
    public int getCount() {
        return reservations.size();
    }

    public List<Reservation> getReservations()
    {
        return reservations;
    }

    public void setReservations(List reservations)
    {
        this.reservations = reservations;
    }


}
