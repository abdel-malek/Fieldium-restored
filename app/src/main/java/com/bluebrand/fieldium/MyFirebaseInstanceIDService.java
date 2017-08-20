/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bluebrand.fieldium;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.bluebrand.fieldium.core.controller.PlayerController;
import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.core.model.Player;
import com.bluebrand.network.Code;
import com.bluebrand.network.Controller;
import com.bluebrand.network.FaildCallback;
import com.bluebrand.network.SuccessCallback;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    SharedPreferences sharedPreferences ;


    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sharedPreferences.edit().putString("user_token_sent_to_server", "").apply();
        sharedPreferences.edit().putString("unregistered_user_token_sent_to_server", "").apply();
        sharedPreferences.edit().putString("token", "123").apply();
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        if (refreshedToken != null) {
            sharedPreferences.edit().putString("token", refreshedToken).apply();
            sendRegistrationToServer(refreshedToken);
        }
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(final String token) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (UserUtils.getInstance(this).IsLogged()) {
            PlayerController.getInstance(new Controller(getApplicationContext(), new FaildCallback() {
                @Override
                public void OnFaild(Code errorCode, String Message) {
                    sharedPreferences.edit().putString("user_token_sent_to_server", "").apply();
                }
            })).refreshToken(token, new SuccessCallback<Player>() {
                @Override
                public void OnSuccess(Player result) {
                    final Player player = UserUtils.getInstance(getApplicationContext()).Get();
                    player.setToken(token);
                    UserUtils.getInstance(getApplicationContext()).Save(player);
                    sharedPreferences.edit().putString("user_token_sent_to_server", token).apply();
                }
            });
        }else {
           PlayerController.getInstance(new Controller(getApplicationContext(), new FaildCallback() {
                @Override
                public void OnFaild(Code errorCode, String Message) {
                    sharedPreferences.edit().putString("unregistered_user_token_sent_to_server", "").apply();
                }
            })).unRegisteredUseRefreshToken(token, new SuccessCallback<String>() {
                @Override
                public void OnSuccess(String result) {
                    sharedPreferences.edit().putString("unregistered_user_token_sent_to_server", token).apply();
                }
            });
        }
    }
}
