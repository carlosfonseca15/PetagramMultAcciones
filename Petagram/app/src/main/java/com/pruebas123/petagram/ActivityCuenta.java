package com.pruebas123.petagram;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ActivityCuenta extends AppCompatActivity {

    private TextView etCuenta;
    Button btGuardarCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);

        Toolbar miActionBar = (Toolbar) findViewById(R.id.miactionbar);
        miActionBar.setTitleTextColor(getResources().getColor(R.color.textoOscuro));
        setSupportActionBar(miActionBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.dog_footprint);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etCuenta = (TextView) findViewById(R.id.etCuenta);
        btGuardarCuenta = (Button) findViewById(R.id.btGuardarCuenta);


    }
}