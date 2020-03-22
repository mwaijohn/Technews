package com.honetware.technology.technews.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {BookMark.class}, version = 1)
public abstract class BookMarkDatabase extends RoomDatabase {

    private static volatile BookMarkDatabase INSTANCE;

    public abstract BookMarkDao bookMarkDao();

    public static BookMarkDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (BookMarkDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BookMarkDatabase.class, "bookmarks.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
