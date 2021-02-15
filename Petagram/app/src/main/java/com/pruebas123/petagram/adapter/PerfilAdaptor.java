package com.pruebas123.petagram.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pruebas123.petagram.R;
import com.pruebas123.petagram.pojo.Mascota;

import java.util.ArrayList;

public class PerfilAdaptor extends RecyclerView.Adapter<PerfilAdaptor.PerfilAdaptorViewHolder> {

    private ArrayList<Mascota> mascotaPerfil;
    Activity activity;

    public PerfilAdaptor(ArrayList<Mascota> mascota, Activity activity) {
        this.mascotaPerfil = mascota;
        this.activity = activity;
    }


    @NonNull
    @Override
    public PerfilAdaptorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_mascota_perfil,parent,false);
        return new PerfilAdaptorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PerfilAdaptorViewHolder holder, int position) {
        Mascota pmascota = mascotaPerfil.get(position);

        holder.img_FotoP.setImageResource(pmascota.getImagen());
        /*Picasso.with(activity)
                .load(pmascota.getUrlFoto())
                .placeholder(R.drawable.max)
                .into(holder.img_FotoP);*/
        holder.tvCalificacionP.setText(String.valueOf(pmascota.getCalificacion()));
    }

    @Override
    public int getItemCount() {
        return mascotaPerfil.size();
    }

    public static class PerfilAdaptorViewHolder extends RecyclerView.ViewHolder{

        private ImageView img_FotoP;
        private TextView tvCalificacionP;

        public PerfilAdaptorViewHolder(@NonNull View itemView) {
            super(itemView);
            img_FotoP = (ImageView) itemView.findViewById(R.id.img_FotoP);
            tvCalificacionP = (TextView) itemView.findViewById(R.id.tvCalificacionP);
        }
    }
}
