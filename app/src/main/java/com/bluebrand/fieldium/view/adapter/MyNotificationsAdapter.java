package com.bluebrand.fieldium.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.toolbox.ImageLoader;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.model.Notification;
import com.bluebrand.network.InternetManager;

import java.util.List;

/**
 * Created by Farah Etmeh on 4/22/16.
 */
public class MyNotificationsAdapter extends ArrayAdapter<Notification> {

    public MyNotificationsAdapter(Context context, int resource, List<Notification> objects) {
        super(context, resource, objects);

    }

    @Override
    public int getViewTypeCount() {
        int n = getCount();
        if (n == 0)
            return 1;
        else
            return n;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;

        if (convertView == null) {
            view = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.item_booking, null);
            holder = new ViewHolder();
           /* holder.textView_notfText = (TextView) view.findViewById(R.id.textView_notificationText);
            holder.textView_time = (TextView) view.findViewById(R.id.textView_notificationTime);
            holder.textView_date = (TextView) view.findViewById(R.id.textView_notificationDate);
*/
            holder.textView_name = (TextView) view.findViewById(R.id.textView_name);
            holder.textView_address = (TextView) view.findViewById(R.id.textView_address);
            holder.textView_date = (TextView) view.findViewById(R.id.booking_date);
            holder.textView_startTime = (TextView) view.findViewById(R.id.booking_startTime);
            holder.textView_duration = (TextView) view.findViewById(R.id.booking_duration);
            holder.textView_bookingId = (TextView) view.findViewById(R.id.booking_id);
            holder.textView_status = (TextView) view.findViewById(R.id.booking_status);
            ImageView imageView_mask = (ImageView) view.findViewById(R.id.imageView_mask);
            holder.imageView_bookingLogo = (ImageView) view.findViewById(R.id.imageView_booking_logo);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        final Notification notification =(Notification) getItem(position);
     /*   if(notification.getSent()==1)
            view.setBackgroundColor(getContext().getResources().getColor(R.color.bright_gray));
        else view.setBackgroundColor(getContext().getResources().getColor(R.color.white_bg));*/

        holder.textView_name.setText(notification.getBooking().getField().getCompany().getName());
        holder.textView_address.setText(notification.getBooking().getField().getCompany().getAddress().getName());
        holder.textView_status.setText("|" + notification.getBooking().getState().toString(getContext()) + "|");
        holder.textView_bookingId.setText(notification.getNotification_time());
        int intDuration=Integer.parseInt(notification.getBooking().getDuration());
        String hDuration=(intDuration/60)<10?"0"+intDuration/60:intDuration/60+"";
        String mDuration=(intDuration%60)<10?"0"+intDuration%60:intDuration%60+"";
        holder.textView_duration.setText(hDuration+":"+mDuration);
        holder.textView_date.setText(notification.getBooking().getDate());
        holder.textView_startTime.setText(notification.getBooking().getStartTime());
        holder.imageView_bookingLogo.setImageResource(R.mipmap.circle_mask);

        try {
            ImageLoader mImageLoader = InternetManager.getInstance(getContext()).getImageLoader();
            mImageLoader.get(notification.getBooking().getField().getCompany().getLogoURL(), ImageLoader.getImageListener(holder.imageView_bookingLogo,
                    R.drawable.default_image, R.drawable.default_image));
        } catch (Exception e) {
//            e.getMessage();
            holder.imageView_bookingLogo.setImageResource(R.drawable.default_image);
        }
//        holder.textView_date.setText(notification.getNotification_date());

        return view;
    }

    static class ViewHolder {
        TextView textView_name;
        TextView textView_address;
        TextView textView_date;
        TextView textView_startTime;
        TextView textView_duration;
        TextView textView_bookingId;
        TextView textView_status;
        ImageView imageView_bookingLogo;
//        TextView textView_state;
//        TextView textView_total;
    }
}
