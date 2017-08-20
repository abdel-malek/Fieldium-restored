package com.bluebrand.fieldiumadmin.View;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bluebrand.fieldiumadmin.controller.CurrentAndroidUser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.tradinos.core.network.Code;
import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;

import com.bluebrand.fieldiumadmin.R;

/**
 * Created by malek on 5/15/16.
 */
public abstract class MasterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    protected int prossessThread = 0;

    Dialog progressDialog = null;
    Context mContext;
    Controller mController;
    int contentViewRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(contentViewRes);
        mContext = this;

        defineController();
        assignUIReferences();
        getData();
        showData();
        assignActions();
    }


    @Override
    public void setContentView(int viewRes) {
        this.contentViewRes = viewRes;
    }

     abstract void getData();

     abstract void showData();

    @Override
    public void setTitle(CharSequence title) {
        showBackButton();
    }


    void showBackButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    abstract void assignUIReferences();

    abstract void assignActions();


    public void defineController() {
        mController = new Controller(this, new FaildCallback() {
            @Override
            public void OnFaild(Code errorCode, String Message) {
                if (findViewById(R.id.parentPanel) != null)
                    Snackbar.make(findViewById(R.id.parentPanel), Html.fromHtml(Message), Snackbar.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), Html.fromHtml(Message), Toast.LENGTH_LONG).show();
                HideProgressDialog();
                if(Message.equals("Not authorized")){
                    new CurrentAndroidUser(mContext).SignOut();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public Controller getmController(){
        return mController;
    }

    public void ShowProgressDialog() {
        prossessThread++;
        if (progressDialog == null) {
            progressDialog = new CustomProgressDialog(this, getResources().getString(R.string.wait), true);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void HideProgressDialog() {
        if (progressDialog!=null) {
            progressDialog.cancel();
        }

    }

    @Override
    protected void onPause() {
        HideProgressDialog();
        super.onPause();
    }

    public void showMessageInToast(String message) {
        Toast.makeText(getApplicationContext(), Html.fromHtml(message), Toast.LENGTH_SHORT).show();
    }

    protected void showMessageInToast(int messageRes) {
        Toast.makeText(getApplicationContext(), messageRes, Toast.LENGTH_SHORT).show();
    }

    protected void showMessageInSnackbar(String message) {
        if (findViewById(R.id.parentPanel) != null)
            Snackbar.make(findViewById(R.id.parentPanel), Html.fromHtml(message), Snackbar.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showMessageInSnackbar(int messageRes) {
        if (findViewById(R.id.parentPanel) != null)
            Snackbar.make(findViewById(R.id.parentPanel), messageRes, Snackbar.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), messageRes, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


}
