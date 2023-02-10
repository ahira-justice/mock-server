package com.ahirajustice.mockserver.modules.mock.services;

import com.ahirajustice.mockserver.modules.mock.models.SaveEndpointRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface MockService {

    void saveEndpoint(SaveEndpointRequest request);

    ResponseEntity<Object> handleRequest(HttpServletRequest request);

}
