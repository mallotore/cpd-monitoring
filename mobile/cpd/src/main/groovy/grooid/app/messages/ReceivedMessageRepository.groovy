package grooid.app.messages

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import groovy.transform.CompileStatic

@CompileStatic
class ReceivedMessageRepository {

    private ReceivedMessageSqlLiteHelper dbHelper;

    public ReceivedMessageRepository(Context context) {
        dbHelper = new ReceivedMessageSqlLiteHelper(context);
    }

    public void save(String title, String message, Date creationDate){
        SQLiteDatabase db = dbHelper.getWritableDatabase()
        if(db != null)
        {
            db.execSQL("INSERT INTO Messages (title, message, creation_date) " +
                    "VALUES ('" + title +"', '" + message +"', '" + creationDate +"')")
            db.close()
        }
    }

    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase()
        if(db != null)
        {
            db.execSQL("Delete from Messages")
            db.close()
        }
    }

    public ArrayList<ReceivedMessage> findAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT * from Messages order by creation_date desc"
        ArrayList<ReceivedMessage> messages = new ArrayList<ReceivedMessage>()

        Cursor cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                ReceivedMessage message = new ReceivedMessage(title: cursor.getString(cursor.getColumnIndex("title")),
                message: cursor.getString(cursor.getColumnIndex("message")),
                date: cursor.getString(cursor.getColumnIndex("creation_date")))
                messages.add(message)
            }
        }

        cursor.close()
        db.close()
        return messages
    }
}