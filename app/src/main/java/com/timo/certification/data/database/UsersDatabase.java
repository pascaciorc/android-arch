package com.timo.certification.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {UserEntry.class}, version = 1)
public abstract class UsersDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "users";

    private static final Object LOCK = new Object();
    private static volatile UsersDatabase sInstance;

    public static UsersDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            UsersDatabase.class,
                            DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }

    public abstract UserDao userDao();
}
