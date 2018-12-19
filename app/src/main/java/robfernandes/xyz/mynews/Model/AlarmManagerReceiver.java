package robfernandes.xyz.mynews.Model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Roberto Fernandes on 18/12/2018.
 */
public class AlarmManagerReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmManagerReceiver";
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Log.d(TAG, "onReceive: ");
        createNotifications();
    }

    private void createNotifications() {
        Notifications notifications = new Notifications(context);
        DataManager dataManager = new DataManager(context);
        String title = "MyNews: We found new news you may be interested";
        String message = "";
        notifications.createNotification(title, message);
    }
}