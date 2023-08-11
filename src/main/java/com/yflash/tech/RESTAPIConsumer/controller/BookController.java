package com.yflash.tech.RESTAPIConsumer.controller;

import com.yflash.tech.RESTAPIConsumer.model.out.Book;
import com.yflash.tech.RESTAPIConsumer.service.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumer")
public class BookController {

    private static final Logger LOGGER = LogManager.getLogger(BookController.class);

    @Autowired
    BookService bookService;

    @PostMapping(value = "/get-all-users", produces = "application/json")
    ResponseEntity<List<Book>> getAllUsers() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

}
