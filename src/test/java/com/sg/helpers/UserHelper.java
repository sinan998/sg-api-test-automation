package com.sg.helpers;

import Config.APIConstant;
import clients.RestAssuredClient;
import com.sg.models.requests.CreateUserRequest;
import io.restassured.response.Response;

import java.util.*;

public class UserHelper extends RestAssuredClient {

    public UserHelper(){super(APIConstant.APIUrl.BASE_URL);}

    public Response createUser(Integer id,String username,String firstname,String lastname,String email,String password,String phone, Integer userStatus){
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Content-Type", "application/json");

        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .id(id)
                .username(username)
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .password(password)
                .phone(phone)
                .userStatus(userStatus)
                .build();

        return post(APIConstant.APIUrl.VERSION+APIConstant.APIUrl.USER,null,headers,createUserRequest);

    }

    public Response createWithList(Integer id,String username,String firstname,String lastname,String email,String password,String phone, Integer userStatus){
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Content-Type", "application/json");

        List<CreateUserRequest> users = Arrays.asList(
                CreateUserRequest.builder()
                        .id(id)
                        .username(username)
                        .firstname(firstname)
                        .lastname(lastname)
                        .email(email)
                        .password(password)
                        .phone(phone)
                        .userStatus(userStatus)
                        .build()
        );

        return post(APIConstant.APIUrl.VERSION+APIConstant.APIUrl.USER+"/createWithList",null,headers,users);

    }

    public Response getUserWithUser(String userName){
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "application/json");
        return get(APIConstant.APIUrl.VERSION+APIConstant.APIUrl.USER+"/"+userName,null,headers,null);
    }

    public Response getLoginUser(String userName,String password){
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "application/json");

        Map<String, Object> param = new HashMap<>();
        param.put("username", userName);
        param.put("password", password);

        return get(APIConstant.APIUrl.VERSION+APIConstant.APIUrl.USER+"/login",param,headers,null);
    }

    public Response getLogoutUser(String userName,String password){
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "application/json");

        Map<String, Object> param = new HashMap<>();
        param.put("username", userName);
        param.put("password", password);
        return get(APIConstant.APIUrl.VERSION+APIConstant.APIUrl.USER+"/logout",param,headers,null);
    }


    public Response uploadUser(Integer id,String username,String firstname,String lastname,String email,String password,String phone, Integer userStatus){
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Content-Type", "application/json");

        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .id(id)
                .username(username)
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .password(password)
                .phone(phone)
                .userStatus(userStatus)
                .build();

        return put(APIConstant.APIUrl.VERSION+APIConstant.APIUrl.USER+"/"+username,null,headers,createUserRequest);

    }


    public Response deleteUser(String userName){
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "application/json");
        return delete(APIConstant.APIUrl.VERSION+APIConstant.APIUrl.USER+"/"+userName,null,headers,null);
    }



}
