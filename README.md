# About
This project includes simple CRUD operation with one-to-many relation.
Backend has developed by Spring Boot, REST-API. There are base two entities: `User` and `Todo`. Helper entities are: `Lookups` for todo and user.

Aim ise to add/delete/update/list user and todos, also add todos to users.

# Plugins & Extensions

| Plugin | Link | Usage |
| ------ | ------ | ------ |
| Spring -JPA | [https://spring.io/projects/spring-data-jpa][Spring] | Download Spring with dependency JPA and PostgreSL|
| QueryDSL | [http://querydsl.com][QueryDSL] | Unified Queries for Java
| Liquibase | [https://www.liquibase.org][Liquibase] | Tracking changeset and version for database 
| Projection | [https://www.baeldung.com/spring-data-jpa-projections][Projection] | Retrieving data as objects of customized type
| PostgreSQL | [https://www.postgresql.org][PostgreSQL] | Open source database system
| Docker | [https://hub.docker.com/_/postgres][Docker] | Docker image for postgresql
| Heroku | [heroku.coms][Heroku] | Platform as a service, for deploying code and database

[//]: #
   [Spring]: <https://spring.io/projects/spring-data-jpa>
   [QueryDSL]: <http://querydsl.com>
   [Liquibase]: <https://www.liquibase.org>
   [Projection]: <https://www.baeldung.com/spring-data-jpa-projections>
   [PostgreSQL]: <https://www.postgresql.org>
   [Docker]: <https://hub.docker.com/_/postgres>
   [Heroku]: <heroku.com>
   
# Details
* [Users](#users)
* [Lookups](#lookups)
* [Projections](#projections)
* [Validations](#validations)
* [Docker](#docker)
* [Heroku](#heroku)
* [Liquibase](#liquibase)

# Users

I prefered QuerydslPredicateExecutor and paging.
By using QDSL; filtering operations and paging will be executed easily. 
<details>
  <summary>Sample usages</summary>

GET: List of Users:
	
```sh
    GET /api/users
```
	
GET by id:
	
```sh
    GET /api/users/1
```
<details>
<summary>Response</summary>

```json
    "embedded":{ "users": [
            { "id": 1,
                "name": "tuba",
                "email": "tubademir23@gmail.com",
                "gender": FEMALE,
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/users/1"
                    },
                    "appUser": {
                        "href": "http://localhost:8080/api/users/1"
                    },
                    "status": {
                        "href": "http://localhost:8080/api/users/1/status"
                    },
                    "todos": {
                        "href": "http://localhost:8080/api/users/1/todos"
                    }
                }
              }]},
              "page": {
			        "size": 20,
			        "totalElements": 2,
			        "totalPages": 1,
			        "number": 0
			    }
```              
</details>
	
GET name contains `/ay` with ignore case sensitive:	
	
```sh
GET /api/users?name=ay
``` 
	
 In AppUserRepository; the belove code block provides all string field filter:
```java
bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase); 
  ```
  
GET users have `status=1` todos :
	
```sh
GET /api/users?todos.status.id=1
```
GET users have greater than `status=1` todos.
	
```sh
GET /api/users?todos.status.id=gt(1)
```
GET users by filter name, email, todos title by only one field:
	
```sh
GET /api/users?name=tas
```
	
Here the code for this:
```java
bindings.bind(obj.name)
				.first((path, value) -> ExpressionUtils.anyOf(
						obj.name.containsIgnoreCase(value),
						obj.email.containsIgnoreCase(value),
						obj.todos.any().title.containsIgnoreCase(value))); 
```
DELETE:
* During delete of a user, system control if user has any todo.
	
```sh
    DELETE /api/users/{id}
```
Create new record
```json
	POST /api/users
	  {
		  "email":"tuba@gmail.com",
		  "status":"/api/userstatus/1",
		  "name":"tuncay",
		  "gender":"FEMALE"
		}
```
Create todo array for existing user
 
* PUT, POST and PATCH /api/users/{id}/todos
  * `/PUT` deletes todos of the current user and create request array.
  * `/POST` creates new todo entity from request array.
  * `/PATCH` if request todo entity has id info then update else create.
 
 ```json
 [{
	 "title":"first todo",
	"dueOn":"2021-11-01",
	"status":{"id":"1"}
},
{
	 "title":"second todo",
	"dueOn":"2021-12-01",
	"status":{"id":"1"}
}]
 ```
 
</details>

# Lookups 
Status must be defined with table or enum. I prefered database tables and created CrudRepository.

<details>
<summary>Sample usages</summary>

List of todostatus:
 
```sh
    GET /api/todostatus
```
GET by id:
```sh
    GET /api/todostatus/1
```
Response:
	
```json
    "todostatus": [
      {"id": "1", "value": "TO DO"}
    ]
```
Create new record
```json
POST /api/todostatus
{
	 "value" : "PENDING"
}
```

</details>

# Validations

 <details>  
 For validation, I used RepositoryEventHandler [Sample](https://www.baeldung.com/spring-data-rest-events)
 
### For todo and user entity, handlebefore save(put, patch) and create(post) for necessary fields not found status or users or duplicate emails etc..
 
Request
	 
```json 
 {
"email":"tubademir232@gmail.com",
"status":"/api/userstatus/2"}
```
Response

```json 
 {
    "data": [
        {
            "code": "UNPROCESSABLE_ENTITY",
            "field": "name",
            "message": "can't be blank"
        },
        {
            "code": "UNPROCESSABLE_ENTITY",
            "field": "email",
            "message": "can't be blank"
        },
        {
            "code": "UNPROCESSABLE_ENTITY",
            "field": "status",
            "message": "can't be blank"
        }
    ]
}
```
### During deleting a user, control if there is any todos for the user, then throw exception for dependency.

Request

```sh 
    DELETE /api/users/14
```
	 
Response

```json 
 {
    {
    "data": [
        {
            "code": "FAILED_DEPENDENCY",
            "field": "todos",
            "message": "found child records"
        }
    ]
}
```

 Sample invalid entity response here:
</details>

# Projections
<details>

 
[Sample](https://www.baeldung.com/spring-data-jpa-projections), provide simple view of entities.
For example POST sample row with password

 ```json
{
	 "email":"tubademir23@gmail.com",
	 "status":"/api/userstatus/1",
	 "name":"tuba demir",
         "password":"123",
	 "gender":"FEMALE"
}
 ```
 Here password field added, but response password need to be hidden, for this:
 
GET users have email like 23; 
	
 ```sh
/api/users?projection=appUser&email=23
 ```
 
 By using projection; password field be hidden

  ```java
 @Value("#{(target.gender != null ? target.gender.name : '') }")
	String getGenderName();

	@Value("#{(target.status != null ? target.status.value : '') }")
	String getStatusName();

	@Value("#{(target.todos != null ? target.todos.size : 0) }")
	String getTodoCount();
   ```
</details>
	
# Docker

 <details>
 
 For local database usage, I used postgresql and docker.
 under .init folder there is a bat file. With db_up.bat the postgre image will created and with init.sql database and schema will be created.
 
 * JDBC URL:
 
 ```java
 jdbc:postgresql://localhost:5433/mulakat
  ```
 </details>
	
# Heroku

 <details>
 [Look](https://devcenter.heroku.com/articles/deploying-java) how to deploy a java project to heroku.
 
 ```sh
git add .
git commit -m "Added a Procfile."
heroku login
Enter your Heroku credentials.
...
heroku create
Creating arcane-lowlands-8408... done, stack is heroku-18
http://arcane-lowlands-8408.herokuapp.com/ | git@heroku.com:arcane-lowlands-8408.git
Git remote heroku added
git push heroku master
...
-----> Java app detected
...
-----> Launching... done
       http://arcane-lowlands-8408.herokuapp.com deployed to Heroku
 ```
 </details>

# Liquibase

 <details>
 
In project, database service created by docker-compose, then tables, columns and keys created by JPA. In the project I prefer the todo status like a KANBAN. 
Liquibase looks for changeset in the xml and according to context run the sql or commands.
In db_init.xml, include the scripts file, here sample is status.xml
```xml
<include relativeToChangelogFile="true" file="scripts/status.xml"/> 
```

 here id is unique, and if the bloxk execute then liquabase add row on the databasechangesetlog table created by liquabase.
```xml
<changeSet id="2021301203" author="tubademir23" context="init">

<sql>
insert into lookup_todo_status (id, value) values (1, 'Backlog') ON CONFLICT DO NOTHING;
insert into lookup_todo_status (id, value) values (2, 'To Do') ON CONFLICT DO NOTHING;
insert into lookup_todo_status (id, value) values (3, 'In Progress') ON CONFLICT DO NOTHING;
insert into lookup_todo_status (id, value) values (4, 'In Review') ON CONFLICT DO NOTHING;
insert into lookup_todo_status (id, value) values (5, 'Test') ON CONFLICT DO NOTHING;
insert into lookup_todo_status (id, value) values (6, 'Done') ON CONFLICT DO NOTHING;
insert into lookup_todo_status (id, value) values (7, 'To Hold') ON CONFLICT DO NOTHING;
insert into lookup_todo_status (id, value) values (8, 'Cancel') ON CONFLICT DO NOTHING;
insert into lookup_todo_status (id, value) values (9, 'Reopen') ON CONFLICT DO NOTHING;

insert into lookup_user_status (id, value) values (1, 'Active') ON CONFLICT DO NOTHING;
insert into lookup_user_status (id, value) values (2, 'Inactive') ON CONFLICT DO NOTHING;
</sql>
</changeSet>
```

</details>
