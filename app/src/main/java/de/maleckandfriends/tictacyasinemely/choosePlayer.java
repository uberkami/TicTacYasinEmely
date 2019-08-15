package de.maleckandfriends.tictacyasinemely;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class choosePlayer extends AppCompatActivity {
    ArrayList<Player> playerArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_player);
        playerArrayList = new ArrayList<Player>();
        loadSavedPlayerList();
        ListView playerListview = findViewById(R.id.listView);
        //ArrayAdapter adapter = new ArrayAdapter()
        PlayerListAdapterArrayList adapter = new PlayerListAdapterArrayList(this, R.layout.player_list_layout, playerArrayList);
        playerListview.setAdapter(adapter);
    }

    private Map<String, String> loadSavedPlayerList() {
        Map<String, String> outputMap = new HashMap<String, String>();
        SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("savedPlayerList", Context.MODE_PRIVATE);
        try {
            if (pSharedPref != null) {
                String jsonString = pSharedPref.getString("My_map", (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while (keysItr.hasNext()) {
                    String key = keysItr.next();
                    String value = (String) jsonObject.get(key);
                    outputMap.put(key, value);
                    playerArrayList.add(new Player(key, Uri.parse(value)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputMap;
    }
}
