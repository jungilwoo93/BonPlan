package com.example.panpa.bonplan.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.panpa.bonplan.R;
import java.io.File;
import java.io.OutputStream;

public class PreviewActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_photo);
        ImageView img = findViewById(R.id.photo_preview);
        Button validate = findViewById(R.id.validate_photo);
        Button cancel = findViewById(R.id.cancel_photo);
        String path = getIntent().getStringExtra("path");
        if(path != null){
            img.setImageURI(Uri.fromFile(new File(path)));
        }
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreviewActivity.this,NoteEditActivity.class);
                // Get the external storage directory path
                String pathStockage = Environment.getExternalStorageDirectory().toString();
                //File file = new File(getIntent().getStringExtra("path"));
                Uri saveImgUri=Uri.parse(getIntent().getStringExtra("path"));
                intent.putExtra("pathImg",getIntent().getStringExtra("path"));
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreviewActivity.this,CameraActivity.class);
                startActivity(intent);
            }
        });




    }
}
