package com.pruebas123.petagram;

import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetalleMascota extends AppCompatActivity {
    private static final String KEY_EXTRA_URL = "url";
    private static final String KEY_EXTRA_CAl = "like";

    private ImageView imgFotoDetalle;
    private TextView tvCalificacionDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_mascota);
        Bundle extras = getIntent().getExtras();
        String url   = extras.getString(KEY_EXTRA_URL);
        int likes    = extras.getInt(KEY_EXTRA_CAl);

        tvCalificacionDetalle     = (TextView) findViewById(R.id.tvCAlificacionDetalle);
        tvCalificacionDetalle.setText(String.valueOf(likes));

        imgFotoDetalle = (ImageView) findViewById(R.id.imgFotoDetalle);
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.max)
                .into(imgFotoDetalle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide(Gravity.BOTTOM);
            slide.setDuration(1200);
            getWindow().setEnterTransition(slide);
            slide.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                    Toast.makeText(DetalleMascota.this, "Empezando", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    Toast.makeText(DetalleMascota.this, "Termina", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });

            getWindow().setReturnTransition(new Fade());
        }else {

        }

    }
}