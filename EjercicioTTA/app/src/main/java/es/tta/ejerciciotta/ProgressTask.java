package es.tta.ejerciciotta;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by LARA MARIA on 29/12/2015.
 */
public abstract class ProgressTask<T> extends AsyncTask<Void,Void,T>{

    protected final Context context;
    private final ProgressDialog dialog;
    private Exception e;



    public ProgressTask (Context context){
        this.context=context;
        dialog=new ProgressDialog(context);
        dialog.setMessage("Conectando...");
    }

    @Override
    protected void onPreExecute(){
        dialog.show();
    }

    @Override
    protected T doInBackground(Void... params) {
        T result=null;
        try{
            result=work();
        }
        catch(Exception e){
            this.e=e;

        }
        return result;
    }

    @Override
    protected void onPostExecute(T result){
       if( dialog.isShowing()){
           dialog.dismiss();
       }
        if(e!=null){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        else
            Log.d("tag", "Lara:ejecutar onFinish");
            onFinish(result);
            Log.d("tag", "Lara:onfinish");
    }

    protected abstract T work() throws Exception;
    public abstract void onFinish(T result);

}
