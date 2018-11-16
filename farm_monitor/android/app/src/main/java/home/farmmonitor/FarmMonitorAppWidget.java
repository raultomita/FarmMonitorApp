package home.farmmonitor;

import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

public class FarmMonitorAppWidget extends AppWidgetProvider {
    @Override
    public void onEnabled(Context context) {
        Toast.makeText(context, "Farm monitor widget provider enabled", Toast.LENGTH_LONG).show();
        ServiceManager.schedule(context);
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        Toast.makeText(context, "Farm monitor widget provider disabled", Toast.LENGTH_LONG).show();
        super.onDisabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            String status = intent.getStringExtra("connectionStatus");

            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.farm_monitor_app_widget);
            views.setTextViewText(R.id.bWidget, status);
            // Instruct the widget manager to update the widget
            mgr.updateAppWidget(appWidgetId, views);
        }
        super.onReceive(context, intent);
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.farm_monitor_app_widget);

        // Construct an Intent object includes web adresss.
        Intent intent = new Intent(context, MainActivity.class);

        // In widget we are not allowing to use intents as usually. We have to use PendingIntent instead of 'startActivity'
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Here the basic operations the remote view can do.
        views.setOnClickPendingIntent(R.id.bWidget, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
