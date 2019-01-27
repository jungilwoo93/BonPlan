package com.example.panpa.bonplan.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.panpa.bonplan.Plan.Note;
import com.example.panpa.bonplan.R;
import java.io.FileNotFoundException;

public class GalleryActivity extends AppCompatActivity {
    private ImageView imageView;
    private static final int REQUEST_CODE_PICK_IMAGE=3;
    private Uri uri;
    private Button btn_pick_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        btn_pick_image=findViewById(R.id.validate_pick_photo);
        btn_pick_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
        // NoteEditActivity lancer gallerie pour choisir une photo, après le choix, ça envoie le path de photo ici pour afficher
        String path = getIntent().getStringExtra("uri");
        uri = Uri.parse(path);
        imageView=findViewById(R.id.image);
        try {
            Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            imageView.setImageBitmap(bit);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void validate(){
        Intent intent = new Intent(GalleryActivity.this,NoteEditActivity.class);
        Note note = (Note)getIntent().getSerializableExtra("note");
        note.setPathImg(uri.toString());
        intent.putExtra("note",note);
        startActivity(intent);
    }

}
