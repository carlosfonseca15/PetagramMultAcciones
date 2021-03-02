package com.pruebas123.petagram;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.pruebas123.petagram.adapter.PageAdapter;
import com.pruebas123.petagram.db.BaseDatos;
import com.pruebas123.petagram.db.ConstantesBaseDatos;
import com.pruebas123.petagram.fragmnet.PerfilFragment;
import com.pruebas123.petagram.fragmnet.RecyclerviewFragment;
import com.pruebas123.petagram.pojo.Mascota;
import com.pruebas123.petagram.restApi.ConstantesRestApi;
import com.pruebas123.petagram.restApiFirebase.EndpointsRestApiFirebase;
import com.pruebas123.petagram.restApiFirebase.adapter.RestApiFirebaseAdapter;
import com.pruebas123.petagram.restApiFirebase.model.UsuarioResponse;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {


    ArrayList<Mascota> mascotas_sort;
    public static int numTab = 0;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    UsuarioResponse usuarioResponse;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.top5:
                Toast.makeText(this, "Diste a top5", Toast.LENGTH_SHORT).show();
                //sortLista();
                irDetalleTop5();
                break;
            case  R.id.mContact:
                Intent intentC = new Intent(this, ActivityContact.class);
                startActivity(intentC);
                break;
            case R.id.mAbout:
                Intent intentA = new Intent(this, ActivityAbout.class);
                startActivity(intentA);
                break;
            case R.id.mCuenta:
                Intent intentCC = new Intent(this, ActivityCuenta.class);
                startActivity(intentCC);
                break;
            case R.id.mNotificacion:
                //lanzarNotificacion();
                enviarToken();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.miactionbar);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        toolbar.setTitleTextColor(getResources().getColor(R.color.textoOscuro));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.dog_footprint);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setUpViewPager();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        //getToken();
    }

    private ArrayList<Fragment> agregarFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new RecyclerviewFragment());
        fragments.add(new PerfilFragment());
        return  fragments;
    }
    private void setUpViewPager(){
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(),agregarFragments()));
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_dog_oscuro);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_dog_claro);
        tabLayout.setTabIconTint(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
    }

    public void irDetalleTop5(){
        BaseDatos db = new BaseDatos(this);
        mascotas_sort = db.obtenerTodasLasMascotas("SI");
        Intent intent = new Intent(this, DetalleTop5.class);
        //intent.putExtra("FILES_TO_SEND", mascotas_sort);
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST",(Serializable)mascotas_sort);
        intent.putExtra("BUNDLE",args);
        startActivity(intent);
    }

    public void lanzarNotificacion(){
        Log.e("Error","**************inicia*************");
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = "0";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.notificacion)
                        .setContentTitle("Notificacion")
                        .setContentText("Hola Mundo")
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // para Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 , notificationBuilder.build());
    }

    /*public void getToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( MainActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("FIREBASE","==========T:" + newToken);

            }
        });
    }*/

    public void enviarToken(){
        String token = FirebaseInstanceId.getInstance().getToken();
        enviarTokenRegistro(token, ConstantesRestApi.ID);
    }

    private void enviarTokenRegistro(String token, String userid) {
        Log.d("*********TOKEN", token);
        RestApiFirebaseAdapter restApiFirebaseAdapter = new RestApiFirebaseAdapter();
        //EndpointsRestApiFirebase endpointsRestApiFirebase = restApiFirebaseAdapter.establecerConRestApiFirebase();
        EndpointsRestApiFirebase endpointsRestApiFirebase = restApiFirebaseAdapter.establecerConRestApiFirebase();
        ///Call<UsuarioResponse> usuarioResponseCall = endpointsRestApiFirebase.registrarTokenID(token);
        Call<UsuarioResponse> usuarioResponseCall = endpointsRestApiFirebase.registrarUsuario(token,userid);

        usuarioResponseCall.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                UsuarioResponse usuarioResponse = response.body();
                //usuarioResponse = response.body();
                Log.d("ID_FIREBASE",usuarioResponse.getId());
                Log.d("TOKEN_FIREBASE",usuarioResponse.getToken());
                Log.d("TOKEN_FIREBASE",usuarioResponse.getUserid());
                almacenarRegistro(usuarioResponse);
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {

            }
        });
    }

    private void almacenarRegistro(UsuarioResponse usuarioResponse){
        BaseDatos db = new BaseDatos(this);
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_USER_REGISTER_ID, usuarioResponse.getId());
        contentValues.put(ConstantesBaseDatos.TABLE_USER_REGISTER_TOKEN, usuarioResponse.getToken());
        contentValues.put(ConstantesBaseDatos.TABLE_USER_REGISTER_USERID, usuarioResponse.getUserid());
        db.insertarRegistro(contentValues);
    }

}