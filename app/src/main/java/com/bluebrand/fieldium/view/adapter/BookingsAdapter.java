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
import com.bluebrand.fieldium.view.Booking.BookingDetailActivity;
import com.bluebrand.network.InternetManager;
import com.bluebrand.fieldium.core.model.Booking;

import java.util.List;

/**
 * Created by Farah Etmeh on 4/22/16.
 */
public class BookingsAdapter extends ArrayAdapter<Booking> {

    public BookingsAdapter(Context context, int resource, List<Booking> objects) {
        super(context, resource, objects);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;

        if (convertView == null) {
            view = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_booking, null);
            holder = new ViewHolder();
            holder.textView_name = (TextView) view.findViewById(R.id.textView_name);
            holder.textView_address = (TextView) view.findViewById(R.id.textView_address);
            holder.textView_date = (TextView) view.findViewById(R.id.booking_date);
            holder.textView_startTime = (TextView) view.findViewById(R.id.booking_startTime);
            holder.textView_duration = (TextView) view.findViewById(R.id.booking_duration);
            holder.textView_bookingId = (TextView) view.findViewById(R.id.booking_id);
            holder.textView_status = (TextView) view.findViewById(R.id.booking_status);
            holder.imageView_mask = (ImageView) view.findViewById(R.id.imageView_mask);
            holder.imageView_bookingLogo = (ImageView) view.findViewById(R.id.imageView_booking_logo);

            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        final Booking booking = getItem(position);

        holder.textView_name.setText(booking.getField().getName());
        holder.textView_address.setText(booking.getField().getCompany().getAddress().getName());
        holder.textView_status.setText("|" + booking.getState().toString(getContext()) + "|");
        holder.textView_bookingId.setText("Ref.# "+booking.getId());
        holder.textView_duration.setText(booking.getDuration()+" Hours");
        holder.textView_date.setText(booking.getDate());
        holder.textView_startTime.setText(booking.getStartTime());
        holder.imageView_bookingLogo.setImageResource(R.mipmap.circle_mask);

        try {
            ImageLoader mImageLoader = InternetManager.getInstance(getContext()).getImageLoader();
            mImageLoader.get(booking.getField().getCompany().getLogoURL(), ImageLoader.getImageListener(holder.imageView_bookingLogo,
                    R.drawable.default_image, R.drawable.default_image));
        } catch (Exception e) {
//            e.getMessage();
            holder.imageView_bookingLogo.setImageResource(R.drawable.default_image);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Intent intent = new Intent(context, BookingDetailActivity.class);
                intent.putExtra("booking", booking);
                context.startActivity(intent);

            }
        });
        return view;
    }

    static class ViewHolder {
        TextView textView_name;
        TextView textView_address;
        TextView textView_date;
        TextView textView_duration;
        TextView textView_startTime;
        TextView textView_status;
        TextView textView_bookingId;
        ImageView imageView_mask;
        ImageView imageView_bookingLogo;

    }
}
