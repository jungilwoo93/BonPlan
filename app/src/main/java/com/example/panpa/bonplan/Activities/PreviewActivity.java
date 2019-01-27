package com.example.panpa.bonplan.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.panpa.bonplan.Plan.Note;
import com.example.panpa.bonplan.R;
import java.io.File;

public class PreviewActivity extends AppCompatActivity {
    //afficher la photo qu'on a pris par caméra
    private Note note;
    public ImageView img;
    public Button validate;
    public Button cancel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_photo);
        img = findViewById(R.id.photo_preview);
        validate = findViewById(R.id.validate_photo);
        cancel = findViewById(R.id.cancel_photo);
        Intent intent = getIntent();
        note = (Note)intent.getSerializableExtra("note");
        String path = note.getPathImg();
        Toast.makeText(PreviewActivity.this,path,Toast.LENGTH_SHORT).show();
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreviewActivity.this,NoteEditActivity.class);
                intent.putExtra("note",note);
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
        if(path != null){
            img.setImageURI(Uri.fromFile(new File(path)));
        }
    }


}
