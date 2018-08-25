package ainullov.kamil.com.notes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB {

    private static final String DB_NAME = "mydb";
    private static final String DB_TABLE = "mytab";
    private static final int DB_VERSION = 1;
    public static final String COLUMN_TXT = "txt";
    public static final String COLUMN_DESC = "description";
    public static final String COLUMN_ID = "_id";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_DESC + " text, " +
                    COLUMN_TXT + " text " +
                    ");";

    private final Context mCtx;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }

    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    public void addRec(String txt, String desc) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TXT, txt);
        cv.put(COLUMN_DESC, desc);
        mDB.insert(DB_TABLE, null, cv);
    }

    public void delRec(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

    public void update(ContentValues contentValues, String[] id) {
        mDB.update(DB_TABLE, contentValues, "_id = ?", id);
    }

    // Класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {


        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
