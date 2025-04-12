package com.sg.helpers;

import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class HelperMethods {

    public static void checkStatus(Response res, int statusCode) {
        assertThat(statusCode).isEqualTo(res.getStatusCode()).withFailMessage("Status Check Fail!");
    }
}
