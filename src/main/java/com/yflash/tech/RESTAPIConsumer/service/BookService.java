package com.yflash.tech.RESTAPIConsumer.service;

import com.yflash.tech.RESTAPIConsumer.model.out.Book;
import com.yflash.tech.RESTAPIConsumer.model.out.BookList;

import java.util.List;

public interface BookService {
    BookList getAllBooks();
}
