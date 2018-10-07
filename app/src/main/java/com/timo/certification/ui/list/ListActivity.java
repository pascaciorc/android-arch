package com.timo.certification.ui.list;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.timo.certification.R;
import com.timo.certification.data.network.SyncJobService;
import com.timo.certification.ui.main.MainActivity;
import com.timo.certification.utilities.InjectorUtils;

import java.util.concurrent.TimeUnit;


public class ListActivity extends AppCompatActivity
        implements UserListAdapter.UserListAdapterOnItemClickHandler {
    private static final String TAG = ListActivity.class.getSimpleName();
    private long interval = TimeUnit.MINUTES.toMillis(20);

    private RecyclerView mRecyclerView;
    private ListActivityViewModel mViewModel;
    private UserListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mRecyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayout =
                new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(linearLayout);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new UserListAdapter(this,this);

        mRecyclerView.setAdapter(mAdapter);

        ListViewModelFactory factory = InjectorUtils.provideListViewModelFactory(this.getApplicationContext());
        mViewModel = ViewModelProviders.of(this,factory).get(ListActivityViewModel.class);

        mViewModel.getUsers().observe(this, userEntries -> {
            if (userEntries != null && userEntries.size() == 0) {
                scheduleRecurringFetchWeatherSync();
            }
            mAdapter.swapForecast(userEntries);
        });

    }

    @Override
    public void onItemClick(int id) {
        Intent i = new Intent(ListActivity.this, MainActivity.class);
        i.putExtra(MainActivity.USER_ID_EXTRA,id);
        startActivity(i);
    }

    public void scheduleRecurringFetchWeatherSync() {
        ComponentName componentName = new ComponentName(this,SyncJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(123,componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(interval)
                .build();
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = jobScheduler.schedule(jobInfo);
        if(resultCode == JobScheduler.RESULT_SUCCESS)
            Toast.makeText(this,"Alarm started🔔",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Alarm failed💀",Toast.LENGTH_SHORT).show();
    }
}
