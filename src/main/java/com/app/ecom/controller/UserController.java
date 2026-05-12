package com.app.ecom.controller;

import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

//    @Autowired
    private final UserService userService;

    private Object user;

//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

    @GetMapping
//    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserResponse>> getAllUsers(){
//        way 1:
//        return new ResponseEntity<>(userService.fetchAllUsers());
//        way 2:
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){

        // Basic
//        User user = userService.fetchUserById(id);
//        if(user == null)
//            return ResponseEntity.notFound().build();
//        return ResponseEntity.ok(user);

        // Using Java 8 feture "map"

        return userService.fetchUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,
                       @RequestBody UserRequest updatedUserRequest){

        // Traditional way
//        User user = userService.updateUser(id, updatedUser);
//        if(user == null)
//            return ResponseEntity.notFound().build();
//        return ResponseEntity.ok(user);


        // Using Java 8 feture "map"

        boolean updated = userService.updateUser(id, updatedUserRequest);
        if(updated){
            return ResponseEntity.ok("User updated successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
        return ResponseEntity.ok("User added successfully");
    }


}
