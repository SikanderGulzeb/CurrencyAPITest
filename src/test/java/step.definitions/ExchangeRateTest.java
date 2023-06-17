package step.definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import utils.ConfigReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;



public class ExchangeRateTest {

    private String base_url;
    private String path;
    private String query_param_key;
    private String query_param_from;
    private String apiKey;
    private String invalid_key;
    private Response response;

    @Before
    public void loadDataFromConfig() {
            base_url = ConfigReader.getProperty("BASE_URL");
            path = ConfigReader.getProperty("PATH");
            apiKey = ConfigReader.getProperty("API_KEY");
            invalid_key = ConfigReader.getProperty("INVALID_KEY");
            query_param_key = ConfigReader.getProperty("q_param_api_key");
            query_param_from = ConfigReader.getProperty("q_param_from");
    }

    @Given("I have a valid API key")
    public void loadApiKeyFromConfigFile() {
        apiKey = ConfigReader.getProperty("API_KEY");
    }

    @Given("I have a invalid API key")
    public void provideInvalidKey() {
        apiKey = invalid_key;
    }
    @When("I send a GET request to the API endpoint")
    public void sendGetRequestToEndpoint() {
        response = RestAssured
                .given()
                .queryParam(query_param_key, apiKey)
                .get(base_url + path);
    }

    @When("I send a GET request to the API endpoint with base currency {string}")
    public void sendGETRequestWithBaseCurrency(String baseCurrency) {
        response = RestAssured
                .given()
                .queryParam(query_param_key, apiKey)
                .queryParam(query_param_from, baseCurrency)
                .get(base_url + path);
    }

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Then("the response body should contain the error message {string}")
    public void verifyErrorMessage(String expectedErrorMessage) {
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains(expectedErrorMessage));
    }

    @And("the response body contains exchange rates for {string} currency")
    public void verifyResponseBodyContainsExchangeRates(String baseCurrency) {
        String base = response.jsonPath().get("base");
        Assert.assertEquals("Unexpected base currency", base, baseCurrency);
    }

    @Then("the response body should contains exchange rates for {int} currencies")
    public void verifyResponseBodyContainsExchangeRates(int expectedLength) {
        int resultLength = response.jsonPath().get("results.size()");
        Assert.assertEquals("Unexpected length of result dictionary", expectedLength, resultLength);
    }

    @Then("the response contains today's date")
    public void verifyResponseContainsTodaysDate() {
        String dateTime = response.jsonPath().get("updated");
        String responseDate = dateTime.split(" ")[0];
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = currentDate.format(formatter);
        Assert.assertEquals("Unexpected date in the response", today, responseDate);
    }

    @And("the response should contain exchange rates for {string}")
    public void the_result_should_contain_exchange_rates_for(String currency) {
        LinkedHashMap<String, Object> resultsMap = response.jsonPath().get("results");
        JSONObject resultsObject = new JSONObject(resultsMap);
        Assert.assertTrue("Currency " + currency + " is not present in the response.",
                resultsObject.has(currency));
    }

}