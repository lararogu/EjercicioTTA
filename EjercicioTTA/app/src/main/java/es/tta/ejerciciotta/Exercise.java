package es.tta.ejerciciotta;

import java.io.Serializable;

/**
 * Created by LARA MARIA on 28/12/2015.
 */
public class Exercise implements Serializable{

    private int id;
    private String wording;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }

    public String getWording(){
        return wording;
    }

    public void setWording(String wording){
        this.wording=wording;
    }
}
