package com.yflash.tech.RESTAPIConsumer.service.impl;

import com.yflash.tech.RESTAPIConsumer.model.in.GraphqlQueryResponse;
import com.yflash.tech.RESTAPIConsumer.model.out.Book;
import com.yflash.tech.RESTAPIConsumer.service.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class BookServiceImpl implements BookService {

    private final Environment environment;
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;

    @Autowired
    public BookServiceImpl(Environment environment, RestTemplate restTemplate, ModelMapper modelMapper) {
        this.environment = environment;
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
    }

    private static final Logger LOGGER = LogManager.getLogger(BookServiceImpl.class);

    @Override
    public List<Book> getAllBooks() {
        List<Book> result = null;
        GraphqlQueryResponse graphqlQueryResponse = null;
        try {
            LOGGER.info("Preparing the request ...");
            String wsUrl = environment.getProperty("producer.api.url");

            LOGGER.info("\t|--- Setting up the headers");
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.valueOf("application/graphql"));

            LOGGER.info("\t|--- Setting up the request body");
            HttpEntity<?> graphqlEntity = new HttpEntity<>(fetchQuery(), headers);

            LOGGER.info("Fetching books data ...");
            ResponseEntity<GraphqlQueryResponse> response = restTemplate.exchange(Objects.requireNonNull(wsUrl), HttpMethod.POST, graphqlEntity, GraphqlQueryResponse.class);

            if(response.getBody() != null) {
                graphqlQueryResponse = response.getBody();
            }

            if(graphqlQueryResponse != null && graphqlQueryResponse.getErrors() == null) {
                result = graphqlQueryResponse.getData().getFindAllBooks();
            }

        }
        catch (Exception e) {
            LOGGER.error("Error in getAllBooks() : {}", e.getMessage());
        }
        return result;
    }

    /**
     * The fetchQuery() method deals with any modification with the GraphQL query
     * For example, assigning appropriate values for GraphQL variables at runtime, thereby generating dynamic queries
     */
    private String fetchQuery() {
        String queryStart = "query MyQuery { \n";
        String queryEnd = "\n}";
        String fetchAllBooksQuery = environment.getProperty("graphqlQueries.findAllBooks");
        return queryStart + fetchAllBooksQuery + queryEnd;
    }

}
