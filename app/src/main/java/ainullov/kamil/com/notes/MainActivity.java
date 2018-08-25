package ainullov.kamil.com.notes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import ainullov.kamil.com.notes.db.DB;
import ainullov.kamil.com.notes.notes.ChangeNote;
import ainullov.kamil.com.notes.notes.CreateNote;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    final int REQUEST_CODE_CREATE = 1;
    final int REQUEST_CODE_CHANGE = 2;
    Cursor cursor;

    private static final int CM_DELETE_ID = 1;
    ListView lvData;
    DB db;
    SimpleCursorAdapter scAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DB(this);
        db.open();

        final String[] from = new String[]{DB.COLUMN_DESC, DB.COLUMN_TXT};
        int[] to = new int[]{R.id.tvDesc, R.id.tvText};

        scAdapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to, 0);
        lvData = (ListView) findViewById(R.id.lvData);
        lvData.setAdapter(scAdapter);
        registerForContextMenu(lvData);

        // создаем лоадер для чтения данных
        getSupportLoaderManager().initLoader(0, null, this);

        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                cursor = (Cursor) parent.getAdapter().getItem(position);
                Intent intentChange = new Intent(MainActivity.this, ChangeNote.class);
                String strName = "";
                String strDesc = "";
                int id;
                int idColIndex = cursor.getColumnIndex("_id");
                int nameColIndex = cursor.getColumnIndex("txt");
                int descColIndex = cursor.getColumnIndex("description");
                strName = cursor.getString(nameColIndex);
                strDesc = cursor.getString(descColIndex);
                id = cursor.getInt(idColIndex);
                cursor.close();

                intentChange.putExtra("strNameChange", strName);
                intentChange.putExtra("strDescChange", strDesc);
                intentChange.putExtra("id", id);
                getSupportLoaderManager().getLoader(0).forceLoad();
                startActivityForResult(intentChange, REQUEST_CODE_CHANGE);
            }
        });
    }


    public void onButtonClick(View view) {
        Intent intentCreate = new Intent(MainActivity.this, CreateNote.class);
        startActivityForResult(intentCreate, REQUEST_CODE_CREATE);
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "delete");
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // получаем из пункта контекстного меню данные по пункту списка
            AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item
                    .getMenuInfo();
            // id записи
            db.delRec(acmi.id);
            getSupportLoaderManager().getLoader(0).forceLoad();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CREATE:
                    String strName = data.getStringExtra("strName");
                    String strDesc = data.getStringExtra("strDesc");
                    if (strName != null && !strName.equals("")) {
                        db.addRec(strName, strDesc);
                        // получаем новый курсор с данными
                        getSupportLoaderManager().getLoader(0).forceLoad();
                    } else Toast.makeText(this, "Empty name field", Toast.LENGTH_SHORT).show();
                    break;
                case REQUEST_CODE_CHANGE:
                    String strNameChange = data.getStringExtra("strNameUpdate");
                    String strDescChange = data.getStringExtra("strDescUpdate");
                    String id = data.getStringExtra("id");

                    if (strNameChange != null && !strNameChange.equals("")) {
                        ContentValues cv = new ContentValues();
                        cv.put("txt", strNameChange);
                        cv.put("description", strDescChange);
                        db.update(cv, new String[]{id});

                        getSupportLoaderManager().getLoader(0).forceLoad();
                    } else Toast.makeText(this, "Empty name field", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(this, db);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

