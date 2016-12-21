package com.torito;
//Falta  sustituir los fragments de la pantalla de rescatenme y la de legal

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import io.conekta.conektasdk.Conekta;
import io.conekta.conektasdk.Card;
import io.conekta.conektasdk.Token;


public class Rescatenme extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private EditText NombreTarj, NumeroTarj,MesTarj, AñoTarj, CVCTarj;
    private Button Confirmar;
    DBHelper baseDatos;
    Cursor res;
    Activity dePaso;
    View view;

    public Rescatenme() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NombreTarj = (EditText) getView().findViewById(R.id.NombreTarjeta);
        MesTarj = (EditText) getView().findViewById(R.id.MesTarjeta);
        AñoTarj = (EditText) getView().findViewById(R.id.AñoTarjeta);
        CVCTarj = (EditText) getView().findViewById(R.id.CVCTarjeta);
        NumeroTarj = (EditText) getView().findViewById(R.id.NumeroTarjeta);
        Confirmar = (Button) getView().findViewById(R.id.button2);
        Confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (NombreTarj.getText().toString().equalsIgnoreCase("") || MesTarj.getText().toString().equalsIgnoreCase("") || AñoTarj.getText().toString().equalsIgnoreCase("") || CVCTarj.getText().toString().equalsIgnoreCase("") || NumeroTarj.getText().toString().equalsIgnoreCase("") ) {
                    Toast.makeText(getActivity(), "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                }else{

                validarDatos();
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_rescatenme, container, false);
        return view;
    }

    private void validarDatos(){

        dePaso = getActivity();
        new AlertDialog.Builder(dePaso)
                .setTitle("¿Estas seguro?")
                .setMessage("¿Son correctos los datos?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       // Si son correctos llevarlos a Conekta



                        Activity activity = getActivity();

                        Conekta.setPublicKey("key_CDfPNikmqyvjvUksrF7Lz6w"); //Cambiar llave sandbox por llave de producción
                        Conekta.setApiVersion("0.3.0"); //Set api version (optional)
                        Conekta.setLanguage("es");
                        Conekta.collectDevice(activity); //Collect device

                        Card card = new Card(NombreTarj.getText().toString(),NumeroTarj.getText().toString(),CVCTarj.getText().toString(),MesTarj.getText().toString(),AñoTarj.getText().toString());
                        Token token = new Token(activity);

                        token.onCreateTokenListener(new Token.CreateToken() {
                            @Override
                            public void onCreateTokenReady(JSONObject data) {
                                try {
                                    //TODO: Create charge
                                    Log.d("Token::::", data.getString("id"));

                                        //Consultar IDUS
                                        res = baseDatos.getData();
                                        res.moveToFirst();
                                        int idus = res.getInt(res.getColumnIndex(DBHelper.CLIENT_COLUMN_IDUS));

                                        //Insertamos num token actual en base de datos
                                        baseDatos.actualizarToken(idus, data.getString("id"));
                                        new MyThread().execute(Integer.toString(idus), data.getString("id"));


                                } catch (Exception err) {
                                    //TODO: Handle error
                                    //Falta probar esta parte nomas//////////////////////////////////////////////////////////////////////////////////
                                    Toast.makeText(getActivity(), err.toString(), Toast.LENGTH_LONG).show();
                                    Log.d("Error: ", err.toString());
                                }
                            }
                        });

                        token.create(card);//Create token

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Nada que hacer sigue trabajando
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dePaso = activity;
        baseDatos = new DBHelper(dePaso);
    }





    private class MyThread extends AsyncTask<String, Void, String > {
        protected String doInBackground(String... datos) {

            //Asigno num token
            EnvíoDatosCliente cliente = new EnvíoDatosCliente();

            return cliente.AltaCard(Integer.parseInt(datos[0]),datos[1]);

        }


        protected void onPostExecute(String result) {
            // This is executed in main Thread, use the result
            try {
                JSONObject parser = new JSONObject(result);
                String status = parser.getString("status");
                String accion = parser.getString("accion");
                //En caso de que la respuesta sea error
                if(!status.equals("0")){
                    Toast.makeText(getActivity(), accion, Toast.LENGTH_LONG);



                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    ConfirmarPago confirmarPago = new ConfirmarPago();
                    //IntroFunciones funcFrag = new IntroFunciones();
                    transaction.add(R.id.fragment_container, confirmarPago,"confPago")
                            .commit();


                    ViewPager view = (ViewPager) getActivity().findViewById(R.id.pager);
                    view.setCurrentItem(2);


                }
                //En caso de error
                else{
                    Toast.makeText(getActivity(),"Ha ocurrido un error", Toast.LENGTH_LONG);
                }
                /*ViewPager view = (ViewPager) getActivity().findViewById(R.id.pager);
                view.setCurrentItem(1);
*/
                System.out.println(status + "\n" + accion);

            } catch (JSONException e) {
                System.out.println("El parser fallo");
                e.printStackTrace();
            }


        }
    }


}
