package home.farmmonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class FarmMonitorBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String wifiSSID = getWifiSSID(context);
        if (wifiSSID != null) {
            Toast.makeText(context, "Wifi connected:" + wifiSSID, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Wifi NOT connected", Toast.LENGTH_SHORT).show();
        }
    }

    private String getWifiSSID(Context context) {
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
            return wifiInfo.getSSID();
        }

        return null;
    }
}
