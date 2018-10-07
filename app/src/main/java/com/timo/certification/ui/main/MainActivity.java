package com.timo.certification.ui.main;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.timo.certification.R;
import com.timo.certification.data.database.UserEntry;
import com.timo.certification.data.network.SyncJobService;
import com.timo.certification.databinding.ActivityMainBinding;
import com.timo.certification.utilities.InjectorUtils;
import com.timo.certification.utilities.NotificationUtils;
import com.timo.certification.utilities.SharedPreferencesUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String USER_ID_EXTRA = "USER_ID_EXTRA";

    private MainActivityViewModel mViewModel;
    private ActivityMainBinding mActivityMainBinding;
    private long interval = TimeUnit.MINUTES.toMillis(20);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        int id = getIntent().getIntExtra(USER_ID_EXTRA, -1);

        /*NotificationUtils.getInstance(this)
                .sendNotification("q pdo","bandita");*/



        MainViewModelFactory factory = InjectorUtils.provideMainViewModelFactory(this.getApplicationContext(),id);
        mViewModel = ViewModelProviders.of(this,factory).get(MainActivityViewModel.class);

        mViewModel.getUser().observe(this,userEntry -> {
            if (userEntry != null) {
                Log.d(TAG,"YAY");
                bindUserToUI(userEntry);
            } else {
                Log.e(TAG,"No hay nada");
            }
        });
    }

    private void bindUserToUI(UserEntry entry) {
        //mActivityMainBinding.countText.setText(String.valueOf(69));
        mActivityMainBinding.nameText.setText(entry.getName());
        mActivityMainBinding.emailText.setText(entry.getEmail());
        mActivityMainBinding.usernameText.setText(entry.getUsername());
        mActivityMainBinding.websiteText.setText(entry.getWebsite());
        mActivityMainBinding.numberText.setText(entry.getPhone());
        //mActivityMainBinding.idText.setText(String.valueOf(entry.getId()));
    }
}
