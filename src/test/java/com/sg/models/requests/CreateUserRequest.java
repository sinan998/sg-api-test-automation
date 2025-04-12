package com.sg.models.requests;

import io.restassured.response.Response;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.testng.annotations.BeforeClass;

@Builder
@Getter
@Setter
public class CreateUserRequest {
    private Integer id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    private Integer userStatus;



}
