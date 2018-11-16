package home.farmmonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class FarmMonitorBroadcastReceiver extends BroadcastReceiver {

    private FarmMonitorService service;

    public FarmMonitorBroadcastReceiver(FarmMonitorService service){

        this.service = service;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isWifiConnected(context)) {
            service.wifiConnected();
        } else {
            service.wifiDisconnected();
        }
    }

    private boolean isWifiConnected(Context context) {
        final ConnectivityManager connManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final WifiManager wifiManager = (WifiManager)
                context.getSystemService(Context.WIFI_SERVICE);

        final NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        final WifiInfo wifiInfo = wifiManager.getConnectionInfo();


        if (networkInfo != null &&
                networkInfo.getType() == ConnectivityManager.TYPE_WIFI &&
                networkInfo.isConnected() &&
                wifiInfo != null) {
            return true;
        }

        return false;
    }
}
