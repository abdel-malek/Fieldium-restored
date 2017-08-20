package com.bluebrand.fieldium.core.controller;

import android.content.Context;

import com.bluebrand.fieldium.core.API.APIModel;
import com.bluebrand.fieldium.core.API.URLBuilder;
import com.bluebrand.fieldium.core.model.Player;
import com.bluebrand.fieldium.core.parser.EmptyParser;
import com.bluebrand.fieldium.core.parser.MessageParser;
import com.bluebrand.network.Controller;
import com.bluebrand.network.FaildCallback;
import com.bluebrand.network.PhotoMultipartRequest;
import com.bluebrand.network.RequestMethod;
import com.bluebrand.network.SuccessCallback;
import com.bluebrand.network.TradinosRequest;
import com.bluebrand.fieldium.core.parser.PlayerParser;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by Farah Etmeh on 4/15/16.
 */
public class PlayerController extends ParentController {

    public static PlayerController getInstance(Controller controller) {
        return new PlayerController(controller);
    }

    public PlayerController(Context context, FaildCallback faildCallback) {
        super(context, faildCallback);
    }

    public PlayerController(Controller controller) {
        super(controller.getmContext(), controller.getmFaildCallback());
    }

    public void Login(String email, String password, SuccessCallback<Player> successCallback) {
        String url = new URLBuilder(APIModel.players, "login").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new PlayerParser(), successCallback, getmFaildCallback());
        request.addParameter("email", email);
        request.addParameter("password", password);
        addCountryToHeader(request);
        request.Call();
    }

    public void refreshToken(String token, SuccessCallback<Player> successCallback) {
        String url = new URLBuilder(APIModel.players, "refresh_token").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new PlayerParser(), successCallback, getmFaildCallback());
//        request.addParameter("user_id",String.valueOf(user_id));
        request.addParameter("token", token);
//        addCountryToHeader(request);
        authenticationRequired(request);
        request.Call();
    }

    public void unRegisteredUseRefreshToken(String token, SuccessCallback<String> successCallback) {
        String url = new URLBuilder(APIModel.players, "refresh_token").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new EmptyParser(), successCallback, getmFaildCallback());
        request.addParameter("token", token);
        request.addParameter("os", "android");
        request.addParameter("version", "1.0.1");
//        addCountryToHeader(request);
        request.Call();
    }

    public void Register(String mobileNumber, String name, int country_id, SuccessCallback<Player> successCallback) {
        String url = new URLBuilder(APIModel.players, "register").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new PlayerParser(), successCallback, getmFaildCallback());
        request.addParameter("country_id", String.valueOf(country_id));
        request.addParameter("phone", mobileNumber);
        request.addParameter("name", name);
        request.addParameter("os", "android");
        request.addParameter("country", String.valueOf(country_id));
        request.Call();
    }

    public void ActivateUser(String mobileNumber, String code, SuccessCallback<Player> successCallback) {
        String url = new URLBuilder(APIModel.players, "verify").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new PlayerParser(), successCallback, getmFaildCallback());
        request.addParameter("verification_code", code);
        request.addParameter("phone", mobileNumber);
        addCountryToHeader(request);
        request.Call();
    }

    public void DeactivateUser(SuccessCallback<String> successCallback) {
        String url = new URLBuilder(APIModel.players, "deactive").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new MessageParser(), successCallback, getmFaildCallback());
        authenticationRequired(request);
        addCountryToHeader(request);
        request.Call();
    }

    public void requestActivationCode(String mobileNumber, String serverId, SuccessCallback<Player> successCallback) {
        String url = new URLBuilder(APIModel.players, "request_verification_code").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new PlayerParser(), successCallback, getmFaildCallback());
        request.addParameter("server_id", serverId);
        request.addParameter("phone", mobileNumber);
        addCountryToHeader(request);
        request.Call();
    }

    public void updateInfo(Player player, SuccessCallback<Player> successCallback) {
        String url = new URLBuilder(APIModel.players, "update").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new PlayerParser(), successCallback, getmFaildCallback());
        request.addParameter("name", player.getName());
        request.addParameter("email", player.getEmail());
        request.addParameter("address", player.getAddress());
        request.addParameter("image_updated", String.valueOf(false));

        ArrayList<Integer> games = new ArrayList<>();
        for (int i = 0; i < player.getGames().size(); i++) {
            games.add(player.getGames().get(i).getId());
        }
        request.addParameter("prefered_games", games.toString());
        if (player.getLanguage() == null)
            request.addParameter("lang", Locale.getDefault().getLanguage().equals("ar") ? "ar" : "en");
        else
            request.addParameter("lang", player.getLanguage());
        if (player.getCountry() != null)
            request.addParameter("country_id", String.valueOf(player.getCountry().getId()));
//        request.addParameter("profile_picture", player.getProfileImage().getName());
        addCountryToHeader(request);
        authenticationRequired(request);
        request.Call();
    }

    //use when delete the image or upload new image
    public void uploadImage(Player player, File image, final SuccessCallback<Player> successCallback/*, final FaildCallback faildCallback*/) {
        String url = new URLBuilder(APIModel.players, "upload_image").getURL();
      /*  ArrayList<Integer> games = new ArrayList<>();
        for (int i = 0; i < player.getGames().size(); i++) {
            games.add(player.getGames().get(i).getId());
        }*/
        PhotoMultipartRequest photoMultipartRequest = new PhotoMultipartRequest(
                /*player.getName(), player.getEmail(), player.getAddress(), games.toString(),*/ image, getmContext(), url, RequestMethod.Post, new PlayerParser(), successCallback, getmFaildCallback());

        addCountryToHeader(photoMultipartRequest);
        authenticationRequired(photoMultipartRequest);
        photoMultipartRequest.Call();
    }

    public void ContactUs(String email, String phone, String message, SuccessCallback<String> successCallback) {
        String url = new URLBuilder(APIModel.players, "contact_us").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new MessageParser(), successCallback, getmFaildCallback());
        request.addParameter("email", email);
        request.addParameter("phone", phone);
        request.addParameter("message", message);
        addCountryToHeader(request);
        request.Call();
    }
}

