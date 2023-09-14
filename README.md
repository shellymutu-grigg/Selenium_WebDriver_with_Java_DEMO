# Selenium_WebDriver_with_Java_DEMO
Demonstration of material learnt from "Selenium WebDriver with Java -Basics to Advanced+Frameworks" Udemy course

## SETUP
This repository requires three user environment variables to be setup:
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
- Run profiles: `mvn test -P<profile-id> e.g. LoginFailure` e.g `mvn test -PLoginFailure`

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
	- Login Success Test
	- Search Orders Test
	- E2E Add to Cart Test 
3. Login Failure Test
	- Login Failure Test
4. Selenium Grid Tests
	- Selenium Grid Google Test
	- Selenium Grid SMG Test
	
## Evidence supplied
1. Inheritance vs. Abstraction
	- Inheritance uses extends (can only be used once) e.g. pageObjects extends AbstractComponents
	- Abstraction uses implements (can be used multiple times but the abstract class can not be instantiated) e.g. E2ECreateOrderTest implements IHelper, and it must define the method defined in IHelper
2. Framework Design
	- Page Object Approach
3. Performance considerations
	- Stack runtime memory
	- Wait times
	- SQL queries
4. ExtentReports
5. Selenium Grid 

## Selenium Grid
### Using Docker - https://hub.docker.com/r/selenium/hub *- NOT CURRENTLY WORKING 14/09/23*
- Install docker `https://docs.docker.com/get-docker/`
- Pull the following images:
  - `docker pull selenium/hub`
  - `docker pull selenium/node-chrome`
  - `docker pull selenium/node-firefox`
  - `docker pull selenium/node-edge`
  - `docker network create grid`
  - `docker run -d -p 4442-4444:4442-4444 --net grid --name selenium-hub selenium/hub`
  - `docker run -d --net grid -e SE_EVENT_BUS_HOST=selenium-hub \                                                                                          ok  at 18:04:53  
	--shm-size="2g" \
	-e SE_EVENT_BUS_PUBLISH_PORT=4442 \
	-e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 \
	selenium/node-chrome`
  - `docker run -d --net grid -e SE_EVENT_BUS_HOST=selenium-hub \                                                                                          ok  at 18:05:14  
	--shm-size="2g" \
	-e SE_EVENT_BUS_PUBLISH_PORT=4442 \
	-e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 \
	selenium/node-edge`
  -`docker run -d --net grid -e SE_EVENT_BUS_HOST=selenium-hub \                                                                                          ok  at 18:05:25  
	--shm-size="2g" \
	-e SE_EVENT_BUS_PUBLISH_PORT=4442 \
	-e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 \
	selenium/node-firefox:`
  - `docker stop <CONTAINER_ID>` repeat for all containers started
  - `docker rm <CONTAINER_ID>` repeat for all containers started
- `docker network rm grid`

### Using selenium-server-<VERSION>.jar *- NOT CURRENTLY WORKING AFTER LATEST WINDOWS PATCH 14/09/23*
- In a terminal window navigate to the folder with `selenium-server.jar` file:  `C:\Users\<USER_NAME>\development\downloads`
- Execute following command: `java -jar selenium-server-<VERSION>.jar hub`
- Verify Hub is running on local machine: open browser and navigate to: `http://localhost:4444/` or `http://192.168.50.207:4444`
- Open a new terminal window 
- window navigate to the folder with `selenium-server-<VERSION>.jar` file:  `C:\Users\<USER_NAME>\development\downloads`
- Execute following command: `java -jar selenium-server-<VERSION>.jar  node --detect-drivers true --selenium-manager true`
- Refresh browser and the node should be registered `URI:http://192.168.50.207:5555`
- Add `selenium-server-<VERSION>.jar` file to project build path
- Create Tests
- Create .xml to run Tests
- Create profile in pom.xml
- Execute profile via command line e.g. `mvn test -PGrid`