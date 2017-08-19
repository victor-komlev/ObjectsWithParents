# Objects With Parents

To build with gradle launch
```groovy
gradlew clean build
```
Then
```groovy
java -jar build/libs/object-refercened-by-object-1.0-SNAPSHOT.jar
```

Server will be started on port 8180. Send POST request to _http://localhost:8189/api/testobject_ with follwong content
```json

{
  "object" : [
    {
      "id" : "1234",
      "value" : "P1"
    },
    {
      "id" : "1235",
      "value" : "P12",
      "dependent_id" : "1234"
    },
   {
      "id" : "1236",
      "value" : "P13",
      "dependent_id" : "1235"
    },
   {
      "id" : "1236",
      "value" : "P2"
    },
   {
      "id" : "1237",
      "value" : "P21",
      "dependent_id" : "1236"
    },
   {
      "id" : "1238",
      "value" : "P22",
      "dependent_id" : "1236"
    }
  ]
} 
```

Available API:

- **GET /api/testobject/{id}**
Parameters : hierarchy - ASC/DESC, depth - depth of hierarchy
- **POST /api/testobject** 
Parameters : none