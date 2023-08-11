package com.yflash.tech.RESTAPIConsumer.model.out;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book{

    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "isbn")
    private String isbn;

    @JsonProperty(value = "pageCount")
    private int pageCount;

    @JsonProperty(value = "author")
    private Author author;

}
