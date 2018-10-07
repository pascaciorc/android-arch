package com.timo.certification.ui.list;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;

import com.timo.certification.data.UsersRepository;

public class ListViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final UsersRepository mRepository;

    public ListViewModelFactory(UsersRepository repository) {
        mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ListActivityViewModel(mRepository);
    }
}
