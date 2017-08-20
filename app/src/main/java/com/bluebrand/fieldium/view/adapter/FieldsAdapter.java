package com.bluebrand.fieldium.view.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.view.Fields.FieldsListActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.model.Field;

import java.util.List;
import java.util.Locale;

/**
 * Created by Farah Etmeh on 4/22/16.
 */
public class FieldsAdapter extends ArrayAdapter<Field> {
    public FieldsAdapter(Context context, int resource, List<Field> objects) {
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
            view = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.field_item, null);
            holder = new ViewHolder();
            holder.textView_name = (TextView) view.findViewById(R.id.title_field_name);
            holder.textView_hourRate = (TextView) view.findViewById(R.id.hourRate_textView);
            holder.textView_capacity = (TextView) view.findViewById(R.id.title_field_capacity);
            holder.textView_Image = (ImageView) view.findViewById(R.id.field_image);
//            holder.viewPager=(ViewPager)view.findViewById(R.id.field_image);

            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        Field field = getItem(position);
//        Company company = getItem(position);

        String currency=((FieldsListActivity)getContext()).getCurrency();
//        holder.textView_name.setTypeface(TextUtils.getFont(getContext()));
        holder.textView_name.setText(field.getName());
        holder.textView_hourRate.setText(field.getHourRate() +
                " " + currency+
                " "+getContext().getResources().getString(R.string.per_hour));
        holder.textView_capacity.setText(field.getMaxCapacity()+" "+getContext().getResources().getString(R.string.players));

        /*  if (field.getImages() != null)
        {
            holder.viewPager.setAdapter(new ImageAdapter(field.getImages(),((FieldsListActivity)getContext()).getSupportFragmentManager()));
//            circleIndicator.setViewPager(viewPager);
        }
*/
        if(!field.getImages().isEmpty())
        if (!field.getImages().get(0).getUrl().equals("")) {
            try {
//                Picasso.with(getContext())
//                        .setIndicatorsEnabled(true);
                Picasso.with(getContext())
                        .load(field.getImages().get(0).getUrl()).memoryPolicy(MemoryPolicy.NO_STORE)
                        .error(R.drawable.default_image)
                        .placeholder(R.drawable.default_image)
                        .into(holder.textView_Image);
            } catch (Exception e) {

            }
        }

        return view;
    }

    static class ViewHolder {
        TextView textView_name;
        TextView textView_description;
        TextView textView_hourRate;
        TextView textView_capacity;
        ImageView textView_Image;
        ViewPager viewPager;

    }
}
