package com.pruebas123.petagram.correo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class EnviarCorreo extends AsyncTask<Void,Void,Void> {
    private Context context;
    private String correo, asunto, mensaje;

    private ProgressDialog progressDialog;

    public EnviarCorreo(Context context, String correo, String asunto, String mensaje) {
        this.context = context;
        this.correo = correo;
        this.asunto = asunto;
        this.mensaje = mensaje;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context,"Enviando mensaje...","un momento..",false,false);

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();

        Toast.makeText(context,"Correo enviado",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        Session sesion;
        Properties props = new Properties();

        //Configuración de propiedades para correo de Gmail
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        sesion = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Autenticacion de la cuenta
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Configuracion.EMAIL, Configuracion.PASSWORD);
                    }
                });

        try {
            MimeMessage mm = new MimeMessage(sesion);

            //remitente
            mm.setFrom(new InternetAddress(Configuracion.EMAIL));

            //destinatario
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(Configuracion.DESTINATARIO));
            mm.setSubject(asunto);
            mm.setText(mensaje);

            Transport.send(mm);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
