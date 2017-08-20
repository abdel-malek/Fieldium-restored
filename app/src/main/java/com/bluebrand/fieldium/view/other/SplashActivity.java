package com.bluebrand.fieldium.view.other;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


import com.bluebrand.fieldium.R;

import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.view.Booking.HomeActivity;
import com.bluebrand.fieldium.view.Player.RegisterActivity;
import com.bluebrand.fieldium.view.Player.VerficationActivity;

/**
 * Created by user on 12/18/16.
 */
public class SplashActivity extends AppCompatActivity {


    private static int SPLASH_TIME_OUT = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);
        // Obtain the shared Tracker instance.


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nextView();
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    void nextView (){
        Intent intent = new Intent(SplashActivity.this,FirstScreenActivity.class);
        startActivity(intent);
/*
        if (UserUtils.getInstance(this).IsLogged()) {

//            if (!UserUtils.getInstance(this).IsRegisterCompleted()) {
//                Intent intent = new Intent(SplashActivity.this, CompleteRegisterationActivity.class);
//                startActivity(intent);
//            } else {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);}
//        }
    else{
            if ( UserUtils.getInstance(this).IsRegistered()){
                String phone = UserUtils.getInstance(this).GetPhone();
                Intent intent = new Intent(SplashActivity.this, VerficationActivity.class);
                intent.putExtra("mobile", phone);
                intent.putExtra("serverId",UserUtils.getInstance(this).getServerId());
                startActivity(intent);
            }else {
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
            }
        }
   */ }

}
