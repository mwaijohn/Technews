package com.honetware.technology.technews.persistence;

import android.content.Context;
import android.widget.Toast;

import com.honetware.technology.technews.R;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class DataStore {

    BookMarkDatabase BookMarkDatabase;
    static DataStore instance;
    public Context context;

    public static  DataStore getInstance(Context context){
        if (instance==null){
            instance = new DataStore(context);
        }
        return instance;
    }

    public DataStore(Context context){
        this.context = context;
        BookMarkDatabase = BookMarkDatabase.getInstance(context);
    }

    public void insertBookMark(BookMark bookMark) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                BookMarkDatabase.bookMarkDao().insertBookMark(bookMark);
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(context, R.string.article_saved, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void deleteBookMark(BookMark bookMark) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                BookMarkDatabase.bookMarkDao().deleteBookMark(bookMark);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, R.string.bookmark_deleted, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void getBookmarks(DataBaseCallback dataBaseCallback){

        BookMarkDatabase.bookMarkDao().fetchAllBookmarks().observeOn(
                AndroidSchedulers.mainThread()).subscribe(
                new Consumer<List<BookMark>>() {
                    @Override
                    public void accept(List<BookMark> bookMarks) throws Exception {
                        dataBaseCallback.onBooksmarksLoaded(bookMarks);
                    }
                }
        );
    }

}
