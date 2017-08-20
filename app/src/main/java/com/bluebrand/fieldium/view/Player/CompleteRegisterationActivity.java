package com.bluebrand.fieldium.view.Player;

import android.os.Bundle;
import android.widget.EditText;

import com.bluebrand.fieldium.core.controller.FieldsController;
import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.core.model.Field;
import com.bluebrand.fieldium.core.model.Player;
import com.bluebrand.fieldium.view.InvalidInputException;
import com.bluebrand.fieldium.view.MasterFormActivity;
import com.bluebrand.fieldium.R;
import com.bluebrand.network.SuccessCallback;

import java.util.List;

public class CompleteRegisterationActivity extends MasterFormActivity {

    // UI references.
    private EditText mNameView;
    private EditText mEmailView;
    private EditText mAddressView;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);
        super.onCreate(savedInstanceState);
//        setTitle(getTitle());
        setTitleText(getTitle());
        player = UserUtils.getInstance(getmContext()).Get();
        showData();
    }

    @Override
    public void showData() {
        if (!player.getName().equals("null") )
            mNameView.setText(player.getName());
       if( !player.getEmail().equals("null"))
            mEmailView.setText(player.getEmail());
        if( !player.getAddress().equals("null"))
            mAddressView.setText(player.getAddress());

    }

    @Override
    public void assignUIRefrences() {
        mNameView = (EditText) findViewById(R.id.editText_name);
        mEmailView = (EditText) findViewById(R.id.editText_email);
        mAddressView = (EditText) findViewById(R.id.editText_address);
        mFormView = findViewById(R.id.form_container);
        mProgressView = findViewById(R.id.proccess_indicator);
    }


    @Override
    public void submitForm() {
        // Reset errors.
        mNameView.setError(null);
        mEmailView.setError(null);


        // Store values at the time of the login attempt.
        final String name = mNameView.getText().toString();
        final String email = mEmailView.getText().toString();
        final String address = mAddressView.getText().toString();
        EditText focusView = null;
        showProgress(true);
//        try {
    /*        focusView = mNameView;
            isNameValid(name);
            focusView = mEmailView;
            isEmailValid(email);

            showProgress(true);
            final Player player = UserUtils.getInstance(getmContext()).Get();
            player.setName(name);
            player.setEmail(email);
            player.setAddress(address);*/
    /*        PlayerController.getInstance(getmController()).updateInfo(player, new SuccessCallback<Player>() {
                @Override
                public void OnSuccess(Player result) {
                    UserUtils.getInstance(CompleteRegisterationActivity.this).Save(player);
                    UserUtils.getInstance(CompleteRegisterationActivity.this).CompleteRegister();
                    Intent intent = new Intent(CompleteRegisterationActivity.this, SpecialOffersActivity.class);
                    intent.putExtra("skip",true);
                    startActivity(intent);
                    finish();
                }
            });*/
//        FieldsController.getInstance(getmController()).LoadFields(1, 0, 0, new SuccessCallback<List<Field>>() {
//            @Override
//            public void OnSuccess(List<Field> result) {
//                String test;
//                showProgress(false);
//            }
//        });


//        } catch (InvalidInputException e) {
//            focusView.requestFocus();
//            focusView.setError(e.getMessage());
//        }
    }

    private void isEmailValid(String email) throws InvalidInputException {
      /*  if (android.text.TextUtils.isEmpty(email))
            throw new InvalidInputException(getResources().getString(R.string.error_field_required));*/
        if (!android.text.TextUtils.isEmpty(email)&&!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            throw new InvalidInputException(getResources().getString(R.string.error_invalid_email));

    }

    private void isNameValid(String name) throws InvalidInputException {
        if (android.text.TextUtils.isEmpty(name))
            throw new InvalidInputException(getResources().getString(R.string.error_field_required));
    }

}
