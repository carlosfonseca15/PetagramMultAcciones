package com.pruebas123.petagram.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pruebas123.petagram.DetalleMascota;
import com.pruebas123.petagram.R;
import com.pruebas123.petagram.db.ConstructorMascotas;
import com.pruebas123.petagram.pojo.Mascota;
import com.pruebas123.petagram.restApiFirebase.EndpointsRestApiFirebase;
import com.pruebas123.petagram.restApiFirebase.adapter.RestApiFirebaseAdapter;
import com.pruebas123.petagram.restApiFirebase.model.UsuarioResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.core.app.ActivityOptionsCompat.makeSceneTransitionAnimation;

public class MascotaAdaptador extends RecyclerView.Adapter<MascotaAdaptador.MascotaViewHolder> implements View.OnClickListener{

    ArrayList<Mascota> mascotas;
    Activity activity;
    private View.OnClickListener listener;

    public MascotaAdaptador(ArrayList<Mascota> mascotas, Activity activity) {
        this.mascotas = mascotas;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MascotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_mascota, parent, false);

        v.setOnClickListener(this);
        return new MascotaViewHolder(v);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull MascotaViewHolder mascotaViewHolder, int position) {
        Mascota mascota = mascotas.get(position);
        //mascotaViewHolder.imgFoto.setImageResource(mascota.getImagen());
        Picasso.get()
                .load(mascota.getUrlFoto())
                .placeholder(R.drawable.max)
                .into(mascotaViewHolder.imgFoto);
        mascotaViewHolder.tvNombreCV.setText(mascota.getNombre());
        mascotaViewHolder.tvCalificacionCV.setText(String.valueOf(mascota.getCalificacion()));
        mascotaViewHolder.tvImagenIdCV.setText(mascota.getImagenid());
        mascotaViewHolder.ibCalificacionCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cal = mascota.getCalificacion() + 1;
                mascota.setCalificacion(cal);
                ConstructorMascotas constructorMascotas = new ConstructorMascotas(activity);
                constructorMascotas.actualizarCalificacion(mascota);

                Toast.makeText(activity, "Diste a like", Toast.LENGTH_LONG).show();
                toqueFoto();
                notifyDataSetChanged();  //hace actulizar el valor
            }
        });

        mascotaViewHolder.imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DetalleMascota.class);
                intent.putExtra("url", mascota.getUrlFoto());
                intent.putExtra("like", mascota.getCalificacion());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    Explode explode = new Explode();
                    explode.setDuration(1000);
                    activity.getWindow().setExitTransition(explode);
                    activity.startActivity(intent,
                            makeSceneTransitionAnimation(activity, v, activity.getString(R.string.transicion_foto)).toBundle());

                }else {
                    activity.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {  //cant de elem de la lista
        return mascotas.size();
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public static class MascotaViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgFoto;
        private ImageButton ibCalificacionCV;
        private TextView tvNombreCV;
        private TextView tvCalificacionCV;
        private TextView tvImagenIdCV;

        public MascotaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFoto = (ImageView) itemView.findViewById(R.id.imgFoto);
            ibCalificacionCV = (ImageButton) itemView.findViewById(R.id.ibCalificacionCV);
            tvNombreCV = (TextView) itemView.findViewById(R.id.tvNombreCV);
            tvCalificacionCV = (TextView) itemView.findViewById(R.id.tvCalificacionCV);
            tvImagenIdCV = (TextView) itemView.findViewById(R.id.tvImagenIdCV);

        }
    }

    public void toqueFoto(){
        Log.d("TOQUE","entra a toque");
        ConstructorMascotas constructorMascotas = new ConstructorMascotas(activity);
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
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {

            }
        });

    }

}
