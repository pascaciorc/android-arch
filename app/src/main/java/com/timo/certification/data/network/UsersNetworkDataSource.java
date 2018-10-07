package com.timo.certification.data.network;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.MutableLiveData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.timo.certification.AppExecutors;
import com.timo.certification.utilities.NotificationUtils;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class UsersNetworkDataSource {
    private static final String TAG = UsersNetworkDataSource.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static UsersNetworkDataSource sInstance;
    private final Context mContext;

    private final AppExecutors mExecutors;

    private final MutableLiveData<UserApiResponse> mDownloadedUser;


    public UsersNetworkDataSource(Context context, AppExecutors executors) {
        mContext = context;
        mExecutors = executors;
        mDownloadedUser = new MutableLiveData<>();
    }

    public static UsersNetworkDataSource getInstance(Context context, AppExecutors executors) {
        Log.d(TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new UsersNetworkDataSource(context.getApplicationContext(), executors);
                Log.d(TAG, "Made new network data source");
            }
        }
        return sInstance;
    }

    public MutableLiveData<UserApiResponse> getDownloadedUser() {
        return mDownloadedUser;
    }

    public void startFetchUserService() {
        Intent intentToFetch = new Intent(mContext, UserSyncIntentService.class);
        mContext.startService(intentToFetch);
        Log.d(TAG, "Service created");
    }

    void fetchUser() {
        Log.d(TAG, "Fetch user started");
        mExecutors.networkIO().execute(() -> {

            int min = 1;
            int max = 10;

            Random r = new Random();
            int i1 = r.nextInt(max - min + 1) + min;

            NetworkUtils.getAPIService().getUser(String.valueOf(i1)).enqueue(new Callback<UserApiResponse>() {
                @Override
                public void onResponse(Call<UserApiResponse> call, Response<UserApiResponse> response) {
                    mDownloadedUser.postValue(response.body());
                    if (response.body() != null) {
                        NotificationUtils.getInstance(mContext)
                                .sendNotification(response.body().getName(),response.body().getUsername());
                    }
                }

                @Override
                public void onFailure(Call<UserApiResponse> call, Throwable t) {
                    Toast.makeText(mContext.getApplicationContext(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            });

        });
    }
}
