package com.example.panpa.bonplan.Activities;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
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
            //int hasWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            List<String> permissions = new ArrayList<String>();

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
                //user granted your permission
            }
            /*if(hasWritePermission!= PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }*/
            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 111);
                //user denied your permission
            }
        }


    }



    private void bindViews() {
        sfv_preview = findViewById(R.id.sfv_preview);
        btn_take = findViewById(R.id.btn_take);
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
            if (ContextCompat.checkSelfPermission(CameraActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    &&ContextCompat.checkSelfPermission(CameraActivity.this,
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)
                    != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(CameraActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        112);
                ActivityCompat.requestPermissions(CameraActivity.this,
                        new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},
                        113);

            }else{
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Bitmap bitmapRotate = rotateBitmapByDegree(bitmap,90);
                bitmap.recycle();
                ByteArrayOutputStream blob = new ByteArrayOutputStream();
                bitmapRotate.compress(Bitmap.CompressFormat.PNG, 100 /* Ignored for PNGs */, blob);
                bitmapRotate.recycle();
                byte[] bitmapdata = blob.toByteArray();
                /*int size = 960*1280;
                ByteBuffer byteBuffer=ByteBuffer.allocate(size);
                bitmapRotate.copyPixelsToBuffer(byteBuffer);*/
                File file =createImageFile(bitmapdata);
                return file.getAbsolutePath();
            }

            /*File file = new File("image.png");
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();*/
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }

        if (bm != null && bm != returnBm) {
            bm.recycle();
        }

        return returnBm;
    }

    private File createImageFile(byte[] bytes) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + "_";
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //File storageDir = getExternalFilesDir(Environment.getExternalStorageDirectory().getAbsolutePath());
        String filePath =Environment.getExternalStorageDirectory()
                .getAbsolutePath()
                + "/"
                + imageFileName
                + ".png";
        /*File image = File.createTempFile(
                imageFileName,  /* prefix */
               // ".png",         /* suffix */
               // storageDir      /* directory */
       // );
        File image = new File(filePath);
        if (!image.exists()) {// 如果文件不存在，就创建文件
            try {
                image.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream fos = new FileOutputStream(image);
        fos.write(bytes);
        fos.flush();
        fos.close();
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(image);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }


    //开始预览
    private void startPreview(){
        camera = Camera.open();
        try {

            camera.setPreviewDisplay(sfv_preview.getHolder());
            camera.setDisplayOrientation(90);   //tourner caméra 90 degré

            /*Camera.Parameters mParameters = camera.getParameters();
            mParameters.setPictureSize(960,1280);
            camera.setParameters(mParameters);*/
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
