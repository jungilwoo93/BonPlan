package com.example.panpa.bonplan.Activities;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.panpa.bonplan.Plan.Note;
import com.example.panpa.bonplan.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CameraActivity extends AppCompatActivity {
    private SurfaceView sfv_preview;
    private Button btn_take;
    private Camera camera = null;
    private Note note;
    private SurfaceHolder.Callback cpHolderCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            startPreview();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            stopPreview();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        //note = new Note("coucou","maison","10:00","12:00","1fois","10 mins avant","bello","");
        //note = getIntent().getParcelableExtra("note");
        note = (Note)getIntent().getSerializableExtra("note");
        bindViews();
    }
    // need to give android.hardware.Camera permission programmatically.
    //MANIFEST PERMISSIONS WON'T WORK on Android 6
    @Override
    protected void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA);
            int hasWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            List<String> permissions = new ArrayList<String>();

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
                //user granted your permission
            }
            if(hasWritePermission!= PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 111);
                requestPermissions(permissions.toArray(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}), 112);
                //user denied your permission
            }
        }


    }

    private void bindViews() {
        sfv_preview = (SurfaceView) findViewById(R.id.sfv_preview);
        btn_take = (Button) findViewById(R.id.btn_take);
        sfv_preview.getHolder().addCallback(cpHolderCallback);

        btn_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        Intent it = new Intent(CameraActivity.this, PreviewActivity.class);
                        String path = "";
                        if ((path = saveFile(data)) != null) {

                            //Bundle bundle=new Bundle();
                            //bundle.putString("path", path);
                            //bundle.putParcelable("note",note);                            //bundle.putInt("age", 24);
                            //附带上额外的数据
                            //it.putExtras(bundle);

                            Note newNote= new Note(note.getTitle(),note.getPlace(),note.getStartTime(),note.getEndTime(),note.getFrequency(),note.getRecallTime(),note.getDescrip(),path);
                            newNote.setPathImg(path);

                            it.putExtra("note",newNote);
                            //it.putExtra("path", path);
                            //ArrayList<String> path1 = new ArrayList<>();
                            //path1.add(path);
                            //it.putStringArrayListExtra("path",path1);
                            Toast.makeText(CameraActivity.this, newNote.getPathImg(), Toast.LENGTH_SHORT).show();

                            //startActivityForResult(it,200);
                            //startActivity(it);
                        } else {
                            Toast.makeText(CameraActivity.this, "fail to save photo", Toast.LENGTH_SHORT).show();
                        }

                        startActivity(it);
                    }
                });
            }
        });
    }

    //保存临时文件的方法
    private String saveFile(byte[] bytes){
        try {
            /*String pathStockage = Environment.getExternalStorageDirectory().toString();
            File fileTempo = File.createTempFile("img","");*/

            File file =createImageFile(bytes);
            /*File file = new File("image.png");
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();*/
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private File createImageFile(byte[] bytes) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );
        FileOutputStream fos = new FileOutputStream(image);
        fos.write(bytes);
        fos.flush();
        fos.close();
        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }


    //开始预览
    private void startPreview(){
        camera = Camera.open();
        try {
            camera.setPreviewDisplay(sfv_preview.getHolder());
            camera.setDisplayOrientation(90);   //tourner caméra 90 degré
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //stop Preview
    private void stopPreview() {
        camera.stopPreview();
        camera.release();


        camera = null;
    }

    /*protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==200) {
            if (resultCode == RESULT_OK) {
                Note ha = (Note) data.getParcelableExtra("note");//récuperer le contact
                //Toast.makeText(this,ha.getNom(),Toast.LENGTH_SHORT).show();
                String position = data.getStringExtra("path");
            } else if (resultCode == RESULT_CANCELED) {

            }
        }

    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 111: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        System.out.println("Permissions --> " + "Permission Granted: " + permissions[i]);


                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        System.out.println("Permissions --> " + "Permission Denied: " + permissions[i]);

                    }
                }
            }
            break;
            case 112:{
                if (grantResults.length == 0 || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    Log.i("CameraActivity", "Permission has been denied by user");

                } else {

                    Log.i("CameraActivity", "Permission has been granted by user");

                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

}
