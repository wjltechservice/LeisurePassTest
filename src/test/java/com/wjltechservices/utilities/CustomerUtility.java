package com.wjltechservices.utilities;

import io.cucumber.core.internal.gherkin.deps.com.google.gson.Gson;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import java.util.Map;

import static io.restassured.RestAssured.given;

@Component
public class CustomerUtility {
    public String addCustomer(String fullName, String homeCity) {
        // Call endpoint for creating customer
        Response response = given()
                .param("fullName", fullName)
                .param("homeCity", homeCity)
                .when()
                .request("POST", "/customer/new");

        // Verify 200 response
        response.then().statusCode(200);

        // Fetch the JSON response and convert to map
        String responseJson = response.getBody().asString();
        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = (Map<String, Object>) new Gson().fromJson(responseJson, Map.class);

        return (String) responseMap.get("customerId");
    }

    public String getCustomer(String customerId) {
        Response response = given().when().request("GET", "/customer/" + customerId);
        response.then().statusCode(200);
        return response.getBody().asString();
    }
}
