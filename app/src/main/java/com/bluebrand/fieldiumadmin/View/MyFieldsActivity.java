package com.bluebrand.fieldiumadmin.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.bluebrand.fieldiumadmin.Model.Field;
import com.bluebrand.fieldiumadmin.MyApplication;
import com.bluebrand.fieldiumadmin.R;
import com.bluebrand.fieldiumadmin.View.Adapter.CheckFieldsItemAdapter;
import com.bluebrand.fieldiumadmin.View.Adapter.FieldAdapter;


import java.util.ArrayList;
import java.util.List;

public class MyFieldsActivity extends MasterActivity {
    ListView fieldsListView;
    final int NEW_FIELD = 1;
    final int EDIT_FIELD = 2;
    public boolean fieldsChanged=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_fields);
        super.onCreate(savedInstanceState);
    }

    @Override
    void getData() {

    }

    @Override
    void showData() {

    }

    @Override
    void assignUIReferences() {
        fieldsListView=(ListView) findViewById(R.id.fieldsListView);
        final FieldAdapter fieldAdapter=new FieldAdapter(this,R.layout.field_listview_item,((MyApplication)getApplication()).getMyFields(),fieldsListView);
        fieldsListView.setAdapter(fieldAdapter);
        fieldsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MyFieldsActivity.this,
                        FieldActivity.class);
                intent.putExtra("field", fieldAdapter.getFields().get(i));
                MyFieldsActivity.this.startActivityForResult(intent, EDIT_FIELD);
            }
        });
        FloatingActionButton addField_fab= (FloatingActionButton) findViewById(R.id.addField_fab);
        addField_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder adb = new AlertDialog.Builder(MyFieldsActivity.this);
                CharSequence[]  items = new CharSequence[] {"Add Field", "Add Aggregate Field"};

                adb.setItems(items,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            Intent intent = new Intent(MyFieldsActivity.this,
                                    FieldActivity.class);
                            intent.putExtra("new_field", true);
                            startActivityForResult(intent, NEW_FIELD);
                        }
                        else{
                            ShowFieldCheckList();
                        }
                    }});
                adb.show();



            }
        });

        ImageButton imagebutton_back=(ImageButton)findViewById(R.id.imagebutton_back);
        imagebutton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void ShowFieldCheckList(){
        final Dialog popup= new Dialog(this);
        popup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popup.setContentView(R.layout.popup_fieldscheck);


        ListView fields_listView = (ListView)popup.findViewById(R.id.fields_listView);
        final CheckFieldsItemAdapter checkFieldsItemAdapter=new CheckFieldsItemAdapter(this,((MyApplication)getApplication()).getMyFields());
        fields_listView.setAdapter(checkFieldsItemAdapter);

        popup.findViewById(R.id.close_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });

        ((Button)popup.findViewById(R.id.create_Button)).setText("Create");
        popup.findViewById(R.id.create_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkFieldsItemAdapter.getSelectedObjectsIDS().size()<2)
                {
                    showMessageInToast("Please select two fields at least!");
                }
                else {
                    Intent intent = new Intent(MyFieldsActivity.this,
                            FieldActivity.class);
                    intent.putExtra("new_field", true);
                    intent.putIntegerArrayListExtra("ids",checkFieldsItemAdapter.getSelectedObjectsIDS());
                    startActivityForResult(intent, NEW_FIELD);
                    popup.dismiss();
                }
            }
        });
        popup.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("fieldsChanged", fieldsChanged);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    void assignActions() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        assignUIReferences();
    }

}
