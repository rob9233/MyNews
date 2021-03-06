package robfernandes.xyz.mynews.ui.activities;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import java.util.Calendar;

import robfernandes.xyz.mynews.receiver.AlarmManagerReceiver;
import robfernandes.xyz.mynews.storage.local.DataManager;
import robfernandes.xyz.mynews.notification.NotifManager;
import robfernandes.xyz.mynews.R;

import static robfernandes.xyz.mynews.utils.Constants.*;

public class NotificationsActivity extends AppCompatActivity
        implements CompoundButton.OnCheckedChangeListener {

    private DataManager mDataManager;
    private boolean isNotificationsActive;
    private Switch notificationsSwitch;
    private static final String TAG = "NotificationsActivity";
    private CheckBox sportsCheckbox;
    private CheckBox artsCheckbox;
    private CheckBox travelCheckbox;
    private CheckBox politicsCheckbox;
    private CheckBox otherCheckbox;
    private CheckBox businessCheckbox;
    private EditText queryTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        initiateVariables();
        setClickListeners();
        changeAlarmManagerStatus();
    }

    private void setClickListeners() {
        queryTerm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                NotifManager notificationMethods = new
                        NotifManager(NotificationsActivity.this);
                notificationMethods.saveQueryTermInMemory(s.toString());

            }
        });
        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isNotificationsActive = isChecked;
            mDataManager.saveBooleanInMemory(FilterKeys.NOTIFICATIONS_STATUS_KEY, isNotificationsActive);
            changeAlarmManagerStatus();

        });

        sportsCheckbox.setOnCheckedChangeListener(this);
        artsCheckbox.setOnCheckedChangeListener(this);
        travelCheckbox.setOnCheckedChangeListener(this);
        politicsCheckbox.setOnCheckedChangeListener(this);
        otherCheckbox.setOnCheckedChangeListener(this);
        businessCheckbox.setOnCheckedChangeListener(this);
    }

    private void initiateVariables() {
        NotifManager notificationMethods = new NotifManager(
                NotificationsActivity.this);
        queryTerm = findViewById(R.id.activity_notifications_query_input_edit_text);
        queryTerm.setText(notificationMethods.loadQueryTermFromMemory());
        mDataManager = new DataManager(NotificationsActivity.this);
        isNotificationsActive = mDataManager.readBooleanFromMemory(FilterKeys.NOTIFICATIONS_STATUS_KEY);
        notificationsSwitch = findViewById(R.id.activity_notifications_switch);
        notificationsSwitch.setChecked(isNotificationsActive);
        setCheckBoxesStatus();
    }

    private void setCheckBoxesStatus() {
        sportsCheckbox = findViewById(R.id.categories_checkboxes_sports);
        artsCheckbox = findViewById(R.id.categories_checkboxes_arts);
        travelCheckbox = findViewById(R.id.categories_checkboxes_travel);
        politicsCheckbox = findViewById(R.id.categories_checkboxes_politics);
        otherCheckbox = findViewById(R.id.categories_checkboxes_other_topics);
        businessCheckbox = findViewById(R.id.categories_checkboxes_business);

        sportsCheckbox.setChecked(mDataManager.readBooleanFromMemory(FilterKeys.SPORTS_STATUS_KEY));
        artsCheckbox.setChecked(mDataManager.readBooleanFromMemory(FilterKeys.ARTS_STATUS_KEY));
        travelCheckbox.setChecked(mDataManager.readBooleanFromMemory(FilterKeys.TRAVEL_STATUS_KEY));
        politicsCheckbox.setChecked(mDataManager.readBooleanFromMemory(FilterKeys.POLITICS_STATUS_KEY));
        otherCheckbox.setChecked(mDataManager.readBooleanFromMemory(FilterKeys.OTHER_CATEGORIES_STATUS_KEY));
        businessCheckbox.setChecked(mDataManager.readBooleanFromMemory(FilterKeys.BUSINESS_STATUS_KEY));
    }

    private void changeAlarmManagerStatus() {
        if (isNotificationsActive) {
            startAlarmManager();
        } else {
            cancelAlarmManager();
        }
    }


    @SuppressLint("ShortAlarm")
    private void startAlarmManager() {
        Log.d(TAG, "startAlarmManager: ");
        Calendar notificationTime = Calendar.getInstance(); //gets right now
        notificationTime.set(Calendar.HOUR_OF_DAY, NotificationsConstants.HOUR_NOTIFICATION);
        notificationTime.set(Calendar.MINUTE, NotificationsConstants.MINUTES_NOTIFICATION);
        notificationTime.add(Calendar.DATE, 1);    //tomorrow

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmManagerReceiver.class);
        PendingIntent pendingIntent = PendingIntent
                .getBroadcast(getApplicationContext(), 0, intent, 0);

/*        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 1000,
                5000, pendingIntent); //just for test*/

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP
                , notificationTime.getTimeInMillis()
                , AlarmManager.INTERVAL_DAY
                , pendingIntent);
    }

    private void cancelAlarmManager() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmManagerReceiver.class);
        PendingIntent pendingIntent = PendingIntent
                .getBroadcast(getApplicationContext(), 0, intent, 0);

        alarmManager.cancel(pendingIntent);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.categories_checkboxes_sports:
                mDataManager
                        .saveBooleanInMemory(FilterKeys.SPORTS_STATUS_KEY, isChecked);
                break;
            case R.id.categories_checkboxes_arts:
                mDataManager
                        .saveBooleanInMemory(FilterKeys.ARTS_STATUS_KEY, isChecked);
                break;
            case R.id.categories_checkboxes_travel:
                mDataManager
                        .saveBooleanInMemory(FilterKeys.TRAVEL_STATUS_KEY, isChecked);
                break;
            case R.id.categories_checkboxes_politics:
                mDataManager
                        .saveBooleanInMemory(FilterKeys.POLITICS_STATUS_KEY, isChecked);
                break;
            case R.id.categories_checkboxes_other_topics:
                mDataManager
                        .saveBooleanInMemory(FilterKeys.OTHER_CATEGORIES_STATUS_KEY, isChecked);
                break;
            case R.id.categories_checkboxes_business:
                mDataManager
                        .saveBooleanInMemory(FilterKeys.BUSINESS_STATUS_KEY, isChecked);
                break;
        }
    }
}
