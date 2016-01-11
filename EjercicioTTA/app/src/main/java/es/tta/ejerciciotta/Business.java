package es.tta.ejerciciotta;

import android.util.Log;
import android.widget.TextView;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import es.tta.ejerciciotta.Test.Choice;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
       String wording=json.getString("wording");
        JSONArray array=json.getJSONArray("choices");
       int length=array.length();
       String [] choicesAnswer = new String[length];
       boolean [] choicesCorrect = new boolean[length];
       String [] choicesAdvise = new String[length];
       String [] choicesAdvType = new String[length];
       int [] choicesId = new int[length];
        for(int i=0;i<length;i++){
            JSONObject item=array.getJSONObject(i);

            choicesId[i] = item.getInt("id");
            choicesAnswer[i] = item.getString("answer");
            choicesAdvise[i] = item.getString("advise");
            choicesCorrect[i] = item.getBoolean("correct");

            if(item.isNull("resourceType")){
                choicesAdvType[i] = "null";
            }else{
                choicesAdvType[i] = item.getJSONObject("resourceType").getString("mime");;
            }

        }

       Test test = new Test(wording,choicesId,choicesAnswer,choicesCorrect,choicesAdvise,choicesAdvType);

       return test;

    }


    public Exercise getExercise(int id)throws IOException,JSONException{
        JSONObject json=rest.getJson(String.format("getExercise?id=%d",id));//Recogemos el ejercicio en formato JSON

        Exercise exercise=new Exercise();
        exercise.setId(json.getInt("id"));
        exercise.setWording(json.getString("wording"));


        return exercise;
    }


    public Status getStatus(String dni,String passwd)throws IOException,JSONException{
        JSONObject json=rest.getJson(String.format("getStatus?dni=%s", dni));//Recogemos el status en formato JSON
        Status userStatus=new Status(dni,passwd);
        userStatus.setId(json.getInt("id"));
        userStatus.setName(json.getString("user"));
        userStatus.setlessonNumber(json.getInt("lessonNumber"));
        userStatus.setlessonTitle(json.getString("lessonTitle"));
        userStatus.setnextTest(json.getInt("nextTest"));
        userStatus.setnextEx(json.getInt("nextExercise"));
        return userStatus;

    }

    public int postTest(int user,int id)throws IOException,JSONException{
        JSONObject json=new JSONObject();
        json.put("userId",user);
        json.put("choiceId",id);
        int codeResponse=rest.postJson(json, "postChoice");//Recogemos el codigo de rspuesta del mensahe POST al servidor
        Log.d("tag", "coderesponse:"+codeResponse);
        return codeResponse;

    }

    public int postExercise(Uri file,String fileName,int userid,int exercise)throws IOException,JSONException{
        String path="postExercise?user="+userid+"&id="+exercise;
        InputStream is=new FileInputStream(file.getPath());
        int codeResponse=rest.postFile(path,is,fileName);//Recogemos el codigo de rspuesta del mensaje POST al servidor
        Log.d("tag", "coderesponse:"+codeResponse);
        return codeResponse;

    }



}
