package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.Address;
import com.bluebrand.fieldium.core.model.Booking;
import com.bluebrand.fieldium.core.model.BookingStatus;
import com.bluebrand.fieldium.core.model.Company;
import com.bluebrand.fieldium.core.model.Field;
import com.bluebrand.fieldium.core.model.Notification;
import com.bluebrand.network.TradinosParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * Created by b.srour on 7/20/2016.
 */
public class NotificationParser implements TradinosParser<Notification> {

    @Override
    public Notification Parse(String data) throws JSONException {
//        boolean arabicLang = Locale.getDefault().getLanguage().equals("ar");

        JSONObject jsonObject = new JSONObject(data);
        Notification notification = new Notification();

//        if (arabicLang)
//            notification.setMessage_text(jsonObject.optJSONObject("message_text").optString("ar"));
//        else
//            notification.setMessage_text(jsonObject.optJSONObject("message_text").optString("en"));
//
//        notification.setMessage_body(jsonObject.optString("message_body"));

     /*   Booking booking=new Booking();
        booking.setState(BookingStatus.getById(jsonObject.optInt("state_id")));
        booking.setDate(jsonObject.getString("date"));
        booking.setDuration(jsonObject.getString("duration"));
        booking.setStartTime(jsonObject.getString("start"));
        Company company=new Company();
        company.setName(jsonObject.optString("company_name"));
        company.setLogoURL(jsonObject.optString("logo_url"));
        Address address=new Address();
        address.setName(jsonObject.optString("address"));
        company.setAddress(address);

        Field field=new Field();
        field.setName(jsonObject.optString("field_name"));
        field.setCompany(company);

        booking.setField(field);
       notification.setBooking(booking);*/
        notification.setBooking(new BookingParser().Parse(jsonObject.toString()));
        notification.setNotification_time(jsonObject.optString("notification_time"));
        return notification;
    }
}


