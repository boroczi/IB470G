package com.example.vizora;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {
    private Context context;
    private NotificationManager manager;

    public NotificationHandler(Context context) {
        this.context = context;
        this.manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel();
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }

        NotificationChannel channel = new NotificationChannel("default", "Vizora", NotificationManager.IMPORTANCE_DEFAULT);

        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(Color.RED);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        manager.createNotificationChannel(channel);
    }

    public void send(String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
        builder.setContentTitle("Vizora");
        builder.setContentText(message);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        manager.notify(1, builder.build());
    }
}
