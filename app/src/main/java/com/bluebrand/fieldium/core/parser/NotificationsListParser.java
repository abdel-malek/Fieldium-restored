package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.Notification;
import com.bluebrand.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Player on 1/8/2017.
 */
public class NotificationsListParser implements TradinosParser<List<Notification>> {
    public List<Notification> Parse(String data) throws JSONException {
       /* data="[ {\n" +
                "      \"notification_time\": \"2017-01-16 11:18:36\",\n" +
                "      \"company_name\": \"IFA Sports Central 2\",\n" +
                "      \"logo\": \"0_image7.jpg\",\n" +
                "      \"address\": \"Barsha Heights\",\n" +
                "      \"state_id\": \"2\",\n" +
                "      \"start\": \"10:00:00\",\n" +
                "      \"duration\": \"1\",\n" +
                "      \"date\": \"2017-01-03\",\n" +
                "      \"field_name\": \"Ahdaff\",\n" +
                "      \"logo_url\": \"http://field-dash.tradinos.com/assets/uploaded_images/0_image7.jpg\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"notification_time\": \"2017-01-16 11:21:08\",\n" +
                "      \"company_name\": \"IFA Meadows Football Centre\",\n" +
                "      \"logo\": \"9a0a0-meadows.png\",\n" +
                "      \"address\": \"Sheikh Zayed Road\",\n" +
                "      \"state_id\": \"3\",\n" +
                "      \"start\": \"07:00:00\",\n" +
                "      \"duration\": \"1\",\n" +
                "      \"date\": \"2017-01-15\",\n" +
                "      \"field_name\": \"Main Field\",\n" +
                "      \"logo_url\": \"http://field-dash.tradinos.com/assets/uploaded_images/9a0a0-meadows.png\"\n" +
                "    }\n" +
                "  ]";*/
        JSONArray notificationListJson = new JSONArray(data);
        List<Notification> notificationList = new ArrayList<>() ;
        NotificationParser notificationParser = new NotificationParser() ;

        for (int i =0 ;i < notificationListJson.length(); i++) {
            notificationList.add(notificationParser.Parse(notificationListJson.getString(i)));
        }

        return notificationList;
    }
}
