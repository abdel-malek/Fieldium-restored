package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.Game;
import com.bluebrand.network.TradinosParser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Player on 12/22/2016.
 */
public class GameParser implements TradinosParser<Game> {
    @Override
    public Game Parse(String text) throws JSONException {
        JSONObject jsonObject = new JSONObject(text);
        return  Parse(jsonObject) ;
    }
    public Game Parse (JSONObject jsonObject) throws JSONException {
        Game game = new Game() ;
        game.setId(jsonObject.getInt("game_type_id"));
        game.setImageUrl(jsonObject.optString("image_url"));
        game.setName(jsonObject.optString("name"));
        game.setMinimum_duration(jsonObject.optInt("minimum_duration"));
        game.setIncreament_factor(jsonObject.optInt("increament_factor"));
        return  game ;
    }
}
