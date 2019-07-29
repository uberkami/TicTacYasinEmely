package com.example.tictacyasinemely;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


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
    public static final int CAPTURE_IMAGE_REQUEST = 1;
    public static final int SAVING_IMAGE_REQUEST = 2;
//public static final int CAPTURE_IMAGE_REQUEST = 3;

    Uri uriPlayer1;
    Uri uriPlayer2;
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
        Bitmap croppedBitmap = null;
        Uri resultUri = null;
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK && data != null) {
                try {
                    //Uri resultUri = data.getData();
                    resultUri = result.getUri();

                    croppedBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(resultUri));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

        //saveImage(bitmap);

        Bitmap circularBitmap = getCircularBitmap(croppedBitmap);

        if (picToPlayer == 1) {
            uriPlayer1 = resultUri;
            imageView1.setImageBitmap(circularBitmap);
            saveImage(circularBitmap, 1);
            //bitmapCreator(bitmap, filename1);
            //imageView1.setImageDrawable(drawable);
        } else if (picToPlayer == 2) {
            uriPlayer2 = resultUri;
            imageView2.setImageBitmap(circularBitmap);
            saveImage(circularBitmap, 2);
            //bitmapCreator(bitmap, filename2);
            // imageView2.setImageDrawable(drawable);
        }
    }

    public Uri getUri(File file) {

        Uri FileUri = FileProvider.getUriForFile(this, "com.example.android.fileprovider", file);
        return FileUri;
    }

    public String createImageName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String FileName = "Image_" + timeStamp;
        return FileName;
    }

    public void saveImage(Bitmap imageFile, int playerNumber) {
        //if (imageName == null) {
        //    imageName = createImageName();
        //}
        String imageName = createImageName();

        String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();
        Log.i("path+Filename", path + File.separator + imageName + ".png");
        File saveImage = null;
        try {
            saveImage = new File(path + File.separator + imageName + ".png");

            // FileOutputStream out = new FileOutputStream(path + "/" + imageName+".jpg");
            saveImage.createNewFile();
            FileOutputStream out = new FileOutputStream(saveImage);

            imageFile.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored

            //OutputStreamWriter outWriter = new OutputStreamWriter(out);
            //outWriter.
            out.flush();
            out.close();
            //MediaScannerConnection ms = new MediaScannerConnection(this, )
        } catch (IOException e) {
            Log.i("Fehler fileOutputStream", e.toString());
            e.printStackTrace();
        }
        scanFile(path + File.separator + imageName + ".png");
        scanFile(path + File.separator);
//        sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(), saveImage.getAbsolutePath(), saveImage.getName(), saveImage.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void scanFile(String path) {
        MediaScannerConnection.scanFile(MainActivity.this,
                new String[]{path}, null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("TAG", "Finished scanning " + path);
                    }
                });
    }

    public static Bitmap getCircularBitmap(Bitmap square) {
        if (square == null) return null;
        Bitmap output = Bitmap.createBitmap(square.getWidth(), square.getHeight(), Bitmap.Config.ARGB_8888);

        final Rect rect = new Rect(0, 0, square.getWidth(), square.getHeight());
        Canvas canvas = new Canvas(output);

        int halfWidth = square.getWidth() / 2;
        int halfHeight = square.getHeight() / 2;

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);

        canvas.drawCircle(halfWidth, halfHeight, Math.min(halfWidth, halfHeight), paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(square, rect, rect, paint);
        return output;
    }
    public void getPhoto() {
        //CropImage.activity(imageUri).start(this);

        CropImage.activity()
                .setAspectRatio(1, 1)
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(MainActivity.this);

    }
    /*
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAPTURE_IMAGE_REQUEST || requestCode == SAVING_IMAGE_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                getPhoto();
            }
        }
    }
/*
    public void imageAdder(View view) {

        picToPlayer = Integer.parseInt(view.getTag().toString());
        final Intent photo = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(photo, 1);
        sb.append(view.getTag().toString());

    }
*/
    public void takePhoto(View view) {

        picToPlayer = Integer.parseInt(view.getTag().toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, CAPTURE_IMAGE_REQUEST);
            } else {
                getPhoto();
            }
        } else {
            getPhoto();
        }
        //Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(cameraIntent, 2);

    }

    }



