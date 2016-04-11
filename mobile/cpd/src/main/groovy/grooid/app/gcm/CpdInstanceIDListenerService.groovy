package grooid.app.gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService
import groovy.transform.CompileStatic;

@CompileStatic
public class CpdInstanceIDListenerService extends InstanceIDListenerService {

    private static final String TAG = "CpdInstanceIDListenerService";

    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
