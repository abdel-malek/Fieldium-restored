package com.bluebrand.fieldium.view.Fields;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.view.Booking.NewCheckAvailabilityActivity;
import com.bluebrand.fieldium.view.Player.RegisterActivity;
import com.bluebrand.fieldium.view.Player.VerficationActivity;
import com.bluebrand.fieldium.view.adapter.CustomListAdapterDialog;
import com.squareup.picasso.Picasso;
import com.bluebrand.fieldium.FieldiumApplication;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.FieldsController;
import com.bluebrand.fieldium.core.model.Booking;
import com.bluebrand.fieldium.core.model.Field;
import com.bluebrand.fieldium.view.Booking.BookingActivity;
import com.bluebrand.fieldium.view.Booking.CheckAvailabilityActivity;
import com.bluebrand.fieldium.view.MasterFormActivity;
import com.bluebrand.fieldium.view.adapter.ImageAdapter;
import com.bluebrand.network.SuccessCallback;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import me.relex.circleindicator.CircleIndicator;

public class FieldDetailsActivity extends MasterFormActivity {
    int fieldId;
    Field field;
    Intent callIntent;
    ImageView logo_image;
    TextView textView_rateHour, textView_companyName, textView_companyAddress, textView_fieldDescription, textView_maxCapacity, textView_fieldArea, textView_openHours, textView_fieldPhone;
    ImageView mapImageView1, mapImageView2, callField;
    CircleIndicator circleIndicator;
    ViewPager viewPager;
    Booking booking;
    boolean anyTime = false;
    FieldiumApplication fieldiumApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        setContentView(R.layout.activity_field_details);
        super.onCreate(savedInstanceState);
        fieldiumApplication = (FieldiumApplication) getApplication();
        booking = fieldiumApplication.getBooking();
        setTitle("");
        getData();

    }

    @Override
    public void getData() {
        fieldId = getIntent().getExtras().getInt("field_id");
        showProgress(true);
        FieldsController.getInstance(getmController()).ShowField(fieldId, new SuccessCallback<Field>() {
            @Override
            public void OnSuccess(Field result) {
                showProgress(false);
                field = result;
                showData();
            }
        });
    }

    @Override
    public void showData() {
      /*  if (booking.getDate() == null) {
            ((Button) findViewById(R.id.button_submit)).setText("Check availability");
            anyTime = true;
        }*/
        if (booking.getStartTime() == null) {
            ((Button) findViewById(R.id.button_submit)).setText(getResources().getString(R.string.check_availability));
            anyTime = true;
        }
        findViewById(R.id.button_submit).setVisibility(View.VISIBLE);
        setTitle(field.getName());
        if (!field.getDescription().equals(""))
            textView_fieldDescription.setText(field.getDescription());
        else {
            textView_fieldDescription.setVisibility(View.GONE);
            findViewById(R.id.desc_divider).setVisibility(View.GONE);
        }
        textView_companyAddress.setText(field.getCompany().getAddress().getName());
        textView_companyName.setText(field.getCompany().getName());
        textView_rateHour.setText(field.getHourRate() + " " + getCurrency()+" " + getResources().getString(R.string.per_hour));
        textView_maxCapacity.setText(String.valueOf(field.getMaxCapacity()) + getResources().getString(R.string.players));
        textView_openHours.setText(convertTime());
//        textView_fieldArea.setText(String.valueOf(calculateArea()) + getResources().getString(R.string.msup2));
        if (UserUtils.getInstance(getmContext()).IsLogged()) {
            textView_fieldPhone.setText(field.getPhone());
        } else textView_fieldPhone.setText(getResources().getString(R.string.phone));

        try {
            Picasso.with(getmContext()).load(field.getCompany().getLogoURL())
                    .error(R.drawable.default_image)
//                        .fitCenter()
                    .placeholder(R.drawable.default_image)
                    .into(logo_image);
        } catch (Exception e) {

        }
        if (field.getImages() != null) {
            viewPager.setAdapter(new ImageAdapter(field.getImages(), getSupportFragmentManager()));
            circleIndicator.setViewPager(viewPager);
        }
        viewAmenities();
        viewGames();

    }

    @Override
    public void assignUIRefrences() {
        textView_companyName = (TextView) findViewById(R.id.company_name);
        textView_companyAddress = (TextView) findViewById(R.id.company_address);
        textView_rateHour = (TextView) findViewById(R.id.hourRate_textView);
        textView_fieldDescription = (TextView) findViewById(R.id.textView_description);
        logo_image = (ImageView) findViewById(R.id.logo_image);
        circleIndicator = (CircleIndicator) findViewById(R.id.circle_indicator);
        viewPager = (ViewPager) findViewById(R.id.field_image);
        textView_maxCapacity = (TextView) findViewById(R.id.max_capacity);
        textView_fieldArea = (TextView) findViewById(R.id.field_area);
        textView_openHours = (TextView) findViewById(R.id.open_hours);
        textView_fieldPhone = (TextView) findViewById(R.id.field_phone);
        mapImageView1 = (ImageView) findViewById(R.id.view_on_map1);
        mapImageView2 = (ImageView) findViewById(R.id.view_on_map2);
        callField = (ImageView) findViewById(R.id.call_field);
        mProgressView = (View) findViewById(R.id.proccess_indicator);
        mFormView = (View) findViewById(R.id.form_container);
    }

    @Override
    public void assignActions() {
        mapImageView1.setOnClickListener(this);
        mapImageView2.setOnClickListener(this);
        callField.setOnClickListener(this);
        super.assignActions();


    }

    @Override
    public void submitForm() {
        if (UserUtils.getInstance(getmContext()).IsLogged()) {
            Booking booking = ((FieldiumApplication) getApplication()).getBooking();
            booking.setField(field);
            ((FieldiumApplication) getApplication()).setBooking(booking);

            if (booking.getGame() != null) {
                submit();
            } else openGameDialog();
        } else redirectToRegistration();


    }

    public void submit() {
        if (anyTime) {
            Intent intent = new Intent(this, NewCheckAvailabilityActivity.class);
            startActivity/*ForResult*/(intent/*, 14*/);

        } else {
            Intent intent = new Intent(this, BookingActivity.class);
//                intent.putExtra("field", field);
            startActivity/*ForResult*/(intent/*, 15*/);
        }

    }

    public void openGameDialog() {

        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.dialog_main, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getmContext()).
                setView(convertView)
                .setTitle(getResources().getString(R.string.select_game))
                .show();
        CustomListAdapterDialog clad = new CustomListAdapterDialog(FieldDetailsActivity.this, booking.getField().getGames());
        ListView lv = (ListView) convertView.findViewById(R.id.lv_test);
        lv.setAdapter(clad);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                booking.setGame(booking.getField().getGames().get(i));
                fieldiumApplication.setBooking(booking);
                submit();
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_on_map1: {
                Intent i = new Intent(getmContext(), com.bluebrand.fieldium.view.Fields.MapActivity.class);
                i.putExtra("label", field.getCompany().getName());
                i.putExtra("address", field.getCompany().getAddress());
                startActivity(i);
                break;
            }
            case R.id.view_on_map2: {
                if (field != null) {
                    Intent i = new Intent(getmContext(), com.bluebrand.fieldium.view.Fields.MapActivity.class);
                    i.putExtra("label", field.getCompany().getName());
                    i.putExtra("address", field.getCompany().getAddress());
                    startActivity(i);
                }
                break;
            }
            case R.id.call_field: {
                if (field != null) {
                    if (UserUtils.getInstance(getmContext()).IsLogged())
                        call(field.getPhone());
                    else redirectToRegistration();
                }
                break;
            }
        }
        super.onClick(v);

    }

    public BigDecimal calculateArea() {
        BigDecimal area = new BigDecimal(field.getArea_x()).multiply(new BigDecimal(field.getArea_y()));
        return area;
    }

    public void call(String phone) {
        try {

            callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 200);
            } else {
                startActivity(callIntent);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public String convertTime() {
        String openHours = "";
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        SimpleDateFormat formatter1 = new SimpleDateFormat("kk:mm", Locale.ENGLISH);
        try {
            openHours = formatter.format(formatter1.parse(field.getOpenTime())) + " - " + formatter.format(formatter1.parse(field.getCloseTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm aa");
//        try {
//            final Date dateObj = sdf.parse(field.getOpenTime());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        return openHours;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 200: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(callIntent);
                }
            }
        }
    }

    public void viewAmenities() {
        if (field.getAmenities().size() != 0) {
            for (int i = 0; i < field.getAmenities().size(); i++) {
                //create imageview here and setbg
                ImageView imageView = new ImageView(this);
                TextView textView = new TextView(this);
//            imageView.setImageResource(R.mipmap.logo);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);
                imageView.setLayoutParams(layoutParams);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(10, 10, 10, 10);
                try {
                    Picasso.with(getmContext()).load(field.getAmenities().get(i).getImageUrl())
                            .error(R.drawable.default_image)
//                        .fitCenter()
                            .placeholder(R.drawable.default_image)
                            .into(imageView);
                } catch (Exception e) {

                }
                textView.setTextColor(getResources().getColor(R.color.black));
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(layoutParams1);
                textView.setPadding(10, 10, 10, 10);
                textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                textView.setText(field.getAmenities().get(i).getName());
                LinearLayout linearLayout = new LinearLayout(this);

                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.CENTER);
//            int t=linearLayout.getChildCount();
                linearLayout.addView(textView, 0);
                linearLayout.addView(imageView, 0);


//            int t1=linearLayout.getChildCount();
                ((LinearLayout) findViewById(R.id.amenities_panel)).addView(
                        linearLayout, i);
            }
        } else {
            findViewById(R.id.amenities_divider).setVisibility(View.GONE);
            findViewById(R.id.amenities_layout).setVisibility(View.GONE);

        }

    }

    public void viewGames() {
        if (field.getGames().size() != 0) {
            for (int i = 0; i < field.getGames().size(); i++) {
                //create imageview here and setbg
                ImageView imageView = new ImageView(this);
                TextView textView = new TextView(this);
//            imageView.setImageResource(R.mipmap.logo);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);
                imageView.setLayoutParams(layoutParams);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(10, 10, 10, 10);
                try {
                    Picasso.with(getmContext()).load(field.getGames().get(i).getImageUrl())
                            .error(R.drawable.default_image)
//                        .fitCenter()
                            .placeholder(R.drawable.default_image)
                            .into(imageView);
                } catch (Exception e) {

                }
                textView.setTextColor(getResources().getColor(R.color.black));
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(layoutParams1);
                textView.setPadding(10, 10, 10, 10);
                textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                textView.setText(field.getGames().get(i).getName());
                LinearLayout linearLayout = new LinearLayout(this);

                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.CENTER);
//            int t=linearLayout.getChildCount();
                linearLayout.addView(textView, 0);
                linearLayout.addView(imageView, 0);


//            int t1=linearLayout.getChildCount();
                ((LinearLayout) findViewById(R.id.games_panel)).addView(
                        linearLayout, i);
            }
        } else {
            findViewById(R.id.games_divider).setVisibility(View.GONE);
            findViewById(R.id.games_layout).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        if (field != null) {
            if (UserUtils.getInstance(getmContext()).IsLogged()) {// when we register from this and return back >>> we must show the field's phone
                textView_fieldPhone.setText(field.getPhone());
            } else textView_fieldPhone.setText(getResources().getString(R.string.phone));
        }
        super.onStart();
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 14 && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        } else if (requestCode == 15 && resultCode == RESULT_OK)
        {  setResult(RESULT_OK);
            finish();}
    }*/

    public void redirectToRegistration() {
        if (UserUtils.getInstance(this).IsRegistered()) {
            String phone = UserUtils.getInstance(this).GetPhone();
            Intent intent = new Intent(FieldDetailsActivity.this, VerficationActivity.class);
            intent.putExtra("mobile", phone);
            intent.putExtra("serverId", UserUtils.getInstance(this).getServerId());
            intent.putExtra("fieldDetailsActivity", true);
         /*   intent.putExtra("from_context", FieldDetailsActivity.class);
            intent.putExtra("field_id", field.getId());*/
            Toast.makeText(FieldDetailsActivity.this, "Please login", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, RegisterActivity.class);
           /* intent.putExtra("from_context", FieldDetailsActivity.class);
            intent.putExtra("field_id", field.getId());*/
            intent.putExtra("fieldDetailsActivity", true);
            Toast.makeText(FieldDetailsActivity.this, "Please login", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }


}
