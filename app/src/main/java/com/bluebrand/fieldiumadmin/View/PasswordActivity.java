package com.bluebrand.fieldiumadmin.View;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bluebrand.fieldiumadmin.Model.User;
import com.bluebrand.fieldiumadmin.R;
import com.bluebrand.fieldiumadmin.controller.CurrentAndroidUser;
import com.bluebrand.fieldiumadmin.controller.UserController;
import com.tradinos.core.network.SuccessCallback;

/**
 * Created by r.desouki on 2/9/2017.
 */
public class PasswordActivity extends MasterActivity {
    private TextView old_password;
    private TextView new_password;
    private TextView new_password_conf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_password);
        super.onCreate(savedInstanceState);
    }

    @Override
    void getData() {

    }

    @Override
    void showData() {

    }

    @Override
    void assignUIReferences() {
        findViewById(R.id.imagebutton_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        old_password=(TextView)findViewById(R.id.old_password);
        new_password=(TextView)findViewById(R.id.new_password);
        new_password_conf=(TextView)findViewById(R.id.new_password_conf);
    }

    @Override
    void assignActions() {

    }

    @Override
    public void onClick(View view) {

    }

    public void Save(View view) {
        if (old_password.getText().toString().equals("")) {
            showMessageInSnackbar("Please enter the old password.");
            return;
        }
        if (new_password.getText().toString().equals("")) {
            showMessageInSnackbar("Please enter the new password.");
            return;
        }
        if (new_password_conf.getText().toString().equals("")) {
            showMessageInSnackbar("Please confirm the password.");
            return;
        }


        if (!new_password.getText().toString().equals(new_password_conf.getText().toString())) {
            showMessageInSnackbar("The new password does not match the new password confirmation.");
        }
        else{
            CurrentAndroidUser user = new CurrentAndroidUser(this);
            ShowProgressDialog();
            UserController.getInstance(mController).change_password(user.Get().getId(), old_password.getText().toString(), new_password.getText().toString(), new SuccessCallback<User>() {
                @Override
                public void OnSuccess(User result) {
                    new CurrentAndroidUser(PasswordActivity.this).Save(result);
                    finish();
                }
            });
        }
    }
}
