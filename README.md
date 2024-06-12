# songs-api
REST API excercise with Spring Core features without Spring Boot. 

## Setup
### Clone repository
```sh
git clone git@github.com:vesalukkarila/songs-api.git
```


### Build and run
```sh
mvn package
```
```sh
java -jar target/songsapi-1.0-SNAPSHOT.jar
 ```


# Endpoints

## Hello world to test API
### Request
`GET /`
```sh
curl http://localhost:8080/
```

### Response
String
`"Hello world from SongsController"`


## Get all songs
### Request
`GET /songs`  
#### Parameters
- No parameters.
```sh
curl http://localhost:8080/songs
```
### Response

```json

{
"key":"value"
}

```





