package mountainq.kinggod.capstone.sogang.smartchairapp.datas;

import android.provider.BaseColumns;

/**
 * Created by jwahn37 on 2017. 3. 20..
 */

// DataBase Table
public final class DataBases {

    public static final class CreateDB implements BaseColumns {

        public static final String _DATE = "_date";
        public static final String _TIME = "_time";
        public static final String POS_WAIST = "waist";
        public static final String POS_NECK = "neck";
        public static final String _TABLENAME = "posture";
        public static final String _CREATE =
                "create table " + _TABLENAME + "("
                        + _ID + " integer primary key autoincrement, "
                        + _DATE + " text not null , "
                        + _TIME + " text not null , "
                        + POS_WAIST + " text not null , "
                        + POS_NECK + " text not null );";
    }
}