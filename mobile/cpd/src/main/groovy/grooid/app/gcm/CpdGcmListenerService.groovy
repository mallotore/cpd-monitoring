package grooid.app.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService
import grooid.app.MainActivity
import grooid.app.R
import grooid.app.alerts.AlertRepository
import groovy.transform.CompileStatic

import java.text.SimpleDateFormat;

@CompileStatic
public class CpdGcmListenerService extends GcmListenerService {

    private static final String TAG = CpdGcmListenerService.class.getSimpleName()
    private AlertRepository alertRepository

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
        saveAlert(data, date)
        notifyUI(data, date)
        sendNotification(message)
    }

    private Date getCurrentDatetime(){
        Calendar cal = Calendar.getInstance(TimeZone.getDefault())
        return cal.getTime()
    }

    private void saveAlert(Bundle data, Date date){
        if(alertRepository == null){
            alertRepository = new AlertRepository(this)
        }
        alertRepository.save("Alerta", data.getString("message"), date)
    }

    private void notifyUI(Bundle data, Date date){
        Intent messageReceivedIntent = new Intent(Events.MESSAGE_RECEIVED)
        messageReceivedIntent.putExtra("message", data.getString("message"))
        messageReceivedIntent.putExtra("title", "Alerta")
        messageReceivedIntent.putExtra("date",formatDate(date))
        LocalBroadcastManager.getInstance(this).sendBroadcast(messageReceivedIntent)
    }

    private String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())
        return sdf.format(date)
    }

    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
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
