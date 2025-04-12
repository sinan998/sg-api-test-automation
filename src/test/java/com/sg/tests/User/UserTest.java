package com.sg.tests;

import com.github.javafaker.Faker;
import com.sg.helpers.HelperMethods;
import com.sg.helpers.UserHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.sg.helpers.HelperMethods.checkStatus;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.AssertJUnit.fail;

public class UserTest {
  Faker faker= new Faker();

  UserHelper userHelper= new UserHelper();
  Integer id = faker.number().numberBetween(1, 10000);
  String username = faker.name().username();
  String firstName = faker.name().firstName();
  String lastName = faker.name().lastName();
  String email = faker.internet().emailAddress();
  String password = faker.internet().password();
  String phone = faker.phoneNumber().cellPhone();
  Integer userStatus = faker.number().numberBetween(0, 2);




    @BeforeClass(alwaysRun = true)
    public void createUser(){
        Response user= userHelper.createUser(id,username,firstName,lastName,email,password,phone,userStatus);
      checkStatus(user, 200);
      assertThat(user.jsonPath().getString("code")).isEqualTo("200");
    }

  @Test(priority = 1, description = "Get user with username")
  public void getBookingIdsTest() throws InterruptedException {
      Thread.sleep(1000);
      Response response = userHelper.getUserWithUser(username);
    checkStatus(response, 200);
    assertThat(response.jsonPath().getString("username")).isEqualTo(username);

  }

  @Test(priority = 2, description = "Get user with username")
  public void getLoginUserTest() {
    Response response = userHelper.getLoginUser(username,password);
    checkStatus(response, 200);
    assertThat(response.jsonPath().getString("code")).isEqualTo("200");
  }
  @Test(priority = 2, description = "Get user with username")
  public void getLogoutUserTest() {
    Response response = userHelper.getLogoutUser(username,password);
    checkStatus(response, 200);
    assertThat(response.jsonPath().getString("code")).isEqualTo("200");
  }
  @Test(priority = 3, description = "Create User with list")
  public void createWithList() {
    Response user= userHelper.createWithList(id,username,firstName,lastName,email,password,phone,userStatus);
    checkStatus(user, 200);
    assertThat(user.jsonPath().getString("code")).isEqualTo("200");
  }


}
