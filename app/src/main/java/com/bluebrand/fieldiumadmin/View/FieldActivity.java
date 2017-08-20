package com.bluebrand.fieldiumadmin.View;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bluebrand.fieldiumadmin.View.Adapter.CheckFieldsItemAdapter;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.tradinos.core.network.Code;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.SuccessCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bluebrand.fieldiumadmin.Model.Amenity;
import com.bluebrand.fieldiumadmin.Model.Field;
import com.bluebrand.fieldiumadmin.Model.Game;
import com.bluebrand.fieldiumadmin.Model.Image;
import com.bluebrand.fieldiumadmin.MyApplication;
import com.bluebrand.fieldiumadmin.R;
import com.bluebrand.fieldiumadmin.View.Utils.CircleTransform;
import com.bluebrand.fieldiumadmin.View.Utils.ImageChooser;
import com.bluebrand.fieldiumadmin.controller.CurrentAndroidUser;
import com.bluebrand.fieldiumadmin.controller.FieldController;

/**
 * Created by r.desouki on 12/15/2016.
 */
public class FieldActivity extends MasterActivity {
    //UI vars
    EditText field_name_editText;
    EditText field_description_editText;
    EditText field_max_capacity_editText;
    EditText field_area_x_editText;
    EditText field_area_y_editText;
    EditText field_phone_editText;
    EditText field_hour_price_editText;
    TextView field_from_time_textView;
    TextView field_to_time_textView;
    CheckBox auto_confirm_checkBox;
    CheckBox all_day_time_checkBox;
    LinearLayout amenities_linearLayout;
    LinearLayout gameType_linearLayout;

    Field currentField;
    Calendar startTime;
    Calendar finishTime;
    ArrayList<Integer>selectedAmenitiesIds=new ArrayList<>();
    List<ImageChooser> amenitiesImageChoosers = new ArrayList<>();

    ArrayList<Integer>selectedGamesIds=new ArrayList<>();
    List<ImageChooser> gamesImageChoosers = new ArrayList<>();

    List<ImageChooser> imageChoosers = new ArrayList<>();

    ArrayList<Integer> ChildsFieldsIds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_field);
        super.onCreate(savedInstanceState);
    }

    @Override
    void getData() {
        Bundle bundle = getIntent().getExtras();
        boolean newField=bundle.getBoolean("new_field");
        if(!newField){
            currentField=(Field)bundle.getSerializable("field");
            startTime =currentField.getOpen_time();
            finishTime=currentField.getClose_time();
            ChildsFieldsIds=currentField.getChildsFieldsIds();
        }
        else{
            startTime =Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY,10);
            finishTime=(Calendar) startTime.clone();
            ChildsFieldsIds=bundle.getIntegerArrayList("ids");

        }
    }



    @Override
    void showData() {
        if(currentField!=null){
            field_name_editText.setText(currentField.getName());
            field_description_editText.setText(currentField.getDescription());
            field_max_capacity_editText.setText(String.valueOf(currentField.getMax_capacity()));
            field_area_x_editText.setText(String.valueOf(currentField.getArea_x()));
            field_area_y_editText.setText(String.valueOf(currentField.getArea_y()));
            field_phone_editText.setText(currentField.getPhone());
            field_hour_price_editText.setText(String.valueOf(currentField.getHour_rate()));
            field_from_time_textView.setText(currentField.getOpen_time_string());
            field_to_time_textView.setText(currentField.getClose_time_string());
            auto_confirm_checkBox.setChecked(currentField.isAutoConfirm());
            if(currentField.getOpen_time_string().equals("12:00 AM")&&currentField.getClose_time_string().equals("11:59 PM"))
            {
                all_day_time_checkBox.setChecked(true);
                findViewById(R.id.field_to_time_relativelayout).setVisibility(View.GONE);
                findViewById(R.id.field_from_time_relativelayout).setVisibility(View.GONE);
            }
            else{
                all_day_time_checkBox.setChecked(false);
                findViewById(R.id.field_to_time_relativelayout).setVisibility(View.VISIBLE);
                findViewById(R.id.field_from_time_relativelayout).setVisibility(View.VISIBLE);
            }
            if(currentField.getChildsFieldsIds()!=null){
                findViewById(R.id.aggregatefields_editText).setVisibility(View.VISIBLE);
            }
        }

        if(ChildsFieldsIds!=null){
            findViewById(R.id.aggregatefields_editText).setVisibility(View.VISIBLE);
        }

        ImagesInit();
        amenitiesInit();
        gamesInit();
    }

    private void ImagesInit(){
        ImageChooser imageChooser1=(ImageChooser) findViewById(R.id.field_ImageChooser1);
        imageChoosers.add(imageChooser1);
        imageChooser1.setLoadingListener(new ImageChooser.LoadingListener() {
            @Override
            public void onCancel() {

            }

            @Override
            public void OnChooseImage() {
                showFileChooser(0);
            }
        });
        if(currentField!=null) {
            ViewGroup layout = (ViewGroup) findViewById(R.id.images_LinearLayout);
            for (int i = 0; i < currentField.getImages().size(); i++) {
                Image image = currentField.getImages().get(i);
                ImageChooser imageChooser = new ImageChooser(this);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageChooser.getLayoutParams();
                params.height = 200;
                params.width = 200;
                imageChooser.setLayoutParams(params);
                imageChooser.setImage(image);
                imageChoosers.add(imageChooser);
                layout.addView(imageChooser);
                try {
                    Picasso.with(this)
                            .load(image.getUrl()).memoryPolicy(MemoryPolicy.NO_STORE)
                            .error(R.mipmap.add_photo)
                            .placeholder(R.mipmap.add_photo)
                            .into(imageChoosers.get(i+1).getImageView());
                } catch (Exception e) {
                    String r = e.getMessage();
                }
            }
        }
    }

    private void amenitiesInit(){

        List<Amenity>allAmenities=((MyApplication)getApplication()).getMyAmenities();
        LinearLayout linearLayout=new LinearLayout(this);
        for (int i=0; i<allAmenities.size(); i++){
            Amenity amenityTemp=allAmenities.get(i);

            if(i%3==0){
                linearLayout=new LinearLayout(this);
                amenities_linearLayout.addView(linearLayout);
            }

            ImageChooser imageChooser=new ImageChooser(this);
            imageChooser.getImageView().setLayoutParams(new FrameLayout.LayoutParams(70,70));

            final Integer index = amenityTemp.getAmenity_id();
            imageChooser.setLoadingListener(new ImageChooser.LoadingListener() {
                @Override
                public void onCancel() {

                }

                @Override
                public void OnChooseImage() {
                    selectAmenity(index);
                }
            });
            amenitiesImageChoosers.add(imageChooser);
            if(currentField!=null) {
                if (!isAmenityChossen(amenityTemp.getAmenity_id())) {
                    imageChooser.getImageView().setAlpha(0.4f);
                } else {
                    selectedAmenitiesIds.add(index);
                }
            }
            else {
                imageChooser.getImageView().setAlpha(0.4f);
            }


            TextView textView=new TextView(this);
            textView.setText(amenityTemp.getName());
            textView.setGravity(Gravity.CENTER);
            LinearLayout imageText_linearLayout=new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(15, 1, 15, 1);
            imageText_linearLayout.setLayoutParams(params);
            imageText_linearLayout.setOrientation(LinearLayout.VERTICAL);
            imageText_linearLayout.addView(imageChooser);
            imageText_linearLayout.addView(textView);
            linearLayout.addView(imageText_linearLayout);
            try
            {
                Picasso.with(this)
                        .load(amenityTemp.getImage().getUrl()).memoryPolicy(MemoryPolicy.NO_STORE)
                        .transform(new CircleTransform())
                        .error(R.mipmap.add_photo)
                        .placeholder(R.mipmap.add_photo)
                        .into(imageChooser.getImageView());
            } catch (Exception e) {
            }

        }
    }

    private void gamesInit(){
        List<Game>allGames=((MyApplication)getApplication()).getMyGames();
        LinearLayout linearLayout=new LinearLayout(this);
        for (int i=0; i<allGames.size(); i++){
            Game gameTemp=allGames.get(i);
            if(i%3==0){
                linearLayout=new LinearLayout(this);
                gameType_linearLayout.addView(linearLayout);
            }
            ImageChooser imageChooser=new ImageChooser(this);
            imageChooser.getImageView().setLayoutParams(new FrameLayout.LayoutParams(70, 70));
            final Integer index = gameTemp.getGame_type_id();
            imageChooser.setLoadingListener(new ImageChooser.LoadingListener() {
                @Override
                public void onCancel() {

                }

                @Override
                public void OnChooseImage() {
                    selectGame(index);
                }
            });
            gamesImageChoosers.add(imageChooser);
            if(currentField!=null) {
                if (!isGameChossen(gameTemp.getGame_type_id())) {
                    imageChooser.getImageView().setAlpha(0.4f);
                } else {
                    selectedGamesIds.add(index);
                }
            }
            else {
                imageChooser.getImageView().setAlpha(0.4f);
            }

            TextView textView=new TextView(this);
            textView.setText(gameTemp.getName());
            textView.setGravity(Gravity.CENTER);
            LinearLayout imageText_linearLayout=new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(15, 1, 15, 1);
            imageText_linearLayout.setLayoutParams(params);
            imageText_linearLayout.setOrientation(LinearLayout.VERTICAL);
            imageText_linearLayout.addView(imageChooser);
            imageText_linearLayout.addView(textView);
            linearLayout.addView(imageText_linearLayout);
            try
            {
                Picasso.with(this)
                        .load(gameTemp.getImage().getUrl()).memoryPolicy(MemoryPolicy.NO_STORE)
                        .transform(new CircleTransform())
                        .error(R.mipmap.add_photo)
                        .placeholder(R.mipmap.add_photo)
                        .into(imageChooser.getImageView());
            } catch (Exception e) {
            }

        }
    }

    @Override
    void assignUIReferences() {

        field_name_editText=(EditText)findViewById(R.id.field_name_editText);
         field_description_editText=(EditText)findViewById(R.id.comp_address_editText);
         field_max_capacity_editText=(EditText)findViewById(R.id.comp_phone_editText);
         field_area_x_editText=(EditText)findViewById(R.id.comp_desc_editText);
         field_area_y_editText=(EditText)findViewById(R.id.field_area_y_editText);
         field_phone_editText=(EditText)findViewById(R.id.field_phone_editText);
         field_hour_price_editText=(EditText)findViewById(R.id.field_hour_price_editText);
         field_from_time_textView=(TextView) findViewById(R.id.field_from_time_textView);
         field_to_time_textView=(TextView) findViewById(R.id.field_to_time_textView);
         auto_confirm_checkBox=(CheckBox) findViewById(R.id.auto_confirm_checkBox);
        amenities_linearLayout=(LinearLayout)findViewById(R.id.amenities_linearLayout);
        gameType_linearLayout=(LinearLayout)findViewById(R.id.gameType_linearLayout);
        auto_confirm_checkBox=(CheckBox) findViewById(R.id.auto_confirm_checkBox);
        all_day_time_checkBox =(CheckBox) findViewById(R.id.all_day_time_textView);
        ImageButton imagebutton_back=(ImageButton)findViewById(R.id.imagebutton_back);
        imagebutton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        findViewById(R.id.field_info_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(FieldActivity.this);
            }
        });
    }



    @Override
    void assignActions() {
        for (int i = 0; i < imageChoosers.size(); i++) {
            final int index = i;
            imageChoosers.get(i).setLoadingListener(new ImageChooser.LoadingListener() {
                @Override
                public void onCancel() {

                }

                @Override
                public void OnChooseImage() {
                    showFileChooser(index);
                }
            });
        }



        field_from_time_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(FieldActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String am_pm = "";
                        startTime = Calendar.getInstance();
                        startTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        startTime.set(Calendar.MINUTE, selectedMinute);
                        if (startTime.get(Calendar.AM_PM) == Calendar.AM)
                            am_pm = "AM";
                        else if (startTime.get(Calendar.AM_PM) == Calendar.PM) {
                            am_pm = "PM";
                        }
                        String strHrsToShow = (startTime.get(Calendar.HOUR) == 0) ?"12": startTime.get(Calendar.HOUR)+"";
                        field_from_time_textView.setText(strHrsToShow + ":" +(startTime.get(Calendar.MINUTE)==0?"00": startTime.get(Calendar.MINUTE))+" "+am_pm);
                    }
                }, startTime.get(Calendar.HOUR_OF_DAY), 0, false);//true for 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        field_to_time_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(FieldActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String am_pm = "";
                        finishTime = Calendar.getInstance();
                        finishTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        finishTime.set(Calendar.MINUTE, selectedMinute);
                        if (finishTime.get(Calendar.AM_PM) == Calendar.AM)
                            am_pm = "AM";
                        else if (finishTime.get(Calendar.AM_PM) == Calendar.PM) {
                            am_pm = "PM";
                        }
                        String strHrsToShow = (finishTime.get(Calendar.HOUR) == 0) ?"12":finishTime.get(Calendar.HOUR)+"";
                        field_to_time_textView.setText(strHrsToShow + ":" +(finishTime.get(Calendar.MINUTE)==0?"00":finishTime.get(Calendar.MINUTE))+" "+am_pm);
                    }
                }, finishTime.get(Calendar.HOUR_OF_DAY), 0, false);//true for 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        all_day_time_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    findViewById(R.id.field_to_time_relativelayout).setVisibility(View.GONE);
                    findViewById(R.id.field_from_time_relativelayout).setVisibility(View.GONE);
                }else {
                    findViewById(R.id.field_to_time_relativelayout).setVisibility(View.VISIBLE);
                    findViewById(R.id.field_from_time_relativelayout).setVisibility(View.VISIBLE);
                }
            }
        });

        findViewById(R.id.aggregatefields_editText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFieldCheckList();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    private boolean isAmenityChossen(int id){
        for(int i=0;i<currentField.getAmenities().size();i++){
            if(currentField.getAmenities().get(i).getAmenity_id()==id){
                return  true;
            }
        }
        return false;
    }

    private void selectAmenity(Integer id){
        ImageChooser imageChooser=new ImageChooser(this);
        for(int i=0;i<((MyApplication)getApplication()).getMyAmenities().size();i++) {
            if(id==((MyApplication)getApplication()).getMyAmenities().get(i).getAmenity_id()){
                imageChooser=amenitiesImageChoosers.get(i);
            }
        }
        if(selectedAmenitiesIds.contains(id)){
            imageChooser.getImageView().setAlpha(0.4f);
            selectedAmenitiesIds.remove(id);
        }
        else {
            imageChooser.getImageView().setAlpha(1f);
            selectedAmenitiesIds.add(id);
        }
    }

    private boolean isGameChossen(int id){
        for(int i=0;i<currentField.getGames().size();i++){
            if(currentField.getGames().get(i).getGame_type_id()==id){
                return  true;
            }
        }
        return false;
    }

    private void selectGame(Integer id){
        ImageChooser imageChooser=new ImageChooser(this);
        for(int i=0;i<((MyApplication)getApplication()).getMyGames().size();i++) {
            if(id==((MyApplication)getApplication()).getMyGames().get(i).getGame_type_id()){
                imageChooser=gamesImageChoosers.get(i);
            }
        }
        if(selectedGamesIds.contains(id)){
            imageChooser.getImageView().setAlpha(0.4f);
            selectedGamesIds.remove(id);
        }
        else {
            imageChooser.getImageView().setAlpha(1f);
            selectedGamesIds.add(id);
        }
    }

    private String listToStringArray(List list){
        String s="[";
        for(int i=0;i<list.size();i++)
            s+=i==0?String.valueOf(list.get(i)):","+String.valueOf(list.get(i));
        return s+"]";
    }

    private void ShowFieldCheckList(){
        final Dialog popup= new Dialog(this);
        popup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popup.setContentView(R.layout.popup_fieldscheck);


        ListView fields_listView = (ListView)popup.findViewById(R.id.fields_listView);
        final CheckFieldsItemAdapter checkFieldsItemAdapter=new CheckFieldsItemAdapter(this,((MyApplication)getApplication()).getMyFields());
        if(ChildsFieldsIds!=null){
            checkFieldsItemAdapter.setSelectedObjects(ChildsFieldsIds);
        }
        fields_listView.setAdapter(checkFieldsItemAdapter);

        popup.findViewById(R.id.close_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        ((Button)popup.findViewById(R.id.create_Button)).setText("Save");
        popup.findViewById(R.id.create_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFieldsItemAdapter.getSelectedObjectsIDS().size()<2)
                {
                    showMessageInToast("Please select two fields at least!");
                }
                else {
                    ChildsFieldsIds= checkFieldsItemAdapter.getSelectedObjectsIDS();
                    popup.dismiss();

                }
            }
        });
        popup.show();
    }

    public void Save(View view){
        CurrentAndroidUser user=new CurrentAndroidUser(this);
        Map<String,String> hashMap=new HashMap<>();
        hashMap.put("company_id",String.valueOf(user.Get().getCompany_id()));
        hashMap.put("name",field_name_editText.getText().toString());
        hashMap.put("hour_rate",field_hour_price_editText.getText().toString());
        if(all_day_time_checkBox.isChecked()){
            hashMap.put("open_time","00:00:00");
            hashMap.put("close_time","23:59:00");
        }else{
            hashMap.put("open_time",String.valueOf(startTime.get(Calendar.HOUR_OF_DAY)) + ":00:00");
            hashMap.put("close_time",String.valueOf(finishTime.get(Calendar.HOUR_OF_DAY)) + ":00:00");
        }
        hashMap.put("phone",field_phone_editText.getText().toString());
        hashMap.put("description",field_description_editText.getText().toString());
        hashMap.put("area_x","0");
        hashMap.put("area_y","0");
        if(field_max_capacity_editText.getText().toString().equals("0")){
            showMessageInSnackbar("Field max capacity can't be zero!.");
            return;
        }
        hashMap.put("max_capacity",field_max_capacity_editText.getText().toString());
        hashMap.put("amenities",listToStringArray(selectedAmenitiesIds));
        hashMap.put("games_types",listToStringArray(selectedGamesIds));


        hashMap.put("auto_confirm",auto_confirm_checkBox.isChecked()==true?"1":"0");

        if(ChildsFieldsIds!=null) {
            hashMap.put("ChildsFieldsIds",ChildsFieldsIds.toString());
        }

            List<Image> images = new ArrayList<>();
        for (int i=0;i<imageChoosers.size();i++) {
            if (imageChoosers.get(i).isLoading()) {
                showMessageInToast("Please wait to finish uploading images .");
                return;
            } else if (imageChoosers.get(i).getImage() != null) {
                images.add(imageChoosers.get(i).getImage());
            }
        }
        if (images.size() == 0) {
            showMessageInSnackbar("You should upload at least one image for the field.");
            return;
        }

        ShowProgressDialog();
        if(currentField==null) {
            FieldController.getInstance(mController).createField(hashMap, images, new SuccessCallback<Field>() {
                @Override
                public void OnSuccess(Field result) {
                    HideProgressDialog();
                    List<Field> fields = ((MyApplication) getApplication()).getMyFields();
                    fields.add(result);
                    ((MyApplication) getApplication()).setMyFields(fields);
                    finish();
                }
            });
        }
        else{
            hashMap.put("field_id",String.valueOf(currentField.getField_id()));
            FieldController.getInstance(mController).updateField(hashMap, images, new SuccessCallback<Field>() {
                @Override
                public void OnSuccess(Field result) {
                    HideProgressDialog();
                    List<Field> fields = ((MyApplication) getApplication()).getMyFields();
                    for (int i=0;i<fields.size();i++) {
                        if(fields.get(i).getField_id()==result.getField_id()){
                            fields.remove(i);
                            fields.add(i,result);
                            break;
                        }
                    }
                    ((MyApplication) getApplication()).setMyFields(fields);
                    finish();
                }
            });
        }
    }

    private int PICK_IMAGE_REQUEST = 1;

    private void showFileChooser(final int index) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        CharSequence[]  items;
        if(index==0) items = new CharSequence[] {"Choose an Image"};
        else items = new CharSequence[] {"Choose an Image", "Delete"};

        adb.setItems(items,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(FieldActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(FieldActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                PICK_IMAGE_REQUEST + index);
                    } else {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, PICK_IMAGE_REQUEST + index);
                    }
                }
                else{
                    imageChoosers.get(index).setVisibility(View.GONE);
                    imageChoosers.remove(index);
                }
            }});
        adb.show();


    }

    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int index = requestCode - PICK_IMAGE_REQUEST;
        if (resultCode == RESULT_OK && data != null && data.getData() != null && index >= -1 && index < imageChoosers.size()) {
            Uri filePath = data.getData();
            try {
                    imageChoosers.get(index).StartLoading(filePath);
                    new DownloadImageThread(imageChoosers.get(index),index).execute(filePath) ;

            } catch (Exception e) {
                showMessageInToast("An error occur while uploading car image ! ");
                imageChoosers.get(index).FinishLoading(false, null);
                e.printStackTrace();
            }
        }
    }

    private class DownloadImageThread extends AsyncTask<Uri, Void, Bitmap> {

        ImageChooser imageChooser ;
        int index ;

        DownloadImageThread ( ImageChooser imageChooser , int index ){
            this.imageChooser = imageChooser ;
            this.index = index ;
        }

        @Override
        protected Bitmap doInBackground(Uri... urls) {
            try {
                return CompressImage(urls[0]);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageChooser.getImageView().setImageBitmap(result);
            try {
                final File f = ConvertBitmapToFile(result, index);
                FieldController.getInstance(mController).uploadImage(index, f, new SuccessCallback<Image>() {
                    @Override
                    public void OnSuccess(Image result) {
                        imageChooser.FinishLoading(true, null);
                        if(index==0) {
                            ImageChooser imageChooser = new ImageChooser(FieldActivity.this);
                            ViewGroup layout = (ViewGroup) findViewById(R.id.images_LinearLayout);
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageChooser.getLayoutParams();
                            params.height = 200;
                            params.width = 200;
                            imageChooser.setLayoutParams(params);
                            imageChooser.setLoadingListener(new ImageChooser.LoadingListener() {
                                @Override
                                public void onCancel() {

                                }

                                @Override
                                public void OnChooseImage() {
                                    showFileChooser(imageChoosers.size()-1);
                                }
                            });
                            Picasso.with(FieldActivity.this)
                                    .load(f).memoryPolicy(MemoryPolicy.NO_STORE)
                                    .error(R.mipmap.add_photo)
                                    .placeholder(R.mipmap.add_photo)
                                    .into(imageChooser.getImageView());
                            Picasso.with(FieldActivity.this)
                                    .load(R.mipmap.add_photo).memoryPolicy(MemoryPolicy.NO_STORE)
                                    .error(R.mipmap.add_photo)
                                    .placeholder(R.mipmap.add_photo)
                                    .into(imageChoosers.get(0).getImageView());
                            imageChooser.setImage(result);
                            imageChoosers.add(imageChooser);
                            layout.addView(imageChooser);
                        }
                    }
                }, new FaildCallback() {
                    @Override
                    public void OnFaild(Code errorCode, String Message) {
                        showMessageInToast("An error occure while uploading image !  ");
                        imageChooser.FinishLoading(false, null);
                    }
                });
            }catch (IOException e){
                showMessageInToast("An error occure while uploading image !  ");
                imageChooser.FinishLoading(false, null);
            }

        }
    }

    String getRealPathFromUri(Uri uri) {

        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String yourRealPath = cursor.getString(columnIndex);
            cursor.close();
            return yourRealPath;
        } else {
            //boooo, cursor doesn't have rows ...
        }
        cursor.close();
        return "";
    }

    Bitmap CompressImage(Uri filePath) throws FileNotFoundException {
        File file = new File(getRealPathFromUri(filePath));
        FileInputStream fileInputStream = new FileInputStream(file);
        Bitmap original = BitmapFactory.decodeStream(fileInputStream);
//        original = Bitmap.createScaledBitmap(
//                original, 1000, 1000,true);

        float factorH = 1000 / (float) original.getHeight();
        float factorW = 1000 / (float) original.getWidth();
        float factorToUse = (factorH > factorW) ? factorW : factorH;
        original = Bitmap.createScaledBitmap(original,
                (int) (original.getWidth() * factorToUse),
                (int) (original.getHeight() * factorToUse),
                false);

        return original;
    }

    File ConvertBitmapToFile(Bitmap bitmap, int index) throws IOException {


        File f = new File(getCacheDir(), index + "_image.png");
        if (f.exists()) {
            f.delete();
        }

        ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
        try {
            bmpStream.flush();//to avoid out of memory error
            bmpStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bmpStream);
        byte[] bmpPicByteArray = bmpStream.toByteArray();

        FileOutputStream fo;

        try {
            fo = new FileOutputStream(f);
            fo.write(bmpStream.toByteArray());
            fo.flush();
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return f;

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
