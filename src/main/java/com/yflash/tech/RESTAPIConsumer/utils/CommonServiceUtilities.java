package com.yflash.tech.RESTAPIConsumer.utils;

import com.yflash.tech.RESTAPIConsumer.model.out.ErrorDetail;
import com.yflash.tech.RESTAPIConsumer.model.out.ErrorResponse;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceUtilities {

    public ErrorResponse extractErrMessages(String errMessages, ErrorResponse errorResponse) {
        if(StringUtils.isNotBlank(errMessages)) {
            ErrorDetail errorDetail = new ErrorDetail();
            errorDetail.setErrorMessage(errMessages);
            errorResponse.setErrorDetail(errorDetail);
        }
        return errorResponse;
    }

}
