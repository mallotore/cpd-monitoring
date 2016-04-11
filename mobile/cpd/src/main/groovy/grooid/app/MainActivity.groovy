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
import android.widget.Toast
import com.arasthel.swissknife.annotations.OnItemClick
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import grooid.app.gcm.Events
import grooid.app.gcm.RegistrationIntentService
import grooid.app.messages.ReceivedMessage
import grooid.app.messages.ReceivedMessagesListAdapter
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.arasthel.swissknife.annotations.OnClick
import groovy.transform.CompileStatic
import com.arasthel.swissknife.annotations.OnUIThread

@CompileStatic
class MainActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000
    private static final String TAG = "MainActivity"

    private BroadcastReceiver mRegistrationBroadcastReceiver
    private BroadcastReceiver mMessageReceiver
    @InjectView(R.id.registrationProgressBar) ProgressBar mRegistrationProgressBar
    @InjectView(R.id.informationTextView) TextView mInformationTextView
    @InjectView(R.id.receivedMessagesListView) ListView messagesListView
    ReceivedMessagesListAdapter receivedMessagesListAdapter
    ArrayList<ReceivedMessage> receivedMessages

    @Override
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SwissKnife.inject(this);
        SwissKnife.restoreState(this, savedInstanceState);
        SwissKnife.loadExtras(this)

        receivedMessages = new ArrayList<ReceivedMessage>()
        receivedMessagesListAdapter = new ReceivedMessagesListAdapter(this, receivedMessages)
        messagesListView.setAdapter(receivedMessagesListAdapter)
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mRegistrationProgressBar.setVisibility(ProgressBar.GONE)
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                boolean sentToken = sharedPreferences.getBoolean(Events.SENT_TOKEN_TO_SERVER, false)
                if (sentToken) {
                    mInformationTextView.setText(getString(R.string.gcm_send_message))
                } else {
                    mInformationTextView.setText(getString(R.string.token_error_message))
                }
            }
        };
        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ReceivedMessage message = new ReceivedMessage(message: intent.getStringExtra("message"),
                                                              title: intent.getStringExtra("title"),
                                                                date: intent.getStringExtra("date"))
                receivedMessages.add(message)
                receivedMessagesListAdapter.notifyDataSetChanged()
                Log.d("receiver", "Got message: " + message)
            }
        };

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class)
            startService(intent)
        }
    }

    @OnItemClick(R.id.receivedMessagesListView)
    public void onItemClick(int position) {
        Object o = messagesListView.getItemAtPosition(position)
        ReceivedMessage message = (ReceivedMessage) o
        Toast.makeText(this, "Seleccionado :" + " " + message, Toast.LENGTH_LONG).show()
    }

    @Override
    protected void onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Events.REGISTRATION_COMPLETE))
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(Events.MESSAGE_RECEIVED))
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
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