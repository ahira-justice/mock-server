package com.ahirajustice.mockserver.common.repositories;

import com.ahirajustice.mockserver.common.entities.Mock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Repository
public interface MockRepository extends JpaRepository<Mock, Long>, QuerydslPredicateExecutor<Mock> {

    Optional<Mock> findByUrlAndRequestMethod(String url, RequestMethod requestMethod);

}
