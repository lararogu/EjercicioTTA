package es.tta.ejerciciotta;

import org.json.JSONObject;

/**
 * Created by LARA MARIA on 15/12/2015.
 */
public class Test {

    //RestClient rest=new RestClient("http://u017633.ehu.eus:18080/AlumnoTta/rest/tta");
    String wording;
   String id;
    //String advise="http://www.wikipedia.com";
    //String advise="<html><body>Fallooooo</body></html>";


    public Test(){

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

    public void setId(String id){
        this.id=id;
    }

    public Choice[] getchoices(){

        Choice [] choices = new Choice[4];
        return choices;
    }


    //Clase que almacena las posibles respuestas de una pregunta
    public static class Choice{
        String wording;
        boolean correct;
        int id;
        String advise;


        public Choice(){
        }

        public String getwording(){
            return wording;
        }
        public boolean isCorrect(){
            return correct;
        }

        public void setwording(String wording){
            this.wording=wording;
        }

        public void setId(int id){
            this.id=id;
        }

        public void setCorrect(Boolean correct){
            this.correct=correct;
        }

        public void setAdvise(String advise){
            this.advise=advise;
        }

        public void add(Choice choice){
            //Choice [] choices = new Choice[];

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
