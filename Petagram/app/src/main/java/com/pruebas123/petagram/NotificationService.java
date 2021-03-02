package com.pruebas123.petagram;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;

//import androidx.core.app.NotificationCompat;
//    import androidx.core.app.NotificationManagerCompat;
//    import androidx.core.app.NotificationCompat.WearableExtender;

public class NotificationService extends FirebaseMessagingService {

    private static final String TAG = "FIREBASE";
    private static final int NOTIFICATION_ID = 001;
    private static final String channelId = "0";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);
        Log.i(TAG, "From:" + remoteMessage.getFrom());
        String mensaje = "Sin mensaje";
        if (remoteMessage.getNotification() != null) {
            Log.i(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            mensaje = remoteMessage.getNotification().getBody();
        }
        //lanzarNotificacion(mensaje);
        lanzaNot(mensaje);
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "NUEVO TOKEN: " + token);
    }

    public void lanzarNotificacion(String msg){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.notificacion)
                        .setContentTitle("Notificacion")
                        .setContentText(msg)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .addAction(R.drawable.ic_full_poke, getString(R.string.texto_sccion_toque), pendingIntent);
        //NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // para Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,"Channel title", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(NOTIFICATION_ID , notificationBuilder.build());
    }

    public void lanzaNot(String msg){
        Intent intent = new Intent();
        intent.setAction("TOQUE_FOTO");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intent_main = new Intent(this, MainActivity.class);
        intent_main.setAction("FOLLOW_UNFOLLOW");
        PendingIntent pendingIntent_main = PendingIntent.getActivity(this, 0 , intent_main, PendingIntent.FLAG_ONE_SHOT);

        Intent intent_perfil = new Intent(this, ActivityAbout.class);
        intent_main.setAction("TOQUE_PERFIL");
        PendingIntent pendingIntent_perfil = PendingIntent.getActivity(this, 0 , intent_perfil, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_full_poke,
                        getString(R.string.texto_sccion_toque), pendingIntent)
                .build();
        NotificationCompat.Action action_main =
                new NotificationCompat.Action.Builder(R.drawable.ic_user,
                        "Seguir/no seguir", pendingIntent_main)
                        .build();
        NotificationCompat.Action action_perfil =
                new NotificationCompat.Action.Builder(R.drawable.ic_customer,
                        getString(R.string.texto_sccion_perfil), pendingIntent_perfil)
                        .build();

        ArrayList<NotificationCompat.Action> acciones = new ArrayList<NotificationCompat.Action>();
        acciones.add(action);
        acciones.add(action_main);
        acciones.add(action_perfil);

        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                .setHintHideIcon(true)
                .setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.bk_androidwear))
                .setGravity(Gravity.CENTER_VERTICAL)
                ;

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.notificacion)
                        .setContentTitle("Notificacion")
                        .setContentText(msg)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .extend(wearableExtender.addActions(acciones))
                ;

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }


}
