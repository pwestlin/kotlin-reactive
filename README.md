# Kotlin Reactive
I'm playing around with Reactive-support in Kotlin.  

The application uses MonoDB for storing Users and presents a REST-API for access.

## Run it
### MongoDB
The application requires a running MongoDB.  
Docker to the rescue:

    docker run --name some-mongo -d mongo

### Application
    gradle bootrun 

### Try it
Use curl or [HTTPie](https://httpie.org/).

All users:

    curl -i -v http://localhost:8080/users
    http http://localhost:8080/users

One user:

    curl -i -v http://localhost:8080/users/{id}
    http http://localhost:8080/users/{id}

Add a new user:

    curl -i -v -d '{"name":"Knaus"}' -H "Content-Type: application/json" -X POST http://localhost:8080/users
    http POST http://localhost:8080/users name=Knaus 

## TODO
Add tests