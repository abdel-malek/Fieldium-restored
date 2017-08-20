package com.bluebrand.fieldiumadmin.controller;


import com.bluebrand.fieldiumadmin.Model.AppInfo;
import com.bluebrand.fieldiumadmin.Parser.AppInfoParser;
import com.bluebrand.fieldiumadmin.Parser.StringParser;
import com.tradinos.core.network.Controller;
import com.tradinos.core.network.RequestMethod;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.core.network.TradinosRequest;


import com.bluebrand.fieldiumadmin.API.APIModel;
import com.bluebrand.fieldiumadmin.API.URLBuilder;
import com.bluebrand.fieldiumadmin.Model.User;
import com.bluebrand.fieldiumadmin.Parser.UserParser;


/**
 * Created by malek on 4/15/16.
 */
public class UserController extends ParentController {

    public static UserController getInstance (Controller controller) {
        return new UserController(controller);
    }

    public UserController(Controller controller) {
        super(controller.getmContext(), controller.getmFaildCallback());
    }

    public void Login (String email , String password , SuccessCallback<User> successCallback) {
        String url= new URLBuilder(APIModel.users,"login").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post,new UserParser(), successCallback,getmFaildCallback()) ;
        request.addParameter("username", email);
        request.addParameter("password", password);
        request.addParameter("support","1");
        request.Call();
    }

    public void Logout (String token , SuccessCallback<String> successCallback) {
        String url= new URLBuilder(APIModel.users,"logout").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get,new StringParser(), successCallback,getmFaildCallback()) ;
        request.addParameter("token", token);
        request.Call();
        authenticationRequired(request);
    }

    public void updateToken (int user_id,String token , SuccessCallback<User> successCallback) {
        String url= new URLBuilder(APIModel.users,"save_token").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post,new UserParser(), successCallback,getmFaildCallback()) ;
        request.addParameter("user_id", String.valueOf(user_id));
        request.addParameter("token", token);
        request.addParameter("os", "android");
        authenticationRequired(request);
        request.Call();
    }

    public void getInfo ( SuccessCallback<AppInfo> successCallback) {
        String url= new URLBuilder(APIModel.site,"about").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get,new AppInfoParser(), successCallback,getmFaildCallback()) ;
        authenticationRequired(request);
        request.Call();
    }

}

