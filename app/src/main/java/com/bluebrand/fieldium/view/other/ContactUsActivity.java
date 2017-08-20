package com.bluebrand.fieldium.view.other;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;


import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.PlayerController;
import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.core.model.Player;
import com.bluebrand.fieldium.view.InvalidInputException;
import com.bluebrand.fieldium.view.MasterFormActivity;
import com.bluebrand.network.SuccessCallback;

public class ContactUsActivity extends MasterFormActivity {
    EditText editText_email , editText_phone , editText_message ;
    Button button_contact_us;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        setLanguage();
        setContentView(R.layout.activity_contact_us);
        super.onCreate(savedInstanceState);

//        CharSequence test=getTitle();
        setTitle(getResources().getString(R.string.title_activity_contact_us));

        if (UserUtils.getInstance(this).IsLogged()) {
            Player user = UserUtils.getInstance(getmContext()).Get() ;
            editText_phone.setText(user.getPhone());
            editText_email.setText(user.getEmail());
        }
    }

    @Override
    public void assignUIRefrences() {
        editText_email=(EditText) findViewById(R.id.editText_email);
        editText_phone=(EditText) findViewById(R.id.editText_phone_number);
        editText_message=(EditText) findViewById(R.id.editText_message);
        mFormView = findViewById(R.id.form_container);
        mProgressView = findViewById(R.id.proccess_indicator);

    }
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        // refresh your views here
//        this.recreate();
//        super.onConfigurationChanged(newConfig);
//    }
    @Override
    public void submitForm() {
        findViewById(R.id.button_submit).setEnabled(false);
        EditText focusView = null;

        try {

            editText_phone.setError(null);
            String phoneNumber = editText_phone.getText().toString();
            focusView = editText_phone;
            isPhoneNumberValid(phoneNumber);


            editText_email.setError(null);
            String email = editText_email.getText().toString();
            focusView = editText_email;
            isEmailValid(email);


            editText_message.setError(null);
            String message = editText_message.getText().toString();
            focusView = editText_message;
            isMessageValid(message);

            showProgress(true);
            PlayerController.getInstance(getmController()).ContactUs(email,phoneNumber,message,new SuccessCallback<String>() {
                @Override
                public void OnSuccess(String result) {
                    showMessageInToast(getResources().getString(R.string.thanks_for_feedback));
                    finish();
                }
            });
        } catch (InvalidInputException e) {
            findViewById(R.id.button_submit).setEnabled(true);
            focusView.setError(e.getMessage());
            focusView.requestFocus();
        }
    }

    private void isPhoneNumberValid(String phoneNumber) throws InvalidInputException {
        //TODO: Replace this with your own logic
        if (TextUtils.isEmpty(phoneNumber)) {
            throw new InvalidInputException(getResources().getString(R.string.error_field_required));
        } else if (!phoneNumber.matches("[0-9]+")) {
            throw new InvalidInputException(getResources().getString(R.string.invalid_phone));
        } /*else if (phoneNumber.charAt(0) != '9') {
            throw new InvalidInputException(getResources().getString(R.string.invalid_phone_start));
        }*/ else if (phoneNumber.length() != 9) {
            throw new InvalidInputException(getResources().getString(R.string.invalid_phone_length));
        }
    }
    private void isEmailValid(String email) throws InvalidInputException {
        if (!TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches())
            throw new InvalidInputException(getResources().getString(R.string.error_invalid_email));

    }

    private void isMessageValid(String phoneNumber) throws InvalidInputException {
        //TODO: Replace this with your own logic
        if (TextUtils.isEmpty(phoneNumber)) {
            throw new InvalidInputException(getResources().getString(R.string.error_field_required));
        }
    }

}
