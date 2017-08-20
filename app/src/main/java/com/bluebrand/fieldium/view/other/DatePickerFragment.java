package com.bluebrand.fieldium.view.other;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.bluebrand.fieldium.R;

import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by b.srour on 7/31/2016.
 */
public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener, DatePickerDialog.OnCancelListener, DatePickerDialog.OnDismissListener{
    String date;
    boolean dateValid;
    int year;
    int month;
    int day;

    //public DatePickerFragment(View v){}
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, -1);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.datepicker, this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        this.year = year;
        this.month = month;
        this.day = day;

//        ((OnDialogClosed) getActivity()).OnDialogClosed(/*getArguments().getInt("viewId"),*/ year, month, day);
    }


    public interface OnDialogClosed {
        void OnDialogClosed(/*int resId,*/ int year, int month, int day);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        this.day=0;
        this.year=0;
        this.month=0;
//        ((OnDialogClosed) getActivity()).OnDialogClosed(/*getArguments().getInt("viewId"),*/ 0, 0, 0);
        super.onCancel(dialog);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
      if (isDateValid()||(year==0&&month==0&&day==0))
            ((OnDialogClosed) getActivity()).OnDialogClosed(/*getArguments().getInt("viewId"),*/ year, month, day);

        else{((OnDialogClosed) getActivity()).OnDialogClosed(/*getArguments().getInt("viewId"),*/ -1,-1, -1);
          Toast.makeText(getContext(), "Invalid date", Toast.LENGTH_SHORT).show();}
        super.onDismiss(dialog);

    }

 /*   @Override
    public void ondismiss() {

    }*/

    public boolean isDateValid() {
        Calendar c = Calendar.getInstance();

        String selectedDateString = year + "-" + ((month + 1) < 10 ? "0" + (month + 1) : (month + 1)) + "-" + (day < 10 ? "0" + day : day);
        Date currentDate = c.getTime();
        Date selectedDate = c.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            currentDate = formatter.parse(formatter.format(c.getTime()));
            selectedDate = formatter.parse(selectedDateString);
            //            c.setTime(formatter.parse(formatter.format(c.getTime())));
//            date = formatter.format(formatter.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (selectedDate.before(currentDate))
        {
            return false;}
        else
            return true;
    }
  /*  @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which==DialogInterface.BUTTON_POSITIVE && isDateValid()){
            dateValid = true;
        }
        super.onClick(dialog, which);
    }*/
}
