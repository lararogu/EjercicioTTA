package es.tta.ejerciciotta;

/**
 * Created by LARA MARIA on 15/12/2015.
 */
public class Test {

    String wording="�Cual de las siguientes opciones NO se indica en el fichero de manifiesto de la app?";
    //String advise="http://www.wikipedia.com";
    //String advise="<html><body>Fallooooo</body></html>";


    public Test(){

    }

    public Advise getAdvise(){
        //Advise advise=new Advise("http://www.wikipedia.com","html");
        //Advise advise=new Advise("sdcard/Whatsapp/Media/Whatsapp Video/VID-20151216-WA0004.3gp","video");
        Advise advise=new Advise("sdcard/Whatsapp/Media/Whatsapp Video/VID-20151216-WA0004.3gp","video");
        return advise;
    }

    public String getwording(){

        return wording;
    }

    public Choice[] getchoices(){
        Choice [] choices = new Choice[4];
        choices[0]= new Choice("Version de la aplicacion",false);
        choices[1]= new Choice("Opciones del menu de ajustes",true);
        choices[2]= new Choice("Nivel minimo de la API",false);
        choices[3]= new Choice("Nombre del paquete JAVA de la app",false);

        return choices;
    }


    //Clase que almacena las posibles respuestas de una pregunta
    public class Choice{
        String wording;
        boolean correct;

        public Choice(String wording,boolean correct){
            this.wording=wording;
            this.correct=correct;
        }

        public String getwording(){
            return wording;
        }
        public boolean isCorrect(){
            return correct;
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
