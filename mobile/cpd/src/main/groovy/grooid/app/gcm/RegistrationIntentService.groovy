package grooid.app.gcm

import android.app.IntentService
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.v4.content.LocalBroadcastManager
import android.util.Log

import com.google.android.gms.gcm.GcmPubSub
import com.google.android.gms.gcm.GoogleCloudMessaging
import com.google.android.gms.iid.InstanceID
import groovy.transform.CompileStatic

@CompileStatic
public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegistrationIntentService"
    private static final String[] TOPICS = ["global"]

    public RegistrationIntentService() {
        super(TAG)
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        try {
            InstanceID instanceID = InstanceID.getInstance(this)
            String token = instanceID.getToken("584278654489",
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null)
            Log.i(TAG, "GCM Registration Token: " + token)
            sendRegistrationToServer(token)
            subscribeTopics(token)
            sharedPreferences.edit().putBoolean(Events.SENT_TOKEN_TO_SERVER, true).apply()
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e)
            sharedPreferences.edit().putBoolean(Events.SENT_TOKEN_TO_SERVER, false).apply()
        }
        Intent registrationComplete = new Intent(Events.REGISTRATION_COMPLETE)
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete)
    }

    private void sendRegistrationToServer(String token) {
        // todo: registrar el movil en el servidor
    }

    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this)
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null)
        }
    }
}