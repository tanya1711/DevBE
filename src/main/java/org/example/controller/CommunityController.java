package org.example.controller;

import org.apache.kafka.common.protocol.types.Field;
import org.example.model.Community;
import org.example.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @RequestMapping(method = RequestMethod.GET,value = "/community/list/{page}")
    public Page<Community> viewCommunities(@PathVariable int page){
        return communityService.getCommunityData(page, 2);
    }

    @RequestMapping(method=RequestMethod.POST,value = "/community/create")
    public void createCommunity(@RequestBody Community community){
        communityService.createCommunity(community);
    }

    @RequestMapping(method=RequestMethod.POST,value = "/community/{name}")
    public void sendMessageToCommunity(@PathVariable String name, @RequestBody String message){
        communityService.sendMessageToCommunity(name, message);

    }

}
