package com.bluebrand.fieldium.view.Booking;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bluebrand.fieldium.FieldiumApplication;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.BookingController;
import com.bluebrand.fieldium.core.model.Booking;
import com.bluebrand.fieldium.core.model.BookingStatus;
import com.bluebrand.fieldium.core.model.Field;
import com.bluebrand.fieldium.view.MasterActivity;
import com.bluebrand.network.InternetManager;
import com.bluebrand.network.SuccessCallback;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * Created by User
 */
public class BookingDetailActivity
        extends MasterActivity {


    Booking booking;
    TextView textView_date, textView_startTime, textView_duration, textView_total, companyAddress,
            companyName, fieldName, textView_status, textView_game;
    EditText editText_note;
    ImageView companyLogo;
    Field field;
    FieldiumApplication fieldiumApplication;
    String cancelReason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        setContentView(R.layout.activity_booking);
        super.onCreate(savedInstanceState);
        findViewById(R.id.button_submit).setVisibility(View.GONE);
        editText_note.setVisibility(View.GONE);
        findViewById(R.id.cost_per_hour_panel).setVisibility(View.GONE);
        findViewById(R.id.voucher_panel).setVisibility(View.GONE);
//        textView_date.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
//        textView_startTime.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
//        textView_duration.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        getData();
    }

    @Override
    protected void getData() {
        booking = (Booking) getIntent().getExtras().getSerializable("booking");
        setTitle(getResources().getString(R.string.booking_no) + booking.getId());
        try {
            ImageLoader mImageLoader = InternetManager.getInstance(getmContext()).getImageLoader();
            mImageLoader.get(booking.getField().getCompany().getLogoURL(), ImageLoader.getImageListener(companyLogo,
                    R.drawable.default_image, R.drawable.default_image));
        } catch (Exception e) {
            companyLogo.setImageResource(R.drawable.default_image);
        }
//        showData();
        showProgress(true);
        BookingController.getInstance(getmController()).getBookDetails(booking.getId(), new SuccessCallback<Booking>() {
            @Override
            public void OnSuccess(Booking result) {
                showProgress(false);
                booking = result;
                showData();
            }
        });
    }

    @Override
    protected void showData() {
        TextView discount_textView=(TextView) findViewById(R.id.discount_value);
        findViewById(R.id.status_panel).setVisibility(View.VISIBLE);
        textView_status.setTextColor(getResources().getColor(R.color.dark_gray));
        textView_status.setText(booking.getState().toString(getmContext()));
        textView_game.setTextColor(getResources().getColor(R.color.dark_gray));
        textView_game.setText(booking.getGame().getName());
        companyName.setText(booking.getField().getCompany().getName());
        fieldName.setText(booking.getField().getName());
        companyAddress.setText(booking.getField().getCompany().getAddress().getName());
        textView_date.setTextColor(getResources().getColor(R.color.dark_gray));

        textView_date.setText(booking.getDate());
        textView_startTime.setTextColor(getResources().getColor(R.color.dark_gray));

        textView_startTime.setText(booking.getStartTime());
        textView_duration.setTextColor(getResources().getColor(R.color.dark_gray));
        int intDuration = Integer.parseInt(booking.getDuration());
        String hDuration = (intDuration / 60) < 10 ? "0" + intDuration / 60 : intDuration / 60 + "";
        String mDuration = (intDuration % 60) < 10 ? "0" + intDuration % 60 : intDuration % 60 + "";
        textView_duration.setText(hDuration + ":" + mDuration);
//        textView_duration.setText(intDuration/60 + ":"+intDuration%60);
//        textView_duration.setText(booking.getDuration() + " Hour");
        textView_total.setText(booking.getTotal() + " " +getCurrency() );

        if (booking.getState() == BookingStatus.Canceled) {
            findViewById(R.id.cancel_reason_panel).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.cancel_reason)).setText(booking.getCancelReason());
        }
        if(booking.getVoucher().getType()!=0)//has voucher
        {
            findViewById(R.id.voucher_value_panel).setVisibility(View.VISIBLE);
            findViewById(R.id.subtotal_panel).setVisibility(View.VISIBLE);
            findViewById(R.id.discount_value_panel).setVisibility(View.VISIBLE);

            if (booking.getVoucher().getType()==1)
            {
                ((TextView)findViewById(R.id.voucher_value)).setText(booking.getVoucher().getValue()+"%");
                BigDecimal m60 = new BigDecimal(60);
                BigDecimal mDur = new BigDecimal(booking.getDuration());
                BigDecimal divideRes/*=(booking.getField().getHourRate()).divide(m60,BigDecimal.ROUND_HALF_EVEN)*/;
                try {
                    divideRes = (booking.getField().getHourRate()).divide(m60);
                } catch (ArithmeticException e) {
                    Log.d("Error bigdecimal", e.toString());
                    divideRes = new BigDecimal((booking.getField().getHourRate()).doubleValue() / m60.doubleValue());
                }
                BigDecimal multiplyRes = divideRes.multiply(mDur);
                BigDecimal subTotal = multiplyRes.setScale(0, BigDecimal.ROUND_HALF_EVEN);
                BigDecimal discountValue = subTotal.multiply(new BigDecimal(booking.getVoucher().getValue())).divide(new BigDecimal(100));
                String lang = Locale.getDefault().getLanguage();
                if (lang.equals("ar"))
                    discount_textView.setText(discountValue + "-");
                else
                    discount_textView.setText("-" + discountValue);
                discount_textView.setPaintFlags(discount_textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            else {
                int durationInt = booking.getVoucher().getValue();
                String mHDuration = (durationInt / 60) < 10 ? "0" + durationInt / 60 : durationInt / 60 + "";
                String mMDuration = (durationInt % 60) < 10 ? "0" + durationInt % 60 : durationInt % 60 + "";
                ((TextView)findViewById(R.id.voucher_value)).setText(mHDuration+":"+mMDuration+getResources().getString(R.string.free));
                BigDecimal m60 = new BigDecimal(60);
//                BigDecimal mDur = new BigDecimal(Integer.parseInt(booking.getDuration()) - booking.getVoucher().getValue());
                BigDecimal discountValue = new BigDecimal(booking.getVoucher().getValue());
                BigDecimal divideRes;
                try {
                    divideRes = (booking.getField().getHourRate()).divide(m60);
                } catch (ArithmeticException e) {
                    Log.d("Error bigdecimal", e.toString());
                    divideRes = new BigDecimal((booking.getField().getHourRate()).doubleValue() / m60.doubleValue());
                }
                discountValue = divideRes.multiply(discountValue);
                String lang = Locale.getDefault().getLanguage();
                if (lang.equals("ar"))
                    discount_textView.setText(discountValue + "-");
                else
                    discount_textView.setText("-" + discountValue);
                discount_textView.setPaintFlags(discount_textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            ((TextView)findViewById(R.id.subtotal_value)).setText(booking.getSubTotal()+" "+getCurrency());
        }


    }

    @Override
    public void assignUIRefrences() {
        editText_note = (EditText) findViewById(R.id.booking_note);
        textView_date = (TextView) findViewById(R.id.booking_date);
        textView_duration = (TextView) findViewById(R.id.booking_duration);
        textView_startTime = (TextView) findViewById(R.id.booking_startTime);
        textView_total = (TextView) findViewById(R.id.booking_total);
        companyAddress = (TextView) findViewById(R.id.company_address);
        companyName = (TextView) findViewById(R.id.company_name);
        fieldName = (TextView) findViewById(R.id.field_name);
        companyLogo = (ImageView) findViewById(R.id.logo_image);
        textView_status = (TextView) findViewById(R.id.booking_status);
        textView_game = (TextView) findViewById(R.id.booking_game);
        mFormView = (View) findViewById(R.id.form_container);
        mProgressView = (View) findViewById(R.id.proccess_indicator);

    }

    @Override
    protected void assignActions() {

    }


    @Override
    public void onClick(View v) {

    }
}
