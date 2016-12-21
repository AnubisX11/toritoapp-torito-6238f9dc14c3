package com.torito;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Legal extends Fragment {

    public Legal() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_legal, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView texto1 = (TextView)getActivity().findViewById(R.id.textView4);
        texto1.setVisibility(View.GONE);

        TextView texto2 = (TextView)getActivity().findViewById(R.id.textView5);
        texto2.setVisibility(View.GONE);

        TextView texto3 = (TextView)getActivity().findViewById(R.id.textView9);
        texto3.setVisibility(View.GONE);

        TextView texto4 = (TextView)getActivity().findViewById(R.id.textView10);
        texto4.setVisibility(View.GONE);

        TextView texto5 = (TextView)getActivity().findViewById(R.id.textView11);
        texto5.setVisibility(View.GONE);
    }

    public void setTextString(String texto){
        TextView orden = (TextView)getActivity().findViewById(R.id.textView11);
        orden.setText(texto);
    }
}
