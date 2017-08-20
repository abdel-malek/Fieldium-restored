package com.bluebrand.fieldium.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bluebrand.fieldium.core.model.AvailableTime;

import java.util.List;

/**
 * Created by Player on 7/19/2016.
 */
public class AvailableTimesAdapter extends ArrayAdapter<AvailableTime> {
    public AvailableTimesAdapter(Context context, int resource, List<AvailableTime> objects) {
        super(context, resource, objects);
    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public AvailableTime getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        textView.setText(getItem(position).getStart()+" - "+getItem(position).getEnd());
        return textView;

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


}
