package de.maleckandfriends.tictacyasinemely;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlayerListAdapterArrayList extends ArrayAdapter<Player> {

    private static final String TAG = "PlayerListAdapter";
    private Context mContext;

    public PlayerListAdapterArrayList(Context context, int resource, ArrayList<Player> objects) {
        super(context, resource, objects);
        this.mContext = mContext;
    }

}
