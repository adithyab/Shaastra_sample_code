package everything.adithya.com.adithya;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActionActivity extends AppCompatActivity {
    String ip="";
    int port=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final String thing=this.getIntent().getStringExtra("NAME");
        this.setTitle( thing );
        Dbm db = new Dbm(this);

        final ListView lv = (ListView) findViewById(R.id.listView2);
       final   ArrayAdapter<String> ar =  new ArrayAdapter(this,R.layout.lv_act);
        ArrayList<String > a = new Dbm(this).getActs(thing.trim());

        for(String n:a){
         ar.add(n);
        }


        lv.setAdapter(ar);



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   new Sender().execute(((TextView) view).getText().toString());
                Toast.makeText(ActionActivity.this,"CONNECTING...",Toast.LENGTH_SHORT).show();
            }
        });






        final   String[] data =  db.getTuple("!"+thing.trim()).split("!");
        ip = data[0];
        port = Integer.parseInt(data[1]);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View v=view;
                AlertDialog.Builder builder = new AlertDialog.Builder(ActionActivity.this);
                builder.setTitle("ADD ACTION");


                final EditText input = new EditText(ActionActivity.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String act = input.getText().toString();
                        if (!act.equals("")) {
                            Dbm dbm = new Dbm(ActionActivity.this);
                            dbm.insertData(thing.trim(), act, data[0], data[1]);

                            ArrayList<String > ab = new Dbm(ActionActivity.this).getActs(thing.trim());
                            ar.add(act);
                         lv.setAdapter(ar);
                            Snackbar.make(v, act+" Added!", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                        }else
                            Snackbar.make(v, "Empty field", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();



            }
        });
    }

      private class Sender extends AsyncTask<String,Void,Boolean>{


          @Override
          protected Boolean doInBackground(String... params) {
              try{
                  ConnectionManager cm = new ConnectionManager(ip,port);
                  cm.sendString(params[0]);

                  return true;
              }catch(Exception e){

                  return false;
              }



          }
          @Override
          protected void onPostExecute(Boolean boo){
              FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
              if(boo)
                 Snackbar.make(fab, "data sent", Snackbar.LENGTH_SHORT)
                      .setAction("Action", null).show();
              else
                  Snackbar.make(fab, "connection failed", Snackbar.LENGTH_SHORT)
                          .setAction("Action", null).show();

          }
      }

}
