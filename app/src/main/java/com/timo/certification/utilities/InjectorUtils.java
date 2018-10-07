package com.timo.certification.utilities;

import android.content.Context;

import com.timo.certification.AppExecutors;
import com.timo.certification.data.UsersRepository;
import com.timo.certification.data.database.UsersDatabase;
import com.timo.certification.data.network.UsersNetworkDataSource;
import com.timo.certification.ui.list.ListViewModelFactory;
import com.timo.certification.ui.main.MainViewModelFactory;

public class InjectorUtils {

    public static UsersRepository provideRepository(Context context) {
        UsersDatabase database = UsersDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        UsersNetworkDataSource networkDataSource =
                UsersNetworkDataSource.getInstance(context.getApplicationContext(),executors);
        return UsersRepository.getInstance(database.userDao(), networkDataSource, executors);
    }

    public static UsersNetworkDataSource provideNetworkDataSource(Context context) {
        provideRepository(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return UsersNetworkDataSource.getInstance(context.getApplicationContext(),executors);
    }

    public static MainViewModelFactory provideMainViewModelFactory(Context context, int id) {
        UsersRepository repository = provideRepository(context.getApplicationContext());
        return new MainViewModelFactory(repository, id);
    }

    public static ListViewModelFactory provideListViewModelFactory(Context context) {
        UsersRepository repository = provideRepository(context.getApplicationContext());
        return new ListViewModelFactory(repository);
    }
}
