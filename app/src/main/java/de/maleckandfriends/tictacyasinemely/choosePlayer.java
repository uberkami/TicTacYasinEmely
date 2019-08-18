package de.maleckandfriends.tictacyasinemely;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class choosePlayer extends AppCompatActivity {
    ArrayList<Player> playerArrayList;
    PlayerListAdapterArrayList adapter;
    Map<String, String> outputMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_player);
        playerArrayList = new ArrayList<Player>();
        loadSavedPlayerList();
        ListView playerListview = findViewById(R.id.listView);

        //ArrayAdapter adapter = new ArrayAdapter()
        adapter = new PlayerListAdapterArrayList(this, R.layout.player_list_layout, playerArrayList);
        playerListview.setAdapter(adapter);


        playerListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Log.i("long click", playerArrayList.get(i).getName());
                PopupMenu popupMenu = new PopupMenu(choosePlayer.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Choose Player")) {
                            choosePlayerFromList(i);
                        }
                        ;
                        if (item.getTitle().equals("Delete Player")) {
                            deletePlayerFromList(i);
                        }
                        ;

                        return false;
                    }
                });
                return true;
            }
        });


        playerListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                choosePlayerFromList(i);
            }
        });


    }

    private void choosePlayerFromList(int i) {

        Log.i("Spielername", playerArrayList.get(i).getName());
        //  finishActivity(5);
        Intent resultIntent = new Intent();
// TODO Add extras or a data URI to this intent as appropriate.
        resultIntent.putExtra("playerName", playerArrayList.get(i).getName());
        resultIntent.putExtra("playerUri", playerArrayList.get(i).getUri().toString());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void deletePlayerFromList(int i) {
        outputMap.remove(playerArrayList.get(i).getName());
        playerArrayList.remove(i);
        adapter.notifyDataSetChanged();
        saveMap(outputMap);

    }

    public void deleteList(View view) {
        playerArrayList.clear();
        adapter.notifyDataSetChanged();
        Map<String, String> newMap = new HashMap<String, String>();
        saveMap(newMap);
    }

    private void saveMap(Map<String, String> inputMap) {
        SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("savedPlayerList", Context.MODE_PRIVATE);
        if (pSharedPref != null) {
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = pSharedPref.edit();
            editor.remove("My_map").commit();
            editor.putString("My_map", jsonString);
            editor.commit();
        }
    }

    private Map<String, String> loadSavedPlayerList() {
        outputMap = new HashMap<String, String>();
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
