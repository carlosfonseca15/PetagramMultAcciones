package com.pruebas123.petagram.restApiFirebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.pruebas123.petagram.MainActivity;
import com.pruebas123.petagram.R;
import com.pruebas123.petagram.db.ConstructorMascotas;
import com.pruebas123.petagram.restApi.ConstantesRestApi;
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
        String ACTION_FOLLOW_UNFOLLOW = "FOLLOW_UNFOLLOW";
        String ACTION_KEY_PERFIL = "TOQUE_PERFIL";
        String accion = intent.getAction();
        contexto = context;

        if (ACTION_KEY_FOTO.equals(accion)) {
            Toast.makeText(context, "Diste un toque a " + userid,  Toast.LENGTH_LONG).show();
            toqueFoto();
        }
        if (ACTION_FOLLOW_UNFOLLOW.equals(accion)) {
            Toast.makeText(context, "Fotos de " + userid,  Toast.LENGTH_LONG).show();
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.nombre_base_datos), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.commit();

            String id_usuario_instagram_followed = sharedPreferences.getString(context.getResources().getString(R.string.liked_user_id), ConstantesRestApi.ID);
            // String nombre_usuario_instagram_followed = sharedPreferences.getString(context.getResources().getString(R.string.liked_user_name), ConstantesRestApi.default_user_name);

            String id_usuario_instagram_follower = sharedPreferences.getString(ConstantesRestApi.ID, ConstantesRestApi.ID);

            RestApiFirebaseAdapter restApiAdapter0 = new RestApiFirebaseAdapter();
            EndpointsRestApiFirebase endpoints0 = restApiAdapter0.establecerConRestApiFirebase();
            Call<followUnfollowResponse> folloResponseCall = endpoints0.followUnfollow(id_usuario_instagram_followed, id_usuario_instagram_follower);

            folloResponseCall.enqueue(new Callback<followUnfollowResponse>() {
                @Override
                public void onResponse(Call<followUnfollowResponse> call, Response<followUnfollowResponse> response) {
                    followUnfollowResponse followUnfollowResponse = response.body();

                    if(followUnfollowResponse.getEstado().equals("1"))
                        Toast.makeText(context, "Ahora sigues a "+id_usuario_instagram_followed, Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "Ya no sigues a "+id_usuario_instagram_followed, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<followUnfollowResponse> call, Throwable t) {

                }
            });
        }
        if (ACTION_KEY_PERFIL.equals(accion)) {
            Toast.makeText(context, "Perfil",  Toast.LENGTH_LONG).show();
            SharedPreferences sharedPreferences2 = context.getSharedPreferences(context.getResources().getString(R.string.nombre_base_datos), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences2.edit();
            editor.putString("liked_user", "false");
            editor.commit();

            MainActivity.numTab = 1;
            Intent intentone = new Intent(context.getApplicationContext(), MainActivity.class);
            intentone.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentone);

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
