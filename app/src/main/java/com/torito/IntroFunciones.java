package com.torito;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Wall on 06/12/2015.
 * android.support.v4.app.Fragment
 */
public class IntroFunciones extends android.support.v4.app.Fragment{


    private onFragmentInteractionListener myListener;
    Button btnContinuarFunc;
    TextView tvTituloFunc, tvFunc1, tvFunc2, tvFunc3;

    public IntroFunciones(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_funciones, container, false);
    }
    public interface onFragmentInteractionListener{

        public void onFragmentInteraction(Uri uri);
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

        btnContinuarFunc=(Button)getView().findViewById(R.id.btn_continuar_func);
        tvTituloFunc =(TextView) getView().findViewById(R.id.titulo_func);
        tvFunc1 =(TextView) getView().findViewById(R.id.funcion_1);
        tvFunc2 =(TextView) getView().findViewById(R.id.funcion_2);
        tvFunc3 =(TextView) getView().findViewById(R.id.funcion_3);


        btnContinuarFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //Insert the fragment by replacing any existing fragment
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    IntroRequisitos funcReq = new IntroRequisitos();
                    transaction.replace(R.id.fragment_container,funcReq,"fragIntro")
                            .commit();
            }
        });



    }

    //El Fragment ha sido quitado de su Activity y ya no está disponible
    @Override
    public void onDetach() {
        super.onDetach();
    }


}
