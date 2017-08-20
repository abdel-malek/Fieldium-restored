package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.Field;
import com.bluebrand.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by b.srour on 6/27/2016.
 */
public class FieldListParser implements TradinosParser<List<Field>> {
    @Override
    public ArrayList<Field> Parse(String data) throws JSONException {

        FieldParser fieldParser = new FieldParser();
        ArrayList<Field> fieldList =new ArrayList<>();

        JSONArray productsArray = new JSONArray(data);
        for (int i = 0; i < productsArray.length(); i++) {
            fieldList.add(fieldParser.Parse(productsArray.getString(i)));
        }
        return fieldList;
    }
}
