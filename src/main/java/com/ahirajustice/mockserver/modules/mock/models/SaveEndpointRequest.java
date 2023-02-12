package com.ahirajustice.mockserver.modules.mock.models;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SaveEndpointRequest {

    @NotBlank
    private String url;
    @NotNull
    private RequestMethod requestMethod;
    @NotNull
    private HttpStatus responseStatusCode;
    private Object responseHeaders;
    private Object responseBody;

}
