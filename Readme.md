#Address-Book 

##Design Decisions

###Requirements
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

###Assumptions
- A user's addressbook will not necessarily contain other users.
  
###Scope
- The application will not implement full solution for authentication. i.e. Only authorisation be implemented. (Access to resources given identity rather than identity management.)
- The application will be a set of apis required to achieve - 
    - POST /users/ - creation of users. (with an embedded addressbook (list of friend names and contact numbers))
    - POST /friends/unique/ - endpoint to check a user's addressbook against a provided list of friends
    - GET /addressbook/ - get list of friends and contact numbers


###Descoped - in the interest of time
- Full authentication implementation
- Containerisation 
- Live demo - i.e. dev environment
- Microservices Architecture
- UI
- ...long list of otherwise 'essentials'

###Strategy
- Since there is no requirement for entity-relations in this architecture, DynamoDB(or other noSQL) will be used as the data management system.
- The 'logged in' user's identifier will be sent through in an 'Authorization' header.
- The endpoints hence will be tied to the context of a user bar the creation of a user.

##Application
This solution will be implemented using spring-boot.

###Prerequisites

###Build

###Run
