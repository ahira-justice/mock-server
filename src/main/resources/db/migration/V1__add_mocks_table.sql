CREATE TABLE mocks
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    created_on datetime NOT NULL,
    last_modified_on datetime NOT NULL,
    created_by VARCHAR(255)NOT NULL,
    last_modified_by VARCHAR(255)NOT NULL,
    is_deleted BIT(1) NOT NULL,
    version INT NOT NULL,
    url VARCHAR(511) NOT NULL,
    request_method VARCHAR(31) NOT NULL,
    response_status_code VARCHAR(31) NOT NULL,
    response_body TEXT,
    CONSTRAINT pk_mocks PRIMARY KEY (id),
    CONSTRAINT uc_mocks_url_request_method UNIQUE (url, request_method)
);