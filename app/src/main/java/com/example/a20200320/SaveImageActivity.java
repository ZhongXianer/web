package com.example.a20200320;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import static com.example.a20200320.MainActivity.shotWeb;

public class SaveImageActivity extends AppCompatActivity {

    private static final String TAG = "Kuihua";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_image);
        MainActivity.askPermissions(SaveImageActivity.this);
        saveTempBitmap(shotWeb);
    }

    public void saveTempBitmap(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            Toast.makeText(this,"stateOK",Toast.LENGTH_SHORT).show();
            saveImage(bitmap);
        }else{
            //prompt the user or do something
        }
    }

    private void saveImage(Bitmap finalBitmap) {
        String dir=Environment.getExternalStorageDirectory().getAbsolutePath()+"/webshot";
        String fileName= UUID.randomUUID().toString();
        try {
            File file=new File(dir);
            if (!file.exists()){
                boolean mkdir=file.mkdir();
                if (mkdir)
                    Log.d(TAG, "saveImage: 创建成功");
                else Log.d(TAG, "saveImage: 创建失败");
            }
            FileOutputStream out = new FileOutputStream(dir+"/"+fileName+".jpg");
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.close();
//            Uri uri=Uri.fromFile(file1);
//            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE), String.valueOf(uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Toast.makeText(this,"saveOK",Toast.LENGTH_SHORT).show();
//        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
//
//        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, new String[]{file.getName()}, null);
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
