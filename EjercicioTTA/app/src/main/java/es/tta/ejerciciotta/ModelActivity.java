package es.tta.ejerciciotta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by LARA MARIA on 29/12/2015.
 */
public class ModelActivity extends AppCompatActivity {
   public static final String baseURL="http://u017633.ehu.eus:18080/AlumnoTta/rest/tta";
    public final static String VALUE ="es.tta.ejemplotta.value";

    protected <T> void startModelActivity(Class<T> cls,String value){
        Intent i = new Intent(getApplicationContext(),cls);
        i.putExtra(VALUE,value);
        startActivity(i);
    }
}
