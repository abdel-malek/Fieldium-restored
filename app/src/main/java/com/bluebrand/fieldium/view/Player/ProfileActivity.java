package com.bluebrand.fieldium.view.Player;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.IntentCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.BookingController;
import com.bluebrand.fieldium.core.controller.PlayerController;
import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.core.model.Game;
import com.bluebrand.fieldium.core.model.Player;
import com.bluebrand.fieldium.view.InvalidInputException;
import com.bluebrand.fieldium.view.MasterActivity;
import com.bluebrand.fieldium.view.adapter.PreferredGamesAdapter;
import com.bluebrand.fieldium.view.other.ImageChooser;
import com.bluebrand.network.Code;
import com.bluebrand.network.Controller;
import com.bluebrand.network.FaildCallback;
import com.bluebrand.network.SuccessCallback;
import com.bumptech.glide.Glide;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity
        extends MasterActivity {


    EditText editText_name;
    EditText editText_address;
    TextView editText_phone;
    EditText editText_email;
    Player player;
    ListView mListView;
    ArrayList<Game> games;
    ImageChooser imageChooser_profileImage;
    TextView deactivate_account;
    AlertDialog alertDialog;
    Uri filePath;
    int index;
    SharedPreferences sharedPreferences;
    boolean imageUpdated = false;
    Bitmap original;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        setContentView(R.layout.activity_profile);
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getmContext());

        getData();
//        showData();
        setTitle(getTitle());

    }

    @Override
    protected void getData() {
        games = new ArrayList();
//        final ArrayList<Game> result = new ArrayList<>();
//        Game mGame=new Game();
//        mGame.setName("Succor");
//        mGame.setId(1);
//        result.add(mGame);
//        mGame=new Game();
//        mGame.setName("Tennis");
//        mGame.setId(2);
//        result.add(mGame);

        player = UserUtils.getInstance(this).Get();
        games.addAll(player.getGames());
        showData();

        BookingController.getInstance(getmController()).getgames(new SuccessCallback<List<Game>>() {
            @Override
            public void OnSuccess(final List<Game> result) {
                getCurrentFocus().clearFocus();

                for (int i = 0; i < games.size(); i++) {
                    for (int i1 = 0; i1 < result.size(); i1++) {
                        if (result.get(i1).getId() == games.get(i).getId()) {
                            result.get(i1).setSelected(true);
                            break;
                        }
                    }
                }
                final PreferredGamesAdapter preferredGamesAdapter = new PreferredGamesAdapter
                        (getmContext(), R.layout.preferred_game_item, result);
                mListView.setAdapter(preferredGamesAdapter);

            }
        });
    }

    @Override
    protected void showData() {
        if (!player.getPhone().equals("null"))
            editText_phone.setText(player.getPhone());
        if (!player.getName().equals("null"))
            editText_name.setText(player.getName());
        if (!player.getAddress().equals("null"))
            editText_address.setText(player.getAddress());
        if (!player.getEmail().equals("null"))
            editText_email.setText(player.getEmail());
//        Bitmap bitmap;
//        try {
        imageChooser_profileImage.getImageView().setImageResource(R.mipmap.circle_mask);
        if (!player.getProfileImage().getName().equals("deleted") && !player.getProfileImage().getName().equals("") && !player.getProfileImage().getName().equals("null")) {
            File f = new File(getFilesDir(), "image.jpg");
//                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
//                imageChooser_profileImage.getImageView().setImageBitmap(bitmap);
            Picasso.with(getmContext()).load("file://" + f).memoryPolicy(MemoryPolicy.NO_STORE).into(imageChooser_profileImage.getImageView());
        } else
            imageChooser_profileImage.getImageView().setImageResource(R.drawable.profile_blue);

//
//        } catch (FileNotFoundException e) {
//            imageChooser_profileImage.getImageView().setImageResource(R.drawable.profile_blue);
//        }

//        String profileImage = sharedPreferences.getString("file_path", "");
//        if (!profileImage.equals("")) {
//            try {
//            try {
//                try {
       /* Picasso.with(getmContext()).load(Uri.parse(profileImage)*//*ConvertBitmapToFile(CompressImage(Uri.parse(profileImage)),0)*//*)
                .placeholder(R.drawable.profile_blue).error(R.drawable.profile_blue)
                .into(imageChooser_profileImage.getImageView());*/
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } catch (FileNotFoundException e) {/*ConvertBitmapToFile(CompressImage(Uri.parse(profileImage)),0)*/
//                e.printStackTrace();
//            }
//                imageChooser_profileImage.getImageView().setImageBitmap(CompressImage(Uri.parse(profileImage)));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }


    }

    @Override
    public void assignUIRefrences() {
        editText_address = (EditText) findViewById(R.id.editText_address);
        editText_phone = (TextView) findViewById(R.id.editText_phone_number);
        editText_name = (EditText) findViewById(R.id.editText_name);
        editText_email = (EditText) findViewById(R.id.editText_email);
        mFormView = findViewById(R.id.form_container);
        mProgressView = findViewById(R.id.proccess_indicator);
        mListView = (ListView) findViewById(R.id.preferredGamesListView);
        deactivate_account = (TextView) findViewById(R.id.deactivate_account);
        imageChooser_profileImage = (ImageChooser) findViewById(R.id.chooseImage_profile);
    }

    @Override
    protected void assignActions() {
        findViewById(R.id.button_submit).setOnClickListener(this);
        deactivate_account.setOnClickListener(this);


        imageChooser_profileImage.setLoadingListener(new ImageChooser.LoadingListener() {
            @Override
            public void onCancel() {

            }

            @Override
            public void OnChooseImage() {
                showFileChooser(-1);
            }
        });
    }

    private void showFileChooser(final int index) {
        if (imageChooser_profileImage.isLoading()) {
            showMessageInToast(getResources().getString(R.string.please_wait_while_uploading_finish));
        } else {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            CharSequence[] items;
            if (player.getProfileImage().getName().equals("") || player.getProfileImage().getName().equals("deleted") || player.getProfileImage().getName().equals("null"))
                items = new CharSequence[]{getResources().getString(R.string.choose_Image), getResources().getString(R.string.take_picture)};
            else
                items = new CharSequence[]{getResources().getString(R.string.choose_Image), getResources().getString(R.string.take_picture), getResources().getString(R.string.delete)};

            adb.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(ProfileActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(ProfileActivity.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    PICK_IMAGE_REQUEST);
                        } else {
                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            photoPickerIntent.setType("image/jpg");
                            startActivityForResult(photoPickerIntent, PICK_IMAGE_REQUEST + index);
                        }
                    } else if (which == 1) {
                        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(ProfileActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(ProfileActivity.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    PICK_CAMERA_IMAGE_REQUEST);
                        } else {
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            startActivityForResult(intent, 22);
                        }
                    } else {
                        imageChooser_profileImage.getImageView().setImageResource(R.drawable.profile_blue);
                        filePath = null;
                        imageChooser_profileImage.StartLoading(filePath);

                        new DownloadImageThread(imageChooser_profileImage, index).execute(filePath);
                        imageUpdated = true;
                        player.getProfileImage().setName("deleted");
                    }
                }
            });
            adb.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == PICK_IMAGE_REQUEST) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, requestCode);
            } else if (requestCode == PICK_CAMERA_IMAGE_REQUEST) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, 22);
            }

        }
    }

    @Override
    public void onClick(View v) {
        if (imageChooser_profileImage.isLoading()) {
            showMessageInToast(getResources().getString(R.string.please_wait_while_uploading_finish));
        } else {
            switch (v.getId()) {
                case R.id.button_submit:
                    editText_name.setError(null);
                    editText_phone.setError(null);
                    editText_email.setError(null);
                    findViewById(R.id.button_submit).setEnabled(false);
                    String address = editText_address.getText().toString();
                    String phone = editText_phone.getText().toString();
                    String name = editText_name.getText().toString();
                    String email = editText_email.getText().toString();

                    EditText focusView = null;
                    try {
                        focusView = editText_name;
                        isNameValid(name);
                        focusView = editText_email;
                        isEmailValid(email);
                        deactivate_account.setVisibility(View.GONE);
                        showProgress(true);

                        player.setAddress(address);
                        player.setPhone(phone);
                        player.setName(name);
                        player.setEmail(email);

                        player.setGames(games);

              /*      if (imageUpdated) {//to don't process image or video (imageUpdated=false)
                        try {
                            if (player.getProfileImage().getName().equals("deleted")) {
                                filePath = null;
                                UserUtils.getInstance(getmContext()).Save(player);
                            }
                            imageChooser_profileImage.StartLoading(filePath);
                            new DownloadImageThread(imageChooser_profileImage, index).execute(filePath);

                        } catch (Exception e) {
                            showMessageInToast("An error occur while uploading image ! ");
                            findViewById(R.id.button_submit).setEnabled(true);
                            imageChooser_profileImage.FinishLoading(false, null);
                            e.printStackTrace();
                        }
                    } else {*/
                        updatePlayerInfo();

//                    }
                    } catch (InvalidInputException e) {
                        focusView.requestFocus();
                        focusView.setError(e.getMessage());
                        findViewById(R.id.button_submit).setEnabled(true);
                    }
                    break;
                case R.id.deactivate_account:
                    showDeactivateAccountDialog();
                    break;

            }
        }
    }

    private void isNameValid(String name) throws InvalidInputException {
        if (TextUtils.isEmpty(name))
            throw new InvalidInputException(getResources().getString(R.string.error_field_required));
    }

    private void isEmailValid(String email) throws InvalidInputException {
      /*  if (android.text.TextUtils.isEmpty(email))
            throw new InvalidInputException(getResources().getString(R.string.error_field_required));*/
        if (!TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches())
            throw new InvalidInputException(getResources().getString(R.string.error_invalid_email));

    }

    public void addGame(Game game) {

        games.add(game);

    }

    public void removeGame(Game game) {
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).getId() == game.getId()) {
                games.remove(i);
                break;
            }
        }
//        games.remove(game);

    }

    File ConvertBitmapToFile(Bitmap bitmap, File fileDir) throws IOException {


        File f = new File(fileDir/*getFilesDir()*/, /*index +*/ "image.jpg");
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
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bmpStream);
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
        Bitmap rotatedImage;
        File file = new File(getRealPathFromUri(filePath));
        FileInputStream fileInputStream = new FileInputStream(file);
        original = BitmapFactory.decodeStream(fileInputStream);
        //        original = Bitmap.createScaledBitmap(
        //                original, 1000, 1000,true);
        if (original != null) {
            float factorH = 1000 / (float) original.getHeight();
            float factorW = 1000 / (float) original.getWidth();
//            float factorToUse = (factorH > factorW) ? factorW : factorH;
            original = Bitmap.createScaledBitmap(original,
                    (int) (original.getWidth() * factorW),
                    (int) (original.getHeight() * factorH),
                    false);
        }

        rotatedImage = rotateImage(file, original);
        return rotatedImage;
    }


    ///////////////
    private class DownloadImageThread extends AsyncTask<Uri, Void, Bitmap> {

        ImageChooser imageChooser;
        int index;

        DownloadImageThread(ImageChooser imageChooser, int index) {
            this.imageChooser = imageChooser;
            this.index = index;
        }

        @Override
        protected Bitmap doInBackground(Uri... urls) {
            try {
                if (urls[0] != null)
                    return CompressImage(urls[0]);
                else return null;
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(final Bitmap result) {
//              imageChooser.getImageView().setImageBitmap(result);
            try {
                File f = null;
                if (result != null)
                    f = ConvertBitmapToFile(result, getCacheDir());

                final File finalF = f;
                PlayerController.getInstance(new Controller(getApplicationContext(), new FaildCallback() {
                    @Override
                    public void OnFaild(Code errorCode, String Message) {
//                        showMessageInToast("An error occure while uploading image !  ");
                        imageChooser.FinishLoading(false, null);
                        onFaildUpload(errorCode, Message);
                    }
                })).uploadImage(player, f, new SuccessCallback<Player>() {
                    @Override
                    public void OnSuccess(Player mPlayer) {
                        try {
                            if (!mPlayer.getProfileImage().getName().equals("")) {
                                ConvertBitmapToFile(result, getFilesDir());

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (mPlayer.getProfileImage().getName().equals(""))
                            showMessageInToast(R.string.delete_profile_img);
                        else
                            showMessageInToast(R.string.update_profile_img);
                        // result.setPassword(player.getPassword());
                        mPlayer.setServerId(player.getServerId());
                        mPlayer.setToken(player.getToken());
                        UserUtils.getInstance(getmContext()).Save(mPlayer);
                        imageChooser.FinishLoading(true, mPlayer.getProfileImage());
                        Picasso.with(ProfileActivity.this)
                                .load(finalF).memoryPolicy(MemoryPolicy.NO_STORE)
                                .error(R.drawable.profile_blue)
                                .placeholder(R.drawable.profile_blue)
                                .into(imageChooser.getImageView());
                      /*  try {
                            imageChooser_profileImage.getImageView().setImageBitmap(CompressImage(filePath));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }*/
//                        finish();

                    }
                /*}, new FaildCallback() {
                    @Override
                    public void OnFaild(Code errorCode, String Message) {

                        showMessageInToast("An error occure while uploading image !  ");
                        imageChooser.FinishLoading(false, null);
//                        ProfileActivity.super.defineController();

                    }*/
                });
            } catch (IOException e) {
                showMessageInToast("An error occurred while uploading image !  ");
                imageChooser.FinishLoading(false, null);
                findViewById(R.id.button_submit).setEnabled(true);

            }

        }

    }

    private int PICK_IMAGE_REQUEST = 1;
    private int PICK_CAMERA_IMAGE_REQUEST = 2;

    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        index = requestCode - PICK_IMAGE_REQUEST;
        if (resultCode == RESULT_OK && data != null && data.getData() != null /*&& index >= -1 && index < imageChoosers.size()*/) {
            filePath = data.getData();
            if (filePath.toString().contains("/video/")) {
                Toast.makeText(ProfileActivity.this, getResources().getString(R.string.please_select_image), Toast.LENGTH_SHORT).show();
                imageChooser_profileImage.getImageView().setImageResource(R.drawable.profile_blue);

                imageUpdated = false;//to don't process video
            } else {
//                try {
                imageChooser_profileImage.StartLoading(filePath);
//                imageChooser_profileImage.StartLoading(filePath);
                new DownloadImageThread(imageChooser_profileImage, index).execute(filePath);
//                    imageChooser_profileImage.getImageView().setImageBitmap(CompressImage(filePath));
                player.getProfileImage().setName("image.jpg");
                imageUpdated = true;
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
            }
        }
    }

    public void updatePlayerInfo() {
        PlayerController.getInstance(getmController()).updateInfo(player, new SuccessCallback<Player>() {
            @Override
            public void OnSuccess(Player result) {
                showMessageInToast(R.string.update_profile_msg);
                // result.setPassword(player.getPassword());
                result.setToken(player.getToken());
                result.setServerId(player.getServerId());
                UserUtils.getInstance(getmContext()).Save(result);
//                                imageChooser.FinishLoading(true, result.getProfileImage());
                finish();
            }
        });
    }

    public void showDeactivateAccountDialog() {
        alertDialog = new AlertDialog.Builder(getmContext())
                .setTitle(getResources().getString(R.string.alert))
                .setMessage(getResources().getString(R.string.deactivate_confirmation))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        findViewById(R.id.deactivate_account).setEnabled(false);
                        showProgress(true);

                        PlayerController.getInstance(getmController()).DeactivateUser(new SuccessCallback<String>() {
                            @Override
                            public void OnSuccess(String result) {
                                showProgress(false);
                                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                                ComponentName cn = intent.getComponent();
                                Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                                startActivity(mainIntent);
                                UserUtils.getInstance(getmContext()).SignOut();
                                UserUtils.getInstance(getmContext()).DeletePhone();
                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                sharedPreferences.edit().putString("user_token_sent_to_server", "").apply();
                                sharedPreferences.edit().putString("file_path", "").apply();
                            }
                        });
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        findViewById(R.id.deactivate_account).setEnabled(true);

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        findViewById(R.id.deactivate_account).setEnabled(true);
                    }
                })
                .show();
    }

    public Bitmap rotateImage(File curFile, Bitmap bitmap) {
//        File curFile = new File("path-to-file"); // ... This is an image file from my device.
        Bitmap rotatedBitmap;

        try {
            ExifInterface exif = new ExifInterface(curFile.getPath());
            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int rotationInDegrees = exifToDegrees(rotation);
            Matrix matrix = new Matrix();
            if (rotation != 0f) {
                matrix.preRotate(rotationInDegrees);
            }
            rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            return rotatedBitmap;

        } catch (IOException ex) {
            return bitmap;
        }
    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    public void onFaildUpload(Code errorCode, String Message) {
        if (findViewById(R.id.swipeRefreshLayout) != null)
            ((SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout)).setRefreshing(false);
        if (findViewById(R.id.deactivate_account) != null) {
            findViewById(R.id.deactivate_account).setVisibility(View.VISIBLE);
            findViewById(R.id.deactivate_account).setEnabled(true);
        }
        if (findViewById(R.id.button_submit) != null)
            findViewById(R.id.button_submit).setEnabled(true);
        if (mFormView != null && mProgressView != null)
            showProgress(false);
        if (errorCode == Code.AuthenticationError || Message.equals("Not authorized")) {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            ComponentName cn = intent.getComponent();
            Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
            startActivity(mainIntent);
            UserUtils.getInstance(getmContext()).SignOut();
            UserUtils.getInstance(getmContext()).DeletePhone();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            sharedPreferences.edit().putString("unregistered_user_token_sent_to_server", "").apply();
            sharedPreferences.edit().putString("user_token_sent_to_server", "").apply();
            sharedPreferences.edit().putString("file_path", "").apply();

        } else if (findViewById(R.id.parent_panel) != null) {
            if (errorCode == Code.NetworkError || errorCode == Code.TimeOutError)
                showSnackBar(Html.fromHtml(Message).toString());
            else if (Message.equals("Invalid date") && findViewById(R.id.availableTimes_listView) != null) {
                findViewById(R.id.availableTimes_listView).setVisibility(View.GONE);
                Snackbar.make(findViewById(R.id.parent_panel), Html.fromHtml(Message), Snackbar.LENGTH_LONG).show();
            } else
                Snackbar.make(findViewById(R.id.parent_panel), Html.fromHtml(Message), Snackbar.LENGTH_LONG).show();

        } else
            Toast.makeText(getApplicationContext(), Html.fromHtml(Message), Toast.LENGTH_LONG).show();
    }
}
