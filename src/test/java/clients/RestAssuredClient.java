package clients;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.listener.ResponseValidationFailureListener;
import io.restassured.path.json.mapper.factory.Jackson2ObjectMapperFactory;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;
import java.util.Objects;

import static io.restassured.config.FailureConfig.failureConfig;
import static io.restassured.config.LogConfig.logConfig;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;

public class RestAssuredClient {

    String baseUrl;
    RequestSpecification httpRequest;

    public RestAssuredClient(String baseUrl){
        RestAssured.config = getDefaultConfig();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.filters(new AllureRestAssured());
        this.baseUrl=baseUrl;
    }

    public Response get(String path, Map<String, Object> params, Map<String, Object> headers, Object body) {
        setHttpRequest(params, headers, body);
        Response response;
        try {
            response = httpRequest.get(path);
        } catch (IllegalArgumentException illegalArgumentException) {
            httpRequest.urlEncodingEnabled(true);
            response = httpRequest.get(path);
        }
        response.then().log().body();
        return response;
    }

    public Response post(String path, Map<String, Object> params, Map<String, Object> headers, Object body) {
        RestAssured.urlEncodingEnabled = false;
        setHttpRequest(params, headers, body);
        Response response = httpRequest.post(path);
        response.then().log().body();
        return response;
    }


    public Response put(String path, Map<String, Object> params, Map<String, Object> headers, Object body) {
        setHttpRequest(params, headers, body);
        Response response = httpRequest.put(path);
        response.then().log().body();
        return response;
    }

    public Response delete(String path, Map<String, Object> params, Map<String, Object> headers, Object body) {
        setHttpRequest(params, headers, body);
        Response response = httpRequest.delete(path);
        response.then().log().body();
        return response;
    }

    public Response patch(String path, Map<String, Object> params, Map<String, Object> headers, Object body) {
        setHttpRequest(params, headers, body);
        Response response = httpRequest.patch(path);
        response.then().log().body();
        return response;
    }

    protected void setHttpRequest(Map<String, Object> params, Map<String, Object> headers, Object body) {
        RestAssured.requestSpecification = new RequestSpecBuilder().build().filter(new AllureRestAssured());
        httpRequest = RestAssured.given().log().all(true).baseUri(baseUrl);
        //httpRequest.header("Content-Type", "application/json");
        if (params != null) {
            httpRequest.queryParams(params);
        }
        if (headers != null) {
            httpRequest.headers(headers);
        }
        if (body != null) {
            httpRequest.body(body);
        }
    }



    public static RestAssuredConfig getDefaultConfig(){
        ResponseValidationFailureListener failureListener=(reqSpec, resSpec, response) ->
                System.out.printf("We have a failure, " +
                                "response status was %s and the body contained: %s",
                        response.getStatusCode(), response.body().print());
        return RestAssured.config()
                .logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL))
                .failureConfig(failureConfig().failureListeners(failureListener))
                .redirect(redirectConfig().maxRedirects(1))
                .objectMapperConfig(objectMapperConfig().jackson2ObjectMapperFactory(getDefaultMapper()));

    }
    private static Jackson2ObjectMapperFactory getDefaultMapper() {
        return (type, s) -> {
            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return om;
        };
    }
}
