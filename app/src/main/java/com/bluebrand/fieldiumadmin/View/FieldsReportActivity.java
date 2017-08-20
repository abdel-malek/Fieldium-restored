package com.bluebrand.fieldiumadmin.View;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bluebrand.fieldiumadmin.Model.Field;
import com.bluebrand.fieldiumadmin.Model.FieldsReservationsReport;
import com.bluebrand.fieldiumadmin.MyApplication;
import com.bluebrand.fieldiumadmin.R;
import com.bluebrand.fieldiumadmin.View.Adapter.ReportsAdapter;
import com.bluebrand.fieldiumadmin.controller.CurrentAndroidUser;
import com.bluebrand.fieldiumadmin.controller.ReportsController;
import com.tradinos.core.network.SuccessCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by r.desouki on 1/18/2017.
 */
public class FieldsReportActivity extends MasterActivity {
    private TextView from_date;
    private TextView to_date;
    private ListView report_listView;
    private Spinner field_spinner;
    private LinearLayout general_info_layout;
    private TextView Total;
    private TextView field_textView;


    private ReportsAdapter reportsAdapter;
    private int currentFieldID;
    private Calendar fromDate;
    private String fromDateString;
    private Calendar toDate;
    private String toDateString;
    private List<Field>fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fields_report);
        fields=((MyApplication)getApplication()).getMyFields();
        currentFieldID=0;
        fromDate=Calendar.getInstance();
        toDate=Calendar.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    void getData() {

    }

    void getFieldReport(){
        FieldsReservationsReport.setTotal(0);
        ShowProgressDialog();
        fromDateString=fromDate.get(Calendar.YEAR) + "-" + (fromDate.get(Calendar.MONTH) + 1) + "-" + fromDate.get(Calendar.DAY_OF_MONTH);
        toDateString=toDate.get(Calendar.YEAR) + "-" + (toDate.get(Calendar.MONTH) + 1) + "-" + toDate.get(Calendar.DAY_OF_MONTH);

        ReportsController.getInstance(getmController()).fieldReservationsReport(currentFieldID, fromDateString, toDateString, new SuccessCallback<FieldsReservationsReport>() {
            @Override
            public void OnSuccess(FieldsReservationsReport result) {
                HideProgressDialog();
                Total.setText(String.valueOf(result.getTotal())+" "+(new CurrentAndroidUser(getApplicationContext())).Get().getCurrency());
                if(currentFieldID==0){
                    field_textView.setVisibility(View.VISIBLE);
                }else{
                    field_textView.setVisibility(View.GONE);
                }
                reportsAdapter=new ReportsAdapter(FieldsReportActivity.this,R.layout.report_item,result.getFieldReservationsReports(),report_listView,currentFieldID==0?true:false);
                report_listView.setAdapter(reportsAdapter);
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
        field_spinner =(Spinner)findViewById(R.id.field_spinner);
        general_info_layout=(LinearLayout)findViewById(R.id.general_info_layout);
        Total=(TextView)findViewById(R.id.total);
        field_textView=(TextView)findViewById(R.id.field_textView);

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
        List<String>strings=new ArrayList<>();
        for(int i=0;i<fields.size();i++){
            if(i==0){
                strings.add("All");
            }
            strings.add(fields.get(i).getName());
        }
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.spinner_layout,strings);
        arrayAdapter.setDropDownViewResource(R.layout.field_spinner_dropdown_item);
        field_spinner.setAdapter(arrayAdapter);
        field_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    currentFieldID=0;
                }
                else{
                    currentFieldID=fields.get(i-1).getField_id();
                }

                getFieldReport();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new android.app.DatePickerDialog(FieldsReportActivity.this, new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int Year, int Month, int Day) {
                        from_date.setText("From: "+Year + "-" + (Month + 1) + "-" + Day);
                        fromDate.set(Calendar.YEAR, Year);
                        fromDate.set(Calendar.MONTH, Month);
                        fromDate.set(Calendar.DAY_OF_MONTH, Day);
                        getFieldReport();
                    }
                }, fromDate.get(Calendar.YEAR), fromDate.get(Calendar.MONTH), fromDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new android.app.DatePickerDialog(FieldsReportActivity.this, new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int Year, int Month, int Day) {
                        to_date.setText("To: "+Year + "-" + (Month + 1) + "-" + Day);
                        toDate.set(Calendar.YEAR, Year);
                        toDate.set(Calendar.MONTH, Month);
                        toDate.set(Calendar.DAY_OF_MONTH, Day);
                        getFieldReport();
                    }
                }, toDate.get(Calendar.YEAR), toDate.get(Calendar.MONTH), toDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
