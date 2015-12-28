package es.tta.ejerciciotta;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;


import org.json.JSONException;

import java.io.File;
import java.io.IOException;

public class ExerciseActivity extends ActionBarActivity {

    public final static int READ_REQUEST_CODE=0;
    public final static int PICTURE_REQUEST_CODE=1;
    public final static int VIDEO_REQUEST_CODE=2;
    public final static int AUDIO_REQUEST_CODE=3;
    public final RestClient rest=new RestClient("http://u017633.ehu.eus:18080/AlumnoTta/rest/tta");
    Uri  pictureUri;
    Exercise exercise=new Exercise();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Business business=new Business(rest);
        try{
        exercise=business.getExercise(1);
        }
        catch(IOException e){
            }
        catch (JSONException j){

        }
        Toast.makeText(getApplicationContext(), exercise.getWording(), Toast.LENGTH_SHORT).show();
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
                String Fpath = data.getDataString();
                Toast.makeText(this,Fpath, Toast.LENGTH_SHORT).show();
                break;
            case VIDEO_REQUEST_CODE:
            case AUDIO_REQUEST_CODE:
                //sendFile(data.getData());
                //pARA LA OPCION CHOOSE DOCUMENT
                //
                // do somthing...
                //super.onActivityResult(requestCode, resultCode, data);
                Toast.makeText(this,"senddata", Toast.LENGTH_SHORT).show();
                break;
            case PICTURE_REQUEST_CODE:
                Toast.makeText(this,"sendPicture", Toast.LENGTH_SHORT).show();
                //sendFile(pictureUri);
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
