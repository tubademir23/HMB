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

# 📝 Lookups 
Status must be defined with table or enum. I prefered database tables and created CrudRepository.

<details>
  <summary>Sample usages</summary>

* GET: List of todostatus:
    * GET /api/todostatus
* GET by id:
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

Status must be defined with table or enum. I prefered database tables and created CrudRepository.
##Sample usages

# 📝 Todos 
