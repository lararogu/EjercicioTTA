package es.tta.ejerciciotta;

import android.content.Intent;
import android.graphics.Color;

import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.io.IOException;


public class TestActivity extends ModelActivity implements View.OnClickListener{

    int correct;
    private Test test;
    private String myadvise;
    private String adviseType;
    private Status user;
    public final RestClient rest=new RestClient(baseURL);
    Business business=new Business(rest);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent intent=getIntent();
        test=(Test)intent.getSerializableExtra(MenuActivity.TEST);//recogemos el objeto Test
        user=(Status)intent.getSerializableExtra(MainActivity.USER);//recogemos el objeto Status
        TextView textWording=(TextView)findViewById(R.id.test_wording);

        textWording.setText(test.getwording());//Escribimos la pregunta en pantalla
        RadioGroup group=(RadioGroup)findViewById(R.id.test_choices);
        int i=0;

        for(Test.Choice choice : test.getchoices()){//Obtenemos cada una de las respuestas y las asociamos a un radiobutton
            RadioButton radio=new RadioButton(this);
            radio.setText(choice.getAnswer());
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
    public void send(final View v){
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
        final int selected = group.indexOfChild(select);
        if(selected!=correct){
            myadvise = test.getChoice(selected).getAdvise();
            adviseType = test.getChoice(selected).getMime();
            group.getChildAt(selected).setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Has fallado!", Toast.LENGTH_SHORT).show();
            if(myadvise!=null &&!myadvise.isEmpty()){
                findViewById(R.id.button_view_advise).setVisibility(View.VISIBLE);
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Correcto!", Toast.LENGTH_SHORT).show();
        }


        //Enviar test al servidor
        rest.setHttpBasicAuth(user.getDni(), user.getpass());
        new Thread(new Runnable() {
            @Override
            public void run() {//nuevo hilo para conexion con el servidor
                try {
                    business.postTest(user.getId(), selected);

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
    //-----------------------------------------------------------------------------------------//
    //Funcion que se ejecuta al pulsar el boton Ver ayuda
    public void advise(View v){

        switch(adviseType){
            case "text/html":
                showHTML(myadvise);
                break;

            case "video/mp4":
                showVideo(myadvise);
                break;

            case "audio/mpeg":
                showAudio(myadvise);
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
        LinearLayout layout=(LinearLayout)findViewById(R.id.test_layout);//buscamos el layout de la pantalla de test
        layout.addView(video);//Le añadimos la vista para el video
        video.start();
    }
//-----------------------------------------------------------------------------------------//

    public void showAudio(String advise){
        View view=new View(this);
        AudioPlayer audio=new AudioPlayer(view);
        try {
            audio.setAudioUri(Uri.parse(advise));
        }
        catch(IOException e) {
        }
        LinearLayout layout=(LinearLayout)findViewById(R.id.test_layout);//buscamos el layout de la pantalla de test
        layout.addView(view);//Le añadimos la vista para el video
        audio.start();


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
