package es.tta.ejerciciotta;

import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by LARA MARIA on 28/12/2015.
 */
public class Business {

    private final RestClient rest;

    public Business(RestClient rest){
        this.rest=rest;
    }

    public Test getTest(int id)throws IOException,JSONException{
        JSONObject json=rest.getJson(String.format("getTest?id=%d",id));//Recogemos el test en formato JSON
        Test test=new Test();
        test.setId(json.getString("id"));
        test.setwording(json.getString("wording"));
        JSONArray array=json.getJSONArray("choices");
        for(int i=0;i<array.length();i++){
            JSONObject item=array.getJSONObject(i);
            Test.Choice choice = new Test.Choice();
            choice.setId(item.getInt("id"));
            choice.setwording(item.getString("wording"));
            choice.setCorrect(item.getBoolean("correct"));
            choice.setAdvise(item.optString("advise", null));
           // test.getchoices().add(choice);
        }
        return test;
    }


    public Exercise getExercise(int id)throws IOException,JSONException{
        JSONObject json=rest.getJson(String.format("getExercise?id=%d",id));//Recogemos el ejercicio en formato JSON
        Exercise exercise=new Exercise();
        exercise.setId(json.getInt("id"));
        exercise.setWording(json.getString("wording"));


        return exercise;
    }


}
