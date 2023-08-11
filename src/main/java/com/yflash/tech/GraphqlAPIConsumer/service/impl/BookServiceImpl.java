package com.yflash.tech.GraphqlAPIConsumer.service.impl;

import com.yflash.tech.GraphqlAPIConsumer.model.in.GraphqlQueryResponse;
import com.yflash.tech.GraphqlAPIConsumer.model.in.GraphqlResponseErrors;
import com.yflash.tech.GraphqlAPIConsumer.model.out.Book;
import com.yflash.tech.GraphqlAPIConsumer.model.out.BookList;
import com.yflash.tech.GraphqlAPIConsumer.service.BookService;
import com.yflash.tech.GraphqlAPIConsumer.utils.CommonServiceUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class BookServiceImpl implements BookService {

    private final Environment environment;
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;
    private final CommonServiceUtilities commonServiceUtilities;

    @Autowired
    public BookServiceImpl(Environment environment, RestTemplate restTemplate, ModelMapper modelMapper, CommonServiceUtilities commonServiceUtilities) {
        this.environment = environment;
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
        this.commonServiceUtilities = commonServiceUtilities;
    }

    private static final Logger LOGGER = LogManager.getLogger(BookServiceImpl.class);

    @Override
    public BookList getAllBooks() {
        BookList result = null;
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
                graphqlQueryResponse = isErrorAvailable(response.getBody());
            }

            if(graphqlQueryResponse != null && graphqlQueryResponse.getErrors() == null) {
                List<Book> books = graphqlQueryResponse.getData().getFindAllBooks();
                result = new BookList(books);
            }

        }
        catch (HttpClientErrorException clientErrorException) {
            LOGGER.error("Client error occurred with status {} : {}", clientErrorException.getStatusCode().value(), clientErrorException.getMessage());
            return (BookList) commonServiceUtilities.extractErrMessages(clientErrorException.getMessage(), new BookList());
        }
        catch (HttpServerErrorException serverErrorException) {
            LOGGER.error("Server side error occurred with status {} : {}", serverErrorException.getStatusCode().value(), serverErrorException.getMessage());
            return (BookList) commonServiceUtilities.extractErrMessages(serverErrorException.getMessage(), new BookList());
        }
        catch (Exception e) {
            LOGGER.error("Error in getAllBooks() : {}", e.getMessage());
            return (BookList) commonServiceUtilities.extractErrMessages(e.getMessage(), new BookList());
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

    /**
     * The isErrorAvailable() method checks if the GraphQL API response has any errors or not
     * If yes, then it collects all the error messages and throws an Exception based on them
     * If no, then simply returns the response body which can be further processed
     */
    private GraphqlQueryResponse isErrorAvailable(GraphqlQueryResponse graphqlQueryResponse) throws Exception {
        List<GraphqlResponseErrors> graphqlResponseErrors = graphqlQueryResponse.getErrors();
        if(graphqlResponseErrors != null) {
            StringBuilder errMsg = new StringBuilder();
            graphqlResponseErrors.forEach(error ->
                    errMsg.append(error.getMessage()));
            throw new Exception(errMsg.toString());
        }
        return graphqlQueryResponse;
    }

}
