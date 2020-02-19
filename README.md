# Leisure Pass Automated Tests
These are the Cucumber automated tests for the LeisurePass application

The feature files can be found in `src/test/resources/com/wjltechservices/features`

All the test code is in `src/test/java/com/wjltechservices`

## To run the tests
Start the LeisurePass application (LeisurePass README for instructions)

Ensure `application.root` and `application.port` in `src/test/resoureces/cucumber.properties` are set to point to where
 the LeisurePass app is running

Then run `gradel test` to kick off the tests