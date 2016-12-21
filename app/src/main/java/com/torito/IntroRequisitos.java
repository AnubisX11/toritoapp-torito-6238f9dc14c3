package com.torito;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Wall on 06/12/2015.
 */
public class IntroRequisitos extends android.support.v4.app.Fragment {


    Button btnContinuarReq;
    TextView tvTituloReq, tvReq1, tvReq2, tvReq3;
    CheckBox checkBox;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_requisitos, container, false);

    }

    //El fragment se ha adjuntado al Activity
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    //El Fragment ha sido creado
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("IntroReq","Se creó intro req");

    }

    //La vista de layout ha sido creada y ya está disponible
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //La vista ha sido creada y cualquier configuración guardada está cargada
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    //El Activity que contiene el Fragment ha terminado su creación
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        btnContinuarReq = (Button) getView().findViewById(R.id.btn_continuar_req);
        tvTituloReq = (TextView) getView().findViewById(R.id.titulo_req);
        tvReq1 = (TextView) getView().findViewById(R.id.requisito_1);
        tvReq2 = (TextView) getView().findViewById(R.id.requisito2);
        tvReq3 = (TextView) getView().findViewById(R.id.requisito_3);

        btnContinuarReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){

                    SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("Terminos", true);
                    editor.commit();
                    // Insert the fragment by replacing any existing fragment
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment f =fragmentManager.findFragmentByTag("fragIntro");
                    if(f!=null) {
                        fragmentTransaction.remove(f)
                                .commit();
                    }
                }
                else{
                    new AlertDialog.Builder(getActivity())
                            .setTitle("¿Continuar?")
                            .setMessage("Para continuar debes aceptar los terminos y condiciones")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Nada que hacer solo cerrar el dialogo

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //Cerrar la app

                                    getActivity().finish();
                                    System.exit(0);

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

            }
        });

        checkBox = (CheckBox)getView().findViewById(R.id.checkbox);
    }

    //El Fragment ha sido quitado de su Activity y ya no está disponible
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
