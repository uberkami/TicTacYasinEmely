package de.maleckandfriends.tictacyasinemely;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PlayerListAdapterArrayList extends ArrayAdapter<Player> {

    private static final String TAG = "PlayerListAdapter";
    private Context mContext;
    private int mRecource;

    public PlayerListAdapterArrayList(Context context, int resource, ArrayList<Player> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mRecource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        Uri imageUri = getItem(position).getUri();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mRecource, parent, false);

        TextView nameView = (TextView) convertView.findViewById(R.id.textViewList);
        ImageView playerImageView = (ImageView) convertView.findViewById(R.id.imageViewList);

        nameView.setText(name);
        playerImageView.setImageURI(imageUri);
        return convertView;
    }
}
