package com.wjltechservices.stepdefinitions;

import com.wjltechservices.context.Context;
import com.wjltechservices.context.ScenarioContext;
import com.wjltechservices.utilities.PassUtility;
import io.cucumber.core.gherkin.vintage.internal.gherkin.deps.com.google.gson.Gson;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PassStepDefinitions {

    public static final int TWO_DAYS_IN_SECONDS = 86400 * 2;
    private final PassUtility passUtility;
    private final ScenarioContext scenarioContext;

    @Autowired
    public PassStepDefinitions(PassUtility passUtility, ScenarioContext scenarioContext) {
        this.passUtility = passUtility;
        this.scenarioContext = scenarioContext;
    }

    @When("^the customer purchases a new (\\d+) day pass for Thorpe Park in (.*)$")
    public void theCustomerPurchasesANewDurationDayPassForThorpeParkInCity(int duration, String passCity) {
        Long customerId = (Long) scenarioContext.getContext(Context.CUSTOMER_ID);
        long validFrom = todayAtMidnight();
        String passId = passUtility.addPass("thorpepark", customerId, passCity, duration, validFrom);

        scenarioContext.putContext(Context.PASS_ID, passId);
    }

    @Then("^they are given their unique Pass ID$")
    public void theyAreGivenTheirUniquePassID() {
        String passId = (String) scenarioContext.getContext(Context.PASS_ID);

        assertThat(passId, is(notNullValue()));
        // Pattern for passId should be complex enough to make collision effectively impossible.
        // pattern is creationEpochMillis-customerId-vendorId-JavaUUID
        assertThat(passId, matchesPattern("([0-9]){13}-thorpepark-\\d+-([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})"));
    }

    @Given("^the customer has an expired (\\d+) day pass for Thorpe Park$")
    public void theCustomerHasAnExpiredDurationDayPassForThorpePark(int duration) {
        Long customerId = (Long) scenarioContext.getContext(Context.CUSTOMER_ID);
        long validFrom = todayAtMidnight() - TWO_DAYS_IN_SECONDS;
        String passId = passUtility.addPass("thorpepark", customerId, "London", duration, validFrom);

        scenarioContext.putContext(Context.PASS_ID, passId);
    }

    @When("^the customer renews their pass$")
    public void theCustomerRenewsTheirPass() {
        Long customerId = (Long) scenarioContext.getContext(Context.CUSTOMER_ID);
        String passId = (String) scenarioContext.getContext(Context.PASS_ID);

        String passDetails = passUtility.renewPass(passId, customerId);
        scenarioContext.putContext(Context.PASS_DETAILS, passDetails);
    }

    @Then("^the pass is valid for another (\\d+) days$")
    public void thePassIsValidForAnotherDurationDays(int duration) {
        String passDetails = (String) scenarioContext.getContext(Context.PASS_DETAILS);

        @SuppressWarnings("unchecked")
        Map<String, Object> passMap = (Map<String, Object>) new Gson().fromJson(passDetails, Map.class);

        long validFrom = ((Number) passMap.get("validFrom")).longValue();
        assertThat(validFrom, is(todayAtMidnight()));

        int durationResult = ((Number) passMap.get("durationDays")).intValue();
        assertThat(durationResult, is(duration));
    }

    @Given("^the customer has an? (\\w+) (\\d+) day pass for Thorpe Park in London$")
    public void theCustomerHasAValidDayPassForThorpeParkInLondon(String validExpired, int duration) {
        Long customerId = (Long) scenarioContext.getContext(Context.CUSTOMER_ID);
        long validFrom = "valid".equals(validExpired) ? todayAtMidnight() : todayAtMidnight() - duration * TWO_DAYS_IN_SECONDS;
        String passId = passUtility.addPass("thorpepark", customerId, "London", duration, validFrom);

        scenarioContext.putContext(Context.PASS_ID, passId);
    }

    @When("^Thorpe Park validate the pass for the London attraction$")
    public void thorpeParkValidateThePassForTheLondonAttraction() {
        String passId = (String) scenarioContext.getContext(Context.PASS_ID);
        boolean valid = passUtility.validatePass("thorpepark", passId);

        scenarioContext.putContext(Context.PASS_IS_VALID, valid);
    }

    @Then("^they are told the pass is (\\w+)$")
    public void theyAreToldThePassIsValid(String validInvalid) {
        boolean validity = "valid".equals(validInvalid);

        boolean result = (boolean) scenarioContext.getContext(Context.PASS_IS_VALID);
        assertThat(result, is(validity));
    }

    @Then("^they are able to cancel the pass$")
    public void theyAreAbleToCancelThePass() {
        Long customerId = (Long) scenarioContext.getContext(Context.CUSTOMER_ID);
        String passId = (String) scenarioContext.getContext(Context.PASS_ID);

        passUtility.cancelPass(passId, customerId);
    }

    private long todayAtMidnight() {
        return ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZoneId.of("UTC")).toEpochSecond();
    }
}
