package com.ahirajustice.mockserver.common.entities;

import com.ahirajustice.mockserver.common.utils.ObjectMapperUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "mocks",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"url", "requestMethod"})}
)
public class Mock extends BaseEntity {

    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestMethod requestMethod;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HttpStatus responseStatusCode;
    @Lob
    private String responseHeaders;
    @Lob
    private String responseBody;

    public MultiValueMap<String, String> getResponseHeaders() {
        ObjectMapper objectMapper = new ObjectMapper();
        Object headers = ObjectMapperUtils.deserialize(
                objectMapper,
                responseHeaders,
                Object.class
        );

        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        Map<String, String> fieldMap = objectMapper.convertValue(headers, new TypeReference<>() {});
        valueMap.setAll(fieldMap);

        return valueMap;
    }

    public Object getResponseBody() {
        return ObjectMapperUtils.deserialize(
                new ObjectMapper(),
                responseBody,
                Object.class
        );
    }

}
