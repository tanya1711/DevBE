package org.example.repository;


import io.micrometer.common.lang.NonNullApi;
import org.example.model.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface CommunityRepository extends MongoRepository<Community, String> {
}
