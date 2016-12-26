package everything.adithya.com.adithya;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Adithya on 21-Jun-16.
 */
public class Dbm extends SQLiteOpenHelper{
    private static final String DBNAME= "Things";
    private static final String TABLE_NAME="Things";
    private static final String IP="IP";
    private static final String T_NAME="THING_NAME";
    private static final String TACTION="ACTION";
    private static final String PORT="PORT";
    private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+IP+" VARCHAR(30), "+PORT+" VARCHAR(10), "+T_NAME+" VARCHAR(30),"+TACTION+" VARCHAT(30))";
    private static final int DATABASE_VERSION = 1;


    public Dbm(Context context) {
        super(context, DBNAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    public long insertData(String name, String action, String ip, String port){
        //this.getWritableDatabase().execSQL("INSERT INTO " + TABLE_NAME + " VALUES (" + ip + "," + port + ")");
        ContentValues cv = new ContentValues();
        cv.put(IP,ip);
        cv.put(PORT,port);
        cv.put(TACTION,action);
        cv.put(T_NAME,name);
        return this.getWritableDatabase().insert(TABLE_NAME,"NULL",cv);


    }

    public ArrayList<String> getName(){
        Cursor cu= this.getReadableDatabase().rawQuery("SELECT * FROM "+ TABLE_NAME,null);
        ArrayList<String> list = new ArrayList<>();

        while(cu.moveToNext()){
            list.add(cu.getString(2));
        }
        return list;
    }
    public String getTuple(String name){

         Cursor cu = this.getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+T_NAME+" = \""+name+"\"",null );
         while(cu.moveToNext())
          return cu.getString(0)+'!'+cu.getString(1);
         return "";
    }
    public ArrayList<String> getActs(String name){
        Cursor cu = this.getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+T_NAME+" = \""+name+"\"",null );
        ArrayList<String> list = new ArrayList<>();

        while(cu.moveToNext()){
            list.add(cu.getString(3));
        }
        return list;
    }
    public boolean attnIsEnabled(){
        Cursor cu = this.getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+T_NAME+" = \"atl\"",null );
        if(cu!=null)
        while(cu.moveToNext()){

            return cu.getString(3).equals("HAM!");
        }
        return false;
    }
    public String getAttnData(){
        Cursor cu = this.getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+T_NAME+" = \"atl\"",null );
        if(cu!=null)
            while(cu.moveToNext()){

                if(cu.getString(3).equals("HAM!")){
                    return cu.getString(0)+"!"+cu.getString(1);
                };
            }
            return "";
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void clearTable(){
        this.getWritableDatabase().execSQL("DELETE FROM "+TABLE_NAME );
    }
}
