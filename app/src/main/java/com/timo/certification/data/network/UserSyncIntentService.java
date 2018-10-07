package com.timo.certification.data.network;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.timo.certification.utilities.InjectorUtils;

public class UserSyncIntentService extends IntentService {
    private static final String TAG = UserSyncIntentService.class.getSimpleName();

    public UserSyncIntentService() {
        super("UserSyncIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "Intent service started");
        UsersNetworkDataSource networkDataSource
                = InjectorUtils.provideNetworkDataSource(this.getApplicationContext());

        networkDataSource.fetchUser();
    }
}
