package es.tta.ejerciciotta;

import android.content.Intent;
import android.graphics.Color;

import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.MediaController;


public class TestActivity extends ActionBarActivity implements View.OnClickListener{

    int correct;
    Test.Advise myadvise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        //Creamos un objeto tipo Data
        Data data=new Data();
        //Obtenemos el metodo getTest definido en la clase Data()
        Test test=data.getTest();
        TextView textWording=(TextView)findViewById(R.id.test_wording);
        textWording.setText(test.getwording());//Escribimos la pregunta en pantalla
        RadioGroup group=(RadioGroup)findViewById(R.id.test_choices);
        int i=0;
        for(Test.Choice choice : test.getchoices()){//Obtenemos cada una de las respuestas y las asociamos a un radiobutton
            RadioButton radio=new RadioButton(this);
            radio.setText(choice.getwording());
            radio.setOnClickListener(this);
            group.addView(radio);
            if(choice.isCorrect()){
                correct=i;

            }
            i++;
        }
    }

    //Al seleccionar una de las opciones se muestra el boton de Enviar
    public void onClick(View v){
        findViewById(R.id.button_send_test).setVisibility(View.VISIBLE);
    }

//-----------------------------------------------------------------------------------------//
    //Funcion que se ejecuta al pulsar el boton Enviar
    public void send(View v){
        RadioGroup group=(RadioGroup)findViewById(R.id.test_choices);
        int choices=group.getChildCount();
        for(int i=0;i<choices;i++){
            group.getChildAt(i).setEnabled(false);//Se deshabilitan los radio buttons

        }

        LinearLayout layout=(LinearLayout)findViewById(R.id.test_layout);
        layout.removeView(findViewById(R.id.button_send_test));//Se elimina el boton Enviar
        group.getChildAt(correct).setBackgroundColor(Color.GREEN);
        int button_selected= group.getCheckedRadioButtonId();//ID de la respuesta elegida por el usuario
        View select = group.findViewById(button_selected);//Cogemos la view asociada a ese ID
        int selected = group.indexOfChild(select);
        if(selected!=correct){
            Test test=new Test();
            myadvise=test.getAdvise();
            group.getChildAt(selected).setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "�Has fallado!", Toast.LENGTH_SHORT).show();
            if(myadvise.advise!=null &&!myadvise.advise.isEmpty()){
                findViewById(R.id.button_view_advise).setVisibility(View.VISIBLE);
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "�Correcto!", Toast.LENGTH_SHORT).show();
        }
    }
    //-----------------------------------------------------------------------------------------//
    //Funcion que se ejecuta al pulsar el boton Ver ayuda
    public void advise(View v){
        Test test=new Test();
        Test.Advise myadvise=test.getAdvise();

        switch(myadvise.data_type){
            case "html":
                showHTML(myadvise.advise);
                break;

            case "video":
                showVideo(myadvise.advise);
                break;

            case "audio":
                showAudio(v,myadvise.advise);
                break;
        }


    }
    //-----------------------------------------------------------------------------------------//
    public void showHTML(String advise){

        if(advise.substring(0,10).contains("://")){
            Uri uri= Uri.parse(advise);
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }
        else{
            WebView web=new WebView(this);
            web.loadData(advise, "text/html", null);
            web.setBackgroundColor(Color.TRANSPARENT);
            web.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
            LinearLayout layout=(LinearLayout)findViewById(R.id.test_layout);
            layout.addView(web);
            //web.loadUrl(advise);
        }
    }
    //-----------------------------------------------------------------------------------------//
    public void showVideo(String advise){

        VideoView video=new VideoView(this);
        video.setVideoURI(Uri.parse(advise));
        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        video.setLayoutParams(params);

        MediaController controller = new MediaController(this){

            @Override
            public void hide(){
            //Se sobreescribe este metodo para que nunca se oculten los controles
            }

            public boolean dispatchKeyEvent(KeyEvent event){
                if(event.getKeyCode()==KeyEvent.KEYCODE_BACK){
                    finish();//Si mientras se visualiza el video se va hacia atras,la reproduccion del video finaliza
                }
                return super.dispatchKeyEvent(event);

            }

        };

        controller.setAnchorView(video);
        video.setMediaController(controller);
        LinearLayout layout=(LinearLayout)findViewById(R.id.test_layout);
        layout.addView(video);
        video.start();
    }
//-----------------------------------------------------------------------------------------//

    public void showAudio(View v,String advise){
        //AudioPlayer audio=new AudioPlayer(v);


    }
    //---------------------------------------------------------------------------------//



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
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
