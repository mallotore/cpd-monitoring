package grooid.app.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService
import grooid.app.MainActivity
import grooid.app.R
import grooid.app.messages.ReceivedMessage
import grooid.app.messages.ReceivedMessageRepository
import grooid.app.messages.ReceivedMessageSqlLiteHelper
import groovy.transform.CompileStatic

import java.text.SimpleDateFormat;

@CompileStatic
public class CpdGcmListenerService extends GcmListenerService {

    private static final String TAG = "CpdGcmListenerService"
    private ReceivedMessageRepository receivedMessageRepository

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message")
        Log.d(TAG, "From: " + from)
        Log.d(TAG, "Message: " + message)

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }
        Date date = getCurrentDatetime()
        saveMessage(data, date)
        updateUI(data, formatDate(date))
        sendNotification(message)
    }

    private void saveMessage(Bundle data, Date date){
        if(receivedMessageRepository == null){
            receivedMessageRepository = new ReceivedMessageRepository(this)
        }
        receivedMessageRepository.save("Alerta", data.getString("message"), date)
    }

    private Date getCurrentDatetime(){
        Calendar cal = Calendar.getInstance(TimeZone.getDefault())
        return cal.getTime()
    }

    private String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())
        return sdf.format(date)
    }

    private void updateUI(Bundle data, String date){
        Intent messageReceivedIntent = new Intent(Events.MESSAGE_RECEIVED)
        messageReceivedIntent.putExtra("message", data.getString("message"))
        messageReceivedIntent.putExtra("title", "Alerta")
        messageReceivedIntent.putExtra("date",date)
        LocalBroadcastManager.getInstance(this).sendBroadcast(messageReceivedIntent)
    }

    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle("CPD")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
