package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.Game;
import com.bluebrand.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Player on 1/9/2017.
 */
public class GamesListParser implements TradinosParser<List<Game>> {
    @Override
    public ArrayList<Game> Parse(String data) throws JSONException {

        GameParser gameParser = new GameParser();
        ArrayList<Game> games =new ArrayList<>();

        JSONArray gamesArray = new JSONArray(data);
        for (int i = 0; i < gamesArray.length(); i++) {
            games.add(gameParser.Parse(gamesArray.getString(i)));
        }
        return games;
    }
}
