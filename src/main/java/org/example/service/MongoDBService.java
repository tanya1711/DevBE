package org.example.service;

import org.example.model.Community;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@Service
public class MongoDBService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoDBService(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    public void enterDataToUserCollection(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User save = this.userRepository.insert(user);
        ResponseEntity.ok(save);
    }

    public Page<Community> paginationOnCommunityCollection(Pageable pageable){
        Query query = new Query()
                .with(pageable);

        List<Community> listAfterPagination = mongoTemplate.find(query, Community.class);
        long count = mongoTemplate.count(query, Community.class);
        return new PageImpl<>(listAfterPagination,pageable,count);

    }

}
