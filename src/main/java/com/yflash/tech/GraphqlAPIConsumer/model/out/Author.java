package com.yflash.tech.GraphqlAPIConsumer.model.out;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {

    private Integer id;
    private String firstName;
    private String lastName;

}
