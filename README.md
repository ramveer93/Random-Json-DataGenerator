# Random-Json-DataGenerator
A Spring-boot project to generate random json data which can be used to perform load test on an API which again calls an external API and it  returns the data 
in JSON format.
## Use case 1
<img src = "https://github.com/ramveer93/Random-Json-DataGenerator/blob/master/src/main/resources/images/randomData1.jpg"></img>

If you want to perform load test on microservice which calls an external API, when external API is not able to handle load test or not allow to perform these tests or doesn't have handle to return n no of records, instead of calling it , you can configure microservice to call this spring-boot project which will returns the JSON data. 

## Use case 2
You can also generate JSON data of any size and any no of records in it. 

## Requirements
- Java 8 or later
- apache-maven-3.6.0 or later


## Building project
Its an standard maven project , which you can open in eclipse or intellij IDE by importing pom.xml file. Once opened in IDE , just build the project using below maven command

```maven
mvn clean install
```

## Usage
Once maven build is successfull , the application will run on default port of > 8081 . You can change it by changing the entry inside resources/application.properties as below
```Java Properties
server.port=8081
```
Once it is done , you can start the application.
From here you can either go to swagger documentation or use postman to hit the request. Below is the url for swagger documentation

```bash
http://localhost:8081/swagger-ui.html
```

This is how the swagger request looks like
<img src = "https://github.com/ramveer93/Random-Json-DataGenerator/blob/master/src/main/resources/images/swagger_request.png"></img>

### Request explained

#### Query parameters
There are 4 query parameters

- recordCount : No of requested records of that particular json element
- dataType : JSON, as of now just json support, later maybe for csv
- randomValues : If you set it to true, the values of that particular json elements will be random, As of now, it generates just string and numbers random
- xpath : path of that particular json element which you want to repeat
#### Request header
````bash
Content-Type : application/json
xpath : <path of the particular json element>
````
#### Request Body
JSON which you want to repeate no of times. Below is one of the example
```JSON
{
  "array": [
    {
      "ii": true,
      "name": "rrrr",
      "try": [
        {
          "level": 2,
          "repeat": true,
          "again": [
            {
              "name": "ihh",
              "id": 1,
              "level3":[
                {
                  "name":"dds",
                  "class":"ssdsdsdssddsds"
                }
                
                
                ]
            }
          ],
          "josh": "high"
        }
      ],
      "id": 123
    }
  ]
}
````
In this JSON if you want to repeat > again   element theb xpath will be > array.try.again


