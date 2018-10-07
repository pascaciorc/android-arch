package com.timo.certification.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(UserEntry... entries);

    @Query("SELECT * FROM users")
    LiveData<List<UserEntry>> getUsers();

    @Query("SELECT COUNT(id) FROM users")
    int countUsers();

    @Query("SELECT * FROM users WHERE id = :id")
    LiveData<UserEntry> getUserById(int id);
}
