package com.timo.certification.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.timo.certification.AppExecutors;
import com.timo.certification.data.database.UserDao;
import com.timo.certification.data.database.UserEntry;
import com.timo.certification.data.network.UserApiResponse;
import com.timo.certification.data.network.UsersNetworkDataSource;

import java.util.List;

public class UsersRepository {
    private static final String TAG = UsersRepository.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static UsersRepository sInstance;
    private final UserDao mUserDao;
    private final UsersNetworkDataSource mUsersNetworkDataSource;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;

    public UsersRepository(UserDao userDao, UsersNetworkDataSource usersNetworkDataSource, AppExecutors executors) {
        mUserDao = userDao;
        mUsersNetworkDataSource = usersNetworkDataSource;
        mExecutors = executors;

        MutableLiveData<UserApiResponse> networkData =
                mUsersNetworkDataSource.getDownloadedUser();

        networkData.observeForever(newUserForNetWork -> {
            mExecutors.diskIO().execute(() -> {
                UserEntry entry = new UserEntry(newUserForNetWork.getName(),
                        newUserForNetWork.getUsername(),
                        newUserForNetWork.getEmail(),
                        newUserForNetWork.getPhone(),
                        newUserForNetWork.getWebsite());
                mUserDao.bulkInsert(entry);
                Log.d(TAG,"New user inserted");
            });
        });
    }

    public synchronized static UsersRepository getInstance(UserDao userDao, UsersNetworkDataSource usersNetworkDataSource, AppExecutors executors) {
        Log.d(TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new UsersRepository(userDao, usersNetworkDataSource,executors);
                Log.d(TAG,"Made new repository");
            }
        }
        return sInstance;
    }

    /*private synchronized void initializeData() {
        if (mInitialized) return;
        mInitialized = true;

        mExecutors.diskIO().execute(this::startFetchUserService);
    }*/

    private boolean isFetchNeeded() {
        int count = mUserDao.countUsers();
        Log.d(TAG,String.valueOf(count));
        return true;
    }

    private void startFetchUserService() {
        mUsersNetworkDataSource.startFetchUserService();
    }

    public LiveData<List<UserEntry>> getUsers() {
        //initializeData();
        return mUserDao.getUsers();
    }

    public LiveData<UserEntry> getUserById(int id) {
        return mUserDao.getUserById(id);
    }
}
