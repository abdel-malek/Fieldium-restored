package com.bluebrand.fieldiumadmin.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import com.bluebrand.fieldiumadmin.Model.Field;
import com.bluebrand.fieldiumadmin.R;
import com.bluebrand.fieldiumadmin.View.FieldActivity;
import com.bluebrand.fieldiumadmin.View.MyFieldsActivity;

/**
 * Created by r.desouki on 1/10/2017.
 */
public class AreaAdapter extends ArrayAdapter<Field> {

    ListView listView;
    List<Field> fields;
    int position;
    Context context;
    final int EDIT_FIELD = 2;
    public AreaAdapter(Context context, int resource, List<Field> fields, ListView listView) {
        super(context, resource);
        this.fields = fields;
        this.listView = listView;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        this.position = position;
        View view = ((LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.field_listview_item, null);

        Field field = getItem(position);
        TextView fieldName_textView = (TextView) view.findViewById(R.id.fieldName_textView);
        TextView fieldType_textView = (TextView) view.findViewById(R.id.fieldType_textView);
        ImageView editField_imageView=(ImageView)view.findViewById(R.id.deleteField_imageView);


        fieldName_textView.setText(String.valueOf(field.getName()));
        String types="";
        for(int i=0;i<field.getGames().size();i++){
            if(i==0)
                types=field.getGames().get(i).getName();
            else
                types+=", "+field.getGames().get(i).getName();
        }
        fieldType_textView.setText(types);

        editField_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,
                        FieldActivity.class);
                intent.putExtra("field", fields.get(position));
                ((MyFieldsActivity) context).startActivityForResult(intent, EDIT_FIELD);
            }
        });



        return view;
    }

    @Override
    public Field getItem(int position) {
        return fields.get(position);
    }

    @Override
    public int getCount() {
        return fields.size();
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List fields) {
        this.fields = fields;
    }
}
