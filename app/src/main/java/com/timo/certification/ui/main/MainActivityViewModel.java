package com.timo.certification.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.timo.certification.data.UsersRepository;
import com.timo.certification.data.database.UserEntry;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private final LiveData<UserEntry> mUser;
    private final UsersRepository mRepository;
    private int mId;

    public MainActivityViewModel(UsersRepository repository, int id) {
        this.mRepository = repository;
        this.mId = id;
        this.mUser = mRepository.getUserById(mId);
    }

    public LiveData<UserEntry> getUser() {
        return mUser;
    }
}
