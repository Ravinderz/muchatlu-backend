package com.muchatlu.service;

import com.muchatlu.model.FriendRequest;
import com.muchatlu.model.MyUserDetails;
import com.muchatlu.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationService {

    public boolean validateRequest(String action, String actionType, Object data, MyUserDetails loggedUser){

        if(actionType.equalsIgnoreCase("self")){
            if(data instanceof Long){
                Long id = ((Long) data).longValue();
                if(loggedUser.getId().equals(id)){
                    return true;
                }
            }
            if(action.equalsIgnoreCase("sendFriendRequest") && data instanceof FriendRequest){
                FriendRequest request = (FriendRequest) data;
                if(request.getRequestFromUserId().equals(loggedUser.getId())){
                    return true;
                }
            }
            if(action.equalsIgnoreCase("updateFriendRequest") && data instanceof FriendRequest){
                FriendRequest request = (FriendRequest) data;
                if(request.getRequestToUserId().equals(loggedUser.getId())){
                    return true;
                }
            }if((action.equalsIgnoreCase("getConversationId") || action.equalsIgnoreCase("getConversation"))
                    && ( data instanceof List)){
                if(loggedUser.getId().equals(((List<Long>) data).get(0)) ||loggedUser.getId().equals(((List<Long>) data).get(1))){
                    return true;
                }
            }
            if(data instanceof String){
                if(((String) data).contains("@")){
                    if(((String) data).equalsIgnoreCase(loggedUser.getEmail())){
                        return true;
                    }
                }else if(loggedUser.getId().equals(Long.parseLong(String.valueOf(data)))){
                    return true;
                }
            }
            if(data instanceof Person){
                if(((Person) data).getId().equals(loggedUser.getId())){
                    return true;
                }
            }
        }
        return false;
    }
}
