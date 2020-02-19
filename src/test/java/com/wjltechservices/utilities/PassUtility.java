package com.wjltechservices.utilities;

import io.cucumber.core.internal.gherkin.deps.com.google.gson.Gson;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import java.util.Map;

import static io.restassured.RestAssured.given;

@Component
public class PassUtility {
    public String addPass(String vendorId, String customerId, String passCity, int duration, long validFrom) {
        // Call endpoint for creating a pass
        Response response = given()
                .param("vendorId", vendorId)
                .param("customerId", customerId)
                .param("passCity", passCity)
                .param("validFrom", validFrom)
                .param("duration", duration)
                .when()
                .request("POST", "/pass/new");

        // Verify 200 response
        response.then().statusCode(200);

        // Fetch the JSON response and convert to map
        String responseJson = response.getBody().asString();
        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = (Map<String, Object>) new Gson().fromJson(responseJson, Map.class);

        return (String) responseMap.get("passId");
    }

    public String renewPass(String passId, String customerId) {
        // Call endpoint for creating a pass
        Response response = given()
                .param("passID", passId)
                .param("customerId", customerId)
                .when()
                .request("POST", "/pass/renew");

        // Verify 200 response
        response.then().statusCode(200);

        // Fetch the JSON response and convert to map
        return response.getBody().asString();
    }

    public boolean validatePass(String vendorId, String passId) {
        // Call endpoint for creating a pass
        Response response = given()
                .param("vendorId", vendorId)
                .param("passId", passId)
                .when()
                .request("POST", "/pass/validate");

        // Verify 200 response
        response.then().statusCode(200);

        // Fetch the JSON response and convert to map
        String responseJson = response.getBody().asString();
        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = (Map<String, Object>) new Gson().fromJson(responseJson, Map.class);

        return (boolean) responseMap.get("isVaid");
    }
}
