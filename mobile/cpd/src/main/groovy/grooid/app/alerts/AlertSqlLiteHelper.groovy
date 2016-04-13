package grooid.app.alerts;

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import groovy.transform.CompileStatic

@CompileStatic
class AlertSqlLiteHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "alerts.db";

    String sqlCreate = "CREATE TABLE Alerts (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, message TEXT, creation_date DATETIME)";

    public AlertSqlLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION)
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate)
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int previousVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Alerts")
        db.execSQL(sqlCreate)
    }
}