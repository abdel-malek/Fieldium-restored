package com.bluebrand.fieldium.core.controller;

import android.content.Context;

import com.bluebrand.fieldium.core.model.Player;
import com.bluebrand.network.Controller;
import com.bluebrand.network.FaildCallback;
import com.bluebrand.network.TradinosRequest;

/**
 * Created by Farah Etmeh on 5/10/16.
 */
public class ParentController extends Controller {


    public ParentController(Context context, FaildCallback faildCallback) {
        super(context, faildCallback);
    }

    public void authenticationRequired (TradinosRequest request) {
        if(UserUtils.getInstance(getmContext()).IsLogged()) {
            Player player = UserUtils.getInstance(getmContext()).Get();
            request.turnOnAuthentication(player.getPhone(), player.getServerId());
        }
    }
    public void addCountryToHeader(TradinosRequest request) {
        request.addHeader("country", String.valueOf(UserUtils.getInstance(getmContext()).getCountry().getId()));
    }
}
