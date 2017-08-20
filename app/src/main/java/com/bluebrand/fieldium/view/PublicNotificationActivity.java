package com.bluebrand.fieldium.view;

import android.os.Bundle;
import android.widget.TextView;

import com.bluebrand.fieldium.R;

/**
 * Created by Player on 10/9/2016.
 */
public class PublicNotificationActivity extends MasterFormActivity {
    String notification_text;
    TextView notification_textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        setContentView(R.layout.activity_public_notification);
        super.onCreate(savedInstanceState);

        getData();
        showData();

    }

    @Override
    public void getData() {
        notification_text = getIntent().getStringExtra("notification_text");
    }

    @Override
    public void showData() {
        notification_textView.setText(notification_text);
    }
    @Override
    public void submitForm() {
        finish();
    }

    @Override
    public void assignUIRefrences() {
        notification_textView = (TextView) findViewById(R.id.notificationTextView);
    }
}
