package com.pruebas123.petagram.restApiFirebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.pruebas123.petagram.db.ConstructorMascotas;
import com.pruebas123.petagram.restApiFirebase.adapter.RestApiFirebaseAdapter;
import com.pruebas123.petagram.restApiFirebase.model.UsuarioResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToqueFoto extends BroadcastReceiver {
    Context contexto;
    String userid;

    @Override
    public void onReceive(Context context, Intent intent) {
        String ACTION_KEY_FOTO = "TOQUE_FOTO";
        String ACTION_KEY_FOTOS = "TOQUE_FOTOS";
        String ACTION_KEY_PERFIL = "TOQUE_PERFIL";
        String accion = intent.getAction();
        contexto = context;

        if (ACTION_KEY_FOTO.equals(accion)) {
            Toast.makeText(context, "Diste un toque a " + userid,  Toast.LENGTH_LONG).show();
            toqueFoto();
        }
        if (ACTION_KEY_FOTOS.equals(accion)) {
            Toast.makeText(context, "Fotos de " + userid,  Toast.LENGTH_LONG).show();
        }
        if (ACTION_KEY_PERFIL.equals(accion)) {
            Toast.makeText(context, "Perfil",  Toast.LENGTH_LONG).show();
        }

    }

    public void toqueFoto(){
        ConstructorMascotas constructorMascotas = new ConstructorMascotas(contexto);
        UsuarioResponse usuarioResponse = constructorMascotas.ObtieneRegistro();
        RestApiFirebaseAdapter restApiFirebaseAdapter = new RestApiFirebaseAdapter();
        EndpointsRestApiFirebase endpointsRestApiFirebase = restApiFirebaseAdapter.establecerConRestApiFirebase();
        Call<UsuarioResponse> usuarioResponseCall = endpointsRestApiFirebase.toqueFoto(usuarioResponse.getId(), usuarioResponse.getUserid());
        usuarioResponseCall.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                UsuarioResponse usuarioResponse1 = response.body();
                Log.d("FIREBASE_ID", usuarioResponse1.getId());
                Log.d("FIREBASE_TOKEN", usuarioResponse1.getToken());
                Log.d("FIREBASE_USERID", usuarioResponse1.getUserid());
                userid = usuarioResponse1.getUserid();
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {

            }
        });

    }

}
