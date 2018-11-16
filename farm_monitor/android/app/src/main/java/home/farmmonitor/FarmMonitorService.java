package home.farmmonitor;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.widget.Toast;

public class FarmMonitorService extends JobService {
    private FarmMonitorBroadcastReceiver receiver;

    @Override
    public boolean onStartJob(JobParameters params) {
        Toast.makeText(this, "Farm monitor job started", Toast.LENGTH_SHORT).show();
        receiver = new FarmMonitorBroadcastReceiver();
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Toast.makeText(this, "Farm monitor job stopped", Toast.LENGTH_SHORT).show();
        if(receiver != null){
            unregisterReceiver(receiver);
        }
        return false;
    }
}
