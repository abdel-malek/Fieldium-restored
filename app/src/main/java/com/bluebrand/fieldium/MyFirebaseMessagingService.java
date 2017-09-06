/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bluebrand.fieldium;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.bluebrand.fieldium.view.Booking.MyVouchersActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.bluebrand.fieldium.core.model.Notification;
import com.bluebrand.fieldium.core.parser.BookingParser;
import com.bluebrand.fieldium.view.Booking.BookingDetailActivity;
import com.bluebrand.fieldium.view.other.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Uri defaultSoundUri;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }


        sendNotification(remoteMessage.getData());
//se
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */

    private void sendNotification(Map notificationData) {
        Intent intent;
//        boolean arabicLang = Locale.getDefault().getLanguage().equals("ar");
        Notification notification = new Notification();
        try {
//            notification = (new NotificationParser()).Parse(message);
            notification.setNotification_type(Integer.parseInt(String.valueOf(notificationData.get("ntf_type"))));
            notification.setMessage_text(/*((new JSONObject(*/String.valueOf(notificationData.get("ntf_text"))/*)).optString("en"))*/);

//            if (arabicLang)
//                notification.setMessage_text((new JSONObject(String.valueOf(notificationData.get("ntf_text")))).optString("ar"));
//            else

            if (notification.getNotification_type() == 1) {
                notification.setBooking(new BookingParser().Parse(String.valueOf(new JSONObject(String.valueOf(notificationData.get("ntf_body"))).optJSONObject("booking"))));
//                int booking_id = new JSONObject(notification.getMessage_body()).getInt("");
                intent = new Intent(this, BookingDetailActivity.class);
                intent.putExtra("booking", notification.getBooking());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            } else if (notification.getNotification_type() == 2) {
                int voucherId = new JSONObject(String.valueOf(notificationData.get("ntf_body"))).optInt("voucher_id");
                intent = new Intent(this, MyVouchersActivity.class);
                intent.putExtra("voucher_id", voucherId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            } else {
                intent = new Intent(this, SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        } catch (JSONException ex) {
            intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }


        PendingIntent pendingIntent = PendingIntent.getActivity(this, new Random().nextInt() /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(getNotificationIcon())
                .setColor(getResources().getColor(R.color.color_primary))
                .setContentTitle(getString(R.string.app_name))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notification.getMessage_text()))
                .setContentText(notification.getMessage_text())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(new

                Random().nextInt() /* ID of notification */, notificationBuilder.build());
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.mipmap.field_notification : R.mipmap.logo;
    }
}
