package com.bluebrand.fieldium.core.controller;

import android.content.Context;

import com.bluebrand.fieldium.core.API.APIModel;
import com.bluebrand.fieldium.core.API.URLBuilder;
import com.bluebrand.fieldium.core.model.AboutModel;
import com.bluebrand.fieldium.core.parser.AboutParser;
import com.bluebrand.network.Controller;
import com.bluebrand.network.FaildCallback;
import com.bluebrand.network.RequestMethod;
import com.bluebrand.network.SuccessCallback;
import com.bluebrand.network.TradinosRequest;

/**
 * Created by b.srour on 8/21/2016.
 */
public class AboutController extends ParentController {
    public static AboutController getInstance (Controller controller) {
        return new AboutController(controller);
    }


    public AboutController(Context context, FaildCallback faildCallback) {
        super(context, faildCallback);
    }

    public AboutController(Controller controller) {
        super(controller.getmContext(),controller.getmFaildCallback());
    }
    public void LoadAbout ( SuccessCallback<AboutModel> successCallback) {
        String url= new URLBuilder(APIModel.site,"about").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get,new AboutParser(), successCallback,getmFaildCallback()) ;
        addCountryToHeader(request);
        request.Call();
    }
}
