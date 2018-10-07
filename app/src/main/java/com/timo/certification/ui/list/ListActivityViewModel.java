package com.timo.certification.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.timo.certification.data.UsersRepository;
import com.timo.certification.data.database.UserEntry;

import java.util.List;

public class ListActivityViewModel extends ViewModel {
    private final LiveData<List<UserEntry>> mUsers;
    private final UsersRepository mRepository;

    public ListActivityViewModel(UsersRepository repository) {
        mRepository = repository;
        mUsers = mRepository.getUsers();
    }

    public LiveData<List<UserEntry>> getUsers() {
        return mUsers;
    }
}
