package com.example.tictacyasinemely;

import android.net.Uri;

public class Player {
    private String name;
    //List<Uri> pictureList;
    private Uri playerPictureUri = null;
    private int wins;
    private int losses;

    public Player(String name, Uri playerPictureUri) {
        this.name = name;
        //pictureList = new ArrayList<Uri>();
        this.playerPictureUri = playerPictureUri;
        this.wins = 0;
        this.losses = 0;
    }

    public Uri getPlayerPictureUri() {
        return playerPictureUri;
    }

    public String getName() {
        return name;
    }

    public void changePicture(Uri newPlayerPicUri) {
        this.playerPictureUri = newPlayerPicUri;
    }

    public void changeName(String newName) {
        this.name = newName;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }
}
