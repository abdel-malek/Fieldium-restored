package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.Game;
import com.bluebrand.fieldium.core.model.Image;
import com.bluebrand.fieldium.core.model.Player;
import com.bluebrand.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Farah Etmeh on 4/15/16.
 */
public class PlayerParser implements TradinosParser<Player> {

    @Override
    public Player Parse(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        Player player = new Player();
        player.setId(jsonObject.getInt("player_id"));
        player.setName(jsonObject.getString("name"));
        player.setEmail(jsonObject.optString("email"));
        // player.setPassword(jsonObject.optString("password"));
        GamesListParser gameParser = new GamesListParser();
        JSONArray preferredGames = jsonObject.optJSONArray("prefered_games");
        ArrayList<Game> preferredGamesList = new ArrayList<>();
        if (preferredGames != null)
            preferredGamesList.addAll(gameParser.Parse(preferredGames.toString()));

       /* for (int i=0;i<preferredGamesList.size();i++)
            preferredGamesList.get(i).setSelected(true);*/
        player.setGames(preferredGamesList);
        player.setAddress(jsonObject.optString("address"));
        player.setPhone(jsonObject.optString("phone"));
        player.setServerId(jsonObject.optString("server_id"));
        Image image=new Image();
        image.setName(jsonObject.optString("profile_picture"));
        player.setProfileImage(image);
        return player;
    }
}
