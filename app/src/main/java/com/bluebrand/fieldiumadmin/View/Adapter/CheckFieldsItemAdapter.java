package com.bluebrand.fieldiumadmin.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bluebrand.fieldiumadmin.Model.Field;
import com.bluebrand.fieldiumadmin.R;


import java.util.ArrayList;
import java.util.List;

public class CheckFieldsItemAdapter extends BaseAdapter {

    private List<Field> objects = new ArrayList<Field>();
    private  ArrayList<Integer> selectedObjectsIds=new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;

    public CheckFieldsItemAdapter(Context context, List<Field> objects) {
        this.context = context;
        this.objects = objects;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Field getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.task_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((Field) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(final Field object, final ViewHolder holder) {
        holder.task_textView.setText(object.getName());

        if(selectedObjectsIds.contains(object.getField_id())){
            holder.task_checkBox.setChecked(true);
        }
        else{
            holder.task_checkBox.setChecked(false);
        }
        holder.task_checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selectedObjectsIds.contains(object.getField_id())){
                    selectedObjectsIds.add(object.getField_id());
                }
                else {
                    selectedObjectsIds.remove((Integer) object.getField_id());
                }
            }
        });

        holder.task_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.task_checkBox.performClick();
            }
        });

    }

    protected class ViewHolder {
        private CheckBox task_checkBox;
        private TextView task_textView;


        public ViewHolder(View view) {
            task_checkBox = (CheckBox) view.findViewById(R.id.task_checkBox);
            task_textView = (TextView) view.findViewById(R.id.task_textView);
        }
    }

    public ArrayList<Integer> getSelectedObjectsIDS() {
        return selectedObjectsIds;
    }

    public void setSelectedObjects(ArrayList<Integer> selectedIDs){
        selectedObjectsIds=selectedIDs;

    }
}
