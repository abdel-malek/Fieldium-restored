package com.bluebrand.fieldiumadmin.View;

import android.Manifest;
import android.app.AlertDialog;
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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bluebrand.fieldiumadmin.Model.Area;
import com.bluebrand.fieldiumadmin.Model.Company;
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
public class companyActivity extends MasterActivity {
    //UI vars
    EditText comp_name_editText;
    EditText comp_address_editText;
    EditText comp_phone_editText;
    EditText comp_desc_editText;
    ImageChooser cover_imageView;
    ImageChooser logo_Rounded;
    ImageView Map;
    Spinner areas_spinner;
    Company company;
    final int LOGO_UPLOAD=0;
    final int COVER_UPLOAD=1;
    final int SHOW_MAP=10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_company);
        super.onCreate(savedInstanceState);
    }


    @Override
    void getData() {
        company= ((MyApplication)getApplication()).getMyCompanyInfo();
    }

    @Override
    void showData() {
        comp_name_editText.setText(company.getName());
        comp_address_editText.setText(company.getAddress());
        comp_phone_editText.setText(company.getPhone());
        comp_desc_editText.setText(company.getDescription());

        areas_spinner = (Spinner)findViewById(R.id.areas_spinner);
        ArrayAdapter<Area> adapter = new ArrayAdapter<Area>(this, R.layout.field_spinner_dropdown_item, ((MyApplication) getApplication()).getMyAreas());
        adapter.setDropDownViewResource(R.layout.field_spinner_dropdown_item);
        areas_spinner.setAdapter(adapter);
        areas_spinner.setSelection(getSelectedAreaIndex());

        try
        {
            Picasso.with(this)
                    .load(company.getLogo().getUrl()).memoryPolicy(MemoryPolicy.NO_STORE)
                    .transform(new CircleTransform())
                    .error(R.mipmap.add_photo)
                    .placeholder(R.mipmap.add_photo)
                    .into(logo_Rounded.getImageView());

            Picasso.with(this)
                    .load(company.getCover().getUrl()).memoryPolicy(MemoryPolicy.NO_STORE)
                    .error(R.mipmap.add_photo)
                    .placeholder(R.mipmap.add_photo)
                    .into(cover_imageView.getImageView());
        } catch (Exception e) {
        }

    }

    @Override
    void assignUIReferences() {
        findViewById(R.id.imagebutton_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        comp_name_editText=(EditText)findViewById(R.id.comp_name_editText);
        comp_address_editText=(EditText)findViewById(R.id.comp_address_editText);
        comp_phone_editText=(EditText)findViewById(R.id.comp_phone_editText);
        comp_desc_editText=(EditText)findViewById(R.id.comp_desc_editText);
        cover_imageView=(ImageChooser) findViewById(R.id.cover_imageView);
        logo_Rounded=(ImageChooser) findViewById(R.id.logo_Rounded);
        Map=(ImageView)findViewById(R.id.Map);

        logo_Rounded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage(LOGO_UPLOAD);
            }
        });

        cover_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage(COVER_UPLOAD);
            }
        });

        Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(companyActivity.this,
                        MapActivity.class);
                intent.putExtra("company", company);
                startActivityForResult(intent, SHOW_MAP);
            }
        });

    }




    @Override
    void assignActions() {


    }

    @Override
    public void onClick(View view) {

    }

    private int getSelectedAreaIndex(){
        List<Area>areas=((MyApplication)getApplication()).getMyAreas();
        for(int i=0;i<areas.size();i++){
            if(areas.get(i).getId()==company.getArea_id()){
                return i;
            }
        }
        return 0;
    }

    public void Save(View view){
        CurrentAndroidUser user=new CurrentAndroidUser(this);
        Map<String,String> hashMap=new HashMap<>();
       hashMap.put("company_id",String.valueOf(user.Get().getCompany_id()));
        hashMap.put("name",comp_name_editText.getText().toString());
        hashMap.put("phone",comp_phone_editText.getText().toString());
        hashMap.put("description",comp_desc_editText.getText().toString());
        hashMap.put("address",comp_address_editText.getText().toString());
        hashMap.put("longitude",String.valueOf(company.getLongitude()));
        hashMap.put("latitude",String.valueOf(company.getLatitude()));

        company.setArea_id(((MyApplication) getApplication()).getMyAreas().get(areas_spinner.getSelectedItemPosition()).getId());

        hashMap.put("area_id",String.valueOf(company.getArea_id()));
        hashMap.put("image",company.getCover().getName());
        hashMap.put("logo",company.getLogo().getName());

        if (cover_imageView.isLoading()||logo_Rounded.isLoading()) {
            showMessageInToast("Please wait to finish uploading images .");
            return;
        } else if (company.getLogo().getName().equals("")) {
            showMessageInSnackbar("You should set an image for logo.");
            return;
        }

        ShowProgressDialog();
        FieldController.getInstance(mController).updateCompanyInfo(hashMap, new SuccessCallback<Company>() {
                @Override
                public void OnSuccess(Company result) {
                    HideProgressDialog();
                    ((MyApplication) getApplication()).setMyCompanyInfo(result);
                    finish();
                }
            });
    }

    private int PICK_IMAGE_REQUEST = 1;


    private void changeImage(final int index){
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        CharSequence[]  items;
        if((company.getCover().getName().equals("")&&index == LOGO_UPLOAD)||(company.getCover().getName().equals("")&&index==COVER_UPLOAD))
            items = new CharSequence[] {"Choose an Image"};
        else
            items = new CharSequence[] {"Choose an Image", "Delete"};

        adb.setItems(items,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    showFileChooser(index);
                }
                else{
                    if (index == LOGO_UPLOAD) {
                        Picasso.with(companyActivity.this)
                                .load(R.mipmap.add_photo).memoryPolicy(MemoryPolicy.NO_STORE)
                                .transform(new CircleTransform())
                                .error(R.mipmap.add_photo)
                                .placeholder(R.mipmap.add_photo)
                                .into(logo_Rounded.getImageView());
                        company.getLogo().setName("");
                    }else{
                        Picasso.with(companyActivity.this)
                                .load(R.mipmap.add_photo).memoryPolicy(MemoryPolicy.NO_STORE)
                                .error(R.mipmap.add_photo)
                                .placeholder(R.mipmap.add_photo)
                                .into(cover_imageView.getImageView());
                        company.getCover().setName("");
                    }
                }
            }});
        adb.show();
    }

    private void showFileChooser(int index) {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(companyActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(companyActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PICK_IMAGE_REQUEST + index);
        } else {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, PICK_IMAGE_REQUEST + index);
        }
    }

    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int index = requestCode - PICK_IMAGE_REQUEST;
        if (resultCode == RESULT_OK && data != null && data.getData() != null && index >= -1) {
            Uri filePath = data.getData();
            try {
                if(index==LOGO_UPLOAD)
                {
                    logo_Rounded.StartLoading(filePath);
                    new DownloadImageThread(logo_Rounded,index).execute(filePath) ;
                } else{
                    cover_imageView.StartLoading(filePath);
                    new DownloadImageThread(cover_imageView,index).execute(filePath) ;
                }

            } catch (Exception e) {
                if(index==LOGO_UPLOAD) {
                    showMessageInToast("An error occur while uploading logo image! ");
                    logo_Rounded.FinishLoading(false, null);
                    e.printStackTrace();
                }else{
                    showMessageInToast("An error occur while uploading cover image! ");
                    cover_imageView.FinishLoading(false, null);
                    e.printStackTrace();
                }
            }
        }
        else{
            if(resultCode == RESULT_OK && requestCode==SHOW_MAP){
                company.setLongitude(data.getExtras().getDouble("Longitude"));
                company.setLatitude(data.getExtras().getDouble("Latitude"));
                comp_address_editText.setText(data.getExtras().getString("AddressDetails"));
                ((MyApplication) getApplication()).setMyCompanyInfo(company);

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
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            try {
                File f = ConvertBitmapToFile(result, index);
                if(index==LOGO_UPLOAD) {
                    Picasso.with(companyActivity.this)
                            .load(f).memoryPolicy(MemoryPolicy.NO_STORE)
                            .transform(new CircleTransform())
                            .error(R.mipmap.add_photo)
                            .placeholder(R.mipmap.add_photo)
                            .into(imageChooser.getImageView());
                }else{
                    Picasso.with(companyActivity.this)
                            .load(f).memoryPolicy(MemoryPolicy.NO_STORE)
                            .error(R.mipmap.add_photo)
                            .placeholder(R.mipmap.add_photo)
                            .into(imageChooser.getImageView());
                }
                FieldController.getInstance(mController).uploadCompanyImage(index, f, new SuccessCallback<Image>() {
                    @Override
                    public void OnSuccess(Image result) {
                        imageChooser.FinishLoading(true, result);
                        if(index==LOGO_UPLOAD)
                        {
                            company.setLogo(result);
                        }
                        else
                            company.setCover(result);
                    }
                }, new FaildCallback() {
                    @Override
                    public void OnFaild(Code errorCode, String Message) {
                        showMessageInToast("An error occure while uploading image !  ");
                        imageChooser.FinishLoading(false, null);
                        Picasso.with(companyActivity.this)
                                .load(R.mipmap.add_photo).memoryPolicy(MemoryPolicy.NO_STORE)
                                .error(R.mipmap.add_photo)
                                .placeholder(R.mipmap.add_photo)
                                .into(imageChooser.getImageView());
                    }
                });
            }catch (IOException e){
                showMessageInToast("An error occure while uploading image !  ");
                imageChooser.FinishLoading(false, null);
                Picasso.with(companyActivity.this)
                        .load(R.mipmap.add_photo).memoryPolicy(MemoryPolicy.NO_STORE)
                        .error(R.mipmap.add_photo)
                        .placeholder(R.mipmap.add_photo)
                        .into(imageChooser.getImageView());
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
}
