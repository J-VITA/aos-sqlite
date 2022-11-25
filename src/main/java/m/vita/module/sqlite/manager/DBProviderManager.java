package m.vita.module.sqlite.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import m.vita.module.sqlite.DBData;
import m.vita.module.sqlite.data.User;
import m.vita.module.sqlite.data.exception.NoSuchUserException;

public class DBProviderManager implements DBData {

    private static DBProviderManager sInstance;

    private Context context;

    public static final DBProviderManager getInstance(Context context) {
        if (sInstance == null) {
            return new DBProviderManager(context);
        }
        return sInstance;
    }

    public DBProviderManager(Context context) {
        this.context = context;
    }

    @SuppressLint("Range")
    public User findUserById(String userId) throws NoSuchUserException {
        Cursor cursor = context.getContentResolver().query(URI_USER, null, COLUMN_IDENTITY + "=?", new String[] { userId }, null);
        cursor.moveToFirst();

        User user = new User();

        if (cursor != null && cursor.getCount() > 0) {
            user.setId(cursor.getString(cursor.getColumnIndex(COLUMN_IDENTITY)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
            user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
            user.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
            user.setAge(cursor.getString(cursor.getColumnIndex(COLUMN_AGE)));
            user.setBirthday(cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDAY)));
            user.setMdn(cursor.getString(cursor.getColumnIndex(COLUMN_MDN))); // PhoneNumber
            user.setIdx(cursor.getString(cursor.getColumnIndex(COLUMN_IDXCODE)));
        } else {
            throw new NoSuchUserException();
        }

        return user;
    }
}