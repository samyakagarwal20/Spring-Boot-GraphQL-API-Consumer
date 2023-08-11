package com.yflash.tech.RESTAPIConsumer.model.in;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yflash.tech.RESTAPIConsumer.model.out.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseData {

    @JsonProperty("findAllBooks")
    private List<Book> findAllBooks;

}
