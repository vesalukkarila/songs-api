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
#### Header  
Status: 200 ok  
Content-Type: application/json  
#### Body  
Before any user POST's.   
```json

[
    {
        "id": "1",
        "name": "Thunderstruck",
        "artist": "AC/DC",
        "publishYear": 1990
    }
]

```

## Create a new Song
### Request

### HTTP Request
`POST /songs/{name}/{artist}/{publishYear}`

### Path Parameters
| Parameter   | Type   | Description                      |
|-------------|--------|----------------------------------|
| `name`      | String | The name of the song.            |
| `artist`    | String | The artist of the song.          |
| `publishYear` | Integer | The year the song was published. |

### Example Request
```sh
curl -X POST http://localhost:8080/songs/Rokkibaby/Frederik/1975
```
### Response
#### Success
Status: 200 ok  
Content-Type: application/json  

```json
{
    "id": "2",
    "name": "Rokkibaby",
    "artist": "Frederik",
    "publishYear": 1975
}
```
#### Error
Status: 400 something..   
... to be added later once validation implemented


