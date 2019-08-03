package de.maleckandfriends.tictacyasinemely;

import android.net.Uri;

public class Player {
    private String name;
    private Uri uri;

    public Player(String name, Uri uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

}
