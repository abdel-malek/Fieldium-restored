package com.bluebrand.fieldiumadmin.View;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.bluebrand.fieldiumadmin.Model.ReservationsReport;
import com.bluebrand.fieldiumadmin.MyApplication;
import com.bluebrand.fieldiumadmin.R;
import com.bluebrand.fieldiumadmin.View.Adapter.ReportsAdapter;
import com.bluebrand.fieldiumadmin.controller.CurrentAndroidUser;
import com.bluebrand.fieldiumadmin.controller.ReportsController;
import com.tradinos.core.network.SuccessCallback;

import java.util.Calendar;

/**
 * Created by r.desouki on 1/18/2017.
 */
public class ReportActivity extends MasterActivity {
    private TextView from_date;
    private TextView to_date;
    private ListView report_listView;
    private LinearLayout general_info_layout;
    private TextView total_reservations;
    private TextView total;



    private ReportsAdapter reportsAdapter;
    private Calendar fromDate;
    private String fromDateString;
    private Calendar toDate;
    private String toDateString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_report);
        fromDate=Calendar.getInstance();
        toDate=Calendar.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    void getData() {
        ReservationsReport.setAll_bookings_number(0);
        ReservationsReport.setAll_total(0);
        ShowProgressDialog();
        fromDateString=fromDate.get(Calendar.YEAR) + "-" + (fromDate.get(Calendar.MONTH) + 1) + "-" + fromDate.get(Calendar.DAY_OF_MONTH);
        toDateString=toDate.get(Calendar.YEAR) + "-" + (toDate.get(Calendar.MONTH) + 1) + "-" + toDate.get(Calendar.DAY_OF_MONTH);

        ReportsController.getInstance(getmController()).reservationsReport(((MyApplication) getApplication()).getMyCompanyInfo().getID(), fromDateString, toDateString, new SuccessCallback<ReservationsReport>() {
                @Override
                public void OnSuccess(ReservationsReport result) {
                    HideProgressDialog();
                    reportsAdapter=new ReportsAdapter(ReportActivity.this,R.layout.report_item,result.getReservationReportList(),report_listView,false);
                    report_listView.setAdapter(reportsAdapter);
                    total_reservations.setText(String.valueOf(result.getAll_bookings_number()));
                    total.setText(String.valueOf(result.getAll_total())+" "+(new CurrentAndroidUser(getApplicationContext())).Get().getCurrency());
                }
            });
    }

    @Override
    void showData() {

    }

    @Override
    void assignUIReferences() {
        from_date=(TextView) findViewById(R.id.from_date);
        to_date=(TextView) findViewById(R.id.to_date);
        report_listView=(ListView)findViewById(R.id.report_listView);
        general_info_layout=(LinearLayout)findViewById(R.id.general_info_layout);
        total_reservations=(TextView)findViewById(R.id.total_reservations);
        total=(TextView)findViewById(R.id.total);
        ((ImageButton)findViewById(R.id.BackButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        fromDateString=fromDate.get(Calendar.YEAR) + "-" + (fromDate.get(Calendar.MONTH) + 1) + "-" + fromDate.get(Calendar.DAY_OF_MONTH);
        toDateString=toDate.get(Calendar.YEAR) + "-" + (toDate.get(Calendar.MONTH) + 1) + "-" + toDate.get(Calendar.DAY_OF_MONTH);
        from_date.setText("From: "+fromDateString);
        to_date.setText("To: "+toDateString);
    }

    @Override
    void assignActions() {
        from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new android.app.DatePickerDialog(ReportActivity.this, new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int Year, int Month, int Day) {
                        from_date.setText("From: "+Year + "-" + (Month + 1) + "-" + Day);
                        fromDate.set(Calendar.YEAR, Year);
                        fromDate.set(Calendar.MONTH, Month);
                        fromDate.set(Calendar.DAY_OF_MONTH, Day);
                        getData();
                    }
                }, fromDate.get(Calendar.YEAR), fromDate.get(Calendar.MONTH), fromDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new android.app.DatePickerDialog(ReportActivity.this, new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int Year, int Month, int Day) {
                        to_date.setText("To: "+Year + "-" + (Month + 1) + "-" + Day);
                        toDate.set(Calendar.YEAR, Year);
                        toDate.set(Calendar.MONTH, Month);
                        toDate.set(Calendar.DAY_OF_MONTH, Day);
                        getData();
                    }
                }, toDate.get(Calendar.YEAR), toDate.get(Calendar.MONTH), toDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
