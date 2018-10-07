package com.timo.certification.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.timo.certification.data.UsersRepository;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final UsersRepository mRepository;
    private int id;

    public MainViewModelFactory(UsersRepository repository, int id) {
        this.mRepository = repository;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(mRepository,id);
    }
}
