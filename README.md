# Pagination Example with Spring Boot

Small application demonstration how to paginate results without using any backend with Spring Boot.

## How it Works
Simply put, application uses paging state to determine where to start reading results from. If no paging state is given,
application will read the result set starting from the beginning.

A token is maintained for each user, works similar as session, this will maintain current index for page of users.

Two main request url are used for Parent and Child details.

For Parent details:
    
Base url: 
```
localhost:8080/demoPagination/parent
```
Next page Url
```
localhost:8080/demoPagination/parent/next?order=des&pagingState=7bfd74b7-4847-449c-abff-c7c8937b0740
```
Previous page Url
```
localhost:8080/demoPagination/parent/previous?order=des&pagingState=7bfd74b7-4847-449c-abff-c7c8937b0740
```
NOTE: we can pass two request parameter order and pageState

For Child details:
Base url:
```
localhost:8080/demoPagination/child?parentId=5
```