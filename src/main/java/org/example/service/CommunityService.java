package org.example.service;

import org.example.kafka.KafkaProducer;
import org.example.model.Community;
//import org.example.repository.CommunityRepository;
import org.example.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CommunityService {

    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private MongoDBService mongoDBService;

    @Autowired
    private KafkaProducer kafkaProducer;

    public void createCommunity(Community community){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        community.setAdmin(username);
        System.out.println("from create com "+username);
        communityRepository.insert(community);
//        kafkaConfig.createTopic(community.getCommunityName());
    }

    public Page<Community> getCommunityData(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return mongoDBService.paginationOnCommunityCollection(pageable);
    }

    public void sendMessageToCommunity(String communityName, String message){
        kafkaProducer.sendMessage("chatSystem2",message);
    }


}
