package com.denis.learning.oop;

public interface BookRepository {

    void save(Book aBook);

    Book loadByPrimaryKey(long id);
}



