package com.pruebas123.petagram.presentador;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pruebas123.petagram.db.ConstructorMascotas;
import com.pruebas123.petagram.fragmnet.IRecyclerviewFragmentView;
import com.pruebas123.petagram.pojo.Mascota;
import com.pruebas123.petagram.restApi.EndpointsApi;
import com.pruebas123.petagram.restApi.adapter.RestApiAdapter;
import com.pruebas123.petagram.restApi.model.Id;
import com.pruebas123.petagram.restApi.model.InstagramApiResponse;
import com.pruebas123.petagram.restApi.model.MediaResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerviewFragmentPresenter implements IRecyclerviewFragmentPresenter {

    private IRecyclerviewFragmentView iRecyclerviewFragmentView;
    private Context context;
    private ConstructorMascotas constructorMascotas;
    private ArrayList<Mascota> mascotas = new ArrayList<Mascota>();

    public RecyclerviewFragmentPresenter(IRecyclerviewFragmentView iRecyclerviewFragmentView, Context context) {
        this.iRecyclerviewFragmentView = iRecyclerviewFragmentView;
        this.context = context;
        //obtenerMascotasBaseDatos();
        obtenerMediosRecientes(); //instagram*************************
    }

    @Override
    public void obtenerMascotasBaseDatos() {
        constructorMascotas = new ConstructorMascotas(context);
        mascotas = constructorMascotas.obtenerDatos();
        mostrarMascotasRV();
    }

    @Override
    public void mostrarMascotasRV() {
        iRecyclerviewFragmentView.inicializarAdaptadorRV(iRecyclerviewFragmentView.crearAdaptador(mascotas));
        iRecyclerviewFragmentView.generarLinerLaoutVertical();
    }

    @Override
    public void obtenerMediosRecientes() {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorMediaRecent();
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonMediaRecent);
        Call<InstagramApiResponse> mascotaResponseCall = endpointsApi.getMedia();

        mascotaResponseCall.enqueue(new Callback<InstagramApiResponse>() {
            @Override
            public void onResponse(Call<InstagramApiResponse> call, Response<InstagramApiResponse> response) {
                if (response != null) {
                    InstagramApiResponse data = response.body();

                    ArrayList<Id> mediaIds = data.getMedia().getData();
                    String name = "mascota";
                    for (Id id: mediaIds) {
                        Mascota mascota = new Mascota();
                        Call<MediaResponse> mediaResponse = endpointsApi.getMediaDetail(id.getId());
                        mediaResponse.enqueue(new Callback<MediaResponse>() {
                            @Override
                            public void onResponse(Call<MediaResponse> call, Response<MediaResponse> response) {
                                if (response != null) {
                                    Log.e("Error", response.body().toString());
                                    MediaResponse info = response.body();
                                    mascota.setNombre(name);
                                    mascota.setUrlFoto(info.getMedia_url());
                                    mascota.setCalificacion(info.getLike_count());
                                    mascota.setImagenid(info.getId());

                                    Log.e("Error ", info.getId() );
                                    Log.e("Error", String.valueOf(info.getLike_count()));
                                    mascotas.add(mascota);

                                }
                                mostrarMascotasRV();
                            }

                            @Override
                            public void onFailure(Call<MediaResponse> call, Throwable t) {
                                Log.e("Error", t.toString());
                            }
                        });
                    }
                } else {
                    Log.e("error", new Gson().toJson(response.errorBody()));
                }

            }

            @Override
            public void onFailure(Call<InstagramApiResponse> call, Throwable t) {
                Toast.makeText(context, "Algo paso con la conexion, Intenta de nuevo", Toast.LENGTH_LONG).show();
                Log.e("Fallo la conexion", t.toString());
            }
        });

    }


}
