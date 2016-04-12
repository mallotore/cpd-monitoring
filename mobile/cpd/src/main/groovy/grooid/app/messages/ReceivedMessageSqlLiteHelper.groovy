package grooid.app.messages;

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import groovy.transform.CompileStatic

@CompileStatic
class ReceivedMessageSqlLiteHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "messages.db";

    String sqlCreate = "CREATE TABLE Messages (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, message TEXT, creation_date DATETIME)";

    public ReceivedMessageSqlLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION)
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate)
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int previousVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Messages")
        db.execSQL(sqlCreate)
    }
}