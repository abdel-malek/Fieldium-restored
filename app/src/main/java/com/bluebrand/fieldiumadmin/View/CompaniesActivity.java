package com.bluebrand.fieldiumadmin.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluebrand.fieldiumadmin.Model.Company;
import com.bluebrand.fieldiumadmin.MyApplication;
import com.bluebrand.fieldiumadmin.R;
import com.bluebrand.fieldiumadmin.View.Adapter.CompaniesAdapter;
import com.bluebrand.fieldiumadmin.View.Utils.CircleTransform;
import com.bluebrand.fieldiumadmin.controller.CurrentAndroidUser;
import com.bluebrand.fieldiumadmin.controller.FieldController;
import com.bluebrand.fieldiumadmin.controller.UserController;
import com.etsy.android.grid.StaggeredGridView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.tradinos.core.network.SuccessCallback;

import java.util.List;

/**
 * Created by r.desouki on 2/22/2017.
 */

public class CompaniesActivity extends MasterActivity implements NavigationView.OnNavigationItemSelectedListener{
    StaggeredGridView gridView;
    CompaniesAdapter companiesAdapter;
    EditText search_company;
    ImageButton search_button;

    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_companies);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.nav_pending_reservations){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }else
        if(id==R.id.nav_approved_reservations){
            drawer.closeDrawers();
        }else
        if (id == R.id.nav_logout) {
            new android.support.v7.app.AlertDialog.Builder(this)
                    .setMessage("Do you really want to logout?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ShowProgressDialog();
                            SharedPreferences preferences = getSharedPreferences("UserToken", Context.MODE_PRIVATE);
                            String deviceToken=preferences.getString("token","");
                            UserController.getInstance(mController).Logout(deviceToken, new SuccessCallback<String>() {
                                @Override
                                public void OnSuccess(String result) {
                                    CurrentAndroidUser user=new CurrentAndroidUser(CompaniesActivity.this);
                                    user.SignOut();
                                    Intent intent = new Intent(CompaniesActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });

                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        }
        else if(id == R.id.nav_about){
            Intent intent = new Intent(this,
                    AboutActivity.class);
            startActivityForResult(intent, 1);
        }


        return true;
    }

    @Override
    void getData() {
        ShowProgressDialog();
        FieldController.getInstance(mController).getAllCompanies(new SuccessCallback<List<Company>>() {
            @Override
            public void OnSuccess(List<Company> result) {
                companiesAdapter=new CompaniesAdapter(CompaniesActivity.this,R.layout.company_item,result,gridView);
                gridView.setAdapter(companiesAdapter);
                HideProgressDialog();
            }
        });
    }

    @Override
    void showData() {

    }

    @Override
    void assignUIReferences() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        gridView = (StaggeredGridView) findViewById(R.id.grid_view);
        search_company=(EditText)findViewById(R.id.search_company);
        search_button=(ImageButton)findViewById(R.id.search_button);

        View navHeaderView= navigationView.getHeaderView(0);
        TextView tvHeaderName= (TextView) navHeaderView.findViewById(R.id.nav_comp_textView);
        tvHeaderName.setText("Support Admin");

        ImageView imageView=(ImageView)navHeaderView.findViewById(R.id.nav_comp_imageView);
        Picasso.with(this)
                .load(R.drawable.fieldium_logo).memoryPolicy(MemoryPolicy.NO_STORE)
                .transform(new CircleTransform())
                .error(R.mipmap.default_image)
                .placeholder(R.mipmap.default_image)
                .into(imageView);
    }

    @Override
    void assignActions() {
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });
        search_company.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });
    }

    private void performSearch(){
        ShowProgressDialog();
        String name=search_company.getText().toString();
        if(name.equals("")){
            FieldController.getInstance(mController).getAllCompanies(new SuccessCallback<List<Company>>() {
                @Override
                public void OnSuccess(List<Company> result) {
                    companiesAdapter.setCompanies(result);
                    companiesAdapter.notifyDataSetChanged();
                    HideProgressDialog();
                }
            });
        }else {
            FieldController.getInstance(mController).searchCompanies(name, new SuccessCallback<List<Company>>() {
                @Override
                public void OnSuccess(List<Company> result) {
                    companiesAdapter.setCompanies(result);
                    companiesAdapter.notifyDataSetChanged();
                    HideProgressDialog();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {

    }
}
