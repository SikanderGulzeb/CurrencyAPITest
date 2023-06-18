# Currency Exchange API Testing - Readme

This readme provides instructions on how to execute and run the currency exchange API tests using Cucumber, Gradle, and the FastForex API.

### Prerequisites

Before running the tests, ensure you have the following:

* A valid API key from FastForex to authenticate your requests.
* JDK installed on your machine. 
* Gradle build tool installed on your machine.


### Getting Started

To get started with the API tests, follow the steps below:

* Clone or download the project from the repository.
* Open the project in your preferred IDE.
* Navigate to below directory

    
        src/test/resources

* Open the below config file and replace the <API_KEY> placeholder with your actual API key obtained from FastForex.


        config.properties

### Executing the Tests

You can execute the tests using either Gradle build.

#### Using Gradle build:

* Open a terminal or command prompt.
* Navigate to the root directory of the project.
* Run the following command to execute the tests:



      ./gradlew clean test

The tests will be executed, and the results will be displayed in the console.



### Test Report

Once the test execution completes, a test report will be generated in the below directory. 

cucumber-html-report
    
        build/reports/cucumber-html-reports

cucumber.json
    
        build/cucumber-reports


### API Endpoint Details

The tests are designed to validate the currency exchange functionality using the FastForex API. The API endpoint used is:

#### Endpoint:

        https://api.fastforex.io/

Please refer to the FastForex API documentation for more information on the available endpoints and request parameters.






