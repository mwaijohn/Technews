package com.honetware.technology.technews.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface BookMarkDao {

    @Insert
    void insertBookMark(BookMark bookMark);

    @Delete
    void deleteBookMark(BookMark bookMark);

    @Query("SELECT * FROM bookmarks ")
    Flowable<List<BookMark>> fetchAllBookmarks();
}
