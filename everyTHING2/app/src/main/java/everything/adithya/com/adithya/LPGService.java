package everything.adithya.com.adithya;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.format.Formatter;
import android.widget.Toast;

import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

public class LPGService extends Service {
    public LPGService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"Service started",Toast.LENGTH_SHORT).show();
        new IP().execute();
        new LP().execute();

         return START_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }

    private class LP extends AsyncTask<Void,String,Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
           ServerSocket socket = null;
            try{

                for(;;) {
                    ServerSocket sock = new ServerSocket(5506);
                    Socket s = sock.accept();

                    s.close();
                    return true;
                }
            }catch(Exception e){

                  return false;
            }


        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) {
            NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(LPGService.this)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("LPG ALERT!")
                                .setContentText("LPG LEAKAGE HAS BEEN DETECTED");
                Intent resultIntent = new Intent(LPGService.this, Home.class);


                PendingIntent resultPendingIntent =
                        PendingIntent.getActivity(
                                LPGService.this,
                                0,
                                resultIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );


                mBuilder.setContentIntent(resultPendingIntent);
                int mNotificationId = 001;
             Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                mBuilder.setSound(uri);
                NotificationManager mNotifyMgr =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                mNotifyMgr.notify(mNotificationId, mBuilder.build());
                Toast.makeText(LPGService.this,"GAS",Toast.LENGTH_SHORT).show();
                LPGService.this.stopSelf();
                Intent inten = new Intent(LPGService.this,LPGService.class);
                startService(inten);

            }

        }
    }
    private class IP extends AsyncTask<Void,Void,String>{


        @Override
        protected String doInBackground(Void... params) {
            try{
                WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
                WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
                int ip = wifiInfo.getIpAddress();
                String ipAddress = Formatter.formatIpAddress(ip);
                return ipAddress;
            }catch (Exception e){
                return "";
            }

           // return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(LPGService.this,s,Toast.LENGTH_LONG).show();


        }
    }
}
