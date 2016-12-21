package com.torito;
import android.content.ContentValues;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;



/**
 * Created by Angel Moreno on 06/12/2015.
 */
public class EnvíoDatosCliente {

    //Camnbié la url, le agregué el ?json=
    private final String SERVER_URL="http://torito.intoconsultancy.com/index.php/api?json=";
    private JSONObject mensaje,datos;
    private String success="";
    private String json;


    //Insertar datos de usuario
    //Movi este, si regresa boolean no hay forma de saber si lo hizo bien o no cambio boolean por String en return type
    public String JsonCliente(int msg,int idus,String nombre, String paterno, String materno, String correo, String telefono){


        switch (msg) {
            case 1://Alta de cliente

                mensaje = new JSONObject();
                try {
                    mensaje.put("msg", "alta_usuario");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                datos = new JSONObject();
                try {
                    datos.put("idus", 0);
                    datos.put("nombre", nombre);
                    datos.put("paterno", paterno);
                    datos.put("materno", materno);
                    datos.put("correo", correo);
                    datos.put("telefono", telefono);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    mensaje.put("datos", datos);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                json = mensaje.toString();


                success = EnviarDatos(json);


                break;
            case 2: //Modificacion de un cliente
                mensaje = new JSONObject();
                try {
                    mensaje.put("msg", "modificar_usuario");
                }catch(Exception e){
                    e.printStackTrace();
                }

                datos = new JSONObject();
                try {
                    datos.put("idus",idus);
                    datos.put("nombre",nombre);
                    datos.put("paterno",paterno);
                    datos.put("materno",materno);
                    datos.put("correo",correo);
                    datos.put("telefono",telefono);
                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    mensaje.put("datos",datos);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                json =mensaje.toString();


                success= EnviarDatos(json);
                break;
        }
        return success;
    }


    public String AltaCard(int idus, String token){
        mensaje = new JSONObject();
        try {
            mensaje.put("msg", "alta_card");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        datos = new JSONObject();
        try {
            datos.put("idus", idus);
            datos.put("token", token);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            mensaje.put("datos", datos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        json = mensaje.toString();
        success = EnviarDatos(json);
        return success;
    }

    public String SolicitudServicio(int idus){
        mensaje = new JSONObject();
        try {
            mensaje.put("msg", "solicitud");
        } catch (Exception e) {
        }

        datos = new JSONObject();
        try {
            datos.put("idus", idus);

        } catch (Exception e) {
        }

        try {
            mensaje.put("datos", datos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        json = mensaje.toString();
        success = EnviarDatos(json);
        return success;
    }





    private String EnviarDatos(String json) {

        StringBuffer chain= new StringBuffer("");
        try {
            //Url del servidor
            System.out.println(json);
            URL url =new URL(SERVER_URL+json);
            HttpURLConnection conexion = (HttpURLConnection)url.openConnection();
            conexion.setRequestProperty("User-Agent", "");
            //conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setRequestMethod("POST");
            conexion.setDoInput(true);
            conexion.setDoOutput(true);
            conexion.connect();

            OutputStream os = conexion.getOutputStream();

            //os.write(String.valueOf(json));
            os.flush();
            os.close();
            InputStream inputStream = conexion.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = rd.readLine()) != null) {
                chain.append(line);
            }
            System.out.println(chain.toString());
            System.out.println();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return chain.toString();
    }





}
