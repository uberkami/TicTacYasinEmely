package com.example.tictacyasinemely;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;


public class MainActivity extends AppCompatActivity {


    TextView player1Name;
    TextView player2Name;
    int picToPlayer;
    Button startButton;
    String playerID = "Player_";
    //static String name1;
//static String name2;
    StringBuffer sb = new StringBuffer(playerID);
    String filename1 = "pictureOfPlayer1.png";
    String filename2 = "pictureOfPlayer2.png";
    ImageView imageView1;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = findViewById(R.id.imagePlayer1);
        imageView2 = findViewById(R.id.imagePlayer2);
        player1Name = findViewById(R.id.Player1Text);
        player2Name = findViewById(R.id.Player2Text);
        startButton = findViewById(R.id.startButton);
        final Intent game = new Intent(this, GameActivity.class);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //name1 = player1Name.getText().toString();
                //name2 = player2Name.getText().toString();

                //startActivity(new Intent(MainActivity.this, GameActivity.class));
                //startService(game);

                //Intent game = new Intent (MainActivity.this, GameActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name1", player1Name.getText().toString());
                bundle.putString("name2", player2Name.getText().toString());

                game.putExtras(bundle);
                game.putExtra("image1", filename1);
                game.putExtra("image2", filename2);
                //game.putExtra(name1, player1Name.getText());
                //game.putExtra(name2, player2Name.getText());
                startActivity(game);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String filename = "Player_";
        Uri selectedImage = data.getData();
        Bitmap bitmap = null;
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            try {
               bitmap = (Bitmap) data.getExtras().get("data");

                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            try {
                //File file = new File(getCacheDir() + filename);
                //ImageDecoder.Source source = ImageDecoder.createSource(file);
                //Drawable drawable = ImageDecoder.decodeDrawable(source);
               bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

       /* int bitmapHeight = bitmap.getHeight();
        Log.i("bitmapHeight", ""+bitmapHeight);
        int bitmapWidth = bitmap.getWidth();
        Log.i("bitmapWidth", ""+bitmapWidth);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 600, bitmap.getWidth()*(600/bitmap.getHeight()), true);
  */
        if (picToPlayer == 1) {
            imageView1.setImageBitmap(bitmap);
            bitmapCreator(bitmap, filename1);
            //imageView1.setImageDrawable(drawable);
        } else if (picToPlayer == 2) {
            imageView2.setImageBitmap(bitmap);
            bitmapCreator(bitmap, filename2);
            // imageView2.setImageDrawable(drawable);
        }
    }
    public void cropImage(View view) {
        //CropImage.activity(imageUri).start(this);

        CropImage.activity()
                .setAspectRatio(1, 1)
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(MainActivity.this);

    }
    public void bitmapCreator(Bitmap bmp, String filename) {
        try {
            //Write file
            // String filename = "bitmap.png";
            FileOutputStream stream = this.openFileOutput(filename, Context.MODE_PRIVATE);
            //bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

            //Cleanup
            stream.close();
            bmp.recycle();

            //Pop intent
/*            Intent in1 = new Intent(this, Activity2.class);
            in1.putExtra("image", filename);
            startActivity(in1);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    public void imageAdder(View view) {

        picToPlayer = Integer.parseInt(view.getTag().toString());
        final Intent photo = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(photo, 1);


        /*else {
            //takePhoto();
        }*/
        sb.append(view.getTag().toString());

    }

    public void takePhoto(View view) {

        picToPlayer = Integer.parseInt(view.getTag().toString());
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 2);

    }
    /*public void post{
        mImage.post(new Runnable() {
            @Override
            public void run() {
                mImage.setImageBitmap(loadBitmapFromView(mImage));
            }
        });*/
    }



