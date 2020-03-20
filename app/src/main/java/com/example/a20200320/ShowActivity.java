package com.example.a20200320;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class ShowActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        imageView=(ImageView)findViewById(R.id.image);
        Intent intent=getIntent();
        imageView.setImageBitmap(MainActivity.shotWeb);
        Toast.makeText(this,"success",Toast.LENGTH_SHORT).show();
    }
}
