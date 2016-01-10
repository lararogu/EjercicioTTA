package es.tta.ejerciciotta;

import android.annotation.TargetApi;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.net.HttpURLConnection;
import java.net.URL;
import android.util.Log;
/**
 * Created by LARA MARIA on 28/12/2015.
 */
public class RestClient {
    private final static String AUTH="Authorization";
    private final String baseURL;
    private final Map<String,String> properties=new HashMap<>();
    private static final String TAG = "MyActivity";

    public RestClient(String baseURL){
        this.baseURL=baseURL;

    }

    public void setHttpBasicAuth(String user,String passwd){
        String basicAuth=Base64.encodeToString(String.format("%s:%s",user,passwd).getBytes(),Base64.DEFAULT);
        properties.put(AUTH, String.format("Basic %s",basicAuth));
    }

    public String getAuthorization(){
        return properties.get(AUTH);
    }

    public void setAuthorization(String auth){
        properties.put(AUTH,auth);
    }

    public void setProperty(String name,String value){
        properties.put(name, value);
    }

    //-------Crea la conexion con la URL que se le indica-------------------------------------------------//
    private HttpURLConnection getConnection(String path)throws IOException{
        URL url =new URL(String.format("%s/%s",baseURL,path));
        HttpURLConnection conn=(HttpURLConnection)url.openConnection();
        for(Map.Entry<String,String>property:properties.entrySet())
            conn.setRequestProperty(property.getKey(),property.getValue());//se incluye en el campo AUTH de la cabecera la autenticacion del usuario guardada en el array Properties
            return conn;

    }


//--------------------------------------------------------------------------------------------------------------//
    public JSONObject getJson(String path) throws IOException,JSONException{
        return new JSONObject(getString(path));
    }
//-------------------------------------------------------------------------------------------------//

 public String getString(String path)throws IOException {
     HttpURLConnection conn = null;
     String contents = new String();

     try {
         conn = getConnection(path);
         conn.setRequestMethod("GET");
         Log.d("tag", "Lara:"+conn.getRequestProperties());
         BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

         contents += br.readLine();

     } catch (IOException i) {
         i.printStackTrace();
     } finally {
         if (conn != null) {
             int a = conn.getResponseCode();
             Log.d("tag", "contents:" + contents);
             conn.disconnect();

         }
         return contents;
     }

 }
//----------------------------------------------------------------------------------//

    public int postJson(final JSONObject json,String path)throws IOException {
        HttpURLConnection conn = null;

        try {
            conn = getConnection(path);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            PrintWriter pw=new PrintWriter(conn.getOutputStream());
            pw.print(json.toString());
            pw.close();
            return conn.getResponseCode();
        }
        finally {
            if (conn != null) {
                conn.disconnect();
               // Log.d("tag", "LARA:disconect ");
            }

        }

    }
//-----------------------------------------------------------------------------------------//
public int postFile(String path,InputStream is,String fileName)throws IOException {
    String boundary=Long.toString(System.currentTimeMillis());
    String newLine="\r\n";
    String prefix="--";
    HttpURLConnection conn = null;

    try {
        conn = getConnection(path);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
        DataOutputStream out=new DataOutputStream(conn.getOutputStream());
        out.writeBytes(prefix+boundary+newLine);
        out.writeBytes("Content-Disposition:form-data;name=\"file\";filename=\""+fileName+"\""+newLine);
        out.writeBytes(newLine);
        byte[] data=new byte[1024*1024];
        int len;
        while((len=is.read(data))>0)
            out.write(data,0,len);
        out.writeBytes(newLine);
        out.writeBytes(prefix+boundary+prefix+newLine);
        out.close();
        return conn.getResponseCode();
    }
    finally {
        if (conn != null) {
            conn.disconnect();
            Log.d("tag", "LARA:disconect ");
        }

    }

}
//--------------------------------------------------------------------------------------------//
}
