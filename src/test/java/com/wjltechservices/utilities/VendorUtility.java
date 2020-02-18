package com.wjltechservices.utilities;

import io.cucumber.core.internal.gherkin.deps.com.google.gson.Gson;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Utilities relating to vendors
 */
@Component
public class VendorUtility {

    /**
     * Add a new vendor into the application
     *
     * @param vendorName The name of the vendor to add
     * @return The vendorId generated when the vendor is added
     */
    public String addNewVendor(String vendorName) {
        // Call endpoint for creating vendor
        Response response = given()
                .param("vendorName", vendorName)
                .when()
                .request("POST", "/vendor/new");

        // Verify 200 response
        response.then().statusCode(200);

        // Fetch the JSON response and convert to map
        String responseJson = response.getBody().asString();
        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = (Map<String, Object>) new Gson().fromJson(responseJson, Map.class);

        return (String) responseMap.get("vendorId");
    }

    public String getVendorDetails(String vendorId) {
        Response response = given().when().request("GET", "/vendor/" + vendorId);
        response.then().statusCode(200);
        return response.getBody().asString();
    }
}
