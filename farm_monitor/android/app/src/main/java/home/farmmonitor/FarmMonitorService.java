package home.farmmonitor;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class FarmMonitorService extends JobService {
    private final String CHANNEL_ID = "notifications.home.farmMonitor";
    private final int NOTIFICATION_ID = 1;
    OkHttpClient client;
    private FarmMonitorBroadcastReceiver receiver;
    private NotificationCompat.Builder notificationBuilder;

    @Override
    public boolean onStartJob(JobParameters params) {
        createNotificationChannel();
        createNotificationBuilder();
        sendNotification(android.R.color.darker_gray, "Service started", "Initialization in progress");
        client = new OkHttpClient();
        receiver = new FarmMonitorBroadcastReceiver(this);
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        return true;
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        sendNotification(android.R.color.darker_gray, "Service stopped", "Nothing to say");
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        return true;
    }

    public void wifiDisconnected() {
        sendNotification(android.R.color.darker_gray, "Offline", "Wifi is NOT connected");
    }

    public void wifiConnected() {
        Request request = new Request.Builder().url("ws://rtomita.intern.essensys.ro:5000/ws").build();
        Client listener = new Client(this);
        WebSocket ws = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();

        //sendNotification(android.R.color.holo_green_dark, "Online", "Wifi is connected");
        //RedisClient redisClient = RedisClient.create("redis://password@localhost:6379/0");
        //StatefulRedisConnection<String, String> connection = redisClient.connect();
    }

    public void sendNotification(int color, String title, String content){
        notificationBuilder.setSmallIcon(color)
                .setColor(color)
                .setContentTitle(title)
                .setContentText(content);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        String name = "Home";
        String description = "It monitors Wifi and redis connections";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

    }

    private void createNotificationBuilder(){
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                //.setOngoing(true)
        ;
    }
}
