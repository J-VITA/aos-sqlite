package m.vita.module.sqlite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper implements DBData {

    private static DBHelper sInstance;

    private DBHelper(Context context, String dbname2, CursorFactory factory, int i) {
        super(context, dbname2, factory, i);
    }

    public static final DBHelper getInstance(Context context) {
        if (sInstance == null) {
            return new DBHelper(context, DBNAME, null, 1); // 마지막 인자인 숫자가 커지도록 수정하면 버전업이 되어 onUpgrade()가 호출됨. // db_version
        }
        return sInstance;
    }

    @Override
    public void onOpen(SQLiteDatabase argDB) {
        // TODO :
//		argDB.execSQL("DROP TABLE IF EXISTS 'user'");
//		argDB.execSQL("DROP TABLE IF EXISTS 'history'");
//		onCreate(argDB);
    }

    @Override
    public void onCreate(SQLiteDatabase argDB) { // 처음 DB를 생성할 때 호출됨.(CREATE TABLE)
        argDB.execSQL(SQL_USER_TABLE);

        for (String[] initValue : INIT_USER_DATA) {
            try {
                argDB.execSQL("INSERT INTO " + TBNAME_USER + "(" + COLUMN_IDENTITY + "," + COLUMN_PASSWORD + ","
                        + COLUMN_USERNAME + "," + COLUMN_GENDER + "," + COLUMN_AGE + "," + COLUMN_BIRTHDAY + ","
                        + COLUMN_MDN + "," + COLUMN_IDXCODE +")"
                        + " VALUES(" + "'" + initValue[0] + "','" + initValue[1]
                        + "','" + initValue[2] + "','" + initValue[3] + "','" + initValue[4] + "','" + initValue[5]
                        + "','" + initValue[6] + "','" + initValue[7] +"'); ");
            } catch (SQLException e) {
                Log.d("swc-sql", e.getMessage());
            } catch (Exception e) {
                Log.d("swc-sql", e.getMessage());
            }
        }
        Log.d("swc-sql", SQL_USER_TABLE);

        argDB.execSQL(SQL_HISTORY_TABLE);
        Log.d("swc-sql", SQL_HISTORY_TABLE);
        for (String[] initHistoryValue : INIT_HISTORY_DATA) {
            argDB.execSQL("INSERT INTO " + TBNAME_HISTORY + "(" + COLUMN_IDENTITY  + "," + COLUMN_ACTIVITY + "," + COLUMN_COM_TIME + "," + COLUMN_HST_IDXCODE +")"
                    + " VALUES(" + "'" + initHistoryValue[0] + "','" + initHistoryValue[1] + "','"+ initHistoryValue[2] + "','" + initHistoryValue[3]  + "'); ");
        }
        Log.d("swc-sql", "onCreate called, DB Created");


    }

    @Override
    public void onUpgrade(SQLiteDatabase argDB, int oldVersion, int newVersion) {
        argDB.execSQL("DROP TABLE IF EXISTS 'user'");
        argDB.execSQL("DROP TABLE IF EXISTS 'history'");

        onCreate(argDB);
        Log.d("swc-sql", "onCreate called, DB Upgraded");
    }

}