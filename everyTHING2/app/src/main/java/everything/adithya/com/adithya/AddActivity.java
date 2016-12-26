package everything.adithya.com.adithya;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
         @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText ip = (EditText) findViewById(R.id.editText2);
        final  EditText port = (EditText) findViewById(R.id.editText3);
        final  EditText name = (EditText) findViewById(R.id.editText);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
            if(!(name.getText().toString().isEmpty()||ip.getText().toString().isEmpty()||port.getText().toString().isEmpty()))
            {
                Dbm db = new Dbm(AddActivity.this);
               Intent in ;
                    if(db.insertData("!"+name.getText().toString(),"123@#$",ip.getText().toString(),port.getText().toString())>0){

                        in =  new Intent(AddActivity.this,Home.class);
                        startActivity(in);
                        Home.act.finish();
                        finish();
                    }
                     else
                        Snackbar.make(view, "Error adding", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();



            }else
                Snackbar.make(view, "Enter all fields", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

}
