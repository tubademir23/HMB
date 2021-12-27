# About
This project includes simple CRUD operation with one-to-many relation.
With REST-API the backend developed by Spring Boot.
Base entities: User and Todo. Aim ise to add/delete/update/list user and todos, also add todo to users.

# 📝 Get spring empty project
Bu using [Spring.io](https://start.spring.io), firstly create empty spring boot application with dependencies: PostgreSql and JPA
	With Hibernate; during application starts, entities will be created on database tables if necessary. Key: hibernate-auto
	
# 📈 QueryDSL 
Here is sample usages for this library: [QueryDSL](https://www.baeldung.com/rest-api-search-querydsl-web-in-spring-data-jpa)

# 📈 RestConfig 
Resources will be start `/api` and see repositories.

# API Usages
* [Lookups](#lookups)
* [Users](#users)
* [Todos](#todos)
* [Projections](#projections)

# 📝 Lookups 
Status must be defined with table or enum. I prefered database tables and created CrudRepository.

<details>
  <summary>Sample usages</summary>

### GET: List of todostatus:
    * GET /api/todostatus
### GET by id:
    * GET /api/todostatus/1
    
* Sample Results:

    "todostatus": [
      {"id": "1", "value": "PENDING"}
    ]

* Create new record
	POST /api/todostatus
	
	{
	  "value" : "PENDING"
	}
* POST with id: if exists update else create

```https
{
  "id":1,
  "value" : "NOT PENDING"
}
```
</details>

# 📝 Users

Repository for users, I prefered QuerydslPredicateExecutor and paging.
By using QDSL; filtering operations and paging will be execued easily. 
<details>
  <summary>Sample usages</summary>

### GET: List of Users:
    * GET /api/users
### GET by id:
    * GET /api/users/1
    
-- Sample Results:

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
              
### GET name contains `/ay` with no case :			
    * GET /api/users?name=ay
 
 In AppUserRepository; the belove code block provides all string field filter:
 
```java
bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase); 
  ```
  
### GET users have `status=1` todos :			
    * GET /api/users?todos.status.id=1
 
### Create new record
	POST /api/users
	
	  {
		  "email":"tuba@gmail.com",
		  "status":"/api/userstatus/1",
		  "name":"tuncay",
		  "gender":"FEMALE"
		}

* POST with id: if exists update else create

### Create todo array for existing user
 
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

# 📝 Todos
 

# 📝 Projections 
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
 
 ### GET users have email like 23; http://localhost:8080/api/users?projection=appUser&email=23
 By using projection; password field be hidden
 