package com.bluebrand.fieldium.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.model.Booking;
import com.bluebrand.fieldium.view.Booking.BookingDetailActivity;
import com.bluebrand.network.InternetManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Player on 1/15/2017.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<String> mParentList; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Booking>> mChildList;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<Booking>> listChildData) {
        this.mContext = context;
        this.mParentList = listDataHeader;
        this.mChildList = listChildData;
    }

    @Override
    public Object getChild(int parentPosition, int childPosititon) {
        return this.mChildList.get(this.mParentList.get(parentPosition))
                .get(childPosititon);
    }
//    @Override
//    public int getChildTypeCount() {
//        int n = getChildrenCount();
//        if (n == 0)
//            return 1;
//        else
//            return n;
//    }
//
//    @Override
//    public int getChildType(int groupPos,int chPos) {
//        return chPos;
//    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int parentPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Booking booking = (Booking) getChild(parentPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_booking, null);
        }

        TextView textView_name = (TextView) convertView.findViewById(R.id.textView_name);
        TextView textView_address = (TextView) convertView.findViewById(R.id.textView_address);
        TextView textView_date = (TextView) convertView.findViewById(R.id.booking_date);
        TextView textView_startTime = (TextView) convertView.findViewById(R.id.booking_startTime);
        TextView textView_duration = (TextView) convertView.findViewById(R.id.booking_duration);
        TextView textView_bookingId = (TextView) convertView.findViewById(R.id.booking_id);
        TextView textView_status = (TextView) convertView.findViewById(R.id.booking_status);
        ImageView imageView_mask = (ImageView) convertView.findViewById(R.id.imageView_mask);
        ImageView imageView_bookingLogo = (ImageView) convertView.findViewById(R.id.imageView_booking_logo);

        textView_name.setText(booking.getField().getCompany().getName());
        textView_address.setText(booking.getField().getCompany().getAddress().getName());
        textView_status.setText("|" + booking.getState().toString(mContext) + "|");
        textView_bookingId.setText("Ref.# " + booking.getId());
        if (parentPosition == 1/*((String) getGroup(parentPosition)).equals("Past Bookings")*/)//position 1 of parent list is "Past Bookings"
            textView_bookingId.setBackgroundResource(R.color.bright_gray);
        else textView_bookingId.setBackgroundResource(R.color.color_primary);

        int intDuration=Integer.parseInt(booking.getDuration());
        String hDuration=(intDuration/60)<10?"0"+intDuration/60:intDuration/60+"";
        String mDuration=(intDuration%60)<10?"0"+intDuration%60:intDuration%60+"";
        textView_duration.setText(hDuration+":"+mDuration);
        textView_date.setText(booking.getDate());
        textView_startTime.setText(booking.getStartTime());
        imageView_bookingLogo.setImageResource(R.mipmap.circle_mask);

        try {
            ImageLoader mImageLoader = InternetManager.getInstance(mContext).getImageLoader();
            mImageLoader.get(booking.getField().getCompany().getLogoURL(), ImageLoader.getImageListener(imageView_bookingLogo,
                    R.drawable.default_image, R.drawable.default_image));
        } catch (Exception e) {
//            e.getMessage();
            imageView_bookingLogo.setImageResource(R.drawable.default_image);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BookingDetailActivity.class);
                intent.putExtra("booking", booking);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.mChildList.get(this.mParentList.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mParentList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.mParentList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_booking_parent, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.booking_parent_name);
//        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}