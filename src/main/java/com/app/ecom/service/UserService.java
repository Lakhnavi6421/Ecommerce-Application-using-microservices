package com.app.ecom.service;

import com.app.ecom.dto.AddressDto;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.repository.UserRepository;
import com.app.ecom.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

//    private List<User> userList = new ArrayList<>();
//
//    private Long userId = 0L;

    public List<UserResponse> fetchAllUsers(){
//        return userList;
//        return userRepository.findAll();

//        Using DTO
        List<User> userList = userRepository.findAll();
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void addUser(UserRequest userRequest){
//        newUser.setId(++userId);
//        userList.add(newUser);

//        userRepository.save(newUser);

//        Using DTO

        User user = new User();
        updateUserFromRequest(user, userRequest);
        userRepository.save(user);
    }


    public Optional<UserResponse> fetchUserById(Long id) {

        // Getting user by for loop
//        for(User user : userList){
//            if(user.getId().equals(id))
//                return user;
//        }
//        return null;

        // Getting user by Stream and filter
//        return userList.stream()
//                .filter(user -> user.getId().equals(id))
//                .findFirst();


        // fetching user id by JPA repo
//        return userRepository.findById(id);

//        Using DTO
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    public boolean updateUser(Long id, UserRequest updatedUserRequest){
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

//        return userList.stream()
//                .filter(user -> user.getId().equals(id))
//                .findFirst()
//                .map(existingUser -> {
//                    existingUser.setFirstName(updatedUser.getFirstName());
//                    existingUser.setLastName(updatedUser.getLastName());
//                    return true;
//                }).orElse(false);

        // updating user by using JPA repo

//        return userRepository.findById(id)
//                .map(existingUser -> {
//                    existingUser.setFirstName(updatedUser.getFirstName());
//                    existingUser.setLastName(updatedUser.getLastName());
//                    userRepository.save(existingUser);
//                    return true;
//                }).orElse(false);


        // Migrating to DTO

        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser, updatedUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);

    }


    // Migrating to DTOs (DATA TRANSFER OBJECT)

    private UserResponse mapToUserResponse(User user){
            UserResponse response = new UserResponse();
            response.setId(String.valueOf(user.getId()));
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getLastName());
            response.setEmail(user.getEmail());
            response.setPhone(user.getPhone());
            response.setRole(user.getRole());

            if(user.getAddress() != null){
                AddressDto addressDto = new AddressDto();
                addressDto.setStreet(user.getAddress().getStreet());
                addressDto.setCity(user.getAddress().getCity());
                addressDto.setState(user.getAddress().getState());
                addressDto.setCountry(user.getAddress().getCountry());
                addressDto.setZipcode(user.getAddress().getZipcode());
                response.setAddress(addressDto);
            }

            return response;
    }



    private void updateUserFromRequest(User user, UserRequest userRequest) {

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if(userRequest.getAddress() != null){
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZipcode(userRequest.getAddress().getZipcode());
            user.setAddress(address);

        }
    }
}
