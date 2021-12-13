The project consists of 2 microservices, customer-authentication service and books-service.
Customer-authentication acts as an OAuth2Client, whereas books-service is a Resource Server which exposes CRUD operations on the Book resource entity.
Books-service has also an implementation of an OAuth2AuthorizationServer.

I have chosen to use H2 database and Thymeleaf for the current project purpose and ease of navigation and implementation. 
Books-client is included as a dependency in customer-authentication pom.xml, in order to use generated DTO classes.
After building the projects with Maven and starting the microservices, the user can navigate in a browser window to http://localhost:8021/index.html 

In order to login, we can use the credentials erika.muller29@gmail.com / pass1 

After the login, CRUD operations on the Book resource are permitted.

Thank you!