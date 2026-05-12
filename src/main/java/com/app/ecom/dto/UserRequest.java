package com.app.ecom.dto;

import com.app.ecom.model.Address;
import com.app.ecom.model.UserRole;
import lombok.Data;


@Data
public class UserRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Address address;

}
