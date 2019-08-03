package de.maleckandfriends.tictacyasinemely;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;


public class GameActivity extends AppCompatActivity {
    ImageView imagePlayer;
    int playersTurn = 0; // 0 = player 1 , 1 = player 2
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    boolean gameActive = true;
    int notPlayedField;
    String name1 = "";
    String name2 = "";
    //Bitmap image1 = null;
    //Bitmap image2 = null;
    Uri imageUri1;
    Uri imageUri2;
    Button playAgainButton;
    TextView winnerTextView;
//Bitmap bm = findViewById(R.id.imagePlayer1).get


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        winnerTextView = (TextView) findViewById(R.id.turnWon);

        imagePlayer = (ImageView) findViewById(R.id.playerTurn);
        Bundle bundle = getIntent().getExtras();
        if (!bundle.get("name1").equals("")) {
            name1 = bundle.getString("name1");
        } else {
            name1 = "Player 1";
        }

        if (!bundle.get("name2").equals("")) {
            name2 = bundle.getString("name2");
        } else {
            name2 = "Player 2";
        }


        imageUri1 = Uri.parse(getIntent().getStringExtra("image1"));
        imageUri2 = Uri.parse(getIntent().getStringExtra("image2"));
        /*
        try {


            image1 = BitmapFactory.decodeFile(getIntent().getStringExtra("image1"));

            //imagePlayer.setImageBitmap(image1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String filename2 = getIntent().getStringExtra("image2");
        try {
            image2 = BitmapFactory.decodeFile(getIntent().getStringExtra("image2"));
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        imagePlayer.setImageURI(imageUri1);
        winnerTextView.setText(name1 + " play!");
    }

    public void drop(View view) {

        ImageView image = (ImageView) view;


        int tappedCounter = Integer.parseInt(image.getTag().toString());
        if (gameActive && gameState[tappedCounter] == 2) {
            gameState[tappedCounter] = playersTurn;

            image.setTranslationY(-1500);
            if (playersTurn == 0) {
                image.setImageURI(imageUri1);
                //image.setImageBitmap(image1);
                playersTurn = 1;
                imagePlayer.setImageURI(imageUri2);
                //imagePlayer.setImageBitmap(image2);
                winnerTextView.setText(name2 + " play!");
            } else {
                image.setImageURI(imageUri2);
                //image.setImageBitmap(image2);
                playersTurn = 0;
                imagePlayer.setImageURI(imageUri1);
                //imagePlayer.setImageBitmap(image1);
                winnerTextView.setText(name1 + " play!");
            }
            image.animate().translationY(00).rotation(360).setDuration(00);
            if (!checkWinner()) {
                checkEndOfTurns();
            }


        }


    }


    public void checkEndOfTurns() {
        notPlayedField = 0;
        for (int i = 0; i < gameState.length; i++) {

            if (gameState[i] == 2) {
                notPlayedField++;
            }
        }
        if (notPlayedField == 0) {
            gameActive = false;
            winnerTextView.setText("NOBODY WON!");
            imagePlayer.setImageDrawable(null);
            playAgainButton.setVisibility(View.VISIBLE);

        }

    }

    public boolean checkWinner() {
        boolean thereIsAWinner = false;
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[0]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {
                gameActive = false;
                String winner = "";
                if (playersTurn == 1) {
                    winner = name1;
                    //imagePlayer.setImageBitmap(image1);
                    imagePlayer.setImageURI(imageUri1);

                } else if (playersTurn == 0) {
                    winner = name2;
                    //imagePlayer.setImageBitmap(image2);
                    imagePlayer.setImageURI(imageUri2);

                }
                winnerTextView.setText(winner + " WON!");
                playAgainButton.setVisibility(View.VISIBLE);
                thereIsAWinner = true;
            }

        }
        return thereIsAWinner;
    }

    public void playAgain(View view) {


        playAgainButton.setVisibility(View.INVISIBLE);
        GridLayout gridLayout = findViewById(R.id.gridLayout);


        for (int i = 0; i < gridLayout.getChildCount(); i++) {

            ImageView image = (ImageView) gridLayout.getChildAt(i);

            image.setImageDrawable(null);

        }

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        if (playersTurn == 0) {
            playersTurn = 1;
            //imagePlayer.setImageBitmap(image2);
            imagePlayer.setImageURI(imageUri2);
            winnerTextView.setText(name2 + " play!");
        } else if (playersTurn == 1) {
            playersTurn = 0;
            //imagePlayer.setImageBitmap(image1);
            imagePlayer.setImageURI(imageUri1);
            winnerTextView.setText(name1 + " play!");
        }


        gameActive = true;
    }
}
