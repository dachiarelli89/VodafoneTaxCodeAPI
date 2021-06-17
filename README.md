# TAX Code API
#### Vodafone Code Assessment - by Davide Chiarelli

[![Build Status](https://api.travis-ci.org/dachiarelli89/VodafoneTaxCodeAPI.svg?branch=master)](https://sonarcloud.io/dashboard?id=dachiarelli89_VodafoneTaxCodeAPI) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=dachiarelli89_VodafoneTaxCodeAPI&metric=coverage)](https://sonarcloud.io/dashboard?id=dachiarelli89_VodafoneTaxCodeAPI) [![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=dachiarelli89_VodafoneTaxCodeAPI&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=dachiarelli89_VodafoneTaxCodeAPI) [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=dachiarelli89_VodafoneTaxCodeAPI&metric=bugs)](https://sonarcloud.io/dashboard?id=dachiarelli89_VodafoneTaxCodeAPI)

This is an application that exposes a JSON API over HTTP that allows a client to extrapolate data from a
tax code string.
It is able to parse italian tax codes using algorithm provided at this link: https://it.wikipedia.org/wiki/Codice_fiscale
---
# Prerequisites
In order to thes the code, you should have the following tools:
- JDK 11
- [GIT](https://git-scm.com/)

# Instructions for testing
The following script will help you to clone the repo and launch JUnit tests
```sh
git clone https://github.com/dachiarelli89/VodafoneTaxCodeAPI.git

# On MacOS or Linux
cd VodafoneTaxCodeAPI

./mvnw clean install


# On Microsoft
dir VodafoneTaxCodeAPI

./mvnw.cmd clean install
```
# Instructions to run the APP locally

If you prefer to run the application, use the following command:
```sh
# On MacOS or Linux
./mvnw spring-boot:run

# On Microsoft
./mvnw.cmq spring-boot:run
```
The application should be reachable on 8080 port. 
Once the application is started, the API documentation could be read at [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/), where is also possible to execute directly some calls to the APIs exposed via the Swagger Web UI. 


# Instructions to use HEROKU instance of the APP 

If you prefer to use the API deployed on an HEROKU instance you could use these links:
- API endpoint: [https://vodafone-tax-code-api.herokuapp.com/](https://vodafone-tax-code-api.herokuapp.com/)
- Swagger UI: [https://vodafone-tax-code-api.herokuapp.com/swagger-ui/](https://vodafone-tax-code-api.herokuapp.com/swagger-ui/)

# Security 
All services has a JWT token authentication, you could use /auth/token API to get a JWT token.
Then you could call secured API adding the previous token as HTTP header paramenter:
```
Authorization: Bearer {TOKEN}
```

The following user are mocked for authentication:

| Username | Password |
| :---: | :---: |
|user1|pass1|
|user2|pass2|
|user3|pass3|

