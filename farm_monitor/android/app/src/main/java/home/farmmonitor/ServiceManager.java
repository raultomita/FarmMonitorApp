package home.farmmonitor;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.widget.Toast;

public class ServiceManager {
    private static int JOB_ID = 1;

    public static void schedule(Context context) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo jobInfo = scheduler.getPendingJob(JOB_ID);

        if (jobInfo != null) {
            Toast.makeText(context, "job found", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "job NOT found JOB_IDnd it will be created", Toast.LENGTH_SHORT).show();
            JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(context, FarmMonitorService.class));
            builder.setOverrideDeadline(0);
            scheduler.schedule(builder.build());
        }
    }

    public static void unschedule(Context context) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.cancel(JOB_ID);
    }
}
