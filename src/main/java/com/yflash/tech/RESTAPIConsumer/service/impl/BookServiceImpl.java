package com.yflash.tech.RESTAPIConsumer.service.impl;

import com.yflash.tech.RESTAPIConsumer.model.out.Book;
import com.yflash.tech.RESTAPIConsumer.service.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    ModelMapper modelMapper;

    private static final Logger LOGGER = LogManager.getLogger(BookServiceImpl.class);

    @Override
    public List<Book> getAllBooks() {
        return null;
    }

}
