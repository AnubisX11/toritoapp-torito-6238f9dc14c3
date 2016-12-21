package com.torito;


        import android.app.Activity;
        import android.app.TabActivity;
        import android.content.SharedPreferences;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.AsyncTask;
        import android.preference.PreferenceManager;
        import android.support.v4.app.Fragment;
        import android.os.Bundle;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentTransaction;
        import android.support.v4.view.ViewPager;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TabHost;
        import android.widget.Toast;

        import org.json.JSONException;
        import org.json.JSONObject;



public class Perfil extends Fragment {

    private EditText nombre,paterno,materno,correo,telefono;
    private Button boton;
    DBHelper baseDatos;
    Cursor res;
    int numRows;
    public Perfil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);
        return v;


    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        baseDatos = new DBHelper(activity);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        boton = (Button) getView().findViewById(R.id.button);

        nombre = (EditText)getView().findViewById(R.id.Nombre);
        paterno = (EditText)getView().findViewById(R.id.Paterno);
        materno = (EditText)getView().findViewById(R.id.Materno);
        correo = (EditText)getView().findViewById(R.id.Email);
        telefono = (EditText)getView().findViewById(R.id.Telefono);

        res = baseDatos.sinCliente();
        res.moveToFirst();
        numRows = res.getInt(0);

        if (numRows == 1) {
            res = baseDatos.getData();
            res.moveToFirst();
            nombre.setText(res.getString(res.getColumnIndex(baseDatos.CLIENT_COLUMN_NAME)));
            paterno.setText(res.getString(res.getColumnIndex(baseDatos.CLIENT_COLUMN_PATERNO)));
            materno.setText(res.getString(res.getColumnIndex(baseDatos.CLIENT_COLUMN_MATERNO)));
            correo.setText(res.getString(res.getColumnIndex(baseDatos.CLIENT_COLUMN_CORREO)));
            telefono.setText(res.getString(res.getColumnIndex(baseDatos.CLIENT_COLUMN_TELEFONO)));
            boton.setText("Modificar");
        }

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Verificar si cliente existe
                res = baseDatos.sinCliente();
                res.moveToFirst();
                numRows = res.getInt(0);

                if (nombre.getText().toString().equalsIgnoreCase("") || paterno.getText().toString().equalsIgnoreCase("") || materno.getText().toString().equalsIgnoreCase("") || correo.getText().toString().equalsIgnoreCase("") || telefono.getText().toString().equalsIgnoreCase("") ) {
                    Toast.makeText(getActivity(), "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    if (numRows < 1) {
                        //Tabla vacía
                        new MyThread().execute("1", "0", nombre.getText().toString(), paterno.getText().toString(), materno.getText().toString(), correo.getText().toString(), telefono.getText().toString());
                    } else {
                        //Tabla llena, Modificamos cliente
                        Log.i("NumRows", "El numero de filas es" + numRows);
                        new MyThread().execute("2", "0", nombre.getText().toString(), paterno.getText().toString(), materno.getText().toString(), correo.getText().toString(), telefono.getText().toString());
                        res = baseDatos.getData();
                        res.moveToFirst();
                        int idus = res.getInt(res.getColumnIndex(DBHelper.CLIENT_COLUMN_IDUS));
                        baseDatos.updateCliente(idus, nombre.getText().toString(), paterno.getText().toString(), materno.getText().toString(), correo.getText().toString(), telefono.getText().toString());
                        Toast.makeText(getActivity(),"Se modificó correctamente",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


    //Aqui esta el AsyncTask, si crees que se ve muy puerco lo movemos a una mega clase solo para hilos
    private class MyThread extends AsyncTask<String, Void, String > {
        protected String doInBackground(String... datos) {
            // Calculate and return retults
            EnvíoDatosCliente cliente = new EnvíoDatosCliente();
            return cliente.JsonCliente(Integer.parseInt(datos[0]),Integer.parseInt(datos[1]) ,datos[2], datos[3], datos[4], datos[5], datos[6]);
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
                    //Insersión de nuevo cliente
                    baseDatos.insertarCliente(Integer.parseInt(accion),null,null,nombre.getText().toString(),paterno.getText().toString(),materno.getText().toString(),correo.getText().toString(),telefono.getText().toString());
                    Toast.makeText(getActivity(),"Se agregó correctamente",Toast.LENGTH_SHORT).show();
                    ViewPager view = (ViewPager) getActivity().findViewById(R.id.pager);
                    view.setCurrentItem(1);
                }
                //Si tuvimos un error
                else
                    Toast.makeText(getActivity(),"Ha ocurrido un error", Toast.LENGTH_LONG);

                System.out.println(status + "\n" + accion);

            } catch (JSONException e) {
                System.out.println("Error en la respuesta obtenida del servidor");
                e.printStackTrace();
            }


        }
    }


}
