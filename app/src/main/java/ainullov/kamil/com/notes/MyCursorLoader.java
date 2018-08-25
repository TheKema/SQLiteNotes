package ainullov.kamil.com.notes;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import ainullov.kamil.com.notes.db.DB;

class MyCursorLoader extends CursorLoader {

    DB db;

    public MyCursorLoader(Context context, DB db) {
        super(context);
        this.db = db;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = db.getAllData();
        return cursor;
    }
}
