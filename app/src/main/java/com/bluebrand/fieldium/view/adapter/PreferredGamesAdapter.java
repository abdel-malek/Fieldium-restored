package com.bluebrand.fieldium.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Toast;


import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.core.model.Game;
import com.bluebrand.fieldium.view.Player.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Player on 10/27/2016.
 */
public class PreferredGamesAdapter extends ArrayAdapter<Game> {
    private final LayoutInflater mInflater;
    boolean[] checkBoxState;

    public PreferredGamesAdapter(Context context, int resource, List<Game> games) {
        super(context, resource, games);
        mInflater = LayoutInflater.from(context);
        checkBoxState = new boolean[games.size()];

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        final ViewHolder holder;

        if (convertView == null) {
            view = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.preferred_game_item, null);
            holder = new ViewHolder();
            holder.gameCheckBox = (CheckBox) view.findViewById(R.id.preferredCheckBox);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        final Game game = getItem(position);
//        if (UserUtils.getInstance(getContext()).Get() != null) {
//            ArrayList<Game> preferredGames = UserUtils.getInstance(getContext()).Get().getGames();
     /*   for (int i = 0; i < preferredGames.size(); i++) {
//                Toast.makeText(PreferredGamesAdapter.this.getContext(), game.getName(), Toast.LENGTH_SHORT).show();

            if (game.getId() == preferredGames.get(i).getId()) {
//                    game.setSelected(true);
//                    holder.gameCheckBox.setChecked(game.isSelected());
//                if (preferredGames.get(i).isSelected())
                checkBoxState[position] = true;
                holder.gameCheckBox.setChecked(checkBoxState[position]);

//                    holder.gameCheckBox.setOnCheckedChangeListener(null);
//                    Toast.makeText(PreferredGamesAdapter.this.getContext(), game.getName()+" checked", Toast.LENGTH_SHORT).show();
                break;
            } else {
//                    Toast.makeText(PreferredGamesAdapter.this.getContext(), game.getName()+" did not", Toast.LENGTH_SHORT).show();
//                    holder.gameCheckBox.setOnCheckedChangeListener(null);
//                    game.setSelected(false);
//                    holder.gameCheckBox.setChecked(game.isSelected());
                checkBoxState[position] = true;
                holder.gameCheckBox.setChecked(checkBoxState[position]);
                }
        }*/
        holder.gameCheckBox.setChecked(game.isSelected());
        checkBoxState[position]=game.isSelected();
        holder.gameCheckBox.setText(game.getName());
//        }
        holder.gameCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((CheckBox) v).isChecked()) {
                    checkBoxState[position] = false;
                    game.setSelected(false);
                    ((ProfileActivity) getContext()).removeGame(game);
                } else {
                    checkBoxState[position] = true;
                    ((ProfileActivity) getContext()).addGame(game);
                    game.setSelected(true);
                }
            }
        });

        /*OnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ((ProfileActivity) getContext()).addGame(game);
                else ((ProfileActivity) getContext()).removeGame(game);
            }
        });*/
//        PreferredGamesAdapter.this.notifyDataSetChanged();
        return view;
    }

    static class ViewHolder {
        CheckBox gameCheckBox;

    }
}
