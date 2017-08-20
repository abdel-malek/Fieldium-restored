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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.BookingController;
import com.bluebrand.fieldium.core.controller.PlayerController;
import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.core.model.Notification;
import com.bluebrand.fieldium.core.model.Player;
import com.bluebrand.fieldium.core.model.Voucher;
import com.bluebrand.fieldium.view.MasterActivity;
import com.bluebrand.fieldium.view.Player.RegisterActivity;
import com.bluebrand.fieldium.view.adapter.MyNotificationsAdapter;
import com.bluebrand.fieldium.view.adapter.MyVouchersAdapter;
import com.bluebrand.network.Code;
import com.bluebrand.network.Controller;
import com.bluebrand.network.FaildCallback;
import com.bluebrand.network.SuccessCallback;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

public class MyVouchersActivity extends MasterActivity {
    ArrayList<Voucher> mVouchers;
    ListView mListView;
    String token;
    boolean ordering;
    SharedPreferences sharedPreferences;
    private SwipeRefreshLayout swipeContainer;
    int voucherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        setContentView(R.layout.activity_vouchers);
        super.onCreate(savedInstanceState);

        setTitle(getResources().getString(R.string.my_vouchers));
        customStyle();
        getData();

    }

    @Override
    protected void getData() {
        voucherId = getIntent().getIntExtra("voucher_id", -1);
        showProgress(true);
//        ordering = getIntent().getBooleanExtra("ordering", false);
        RegisterToken();
    }

    @Override
    protected void showData() {
        if (mVouchers.size() == 0) {
            findViewById(R.id.no_vouchers).setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        } else {
            findViewById(R.id.no_vouchers).setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);

            if (voucherId != -1) {
//                int h1 = mListView.getHeight();
//                int h2 = mListView.getSelectedView().getHeight();
                for (int i = 0; i < mVouchers.size(); i++) {
                    if (mVouchers.get(i).getId() == voucherId) {
//                        mListView.setSelection(i);
                        voucherId = i;
//                        mListView.smoothScrollToPositionFromTop(i/*, h1 / 2 - h2 / 2*/, 500);
                        // toggle clicked cell state
//                        ((FoldingCell) mListView.getSelectedView()).toggle(false);
                        // register in adapter that state for selected cell is toggled
//                        myVouchersAdapter.registerToggle(i);
                        break;
                    }
                }
            }
            final MyVouchersAdapter myVouchersAdapter = new MyVouchersAdapter(getmContext(),
                    R.layout.voucher_item, mVouchers, voucherId);
            mListView.setAdapter(myVouchersAdapter);
//            mListView.setSelection(7);
        /*    mListView.performItemClick(
                    mListView.getAdapter().getView(7, null, null),
                    7,
                    mListView.getAdapter().getItemId(7));*/
            /*getViewByPosition(7,mListView).addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                    ((FoldingCell)view).toggle(false);
                    myVouchersAdapter.registerToggle(7);

                }
            });*/
//            mListView.get.callOnClick();
            if (voucherId != -1) {
                mListView.smoothScrollToPosition(voucherId);
                mListView.setSelection(voucherId);
//                mListView.setSelection(voucherId);
                /* mListView.performItemClick(
                        mListView.getAdapter().getView(voucherId, null, null),
                        voucherId,
                        mListView.getAdapter().getItemId(voucherId));*/
//                myVouchersAdapter.getCell().toggle(false);
//                myVouchersAdapter.registerToggle(voucherId);
            }
           /* if (voucherId != 0) {
//                int h1 = mListView.getHeight();
//                int h2 = mListView.getSelectedView().getHeight();
                for (int i = 0; i < mVouchers.size(); i++) {
                    if (mVouchers.get(i).getId() == voucherId) {
                        mListView.setSelection(i);

                        mListView.smoothScrollToPositionFromTop(i*//*, h1 / 2 - h2 / 2*//*, 500);
                        // toggle clicked cell state
                        ((FoldingCell) mListView.getSelectedView()).toggle(false);
                        // register in adapter that state for selected cell is toggled
                        myVouchersAdapter.registerToggle(i);
                        break;
                    }
                }
            }*/
       /*     mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    // toggle clicked cell state
                    ((FoldingCell) view).toggle(false);
                    // register in adapter that state for selected cell is toggled
                    myVouchersAdapter.registerToggle(pos);
                }
            });*/

        /*    mVouchers.get(0).setRequestBtnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "CUSTOM HANDLER FOR FIRST BUTTON", Toast.LENGTH_SHORT).show();
                }
            });*/
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
        mListView = (ListView) findViewById(R.id.vouchers_list);
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
                getMyVouchers();

            }
        });
    }

   /* public void RefreshVouchers() {

        BookingController.getInstance(getmController()).getMyNotifications(new SuccessCallback<List<Notification>>() {
            @Override
            public void OnSuccess(List<Notification> result) {
                mVouchers = new ArrayList<Notification>();
                mVouchers.addAll(result);
                swipeContainer.setRefreshing(false);
                findViewById(R.id.proccess_indicator).setVisibility(View.GONE);
                showData();
            }
        });
    }*/

    @Override
    public void onClick(View v) {

    }

    public void RegisterToken() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String token = sharedPreferences.getString("token", "123");
//        final String sentToken = sharedPreferences.getString("unregistered_user_token_sent_to_server", "");
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
            })).refreshToken(token, new SuccessCallback<Player>() {
                @Override
                public void OnSuccess(Player result) {
                    final Player player = UserUtils.getInstance(getApplicationContext()).Get();
                    player.setToken(token);
                    UserUtils.getInstance(getApplicationContext()).Save(player);
                    sharedPreferences.edit().putString("user_token_sent_to_server", token).apply();
                    //get data
                    getMyVouchers();

                }
            });
        } /*else if (!UserUtils.getInstance(this).IsLogged() && !token.equals("123") && !token.equals(sentToken)) {
            PlayerController.getInstance(*//*new Controller(getApplicationContext(), new FaildCallback() {
                @Override
                public void OnFaild(Code errorCode, String Message) {
                    sharedPreferences.edit().putString("unregistered_user_token_sent_to_server", "").apply();
                }
            }))*//*new Controller(getApplicationContext(), new FaildCallback() {
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
            })).unRegisteredUseRefreshToken(token, new SuccessCallback<String>() {
                @Override
                public void OnSuccess(String result) {
                    sharedPreferences.edit().putString("unregistered_user_token_sent_to_server", token).apply();
                    //getdata
                    getMyVouchers();
                }
            });
        } */ else getMyVouchers();
    }

    public void getMyVouchers() {

     /*   if (UserUtils.getInstance(getmContext()).IsLogged())
            token = UserUtils.getInstance(getmContext()).Get().getToken();
        else {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            token = sharedPreferences.getString("token", "123");
        }*/
        BookingController.getInstance(getmController()).getMyVouchers(new SuccessCallback<List<Voucher>>() {
            @Override
            public void OnSuccess(List<Voucher> result) {
                showProgress(false);
                swipeContainer.setRefreshing(false);
                mVouchers = new ArrayList<Voucher>();
                mVouchers.addAll(result);
                showData();
              /*  orderingNotifications = new ArrayList<Notification>();
                generalNotifications = new ArrayList<Notification>();*/
//                mVouchers.addAll(result);
             /*   if (ordering)//orderingList
                {
                    for (int i = 0; i < result.size(); i++) {
                        if (result.get(i).getNotification_type() != 3)
                            mVouchers.add(result.get(i));
                    }
                } else {//generalList
                    for (int i = 0; i < result.size(); i++) {
                        if (result.get(i).getNotification_type() == 3)
                            mVouchers.add(result.get(i));
                    }
                }*/


//                Toast.makeText(NotificationActivity.this, "timeList", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}
