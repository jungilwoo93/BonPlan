package com.example.panpa.bonplan.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.panpa.bonplan.Plan.Note;
import com.example.panpa.bonplan.R;
import java.io.File;
import java.io.OutputStream;

public class PreviewActivity extends AppCompatActivity {
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
        //note = savedInstanceState.getParcelable("note");
        //String path = savedInstanceState
        Intent intent = getIntent();
        //note = intent.getParcelableExtra("note");
        note = (Note)intent.getSerializableExtra("note");
        //final Intent data = new Intent();
        /*Bundle bundle=this.getIntent().getExtras();
        String path=bundle.getString("path");*/
        //String path = getIntent().getStringExtra("path");
        String path = note.getPathImg();
        Toast.makeText(PreviewActivity.this,path,Toast.LENGTH_SHORT).show();
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreviewActivity.this,NoteEditActivity.class);
                intent.putExtra("note",note);
                // Get the external storage directory path
                //String pathStockage = Environment.getExternalStorageDirectory().toString();
                //File file = new File(getIntent().getStringExtra("path"));
               // Uri saveImgUri=Uri.parse(getIntent().getStringExtra("path"));
                //.putExtra("path",getIntent().getStringExtra("path"));
                //data.putExtra("note",getIntent().getParcelableExtra("note"));
                startActivity(intent);
                //setResult(RESULT_OK,data);
                //finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreviewActivity.this,CameraActivity.class);
                //intent.putExtra("note",getIntent().getParcelableExtra("note"));
                startActivity(intent);
            }
        });


        //final Note note = (Note)getIntent().getParcelableExtra("note");
        if(path != null){
            img.setImageURI(Uri.fromFile(new File(path)));
        }


    }


}
