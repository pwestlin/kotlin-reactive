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
