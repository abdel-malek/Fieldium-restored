package com.bluebrand.fieldiumadmin.Parser;

import com.tradinos.core.network.TradinosParser;

import org.json.JSONException;
import org.json.JSONObject;

import com.bluebrand.fieldiumadmin.Model.Game;
import com.bluebrand.fieldiumadmin.Model.Image;

/**
 * Created by r.desouki on 12/22/2016.
 */
public class GameParser implements TradinosParser<Game> {
    public GameParser() {
    }

    @Override
    public Game Parse(String jsonText) throws JSONException {
        Game game;
        JSONObject jsonObject = new JSONObject(jsonText);
        game = Parse(jsonObject);
        return game;
    }

    public Game Parse(JSONObject jsonObject) throws JSONException {
        Game game=new Game();
        game.setGame_type_id(jsonObject.getInt("game_type_id"));
        game.setName(jsonObject.getString("name"));

        if (jsonObject.getString("image_url") != null) {
            Image image = new Image() ;
            image.setUrl(jsonObject.optString("image_url"));
            game.setImage(image);
        }

        return game;
    }
}