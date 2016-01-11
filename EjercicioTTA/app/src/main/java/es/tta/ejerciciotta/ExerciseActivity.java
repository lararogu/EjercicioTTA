package es.tta.ejerciciotta;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class ExerciseActivity extends ModelActivity {

    public final static int READ_REQUEST_CODE=0;
    public final static int PICTURE_REQUEST_CODE=1;
    public final static int VIDEO_REQUEST_CODE=2;
    public final static int AUDIO_REQUEST_CODE=3;
    public Status user;
    public Exercise exercise;
    public final RestClient rest=new RestClient(baseURL);
    Business business=new Business(rest);

    Uri  pictureUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Intent i=getIntent();
        exercise=(Exercise)i.getSerializableExtra(MenuActivity.EXERCISE);//recogemos el objeto Test
        user=(Status)i.getSerializableExtra(MainActivity.USER);//recogemos el objeto Status
        TextView textWording=(TextView)findViewById(R.id.exercise_wording);
        textWording.setText(exercise.getWording());

    }

//--------------------------------------------------------------------//
    //Funcion para sacar foto
    public void takePhoto(View v) {
        //Si no esta definida la camara en el Manifest File se le muestra un aviso al usuario
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Toast.makeText(this,R.string.no_camera, Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager())!=null){
                File dir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);//Creamos la carpeta de la SD en la que guardar la foto
                try{
                File file=File.createTempFile("tta", ".jpg", dir);//Las fotos llevaran el prefijo ´tta"
                   pictureUri= Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,pictureUri);
                    startActivityForResult(intent,PICTURE_REQUEST_CODE);//Tras ejecutar el intent se llama a onActivityResult
                }catch(IOException e){

                }
            }
            else{
                Toast.makeText(this,R.string.no_app, Toast.LENGTH_SHORT).show();
            }
        }
    }
//--------------------------------------------------------------------------------------//

    public void onActivityResult(int requestCode,int resultCode,Intent data){

        if(resultCode!= Activity.RESULT_OK)
            return;
        switch(requestCode){
            case READ_REQUEST_CODE:
               sendFile(data.getData());
                break;
            case VIDEO_REQUEST_CODE:
            case AUDIO_REQUEST_CODE:
                sendFile(data.getData());
                break;
            case PICTURE_REQUEST_CODE:

                sendFile(pictureUri);
                break;


        }
    }

//------------------------------------------------------------------------------------//

    //Funcion para grabar video
    public void recordVideo(View v) {
        //Si no esta definida la camara en el Manifest File se le muestra un aviso al usuario
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Toast.makeText(this,R.string.no_camera, Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if(intent.resolveActivity(getPackageManager())!=null){

            startActivityForResult(intent,VIDEO_REQUEST_CODE);//Tras ejecutar el intent se llama a onActivityResult
            }
            else{
                Toast.makeText(this,R.string.no_app, Toast.LENGTH_SHORT).show();
            }
        }
    }
    //--------------------------------------------------------------------------------//

    //Funcion para grabar video
    public void recordAudio(View v) {
        //Si no esta definida la camara en el Manifest File se le muestra un aviso al usuario
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            Toast.makeText(this,R.string.no_micro, Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent=new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
            if(intent.resolveActivity(getPackageManager())!=null){

                startActivityForResult(intent,AUDIO_REQUEST_CODE);//Tras ejecutar el intent se llama a onActivityResult
            }
            else{
                Toast.makeText(this,R.string.no_app, Toast.LENGTH_SHORT).show();
            }
        }
    }
    //--------------------------------------------------------------------------------//
    //Funcion para seleccionar un fichero y enviarlo al servidor
    public void sendFile(View v) {
    Intent i=new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        startActivityForResult(i,READ_REQUEST_CODE);
    }
//--------------------------------------------------------------------------------------//

    //Funcion para enviar archivos al servidor
    public void sendFile(final Uri uri){
        final File file=new File(uri.getPath());
        final View v=findViewById(R.id.exercise_wording);
        rest.setHttpBasicAuth(user.getDni(), user.getpass());

        new Thread(new Runnable() {
            @Override
            public void run() {//nuevo hilo para conexion con el servidor
                try {
                    Log.d("tag", "filename:" + file.getName());
                    business.postExercise(uri, file.getName(),user.getId(),exercise.getId());
                } catch (Exception e) {
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Error al conectar con servidor", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        }).start();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercise, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
