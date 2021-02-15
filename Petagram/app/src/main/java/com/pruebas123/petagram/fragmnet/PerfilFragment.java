package com.pruebas123.petagram.fragmnet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pruebas123.petagram.R;
import com.pruebas123.petagram.adapter.PerfilAdaptor;
import com.pruebas123.petagram.pojo.Mascota;

import java.util.ArrayList;

public class PerfilFragment extends Fragment {

    private RecyclerView listaFotosPerfil;
    ArrayList<Mascota> mperfil;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);

        listaFotosPerfil = (RecyclerView) v.findViewById(R.id.gridPerfil);

        GridLayoutManager glm = new GridLayoutManager(getContext(), 2);
        listaFotosPerfil.setHasFixedSize(true);
        listaFotosPerfil.setLayoutManager(glm);

        iniciarListaFotosPerfil();
        inicializarAdaptador();

        return v;
    }

    private void inicializarAdaptador() {
        PerfilAdaptor perfilAdaptor = new PerfilAdaptor(mperfil, getActivity());
        listaFotosPerfil.setAdapter(perfilAdaptor);
    }

    private void iniciarListaFotosPerfil() {
        mperfil = new ArrayList<Mascota>();
        mperfil.add(new Mascota(R.drawable.max, 2, "0"));
        mperfil.add(new Mascota(R.drawable.max, 3, "0"));
        mperfil.add(new Mascota(R.drawable.max, 4, "0"));
        mperfil.add(new Mascota(R.drawable.max, 1,"0"));
        mperfil.add(new Mascota(R.drawable.max, 7,"0"));
        mperfil.add(new Mascota(R.drawable.max, 3,"0"));
    }

}