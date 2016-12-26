package everything.adithya.com.adithya;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

/**
 * Created by Adithya on 23-Aug-16.
 */
public class NotificationListner extends NotificationListenerService {
     static String action = "everything.adithya.com.adithya.recv";
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Intent intent = new Intent(action);
        intent.putExtra("package",sbn.getPackageName());
        sendBroadcast(intent);

    }
}
