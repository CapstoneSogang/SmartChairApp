package mountainq.kinggod.capstone.sogang.smartchairapp.datas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jwahn37 on 2017. 3. 18..
 */

public class DbOpenHelper {

    private static final String DATABASE_NAME = "posture.db";
    private static final int DATABASE_VERSION = 3;  //db table 수정시 버전을 바꿔줘야함
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        // 생성자
        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // 최초 DB를 만들때 한번만 호출된다.
        @Override
        public void onCreate(SQLiteDatabase db) {
            //db.execSQL("DROP TABLE IF EXISTS "+DataBases.CreateDB._TABLENAME);
            db.execSQL(DataBases.CreateDB._CREATE);
        }

        // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+DataBases.CreateDB._TABLENAME);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context){
        this.mCtx = context;
    }

    public void insertColumn(String _date, String _time, String waist, String neck)
    {

        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB._DATE, _date);
        values.put(DataBases.CreateDB._TIME, _time);
        values.put(DataBases.CreateDB.POS_WAIST, waist);
        values.put(DataBases.CreateDB.POS_NECK, neck);

        mDB.insert(DataBases.CreateDB._TABLENAME, null, values);
    }


    public DbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void deleteAll()
    {
        // Define 'where' part of query.
  //      String selection = DataBases.CreateDB._DATE + " LIKE ?";
// Specify arguments in placeholder order.
       // String[] selectionArgs = { "0319" };
// Issue SQL statement.
        mDB.delete(DataBases.CreateDB._TABLENAME, null, null);

    }

    public Cursor readDbHelper(/*String _date*/){
        mDB = mDBHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                DataBases.CreateDB._DATE,
                DataBases.CreateDB._TIME,
                DataBases.CreateDB.POS_WAIST,
                DataBases.CreateDB.POS_NECK

        };

// Filter results WHERE "title" = 'My Title'
        String selection = DataBases.CreateDB._DATE + " = ?";
        String[] selectionArgs = { "0319" };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                //    DataBases.CreateDB._TIME + " DESC";
                DataBases.CreateDB._DATE + " ASC";



        Cursor c = mDB.query(
                DataBases.CreateDB._TABLENAME,                   // The table to query
                projection,                               // The columns to return
               // selection,                                // The columns for the WHERE clause
                null,
               // selectionArgs,                            // The values for the WHERE clause
                null,
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );



        return c;
    }


    public void close() {
        mDB.close();
    }
}
