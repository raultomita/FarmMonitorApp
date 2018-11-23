package home.farmmonitor;

import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public  class Client extends WebSocketListener {
    private FarmMonitorService service;
    public Client(FarmMonitorService service){
        this.service = service;
    }

    private static final int NORMAL_CLOSURE_STATUS = 1000;
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        webSocket.send("Hello, it's SSaurel !");
        webSocket.send("What's up ?");
        webSocket.send(ByteString.decodeHex("deadbeef"));
    }
    @Override
    public void onMessage(WebSocket webSocket, String text) {
        service.sendNotification(android.R.color.holo_green_light, "Socket", "Receiving : " + text);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        service.sendNotification(android.R.color.holo_red_dark,"Socket", "Closing : " + code + " / " + reason);
    }
    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        service.sendNotification(android.R.color.holo_red_dark,"Socket", "Error : " + t.getMessage());
    }
}