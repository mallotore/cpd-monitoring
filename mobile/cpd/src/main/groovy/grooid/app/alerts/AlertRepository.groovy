package grooid.app.alerts

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import groovy.transform.CompileStatic

import java.text.SimpleDateFormat

@CompileStatic
class AlertRepository {

    private AlertSqlLiteHelper dbHelper;

    public AlertRepository(Context context) {
        dbHelper = new AlertSqlLiteHelper(context);
    }

    public void save(String title, String message, Date creationDate){
        SQLiteDatabase db = dbHelper.getWritableDatabase()
        if(db != null)
        {
            db.execSQL("INSERT INTO Alerts (title, message, creation_date) " +
                    "VALUES ('" + title +"', '" + message +"', '" + creationDate +"')")
            db.close()
        }
    }

    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase()
        if(db != null)
        {
            db.execSQL("Delete from Alerts")
            db.close()
        }
    }

    public ArrayList<Alert> findAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT * from Alerts order by creation_date desc"
        ArrayList<Alert> alerts = new ArrayList<Alert>()

        Cursor cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            alerts.add(createAlert(cursor))
            while (cursor.moveToNext()) {
                alerts.add(createAlert(cursor))
            }
        }

        cursor.close()
        db.close()
        return alerts
    }

    private Alert createAlert(Cursor cursor){
        return new Alert(title: cursor.getString(cursor.getColumnIndex("title")),
                message: cursor.getString(cursor.getColumnIndex("message")),
                date: formatDate(cursor.getString(cursor.getColumnIndex("creation_date"))))
    }

    private String formatDate(String dateToFormat){
        def date = Date.parse("E MMM dd H:m:s z yyyy", dateToFormat)
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())
        return sdf.format(date)
    }
}