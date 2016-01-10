package es.tta.ejerciciotta;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by LARA MARIA on 15/12/2015.
 */
public class Test implements Serializable{


    String wording=null;
    public Choice[] choices;

    //String advise="http://www.wikipedia.com";
    //String advise="<html><body>Fallooooo</body></html>";


    public Test(String wording,int [] choicesId, String [] choicesAnswer,boolean [] choicesCorrect, String [] choicesAdvise, String [] choicesAdvType){

        this.wording = wording;
        int length=choicesId.length;
        for (int i = 0; i < length; i++) {
            choices[i] = new Choice(choicesId[i],choicesAnswer[i],choicesCorrect[i],choicesAdvise[i],choicesAdvType[i]);
        }

            }


    public Advise getAdvise(){
        //Advise advise=new Advise("http://www.wikipedia.com","html");
        //Advise advise=new Advise("sdcard/Whatsapp/Media/Whatsapp Video/VID-20151216-WA0004.3gp","video");
        Advise advise=new Advise("sdcard/Ringtones/hangouts_message.ogg","audio");
        return advise;
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
    public class Choice{
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

    public class Advise{
        String advise;
        String data_type;


        public Advise(String advise,String data_type){
            this.advise=advise;
            this.data_type=data_type;
        }
    }


}
