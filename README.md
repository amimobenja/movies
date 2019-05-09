# movies
A simple web service that registers users and can do a number of CRUD operations like add movies, edit, fetch and delete. The web service is based on REST API design principles and utilize the following technologies:  
● Java 1.8  
● Spring Boot (2+)  
● PostgresSQL  
● Maven(3.5+) 

# how to setup the project
1. Create database named movies_db
2. Run script located in movies-microservice/scripts/oauth2_stuff.sql
3. Build the maven project and check if test cases are passing.
4. If yes, the project is set up successfully
5. Run INSERT INTO public.users(user_id, first_name, id_no, msisdn, password, registration_date, second_name, status) VALUES (0,'Amimo','26089909','254721506974','$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu','2019-05-06 09:36:40.116','Benja',TRUE);
username - 254721506974
password - password
To get the use to access the apis.


