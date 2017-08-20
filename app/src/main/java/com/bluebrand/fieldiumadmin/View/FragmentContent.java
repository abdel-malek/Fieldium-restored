package com.bluebrand.fieldiumadmin.View;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bluebrand.fieldiumadmin.Model.Reservation;
import com.bluebrand.fieldiumadmin.R;
import com.bluebrand.fieldiumadmin.View.Adapter.ReservationAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class FragmentContent extends Fragment {

    long millis;
    private ListView approved_listView;
    static List<Reservation>reservation;
    private static final String KEY_DATE = "date";

    public static FragmentContent newInstance(long date, List<Reservation>reservations) {
        FragmentContent fragmentFirst = new FragmentContent();
        Bundle args = new Bundle();
        args.putLong(KEY_DATE, date);
        reservation=reservations;
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final long millis = getArguments().getLong(KEY_DATE);
        this.millis=millis;
        if (millis > 0) {
            final Context context = getActivity();
            if (context != null) {

                return;
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        approved_listView = (ListView) view.findViewById(R.id.approved_listView);
        ReservationAdapter reservationAdapter = new ReservationAdapter(getContext(), R.layout.reservation_item, filterByDate(), approved_listView);
        approved_listView.setAdapter(reservationAdapter);

        return view;
    }

    List<Reservation> filterByDate(){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        List<Reservation>filtered=new ArrayList<>();
        for (int i=0;i<reservation.size();i++){

            if(reservation.get(i).getDate().get(Calendar.DAY_OF_MONTH)==calendar.get(Calendar.DAY_OF_MONTH)&&
                    reservation.get(i).getDate().get(Calendar.MONTH)==calendar.get(Calendar.MONTH)&&
                    reservation.get(i).getDate().get(Calendar.YEAR)==calendar.get(Calendar.YEAR)&&
                    reservation.get(i).isApproved()){
                filtered.add(reservation.get(i));
            }
        }
        return filtered;
    }



}