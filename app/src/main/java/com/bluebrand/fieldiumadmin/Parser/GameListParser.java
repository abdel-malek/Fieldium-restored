package com.bluebrand.fieldiumadmin.Parser;

import com.tradinos.core.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.bluebrand.fieldiumadmin.Model.Game;

/**
 * Created by r.desouki on 12/22/2016.
 */
public class GameListParser implements TradinosParser<List<Game>> {

    public List<Game> Parse(String jsonText) throws JSONException {
        List<Game> games = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonText);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            games.add(new  GameParser().Parse(jsonObject));
        }
        return games ;
    }
}