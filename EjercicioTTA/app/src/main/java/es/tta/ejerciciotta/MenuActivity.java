package es.tta.ejerciciotta;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends ModelActivity {
    public final RestClient rest=new RestClient(baseURL);
    Business business=new Business(rest);
    public final static String TEST ="es.tta.ejemplotta.test";
    public final static String EXERCISE ="es.tta.ejemplotta.exercise";
    Status user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // get a reference to the TextView on the UI
        TextView textMessage = (TextView) findViewById(R.id.texto_bienvenida);

        //Obtenemos del intent el nombre de usuario introducido en la pantalla de login
        Intent i=getIntent();
       user=(Status)i.getSerializableExtra(MainActivity.USER);
        String usuario=i.getStringExtra(MainActivity.LOGIN);
        String passwd=i.getStringExtra(MainActivity.PASSWD);
        textMessage.setText("Bienvenido " + user.getName());
        rest.setHttpBasicAuth(usuario, passwd); //se guarda el user y passwd en la cabecera de autenticacion del mensaje http


    }

    //Funcion que accede a la pantalla TestActivity
    public void test(View view){
       new ProgressTask<Test>(this){
            @Override
            protected Test work()throws Exception{

                return business.getTest(1);//devuelve el enunciado del ejercicio

            }
            @Override
            public void onFinish(Test result){
                Intent i =new Intent(getApplicationContext(),TestActivity.class);
                i.putExtra(TEST, result);
                i.putExtra(MainActivity.USER,user);
                startActivity(i);
            }
        }.execute();//primero se ejecuta el metodo onPreExecute y despues el doInBackground
    }

    //Funcion que accede a la pantalla ExerciseActivity
    public void exercise(View view){
        new ProgressTask<Exercise>(this){
            @Override
        protected Exercise work()throws Exception{
                return business.getExercise(1);//devuelve el enunciado del ejercicio
            }
            @Override
            public void onFinish(Exercise result){
                Intent i =new Intent(getApplicationContext(),ExerciseActivity.class);
                i.putExtra(EXERCISE, result);
                i.putExtra(MainActivity.USER,user);
                startActivity(i);
            }
        }.execute();//primero se ejecuta el metodo onPreExecute (de ProgressTask) y despues el doInBackground

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
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
