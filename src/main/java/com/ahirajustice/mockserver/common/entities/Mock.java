package com.ahirajustice.mockserver.common.entities;

import com.ahirajustice.mockserver.common.utils.ObjectMapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
    private String responseBody;

    public Object getResponseBody() {
        return ObjectMapperUtils.deserialize(
                new ObjectMapper(),
                responseBody,
                Object.class
        );
    }

}
