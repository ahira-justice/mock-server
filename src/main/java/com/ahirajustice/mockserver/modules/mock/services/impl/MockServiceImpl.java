package com.ahirajustice.mockserver.modules.mock.services.impl;

import com.ahirajustice.mockserver.common.entities.Mock;
import com.ahirajustice.mockserver.common.exceptions.ForbiddenException;
import com.ahirajustice.mockserver.common.exceptions.NotFoundException;
import com.ahirajustice.mockserver.common.repositories.MockRepository;
import com.ahirajustice.mockserver.common.utils.ObjectMapperUtils;
import com.ahirajustice.mockserver.modules.mock.models.SaveEndpointRequest;
import com.ahirajustice.mockserver.modules.mock.services.MockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class MockServiceImpl implements MockService {

    private final MockRepository mockRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void saveEndpoint(SaveEndpointRequest request) {
        validateRequest(request);

        Mock mock = buildMock(request);
        mockRepository.save(mock);
    }

    private Mock buildMock(SaveEndpointRequest request) {
        mockRepository.findByUrlAndRequestMethod(request.getUrl(), request.getRequestMethod())
                .ifPresent(mockRepository::delete);

        return Mock.builder()
                .url(request.getUrl())
                .requestMethod(request.getRequestMethod())
                .responseStatusCode(request.getResponseStatusCode())
                .responseBody(ObjectMapperUtils.serialize(objectMapper, request.getResponseBody()))
                .build();
    }

    private void validateRequest(SaveEndpointRequest request) {
        if (request.getUrl().equals("/endpoint")) {
            throw new ForbiddenException("Mock Server Error -> Cannot mock url: '/endpoint'");
        }
    }

    @Override
    public ResponseEntity<Object> handleRequest(HttpServletRequest request) {
        String queryString = request.getQueryString();
        String path = request.getRequestURI() + (StringUtils.isNotBlank(queryString) ? String.format("?%s", queryString) : "");

        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod());

        Mock mock = mockRepository.findByUrlAndRequestMethod(path, requestMethod)
                .orElse(null);

        if (mock == null) {
            throw new NotFoundException("Mock Server Error -> Resource not found");
        }

        return new ResponseEntity<>(mock.getResponseBody(), mock.getResponseStatusCode());
    }

}
