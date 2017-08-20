package com.bluebrand.fieldium.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.IntentCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.view.Player.RegisterActivity;
import com.bluebrand.fieldium.R;

import com.bluebrand.network.Code;
import com.bluebrand.network.Controller;
import com.bluebrand.network.FaildCallback;

/**
 * Created by malek on 5/15/16.
 */
public abstract class MasterFormActivity extends MasterActivity {


    protected View mProgressView;
    protected View mFormView;


    public abstract void submitForm();


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    protected void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_longAnimTime);

            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    //        public   static  boolean   connectGoogle() {
    @Override
    public void defineController() {
        setmController(new Controller(this, new FaildCallback() {
            @Override
            public void OnFaild(Code errorCode, String Message) {
                if (findViewById(R.id.button_submit) != null)
                    findViewById(R.id.button_submit).setEnabled(true);
                if (findViewById(R.id.register_button) != null)
                    findViewById(R.id.register_button).setEnabled(true);
                if (findViewById(R.id.skip_button) != null)
                    findViewById(R.id.skip_button).setEnabled(true);
                if (findViewById(R.id.swipeRefreshLayout) != null)
                    ((SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout)).setRefreshing(false);
                if (findViewById(R.id.progressLoading_city) != null && findViewById(R.id.progressLoading_game) != null) {
                    findViewById(R.id.progressLoading_city).setVisibility(View.GONE);
                    findViewById(R.id.progressLoading_game).setVisibility(View.GONE);
                }
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
                    if (errorCode == Code.NetworkError || errorCode == Code.TimeOutError) {
                        showSnackBar(Html.fromHtml(Message).toString());
                        if (findViewById(R.id.number_buttons_layout) != null) {
                            findViewById(R.id.number_buttons_layout).setVisibility(View.GONE);
                        }
                    } else {
                        Snackbar.make(findViewById(R.id.parent_panel), Html.fromHtml(Message), Snackbar.LENGTH_LONG).show();

                    }
                } else
                    Toast.makeText(getApplicationContext(), Html.fromHtml(Message), Toast.LENGTH_LONG).show();

            }
        }));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                submitForm();
                break;
        }
    }

    @Override
    public void getData() {

    }

    @Override
    public void showData() {

    }

    @Override
    public void assignActions() {
        findViewById(R.id.button_submit).setOnClickListener(this);
    }

}
