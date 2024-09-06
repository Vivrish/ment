package com.example.apigateway.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        return new ResponseStatusException(
                HttpStatusCode.valueOf(response.status()),
                response.reason()
        );
    }
}
