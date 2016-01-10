package es.tta.ejerciciotta;

/**
 * Created by LARA MARIA on 09/01/2016.
 */
public class Status {

    public int id;
    public String name;
    public int lessonNumber;
    public String lessonTitle;
    public int nextTest;
    public int nextExercise;
    public String dni;
    public String pass;

    public Status(String dni,String passwd){
        this.dni=dni;
        this.pass=passwd;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }


    public int getlessonNumber(){
        return lessonNumber;
    }
    public void setlessonNumber(int lessonNumber){
        this.lessonNumber=lessonNumber;
    }

    public String getlessonTitle(){
        return lessonTitle;
    }

    public void setlessonTitle(String lessonTitle){
        this.lessonTitle=lessonTitle;
    }


    public int getnextTest(){

        return nextTest;
    }

    public void setnextTest(int nextTest){

        this.nextTest=nextTest;
    }

    public int getnextEx(){
        return nextExercise;
    }

    public void setnextEx(int nextExercise){
        this.nextExercise=nextExercise;
    }

}
