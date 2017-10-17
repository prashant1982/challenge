# challenge

# How to run:
1.	Import the project in eclipse as a Maven based project
2.	‘ChallengeApplication.java’ file has a main method; just run as normal java application
3.	Tomcat port is defaulted to 9090, the port can be changed in application.yml file
4.	Open browser and type ‘http://localhost:9090/swagger-ui.html’; to see/test the API(s); 
[Swagger is configured for REST documentation, you will be able to test the API through Swagger also]

# Brief API Info:
[User Resource]
POST: /api/user/login 	
 	Request [ Enter user and deviceType] || Response [JWT token]
GET: /api/user/details 	
 	Request [Authorization : Bearer <JWT token>] || Response [User details]
DELETE: /api/user/logout
 	Request [Authorization : Bearer <JWT token>]

[Product Resource]
GET: /api/products
	Request [Authorization : Bearer <JWT token>]