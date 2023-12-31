package com.yflash.tech.GraphqlAPIConsumer.model.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetail {

    private String errorMessage;
    private String errorCode;
    private String code;

}
