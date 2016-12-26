package everything.adithya.com.adithya;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    public static AppCompatActivity act;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = this;
        Intent inten = new Intent(this,LPGService.class);
        startService(inten);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> ar =  new ArrayAdapter(this,R.layout.layout);
        ArrayList<String> a = new Dbm(this).getName();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Dbm db = new Dbm(this);

          if(db.attnIsEnabled())
             Snackbar.make(fab, "Atention Lights Enabled", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        else
                 ar.add("Attention Lights");
        for(String n:a){
           if(n.charAt(0)=='!') ar.add(n.replace('!',' '));
        }


        lv.setAdapter(ar);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                     if(((TextView) view).getText().equals("Attention Lights")){
                          Intent inten = new Intent(Home.this,AttentionLights.class);
                          startActivity(inten);
                          finish();
                     }else {

                         TextView tv = (TextView) view;
                         Intent intent = new Intent(Home.this, ActionActivity.class);
                         intent.putExtra("NAME", ((TextView) view).getText());
                         startActivity(intent);
                        }
                     }
        });

                fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
                Intent nxt = new Intent(Home.this,AddActivity.class);
                startActivity(nxt);



            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Dbm db = new Dbm(this);
            db.clearTable();
            ListView lv = (ListView) findViewById(R.id.listView);
            ArrayAdapter<String> ar =  new ArrayAdapter(this,R.layout.layout);
            ar.addAll(new Dbm(this).getName());
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            lv.setAdapter(ar);
             Snackbar.make(fab , "Things deleted", Snackbar.LENGTH_SHORT)
                   .setAction("Action", null).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
