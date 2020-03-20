package com.example.a20200320;
import java.io.File;
import java.io.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.content.Intent;
import android.os.Environment;
import	android.provider.MediaStore;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.muddzdev.quickshot.QuickShot;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView mWebView;
    private Button ask;
    private Button shot;
    private Button save;
    private String path;
    public static Bitmap shotWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView=(WebView)findViewById(R.id.mWeb);
        ask=(Button)findViewById(R.id.ask);
        shot=(Button)findViewById(R.id.shot);
        save=(Button)findViewById(R.id.save) ;
        ask.setOnClickListener(this);
        shot.setOnClickListener(this);
        save.setOnClickListener(this);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setInitialScale(90);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl("http://map.dedsec.site");



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ask:
                askPermissions();
                break;
            case R.id.shot:
                shotWeb=ShotWeb();
                Intent intent=new Intent(MainActivity.this,ShowActivity.class);
                startActivity(intent);
                break;
            case R.id.save:
                Intent intent2=new Intent(MainActivity.this,SaveImageActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    private void save(Bitmap bitmap) {
        if (path == null) {
            path = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_PICTURES;
        }
        File directory = new File(path);
        File file;
        directory.mkdirs();
        file = new File(directory,"\\web.jpg");
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bitmap.recycle();
        }
    }

    public void saveImageToGallery(Bitmap bitmap) {
        // 首先保存图片
        File file = null;
        String fileName = System.currentTimeMillis() + ".jpg";
        File root = new File(Environment.getExternalStorageDirectory(), getPackageName());
        File dir = new File(root, "images");
        if (dir.mkdirs() || dir.isDirectory()) {
            file = new File(dir, fileName);
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(this.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Bitmap ShotWeb(){
        mWebView.measure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mWebView.layout(0, 0, mWebView.getMeasuredWidth(), mWebView.getMeasuredHeight());
        mWebView.setDrawingCacheEnabled(true);
        mWebView.buildDrawingCache();
        Bitmap longImage = Bitmap.createBitmap(mWebView.getMeasuredWidth(),
                mWebView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(longImage);  // 画布的宽高和 WebView 的网页保持一致
        Paint paint = new Paint();
        canvas.drawBitmap(longImage, 0, mWebView.getMeasuredHeight(), paint);
        mWebView.draw(canvas);
        return longImage;
    }




    private void askPermissions() {
        int requestCode = 232;
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PermissionChecker.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, requestCode);
            }
        }
    }



}
