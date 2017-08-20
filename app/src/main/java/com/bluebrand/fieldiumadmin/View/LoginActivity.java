package com.bluebrand.fieldiumadmin.View;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.bluebrand.fieldiumadmin.Model.AppInfo;
import com.bluebrand.fieldiumadmin.Model.User;
import com.bluebrand.fieldiumadmin.R;
import com.bluebrand.fieldiumadmin.controller.CurrentAndroidUser;
import com.bluebrand.fieldiumadmin.controller.UserController;
import com.google.firebase.iid.FirebaseInstanceId;
import com.tradinos.core.network.SuccessCallback;

public class LoginActivity extends MasterActivity {

    CurrentAndroidUser user;
    private EditText editText_email;
    private EditText editText_password;
    private Intent intent;
    private boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        BitmapFactory.Options myOptions = new BitmapFactory.Options();
        myOptions.inDither = true;
        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        myOptions.inDither = false;
        myOptions.inPurgeable = true;
        Bitmap preparedBitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.background, myOptions);
        Drawable background = new BitmapDrawable(preparedBitmap);
        findViewById(R.id.login_background)
                .setBackgroundDrawable(background);
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_password = (EditText) findViewById(R.id.editText_password);
        user = new CurrentAndroidUser(this);
        connected = isNetworkAvailable();
        checkPlayServices();
        if (user.IsLogged()) {
            editText_email.setText(user.Get().getUsername());
            editText_password.setText(user.Get().getPassword());
            if (connected) {
                ShowProgressDialog();
                AfterLogin(user.Get());
            }
        }

    }

    @Override
    public void getData() {

    }

    @Override
    public void showData() {

    }

    @Override
    void assignUIReferences() {

    }

    @Override
    void assignActions() {

    }

    @Override
    public void onClick(View v) {

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private Boolean ValidLoginInput(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            showMessageInSnackbar(getResources().getString(R.string.error_invalid_email));
            return false;
        } else if (TextUtils.isEmpty(password)) {
            showMessageInSnackbar(getResources().getString(R.string.error_incorrect_password));
            return false;
        }
        return true;
    }


    public void onLoginClick(View view) {
        final String email = editText_email.getText().toString();
        final String password = editText_password.getText().toString();
        connected = isNetworkAvailable();
        if (!ValidLoginInput(email, password)) {
            return;
        }
        connected = isNetworkAvailable();
        if (connected && checkPlayServices()) {
            if (user.Get() != null) {
                if (password.equals(user.Get().getPassword())) {
                    ShowProgressDialog();
                    AfterLogin(user.Get());
                } else {
                    ShowProgressDialog();
                    new UserController(mController).Login(email, password,
                            new SuccessCallback<User>() {
                                @Override
                                public void OnSuccess(User result) {
                                    AfterLogin(result);
                                }
                            });
                }
            } else {
                ShowProgressDialog();
                new UserController(mController).Login(email, password,
                        new SuccessCallback<User>() {
                            @Override
                            public void OnSuccess(User result) {
                                AfterLogin(result);
                            }
                        });
            }
        } else {
            showMessageInSnackbar(R.string.connection_problem);
        }
    }

    void AfterLogin(final User user) {
        new CurrentAndroidUser(this).Save(user);
        intent = new Intent(this, MainActivity.class);
        UserController.getInstance(mController).getInfo(new SuccessCallback<AppInfo>() {
            @Override
            public void OnSuccess(AppInfo result) {
                new CurrentAndroidUser(LoginActivity.this).SaveAppInfo(result);

                try {
                    final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                    UserController.getInstance(mController).updateToken(user.getId(), refreshedToken == null ? "1" : refreshedToken, new SuccessCallback<User>() {
                        @Override
                        public void OnSuccess(User result) {
                            SharedPreferences preferences = getSharedPreferences("UserToken", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("token", refreshedToken);
                            editor.commit();
                            HideProgressDialog();
                            startActivity(intent);
                            finish();
                        }
                    });
                } catch (Exception e) {
                    HideProgressDialog();
                    showMessageInToast("Error with UserToken");
                }
            }
        });
    }

}
