package com.ahirajustice.mockserver.modules.mock.controllers;

import com.ahirajustice.mockserver.modules.mock.models.SaveEndpointRequest;
import com.ahirajustice.mockserver.modules.mock.services.MockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MockController {

    private final MockService mockService;

    @RequestMapping(path = "/endpoint", method = RequestMethod.POST)
    public void saveEndpoint(@RequestBody @Valid SaveEndpointRequest request) {
        mockService.saveEndpoint(request);
    }

    @RequestMapping(
            path = "/**",
            method = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH}
    )
    public ResponseEntity<Object> handleRequest(HttpServletRequest request) {
        return mockService.handleRequest(request);
    }

}
