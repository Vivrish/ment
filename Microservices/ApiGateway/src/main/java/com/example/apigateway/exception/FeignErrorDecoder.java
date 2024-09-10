package com.example.apigateway.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        log.debug("Feign error intercepted: {}, {}", methodKey, response);
        return new ResponseStatusException(
                HttpStatusCode.valueOf(response.status()),
                response.reason()
        );
    }
}
