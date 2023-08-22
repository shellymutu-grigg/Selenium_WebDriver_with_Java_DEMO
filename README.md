# Selenium_WebDriver_with_Java_DEMO
Demonstration of material learnt from "Selenium WebDriver with Java -Basics to Advanced+Frameworks" Udemy course

## SETUP
This repoistory requires three user environment variables to be setup:
`AMAZON_USERNAME`
`AMAZON_PASSWORD_SUCCESS`
`AMAZON_PASSWORD_FAIL`
for a legitimate account for www.amazon.com.

If using Eclipse IDE please follow these instructions to set up environment variables locally
1. Go to `Run` menu > `Run Configurations`
2. Open `Enviornment` tab
3. Select `Add`
4. Once all three env variables are added select `Apply`

## Dependency versions
 - Maven version: `Apache Maven 3.9.4`
 - Java version: `"17.0.8" 2023-07-18 LTS`
 - Eclipse IDE for Java Developers Version: `2023-06 (4.28.0)`

## Maven commands
- Run smoke test suite: `mvn test -DsuiteXmlFile="src/test/resources/smoke.xml"`
- Run profiles: `mvn test -P<profile-id> e.g. ErrorHandling` e.g `mvn test -PErrorHandling`

## Environment variables 
- View environment variable `Get-ChildItem env:amazon_username`

## Functionality covered
Automation Tests built:
1. E2E Add to Cart Test
	- Login
	- Search for product
	- Add product to cart
	- Remove product from cart
	- Logout
2. Smoke Test Suite
	- Login Failure Test
	- Login Success Test
	- Review Orders Test
3. Error Handling Test
	- Login Failure Test
	
## Evidence supplied
1. Inheritance vs. Abstraction
	- Inhertiance uses extends (can only be used once) e.g. pageObjects extends AbstractComponents
	- Abstraction uses implements (can be used multiple times but the abstract class can not be instantiated) e.g. E2ECreateOrderTest implements IHelper and it must define the method defined in IHelper
2. Framework Design
	- Page Object Approach
3. Performance considerations
	- Stack runtime memory
	- Wait times
	- SQL queries
4. ExtentReports
5. Selenium Grid  ***TO BE COMPLETED***