package com.pruebas123.petagram.fragmnet;

import com.pruebas123.petagram.adapter.MascotaAdaptador;
import com.pruebas123.petagram.pojo.Mascota;

import java.util.ArrayList;

public interface IRecyclerviewFragmentView {

    public void generarLinerLaoutVertical();
    public void generarGridLaout();

    public MascotaAdaptador crearAdaptador(ArrayList<Mascota> mascotas);

    public void inicializarAdaptadorRV(MascotaAdaptador adaptador);

}
