# Address-Book 
Simple application that allows a user to create and query their address books for contacts and friends.

## Design Decisions

### Requirements
You have been asked to develop an address book that allows a user to store (between
successive runs of the program) the name and phone numbers of their friends, with the
following functionality:

- To be able to display the list of friends and their corresponding phone numbers sorted
by their name.
- Given another address book that may or may not contain the same friends, display the
list of friends that are unique to each address book (the union of all the relative
complements). For example given:
    - Book1 = { “Bob”, “Mary”, “Jane” }
    - Book2 = { “Mary”, “John”, “Jane” }
    - The friends that are unique to each address book are:
    - Book1 \ Book2 = { “Bob”, “John” }

### Assumptions
- A user's addressbook will not necessarily contain other users.
- A user's addressbook can be empty
- A user can not have two friends with the same name
  
### Scope
- The application will not implement full solution for authentication. i.e. Only authorisation be implemented. (Access to resources given identity rather than identity management.)
- The application will be a set of apis required to achieve - 
    - POST /user/ - create a user.
    - POST /addressbook/unique/ - check a user's friends against another list
    - GET /addressbook/ - get list of friends and contact numbers
    - POST /address/ - add friend names and contact numbers
    - GET /manage/user/ - management endpoint that gets all users (only use for verifying - since it isn't too straightforward to check data in dynamo local)

### Descoped - in the interest of time
- Full authentication implementation
- Containerisation 
- Live demo - i.e. dev environment with UI
- Microservices Architecture (AWS Lambda)
- UI
- ...long list of otherwise 'essentials'

### Strategy
- Since there is no real requirement for entity-relations in this architecture, DynamoDB(or other noSQL) will be used as the data management system.
- The 'logged in' user's identifier will be sent through in an 'Authorization' header to simulate an authorization flow.
- The endpoints hence will be tied to the context of a user bar the creation of a user.

## Application
This solution will be implemented using spring-boot.

### Prerequisites

- Docker
- Java 1.8

### Build

```sh
./gradlew clean build
```

### Run

```sh
./scripts/run.sh
```

### Documetation of APIs
#### Run first with 
```sh
./scripts/run.sh
```
Then, go to [SwaggerDocumentation](http://localhost:8080/swagger-ui.html#/user-controller) 



## Reflection
- DynamoDb as the storage system of choice wasn't ideal, too much time spent troubleshooting. (Chosen however because of the demand in the job description)
- Would have liked to do exhaustive testing, but ran out of time and decided to finish implementation instead.
