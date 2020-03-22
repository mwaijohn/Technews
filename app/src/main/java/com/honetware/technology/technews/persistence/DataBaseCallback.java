package com.honetware.technology.technews.persistence;

import java.util.List;

public interface DataBaseCallback {
    void onBooksmarksLoaded(List<BookMark> bookMarkList);
}
