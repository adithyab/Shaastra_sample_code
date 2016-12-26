package everything.adithya.com.adithya;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Adithya on 23-Aug-16.
 */
public class Recv extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Dbm db = new Dbm(context);
        if(db.attnIsEnabled()) {
            String s = db.getAttnData();
            if (!s.equals("")) {

                Intent inten = new Intent(context, AttnServ.class);
                String a[] = s.split("!");
                inten.putExtra("IP",a[0]);
                inten.putExtra("PORT",a[1]);
                inten.putExtra("pack",intent.getStringExtra("package"));
                context.startService(inten);

            }
        }


    }
}
