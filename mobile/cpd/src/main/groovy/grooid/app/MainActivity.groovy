package grooid.app

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import com.arasthel.swissknife.annotations.OnClick
import com.arasthel.swissknife.annotations.OnItemClick
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import grooid.app.gcm.Events
import grooid.app.gcm.RegistrationIntentService
import grooid.app.alerts.Alert
import grooid.app.alerts.AlertRepository
import grooid.app.alerts.AlertListAdapter
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import grooid.app.util.Toastable
import groovy.transform.CompileStatic
import com.arasthel.swissknife.annotations.OnUIThread

@CompileStatic
class MainActivity extends AppCompatActivity implements Toastable{

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000
    private static final String TAG = MainActivity.class.getSimpleName()

    @InjectView(R.id.registrationProgressBar)
    private ProgressBar gmcRegistrationProgressBar
    @InjectView(R.id.gcmRegistrationTextView)
    private TextView gcmRegistrationTextView
    @InjectView(R.id.alertsListView)
    private ListView alertsListView

    private BroadcastReceiver gcmRegistrationReceiver
    private BroadcastReceiver alertReceiver
    private AlertListAdapter alertsListAdapter
    private AlertRepository alertRepository

    @Override
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SwissKnife.inject(this);
        SwissKnife.restoreState(this, savedInstanceState);
        SwissKnife.loadExtras(this)

        alertRepository = new AlertRepository(this)
        ArrayList<Alert> alerts = alertRepository.findAll()
        alertsListAdapter = new AlertListAdapter(this, alerts)
        alertsListView.setAdapter(alertsListAdapter)
        gcmRegistrationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                gmcRegistrationProgressBar.setVisibility(ProgressBar.GONE)
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                boolean sentToken = sharedPreferences.getBoolean(Events.SENT_TOKEN_TO_SERVER, false)
                if (sentToken) {
                    gcmRegistrationTextView.setText(getString(R.string.gcm_send_message))
                } else {
                    gcmRegistrationTextView.setText(getString(R.string.token_error_message))
                }
            }
        };
        alertReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Alert alert = new Alert(message: intent.getStringExtra("message"),
                                      title: intent.getStringExtra("title"),
                                        date: intent.getStringExtra("date"))
                alertsListAdapter.addAlert(alert)
                showToastMessage(alert.toString())
            }
        };

        if (checkPlayServices()) {
            Intent intent = new Intent(this, RegistrationIntentService.class)
            startService(intent)
        }
    }

    @OnItemClick(R.id.alertsListView)
    public void onAlertClick(int position) {
        Object o = alertsListView.getItemAtPosition(position)
        Alert alert = (Alert) o
        showToastMessage(alert.toString())
    }

    @OnClick(R.id.deleteAlerts)
    public void deleteAlerts(){
        alertRepository.deleteAll()
        alertsListAdapter.clearAlerts()
    }

    @OnClick(R.id.refreshAlerts)
    public void refreshAlerts(){
        ArrayList<Alert> alerts = alertRepository.findAll()
        alertsListAdapter.updateAlerts(alerts)
    }

    @Override
    protected void onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(gcmRegistrationReceiver,
                new IntentFilter(Events.REGISTRATION_COMPLETE))
        LocalBroadcastManager.getInstance(this).registerReceiver(alertReceiver,
                new IntentFilter(Events.MESSAGE_RECEIVED))
        if(alertRepository == null){
            alertRepository = new AlertRepository(this)
        }
        ArrayList<Alert> alerts = alertRepository.findAll()
        alertsListAdapter.updateAlerts(alerts)
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(gcmRegistrationReceiver)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(alertReceiver)
        super.onPause()
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance()
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this)
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show()
            } else {
                Log.i(TAG, "This device is not supported.")
                finish()
            }
            return false
        }
        return true
    }

    @Override
    boolean onCreateOptionsMenu(Menu menu) {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    @Override
    boolean onOptionsItemSelected(MenuItem item) {
        int id = item.itemId

        if (id == R.id.action_about) {
            showAbout()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    @OnUIThread
    void showAbout() {
         new AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_launcher)
            .setTitle(R.string.app_name)
            .setView(layoutInflater.inflate(R.layout.about, null, false))
            .create()
            .show()
    }
}