package com.yflash.tech.GraphqlAPIConsumer.controller;

import com.yflash.tech.GraphqlAPIConsumer.model.out.BookList;
import com.yflash.tech.GraphqlAPIConsumer.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consumer")
public class BookController {

    private static final Logger LOGGER = LogManager.getLogger(BookController.class);

    @Autowired
    BookService bookService;

    @GetMapping(value = "/get-all-books", produces = "application/json")
    ResponseEntity<BookList> getAllBooks(HttpServletRequest request) {
        long start = System.currentTimeMillis();
        LOGGER.info("Intercepted request for {}", request.getRequestURL());
        ResponseEntity<BookList> response = new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
        long end = System.currentTimeMillis();
        LOGGER.info("Time taken to fetch the response (in milliseconds) : {}",(end - start));
        return response;
    }

}
