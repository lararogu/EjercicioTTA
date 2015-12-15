package es.tta.ejerciciotta;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MenuActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // get a reference to the TextView on the UI
        TextView textMessage = (TextView) findViewById(R.id.texto_bienvenida);

        //Obtenemos del intent el nombre de usuario introducido en la pantalla de login
        Intent i=getIntent();
        String usuario=i.getStringExtra(MainActivity.LOGIN);
        textMessage.setText("Bienvenido "+usuario);

    }

    //Funcion que accede a la pantalla TestActivity
    public void test(View view){
        Intent i=new Intent(this,TestActivity.class);
        startActivity(i);
    }

    //Funcion que accede a la pantalla ExerciseActivity
    public void exercise(View view){
        Intent i=new Intent(this,ExerciseActivity.class);
        startActivity(i);
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
