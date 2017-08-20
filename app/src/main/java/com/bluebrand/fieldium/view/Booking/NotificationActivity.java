package com.bluebrand.fieldium.view.Booking;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.IntentCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.BookingController;
import com.bluebrand.fieldium.core.controller.PlayerController;
import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.core.model.Notification;
import com.bluebrand.fieldium.core.model.Player;
import com.bluebrand.fieldium.view.MasterActivity;
import com.bluebrand.fieldium.view.Player.RegisterActivity;
import com.bluebrand.fieldium.view.adapter.MyNotificationsAdapter;
import com.bluebrand.network.Code;
import com.bluebrand.network.Controller;
import com.bluebrand.network.FaildCallback;
import com.bluebrand.network.SuccessCallback;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends MasterActivity {
    ArrayList<Notification> notifications;
    ArrayList<Notification> generalNotifications;
    ArrayList<Notification> orderingNotifications;
    ListView mListView;
    String token;
    boolean ordering;
    SharedPreferences sharedPreferences;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        setContentView(R.layout.activity_notification);
        super.onCreate(savedInstanceState);

        setTitle(getResources().getString(R.string.notifications));
        customStyle();
        getData();

    }

    @Override
    protected void getData() {
        showProgress(true);
//        ordering = getIntent().getBooleanExtra("ordering", false);
        RegisterToken();

    }

    @Override
    protected void showData() {
        if (notifications.size() == 0) {
            findViewById(R.id.no_notifications).setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        } else {
            findViewById(R.id.no_notifications).setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            mListView.setAdapter(new MyNotificationsAdapter(getmContext(),
                    R.layout.notification_list_content, notifications));
        }
    }

    public void customStyle() {
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void assignUIRefrences() {
        mListView = (ListView) findViewById(R.id.notification_list);
        mProgressView = (View) findViewById(R.id.proccess_indicator);
        mFormView = (View) findViewById(R.id.form_container);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.form_container);

    }

    @Override
    protected void assignActions() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                RefreshNotifications();

            }
        });
    }

    public void RefreshNotifications() {

        BookingController.getInstance(getmController()).getMyNotifications(token,new SuccessCallback<List<Notification>>() {
            @Override
            public void OnSuccess(List<Notification> result) {
                notifications = new ArrayList<Notification>();
                notifications.addAll(result);
                swipeContainer.setRefreshing(false);
                findViewById(R.id.proccess_indicator).setVisibility(View.GONE);
                showData();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    public void RegisterToken() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = sharedPreferences.getString("token", "123");
        final String sentToken = sharedPreferences.getString("unregistered_user_token_sent_to_server", "");
        final String sentUserToken = sharedPreferences.getString("user_token_sent_to_server", "");
        if (UserUtils.getInstance(this).IsLogged() && !token.equals("123") && !token.equals(sentUserToken)) {
            PlayerController.getInstance(new Controller(getApplicationContext(), new FaildCallback() {
                @Override
                public void OnFaild(Code errorCode, String Message) {
                    sharedPreferences.edit().putString("user_token_sent_to_server", "").apply();
                    if (findViewById(R.id.button_submit) != null)
                        findViewById(R.id.button_submit).setEnabled(true);
                    if (findViewById(R.id.register_button) != null)
                        findViewById(R.id.register_button).setEnabled(true);
                    if (findViewById(R.id.skip_button) != null)
                        findViewById(R.id.skip_button).setEnabled(true);
                    showProgress(false);
                    if (errorCode == Code.AuthenticationError || Message.equals("Not authorized")) {
                        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                        ComponentName cn = intent.getComponent();
                        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                        startActivity(mainIntent);
                        UserUtils.getInstance(getmContext()).SignOut();
                        UserUtils.getInstance(getmContext()).DeletePhone();
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        sharedPreferences.edit().putString("user_token_sent_to_server", "").apply();
                        sharedPreferences.edit().putString("unregistered_user_token_sent_to_server", "").apply();
                        sharedPreferences.edit().putString("file_path", "").apply();

                    } else if (findViewById(R.id.parent_panel) != null) {
                        if (errorCode == Code.NetworkError || errorCode == Code.TimeOutError) {
                            showSnackBar(Html.fromHtml(Message).toString());

                        } else {
                            Snackbar.make(findViewById(R.id.parent_panel), Html.fromHtml(Message), Snackbar.LENGTH_LONG).show();
                        }
                    } else
                        Toast.makeText(getApplicationContext(), Html.fromHtml(Message), Toast.LENGTH_LONG).show();
                }
            })).refreshToken(token, new SuccessCallback<Player>() {
                @Override
                public void OnSuccess(Player result) {
                    final Player player = UserUtils.getInstance(getApplicationContext()).Get();
                    player.setToken(token);
                    UserUtils.getInstance(getApplicationContext()).Save(player);
                    sharedPreferences.edit().putString("user_token_sent_to_server", token).apply();
                    //get data
                    getAllNotifications();

                }
            });
        } else if (!UserUtils.getInstance(this).IsLogged() && !token.equals("123") && !token.equals(sentToken)) {
            PlayerController.getInstance(new Controller(getApplicationContext(), new FaildCallback() {
                @Override
                public void OnFaild(Code errorCode, String Message) {
                    sharedPreferences.edit().putString("unregistered_user_token_sent_to_server", "").apply();
                    if (findViewById(R.id.button_submit) != null)
                        findViewById(R.id.button_submit).setEnabled(true);
                    if (findViewById(R.id.register_button) != null)
                        findViewById(R.id.register_button).setEnabled(true);
                    if (findViewById(R.id.skip_button) != null)
                        findViewById(R.id.skip_button).setEnabled(true);
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
                        } else {
                            Snackbar.make(findViewById(R.id.parent_panel), Html.fromHtml(Message), Snackbar.LENGTH_LONG).show();

                        }
                    } else
                        Toast.makeText(getApplicationContext(), Html.fromHtml(Message), Toast.LENGTH_LONG).show();
                }
            })).unRegisteredUseRefreshToken(token, new SuccessCallback<String>() {
                @Override
                public void OnSuccess(String result) {
                    sharedPreferences.edit().putString("unregistered_user_token_sent_to_server", token).apply();
                    //getdata
                    getAllNotifications();
                }
            });
        } else getAllNotifications();
    }

    public void getAllNotifications() {

     /*   if (UserUtils.getInstance(getmContext()).IsLogged())
            token = UserUtils.getInstance(getmContext()).Get().getToken();
        else {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            token = sharedPreferences.getString("token", "123");
        }*/
        BookingController.getInstance(getmController()).getMyNotifications( token,new SuccessCallback<List<Notification>>() {
            @Override
            public void OnSuccess(List<Notification> result) {
                showProgress(false);
                notifications = new ArrayList<Notification>();
                orderingNotifications = new ArrayList<Notification>();
                generalNotifications = new ArrayList<Notification>();
//                notifications.addAll(result);
             /*   if (ordering)//orderingList
                {
                    for (int i = 0; i < result.size(); i++) {
                        if (result.get(i).getNotification_type() != 3)
                            notifications.add(result.get(i));
                    }
                } else {//generalList
                    for (int i = 0; i < result.size(); i++) {
                        if (result.get(i).getNotification_type() == 3)
                            notifications.add(result.get(i));
                    }
                }*/
                notifications.addAll(result);
                showData();
//                Toast.makeText(NotificationActivity.this, "timeList", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
