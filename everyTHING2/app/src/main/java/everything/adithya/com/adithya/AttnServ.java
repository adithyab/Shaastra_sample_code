package everything.adithya.com.adithya;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Adithya on 23-Aug-16.
 */
public class AttnServ extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            String a = new String(intent.getStringExtra("PORT"));


          //  Toast.makeText(this,Integer.parseInt(sb.toString()),Toast.LENGTH_SHORT).show();
            new Sender().execute(intent.getStringExtra("IP"),a,intent.getStringExtra("pack"));
           // Toast.makeText(this,"Lights Triggered!",Toast.LENGTH_SHORT).show();
        } catch(Exception e){

            }
        return START_NOT_STICKY;


    }
    private class Sender extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... params) {
           try{
               ConnectionManager cm = new ConnectionManager(params[0],Integer.parseInt(params[1]));
               cm.sendString(params[2]);
           }catch(Exception e){


            }

            return null;
        }
    }


}
