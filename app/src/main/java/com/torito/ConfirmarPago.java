package com.torito;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ConfirmarPago extends Fragment {
    DBHelper baseDatos;
    Cursor res;

    View view2;
    Context cont;

    public ConfirmarPago(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view =inflater.inflate(R.layout.fragment_confirmar_pago, container, false);
        return view;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        baseDatos = new DBHelper(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        res = baseDatos.getData();
        res.moveToFirst();


        Button confirmar = (Button) getView().findViewById(R.id.btn_confirmarpagos);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                res = baseDatos.getData();
                res.moveToFirst();
                int idus = res.getInt(res.getColumnIndex(DBHelper.CLIENT_COLUMN_IDUS));

                TextView textview = (TextView) getActivity().findViewById(R.id.textView11);

                TextView texto1 = (TextView)getActivity().findViewById(R.id.textView4);
                texto1.setVisibility(View.VISIBLE);

                TextView texto2 = (TextView)getActivity().findViewById(R.id.textView5);
                texto2.setVisibility(View.VISIBLE);

                TextView texto3 = (TextView)getActivity().findViewById(R.id.textView9);
                texto3.setVisibility(View.VISIBLE);
                String correo = res.getString(res.getColumnIndex(baseDatos.CLIENT_COLUMN_CORREO));


                TextView texto4 = (TextView)getActivity().findViewById(R.id.textView10);
                texto4.setVisibility(View.VISIBLE);

                TextView texto5 = (TextView)getActivity().findViewById(R.id.textView11);
                texto5.setVisibility(View.VISIBLE);


                TextView texto6 = (TextView)getActivity().findViewById(R.id.textView14);
                texto6.setVisibility(View.GONE);


                cont=getActivity();

                MyThread hilo= new MyThread(textview);
                hilo.execute(Integer.toString(idus));
                TextView Texto=hilo.dataDisplay;
                Texto =new TextView(getActivity());
                texto5.setText(Texto.getText().toString());
                texto3.setText(correo);


            }
        });
    }


    //Aqui esta el AsyncTask, si crees que se ve muy puerco lo movemos a una mega clase solo para hilos
    public class MyThread extends AsyncTask<String, Void, String > {

        TextView dataDisplay;
        TextView tview;
        public MyThread(TextView view){
            this.tview = view;
        }

        protected String doInBackground(String... datos) {
            // Calculate and return retults
            EnvíoDatosCliente cliente = new EnvíoDatosCliente();
            return cliente.SolicitudServicio(Integer.parseInt(datos[0]));
            //regreso el resultado de la peticion de modificacion de datos
        }


        protected void onPostExecute(String result) {
            // This is executed in main Thread, use the result
            try {
                JSONObject parser = new JSONObject(result);
                String status = parser.getString("status");
                String accion = parser.getString("accion");
                //Si no tuvimos error
                if(!status.equals("0")){

                    tview.setText(accion);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment f =fragmentManager.findFragmentByTag("confPago");
                    if(f!=null) {
                        fragmentTransaction.remove(f)
                                .commit();
                    }
                }
                //Si tuvimos un error
                else
                    Toast.makeText(getActivity(), "Ha ocurrido un error procesando el pago.\n Intentelo de nuevo", Toast.LENGTH_LONG);

                System.out.println(status + "\n" + accion);

            } catch (JSONException e) {
                System.out.println("Error en la respuesta obtenida del servidor");
                e.printStackTrace();
            }
        }
    }

}


