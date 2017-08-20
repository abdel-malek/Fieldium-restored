package com.bluebrand.fieldium.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.model.Game;

import java.util.ArrayList;

/**
 * Created by Developer19 on 3/8/2017.
 */

public class CustomListAdapterDialog extends BaseAdapter {

    private ArrayList<Game> listData;

    private LayoutInflater layoutInflater;

    public CustomListAdapterDialog(Context context, ArrayList<Game> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.game_dialpg_item, null);
            holder = new ViewHolder();
            holder.gameName = (TextView) convertView.findViewById(R.id.test);
//            holder.quantityView = (TextView) convertView.findViewById(R.id.quantity);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.gameName.setText(listData.get(position).getName());
//        holder.quantityView.setText(listData.get(position).getUnit().toString());
        return convertView;
    }

    static class ViewHolder {
        TextView gameName;
//        TextView quantityView;
    }

}