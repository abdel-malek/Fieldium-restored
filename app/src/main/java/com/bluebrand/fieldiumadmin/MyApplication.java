package com.bluebrand.fieldiumadmin;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.multidex.MultiDexApplication;

import com.bluebrand.fieldiumadmin.Model.RefusedMsg;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bluebrand.fieldiumadmin.Model.Amenity;
import com.bluebrand.fieldiumadmin.Model.Area;
import com.bluebrand.fieldiumadmin.Model.Company;
import com.bluebrand.fieldiumadmin.Model.Field;
import com.bluebrand.fieldiumadmin.Model.Game;

/**
 * Created by r.desouki on 8/16/2016.
 */
public class MyApplication  extends MultiDexApplication {
    List<Field> myFields;
    List<Amenity> myAmenities;
    List<Game> myGames;
    List<Area> areas;
    Company myCompany;
    List<RefusedMsg> refusedMsgs;

    SharedPreferences mPrefs;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mPrefs = getSharedPreferences("fields", Context.MODE_PRIVATE);
    }

    public List<Field> getMyFields() {

        if(myFields==null) {
            Gson gson = new Gson();
            int msgs_size = mPrefs.getInt("fields_size", 0);
            myFields=new ArrayList<>();
            for (int i=0;i<msgs_size;i++)
            {
                String json = mPrefs.getString("filed"+i, "");
                myFields.add(gson.fromJson(json, Field.class));

            }
        }
        return myFields;
    }

    public void setMyFields(List<Field> myFields) {

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        for (int i=0;i<myFields.size();i++)
        {
            Gson gson = new Gson();
            String json = gson.toJson(myFields.get(i));
            prefsEditor.putString("filed"+i, json);
        }
        prefsEditor.putInt("fields_size", myFields.size());
        prefsEditor.commit();
        this.myFields = myFields;
    }

    public Field getFieldByID(int id){
        List<Field>fields=getMyFields();
        for(int i=0;i<fields.size();i++){
            if(fields.get(i).getField_id()==id){
                return  fields.get(i);
            }
        }
        return new Field();
    }

    public List<Amenity> getMyAmenities() {

        if(myAmenities==null) {
            Gson gson = new Gson();
            int msgs_size = mPrefs.getInt("Amenities_size", 0);
            myAmenities=new ArrayList<>();
            for (int i=0;i<msgs_size;i++)
            {
                String json = mPrefs.getString("Amenity"+i, "");
                myAmenities.add(gson.fromJson(json, Amenity.class));

            }
        }
        return myAmenities;
    }

    public void setMyAmenities(List<Amenity> myFields) {

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        for (int i=0;i<myFields.size();i++)
        {
            Gson gson = new Gson();
            String json = gson.toJson(myFields.get(i));
            prefsEditor.putString("Amenity"+i, json);
        }
        prefsEditor.putInt("Amenities_size", myFields.size());
        prefsEditor.commit();
        this.myAmenities = myFields;
    }

    public List<Game> getMyGames() {

        if(myGames==null) {
            Gson gson = new Gson();
            int msgs_size = mPrefs.getInt("Games_size", 0);
            myGames=new ArrayList<>();
            for (int i=0;i<msgs_size;i++)
            {
                String json = mPrefs.getString("Game"+i, "");
                myGames.add(gson.fromJson(json, Game.class));

            }
        }
        return myGames;
    }

    public void setMyGames(List<Game> myFields) {

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        for (int i=0;i<myFields.size();i++)
        {
            Gson gson = new Gson();
            String json = gson.toJson(myFields.get(i));
            prefsEditor.putString("Game"+i, json);
        }
        prefsEditor.putInt("Games_size", myFields.size());
        prefsEditor.commit();
        this.myGames = myFields;
    }

    public List<Area> getMyAreas() {

        if(areas==null) {
            Gson gson = new Gson();
            int msgs_size = mPrefs.getInt("Areas_size", 0);
            areas=new ArrayList<>();
            for (int i=0;i<msgs_size;i++)
            {
                String json = mPrefs.getString("Area"+i, "");
                areas.add(gson.fromJson(json, Area.class));

            }
        }
        return areas;
    }

    public void setMyAreas(List<Area> myFields) {

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        for (int i=0;i<myFields.size();i++)
        {
            Gson gson = new Gson();
            String json = gson.toJson(myFields.get(i));
            prefsEditor.putString("Area"+i, json);
        }
        prefsEditor.putInt("Areas_size", myFields.size());
        prefsEditor.commit();
        this.areas = myFields;
    }



    public Company getMyCompanyInfo() {
        if(myCompany==null) {
            Gson gson = new Gson();
            String json = mPrefs.getString("myCompany", "");
            myCompany=gson.fromJson(json, Company.class);
        }
        return myCompany;
    }

    public void setMyCompanyInfo(Company myCompany) {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(myCompany);
        prefsEditor.putString("myCompany", json);
        prefsEditor.commit();
        this.myCompany = myCompany;
    }


    Map<Integer,Bitmap> gamesBitmap;
    public Map<Integer,Bitmap> getMyGamesBitmap() {

        if(gamesBitmap==null) {
            Gson gson = new Gson();
            int msgs_size = mPrefs.getInt("gamesBitmap_size", 0);
            gamesBitmap=new HashMap<>();
            for (int i=0;i<msgs_size;i++)
            {
                String json = mPrefs.getString("gamesBitmap"+i, "");
                int key = mPrefs.getInt("gamesBitmapKey"+i,-1);
                gamesBitmap.put(key,gson.fromJson(json, Bitmap.class));

            }
        }
        return gamesBitmap;
    }

    public void setGamesBitmap(Map<Integer,Bitmap> gamesBitmap) {

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        for (int i=0;i<myGames.size();i++)
        {
            Gson gson = new Gson();
            String value = gson.toJson(gamesBitmap.get(myGames.get(i).getGame_type_id()));
            prefsEditor.putString("gamesBitmap"+i, value);
            prefsEditor.putInt("gamesBitmapKey"+i, myGames.get(i).getGame_type_id());
        }
        prefsEditor.putInt("gamesBitmap_size", gamesBitmap.size());
        prefsEditor.commit();
        this.gamesBitmap = gamesBitmap;
    }

    public List<RefusedMsg> getRefusedMsgs() {

        if(refusedMsgs==null) {
            Gson gson = new Gson();
            int msgs_size = mPrefs.getInt("msgs_size", 0);
            refusedMsgs=new ArrayList<>();
            for (int i=0;i<msgs_size;i++)
            {
                String json = mPrefs.getString("msg"+i, "");
                refusedMsgs.add(gson.fromJson(json, RefusedMsg.class));

            }
        }
        return refusedMsgs;
    }

    public void setRefusedMsgs(List<RefusedMsg> refusedMsgs) {

        refusedMsgs.add(new RefusedMsg(0,""));
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        for (int i=0;i<refusedMsgs.size();i++)
        {
            Gson gson = new Gson();
            String json = gson.toJson(refusedMsgs.get(i));
            prefsEditor.putString("msg"+i, json);
        }
        prefsEditor.putInt("msgs_size", refusedMsgs.size());
        prefsEditor.commit();
        this.refusedMsgs = refusedMsgs;
    }


}