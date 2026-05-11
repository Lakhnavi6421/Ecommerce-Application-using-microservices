package com.app.ecom;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private List<User> userList = new ArrayList<>();

    private Long userId = 0L;

    public List<User> fetchAllUsers(){
        return userList;
    }

    public void addUser(User newUser){
        newUser.setId(++userId);
        userList.add(newUser);
    }

    public Optional<User> fetchUserById(Long id) {

        // Getting user by for loop
//        for(User user : userList){
//            if(user.getId().equals(id))
//                return user;
//        }
//        return null;

        // Getting user by Stream and filter
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public boolean updateUser(Long id, User updatedUser){
        // Tradition way to update

//        User findUser = null;
//        for(User user : userList){
//            if(user.getId().equals(id)){
//                findUser = user;
//            }
//        }
//        findUser.setFirstName(updatedUser.getFirstName());
//        findUser.setLastName(updatedUser.getLastName());
//
//        return findUser;

        // update user using Java 8 features

        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    return true;
                }).orElse(false);

    }
}
