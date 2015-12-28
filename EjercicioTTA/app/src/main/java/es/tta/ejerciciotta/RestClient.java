package es.tta.ejerciciotta;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Created by LARA MARIA on 28/12/2015.
 */
public class RestClient {
    private final static String AUTH="Authorization";
    private final String baseURL;
    private final Map<String,String> properties=new HashMap<>();

    public RestClient(String baseURL){
        this.baseURL=baseURL;

    }

    public void setHttpBasicAuth(String user,String passwd){
        String basicAuth= Base64.encodeToString(String.format("%s:%s",user,passwd).getBytes(),Base64.DEFAULT);
        properties.put(AUTH,String.format("Basic %s",basicAuth));
    }

    public String getAuthorization(){
        return properties.get(AUTH);
    }

    public void setAuthorization(String auth){
        properties.put(AUTH,auth);
    }

    public void setProperty(String name,String value){
        properties.put(name,value);
    }

    //-------Crea la conexion con la URL que se le indica-------------------------------------------------//
    private HttpURLConnection getConnection(String path)throws IOException{
        URL url =new URL(String.format("%s/%s",baseURL,path));
        HttpURLConnection conn=(HttpURLConnection)url.openConnection();
        for(Map.Entry<String,String>property:properties.entrySet()){
            conn.setRequestProperty(property.getKey(), property.getValue());//se incluye en el campo AUTH de la cabecera la autenticacion del usuario guardada en el array Properties
        }
            conn.setUseCaches(false);
            return conn;

    }
//--------------------------------------------------------------------------------------------------------------//
    public JSONObject getJson(String path) throws IOException,JSONException{
        return new JSONObject(getString(path));
    }
//-------------------------------------------------------------------------------------------------//

 public String getString(String path)throws IOException{
     HttpURLConnection conn=null;
     StringBuilder  content=new StringBuilder();
         conn = getConnection(path);
         try {
             BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
             while( br.readLine()!=null)
                content.append(br.readLine());
         }
         catch(IOException e){

         }

     finally{
         if(conn!=null)
             conn.disconnect();
             return content.toString();
     }
 }
//----------------------------------------------------------------------------------//




}
