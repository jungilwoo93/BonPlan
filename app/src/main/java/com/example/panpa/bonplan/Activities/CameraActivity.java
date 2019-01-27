package com.example.panpa.bonplan.Activities;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CameraActivity extends AppCompatActivity {
    private SurfaceView sfv_preview; //pour afficher le view de camera
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
        note = (Note)getIntent().getSerializableExtra("note");
        bindViews();
    }
    //need to give android.hardware.Camera permission programmatically.
    //MANIFEST PERMISSIONS WON'T WORK after Android 6.0
    @Override
    protected void onStart() {
        super.onStart();
        //permission pour utiliser camera de téléphone
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA);
            List<String> permissions = new ArrayList<String>();
            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
                //user granted your permission
            }
            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 111);
                //user denied your permission
            }
        }
    }

    //initialiser la page CameraActivity
    private void bindViews() {
        sfv_preview = findViewById(R.id.sfv_preview);
        btn_take = findViewById(R.id.btn_take); //
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
                            Note newNote= new Note(note.getTitle(),note.getPlace(),note.getStartTime(),note.getEndTime(),note.getFrequency(),note.getRecallTime(),note.getDescrip(),path);
                            newNote.setPathImg(path); //mettre le chemin de fichier de photo dans la note pour qu'on puisse utiliser dans les autres activities.
                            it.putExtra("note",newNote);
                        } else {
                            Toast.makeText(CameraActivity.this, "fail to save photo", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(it);
                    }
                });
            }
        });
    }

    //enregister la photo qu'on a pris
    private String saveFile(byte[] bytes){
        try {
            //demander la permission pour ecrire/créer les fichiers et afficher la photo sur Gallerie
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
                Bitmap bitmapRotate = rotateBitmapByDegree(bitmap,90); //la photo qu'on a pris, elle a pas une bonne orientation.
                bitmap.recycle();
                ByteArrayOutputStream blob = new ByteArrayOutputStream();
                bitmapRotate.compress(Bitmap.CompressFormat.PNG, 100 /* Ignored for PNGs */, blob);
                bitmapRotate.recycle();
                byte[] bitmapdata = blob.toByteArray();
                File file =createImageFile(bitmapdata);
                return file.getAbsolutePath();
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;
        // selon degrée, initialiser matrix pour la rotation
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // image orinale fait la rotation selon matrise, et obtenir la nouvelle image
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
        String filePath =Environment.getExternalStorageDirectory() //path stockage
                .getAbsolutePath()
                + "/"
                + imageFileName
                + ".png";
        File image = new File(filePath);
        if (!image.exists()) {// s'il existe pas, créer un nouveau
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
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE); // pour afficher la photo sur Gallerie
        Uri contentUri = Uri.fromFile(image);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        return image;
    }


    //démarrer le caméra
    private void startPreview(){
        camera = Camera.open();
        try {
            camera.setPreviewDisplay(sfv_preview.getHolder());
            camera.setDisplayOrientation(90);   //tourner caméra 90 degré, parce qu'il a pas une bonne orientation
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //fermer le caméro obligatoire, oublie pas!
    private void stopPreview() {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

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
