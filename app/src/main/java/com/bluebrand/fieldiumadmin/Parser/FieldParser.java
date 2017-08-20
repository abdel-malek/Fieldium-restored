package com.bluebrand.fieldiumadmin.Parser;

import com.tradinos.core.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.bluebrand.fieldiumadmin.Model.Field;
import com.bluebrand.fieldiumadmin.Model.Image;

/**
 * Created by r.desouki on 12/15/2016.
 */
public class FieldParser implements TradinosParser<Field> {
public FieldParser() {
        }

@Override
public Field Parse(String jsonText) throws JSONException {
        Field field;
        JSONObject jsonObject = new JSONObject(jsonText);
         field = Parse(jsonObject);
        return field;
        }

        public Field Parse(JSONObject jsonObject) throws JSONException{
            Field field=new Field();
            field.setField_id(jsonObject.getInt("field_id"));
            field.setName(jsonObject.getString("name"));
            field.setPhone(jsonObject.getString("phone"));
            field.setCompany_id(jsonObject.getInt("company_id"));
            field.setHour_rate(jsonObject.getInt("hour_rate"));

            java.text.DateFormat  StartFormat= new java.text.SimpleDateFormat("hh:mm a", Locale.ENGLISH);
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
            try {
                Date _24HourDt = _24HourSDF.parse(jsonObject.getString("open_time"));
                Calendar startCalendar = Calendar.getInstance();
                startCalendar.setTime(StartFormat.parse(_12HourSDF.format(_24HourDt)));
                field.setOpen_time(startCalendar);
                field.setOpen_time_string(_12HourSDF.format(_24HourDt));

                _24HourDt = _24HourSDF.parse(jsonObject.getString("close_time"));
                Calendar closeCalendar = Calendar.getInstance();
                closeCalendar.setTime(StartFormat.parse(_12HourSDF.format(_24HourDt)));
                field.setClose_time(closeCalendar);
                field.setClose_time_string(_12HourSDF.format(_24HourDt));

            } catch (ParseException e) {
                e.printStackTrace();
            }


            field.setDescription(jsonObject.getString("description"));
            field.setArea_x(jsonObject.getInt("area_x"));
            field.setArea_y(jsonObject.getInt("area_y"));
            field.setMax_capacity(jsonObject.getInt("max_capacity"));
            field.setAutoConfirm(jsonObject.getInt("auto_confirm") == 1);

            field.setAmenities((new AmenityListParser()).Parse(jsonObject.getString("amenities")));

            field.setGames((new GameListParser()).Parse(jsonObject.getString("games")));


            JSONArray imagesJsonArray = jsonObject.optJSONArray("images");
            if (imagesJsonArray != null) {
                ImageParser imageParser = new ImageParser();
                List<Image> images = new ArrayList<>();
                for (int i = 0; i < imagesJsonArray.length(); i++) {
                    images.add(imageParser.Parse((imagesJsonArray.getJSONObject(i))));
                }
                field.setImages(images);
            }

            return field;
        }
}