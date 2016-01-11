package es.tta.ejerciciotta;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by LARA MARIA on 15/12/2015.
 */
public class Test implements Serializable{


    String wording=null;
    public Choice[] choices;


    public Test(String wording,int [] choicesId, String [] choicesAnswer,boolean [] choicesCorrect, String [] choicesAdvise, String [] choicesAdvType){

        this.wording = wording;
        int length=choicesAnswer.length;
        choices = new Choice[length];

        for (int i = 0; i < length; i++) {
            choices[i] = new Choice(choicesId[i],choicesAnswer[i],choicesCorrect[i],choicesAdvise[i],choicesAdvType[i]);
        }

            }

    public String getwording(){
        return wording;
    }

    public void setwording(String wording){
        this.wording=wording;
    }


    public Choice[] getchoices(){
        return choices;
    }

    public Choice getChoice(int i){
        return choices[i];
    }


    //Clase que almacena las posibles respuestas de una pregunta
    public class Choice implements Serializable {
        String answer;
        boolean correct;
        int id;
        String advise;
        String mime;

        public Choice(int id, String answer,boolean correct, String advise, String advType){
            this.id = id;
            this.answer = answer;
            this.correct = correct;
            this.advise = advise;
            this.mime = advType;
        }

        public void setAnswer(String answer){
            this.answer=answer;
        }
        public String getAnswer(){
            return answer;
        }
        public boolean isCorrect(){
            return correct;
        }

        public void setId(int id){
            this.id=id;
        }
        public int getId(){
          return id;
        }

        public void setCorrect(Boolean correct){
            this.correct=correct;
        }

        public void setAdvise(String advise){
            this.advise=advise;
        }

        public String getAdvise(){
           return advise;
        }
        public void setMime(String mime){
            this.mime=mime;
        }

        public String getMime(){
           return mime;
        }

    }


}
