package com.bluebrand.fieldiumadmin.controller;

import android.content.Context;

import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.TradinosRequest;

import com.bluebrand.fieldiumadmin.Model.User;


/**
 * Created by malek on 5/10/16.
 */
public class ParentController extends Controller {


    public ParentController(Context context, FaildCallback faildCallback) {
        super(context, faildCallback);
    }

    public void authenticationRequired (TradinosRequest request) {
        if(UserUtils.getInstance(getmContext()).IsLogged()) {
            User user = UserUtils.getInstance(getmContext()).Get();
            request.turnOnAuthentication(user.getUsername(), user.getPassword());
        }
    }
}
