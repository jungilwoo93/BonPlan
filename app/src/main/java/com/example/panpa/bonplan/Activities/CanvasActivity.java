package com.example.panpa.bonplan.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.panpa.bonplan.Plan.Note;
import com.example.panpa.bonplan.R;
import com.example.panpa.bonplan.View.MyView;

import java.io.File;

public class CanvasActivity extends AppCompatActivity {
    private MyView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        Toolbar toolbar = findViewById(R.id.toolbarEdit);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        view=findViewById(R.id.canvas);//récupérer la canvas pour designer
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.logoCheck){
                    if (ContextCompat.checkSelfPermission(CanvasActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED
                            &&ContextCompat.checkSelfPermission(CanvasActivity.this,
                            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)
                            != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(CanvasActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                112);
                        ActivityCompat.requestPermissions(CanvasActivity.this,
                                new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},
                                113);

                    }else{
                        File image = view.saveImgCanvas();
                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri contentUri = Uri.fromFile(image);
                        mediaScanIntent.setData(contentUri);
                        sendBroadcast(mediaScanIntent);
                        Intent intent = new Intent(CanvasActivity.this,NoteEditActivity.class);
                        Note note = (Note)getIntent().getSerializableExtra("note");
                        note.setPathImg(image.getAbsolutePath());
                        intent.putExtra("note",note);
                        startActivity(intent);
                    }
                }
                return true;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent= new Intent(CanvasActivity.this, NoteEditActivity.class);
                intent.putExtra("note",getIntent().getSerializableExtra("note"));
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }
}
