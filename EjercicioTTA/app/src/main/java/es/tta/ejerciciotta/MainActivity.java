package es.tta.ejerciciotta;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.*;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends ModelActivity {
    public final static String LOGIN ="es.tta.ejemplotta.login";
    public final static String PASSWD ="es.tta.ejemplotta.passwd";
    public final static String USER ="es.tta.ejemplotta.user";
    public final RestClient rest=new RestClient(baseURL);
    final Business business=new Business(rest);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Funcion que recoge el nombre de usuario y passwd y los pasa a la siguiente activity
    public void login(final View view){
     final Intent i=new Intent(this,MenuActivity.class);
        EditText login=(EditText)findViewById(R.id.login);
        EditText passwd=(EditText)findViewById(R.id.passwd);


        //Si ambos campos (LOGIN y PASSWD estan completados se pasa a la siguiente Activity
        if(!TextUtils.isEmpty(login.getText().toString())&&!TextUtils.isEmpty(passwd.getText().toString())){
           final String dni=login.getText().toString();
            final String pass=passwd.getText().toString();
            rest.setHttpBasicAuth(dni,pass); //se guarda el user y passwd en la cabecera de autenticacion del mensaje http

            new Thread(new Runnable() {
                @Override
                public void run() {//nuevo hilo para conexion con el servidor
                    Status user=null;
                    try{
                    user= business.getStatus(dni, pass);

                    }catch(Exception e){

                    }finally {
                        if(user!=null){
                            i.putExtra(LOGIN,dni);
                            i.putExtra(PASSWD,pass);
                            i.putExtra(USER,user);
                            view.post(new Runnable() {
                                @Override
                                public void run() {

                                    startActivity(i);
                                }
                            });
                        }else{
                            Toast.makeText(getApplicationContext(),"Conexion fallida con el servidor",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }).start();




        }
        else{
            Toast.makeText(this,"Falta por introducir algun campo",Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
