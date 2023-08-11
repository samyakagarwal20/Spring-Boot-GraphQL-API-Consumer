package com.yflash.tech.RESTAPIConsumer.service;

import com.yflash.tech.RESTAPIConsumer.model.out.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
}
