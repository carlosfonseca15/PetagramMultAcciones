package com.pruebas123.petagram;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.pruebas123.petagram.correo.EnviarCorreo;

public class ActivityContact extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText etMensaje;
    private TextView etNombreCompleto, etEmail;
    Button btEnviarM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Toolbar miActionBar = (Toolbar) findViewById(R.id.miactionbar);
        miActionBar.setTitleTextColor(getResources().getColor(R.color.textoOscuro));
        setSupportActionBar(miActionBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.dog_footprint);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etNombreCompleto = (TextView) findViewById(R.id.etNombreCompleto);
        etEmail = (TextView) findViewById(R.id.etEmail);
        etMensaje = (TextInputEditText) findViewById(R.id.etMensaje);
        btEnviarM = (Button) findViewById(R.id.btEnviarM);
        btEnviarM.setOnClickListener(this);

    }

    private void enviarCorreo(){
        String correoA = etEmail.getText().toString().trim();
        String nombreA = etNombreCompleto.getText().toString().trim();
        String mensajeA = etMensaje.getText().toString().trim();
        mensajeA = nombreA + ": " + mensajeA;

        EnviarCorreo ec = new EnviarCorreo(this, correoA, "Consulta", mensajeA);
        ec.execute();
    }

    @Override
    public void onClick(View view) {
        enviarCorreo();
    }


}