package everything.adithya.com.adithya;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class AttentionLights extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention_lights);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dbm db =  new Dbm(AttentionLights.this.getApplicationContext());
                EditText edt1 = (EditText) AttentionLights.this.findViewById(R.id.editText4);
                EditText edt2 = (EditText) AttentionLights.this.findViewById(R.id.editText5);
                if(edt1.getText().equals("")||edt1.getText().equals("")) {

                    Snackbar.make(view, "Enter all fields", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();


                } else{
                      db.insertData("atl","HAM!",edt1.getText().toString(),edt2.getText().toString());
                    Snackbar.make(view, "Attention Lights Enabled", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent inten = new Intent(AttentionLights.this, Home.class);
                    startActivity(inten);
                    finish();
                }

            }
        });
    }



}
