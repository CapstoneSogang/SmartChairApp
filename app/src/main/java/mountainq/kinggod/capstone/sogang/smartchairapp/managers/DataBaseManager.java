package mountainq.kinggod.capstone.sogang.smartchairapp.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

import mountainq.kinggod.capstone.sogang.smartchairapp.datas.PostureData;

/**
 * Created by dnay2 on 2017-03-28.
 */

public class DataBaseManager extends SQLiteOpenHelper {

    private static final String TAG = "Database Manager";
    private static final String DB_NAME = "posture.db";
    private static final int DB_VERSION = 1;

    private static class PostureColumns implements BaseColumns {
        private static final String _TABLE_NAME = " posture ";
        private static final String _DATE = " _date ";
        private static final String _TIME = " _time ";
        private static final String POS_WAIST = " waist ";
        private static final String POS_NECK = " neck ";
        private static final String POS_LEFT = "posLeft";
        private static final String POS_RIGHT = "posRight";
    }

    private static final String TEXT_TYPE = " text ";
    private static final String INTEGER_TYPE = " integer ";
    private static final String NOT_NULL = " not null ";
    private static final String PRIMARY_KEY = " primary key autoincrement ";
    private static final String COMMA_SEP = ", ";
    private static final String _CREATE_TABLE = "create table " + PostureColumns._TABLE_NAME + "("
            + PostureColumns._ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP
            + PostureColumns._DATE + TEXT_TYPE + NOT_NULL + COMMA_SEP
            + PostureColumns._TIME + TEXT_TYPE + NOT_NULL + COMMA_SEP
            + PostureColumns.POS_WAIST + TEXT_TYPE + NOT_NULL + COMMA_SEP
            + PostureColumns.POS_NECK + TEXT_TYPE + NOT_NULL + COMMA_SEP
            + PostureColumns.POS_LEFT + TEXT_TYPE + NOT_NULL + COMMA_SEP
            + PostureColumns.POS_RIGHT + TEXT_TYPE + NOT_NULL + " );";
    private static final String _DELETE_TABLE =
            "DROP TABLE IF EXISTS " + DB_NAME;


    /**
     * 생성자
     * @param context 컨텍스트만 담으면 나머지 정보는 알아서 한다.
     */
    public DataBaseManager(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(_DELETE_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * 여기부터 삽입, 삭제, 검색 메소드
     */

    /**
     * 데이터를 SQL에 삽입
     * @param item 삽입할 데이터
     * @return
     */
    public boolean insertItem(PostureData item){
        Log.d(TAG, "insert posture " + item.get_date() + item.get_time());
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PostureColumns._DATE, item.get_date());
        values.put(PostureColumns._TIME, item.get_time());
        values.put(PostureColumns.POS_WAIST, item.getWaist());
        values.put(PostureColumns.POS_NECK, item.getNeck());
        values.put(PostureColumns.POS_LEFT, item.getPosLeft());
        values.put(PostureColumns.POS_RIGHT, item.getPosRight());

        long newRowId = db.insert(PostureColumns._TABLE_NAME, null, values);
        if(newRowId < 0) return false;
        Log.d(TAG, "inserted item ===> newRowId : " + newRowId);
        db.close();
        return true;
    }
    //리스트를 받아와서 삽입
    private boolean insertItem(ArrayList<PostureData> items){
        Log.d(TAG, "insert postures size : " + items.size() );
        boolean flag;
        for(int i = 0; i < items.size() ; i++){
            flag = insertItem(items.get(i));
            if(!flag) return false;
        }
        return true;
    }

    public boolean delete(PostureData item){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + PostureColumns._DATE + " WHERE " +
                PostureColumns._DATE + " = " + item.get_date() +" , " +
                PostureColumns._TIME + " = " + item.get_time() + ";";
        db.execSQL(query);
        return true;
    }

    public boolean deleteAll(){
        return true;
    }

    public PostureData select(PostureData item){
        Log.d(TAG, "find item " + item.get_date() + item.get_time());
        SQLiteDatabase db = SQLiteDatabase.create(null);
        PostureData foundItem = null;
        try {
            db = getReadableDatabase();
        }catch (NullPointerException e){
            Log.d(TAG, "find error : there are no redable Dagabase ErrMsg : " + e.getMessage());
            onCreate(db);
            return null;
        }

        String[] tableColumns = {
                PostureColumns._DATE,
                PostureColumns._TIME,
                PostureColumns.POS_WAIST,
                PostureColumns.POS_NECK,
                PostureColumns.POS_LEFT,
                PostureColumns.POS_RIGHT,
        };

        String whereClause = PostureColumns._DATE + "= ? AND " + PostureColumns._TIME + "= ?";
        String[] whereArgs = {item.get_date(), item.get_time()};

        try{
            Cursor c = db.query(PostureColumns._TABLE_NAME, tableColumns, whereClause, whereArgs, null, null, null);
            c.moveToFirst();
            foundItem = new PostureData(c.getString(0), c.getString(1), c.getString(2), c.getString(3),c.getString(4),c.getString(5));
            c.close();
        } catch (Exception e){
            Log.d(TAG, "Can not get exist item Errmsg : " + e.getMessage());
        }

        db.close();

        return foundItem;
    }

    public ArrayList<PostureData> selectAll(){
        Log.d(TAG, "find item list All");
        SQLiteDatabase db = SQLiteDatabase.create(null);
        ArrayList<PostureData> foundList = new ArrayList<>();
        try {
            db = getReadableDatabase();
        }catch (NullPointerException e){
            Log.d(TAG, "find error : there are no redable Dagabase ErrMsg : " + e.getMessage());
            onCreate(db);
            return null;
        }

        String[] tableColumns = {
                PostureColumns._DATE,
                PostureColumns._TIME,
                PostureColumns.POS_WAIST,
                PostureColumns.POS_NECK,
                PostureColumns.POS_LEFT,
                PostureColumns.POS_RIGHT
        };

        try{
            Cursor c = db.query(PostureColumns._TABLE_NAME, tableColumns, null, null, null, null, null);
            c.moveToFirst();
            foundList.add(new PostureData(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5)));
            while(c.moveToNext())
                foundList.add(new PostureData(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5)));
            c.close();
        } catch (Exception e){
            Log.d(TAG, "Can not get exist item Errmsg : " + e.getMessage());
        }

        db.close();

        return foundList;
    }

    public ArrayList<PostureData> selectTimeToTime(String from, String to){
        Log.d(TAG, "find item list from " + from + " to " + to);
        SQLiteDatabase db = SQLiteDatabase.create(null);
        ArrayList<PostureData> foundList = new ArrayList<>();
        try {
            db = getReadableDatabase();
        }catch (NullPointerException e){
            Log.d(TAG, "find error : there are no redable Dagabase ErrMsg : " + e.getMessage());
            onCreate(db);
            return null;
        }

        String[] tableColumns = {
                PostureColumns._DATE,
                PostureColumns._TIME,
                PostureColumns.POS_WAIST,
                PostureColumns.POS_NECK,
                PostureColumns.POS_LEFT,
                PostureColumns.POS_RIGHT
        };

        String whereClause = PostureColumns._DATE + ">= ? AND " + PostureColumns._DATE + "<= ?";
        String[] whereArgs = {from, to};

        try{
            Cursor c = db.query(PostureColumns._TABLE_NAME, tableColumns, whereClause, whereArgs, null, null, null);
            c.moveToFirst();
            foundList.add(new PostureData(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5)));
            while(c.moveToNext())
                foundList.add(new PostureData(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5)));
            c.close();
        } catch (Exception e){
            Log.d(TAG, "Can not get exist item Errmsg : " + e.getMessage());
        }

        db.close();

        return foundList;
    }

}
