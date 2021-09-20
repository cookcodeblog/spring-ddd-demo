[TOC]

# Spring DDD Demo


## Env Setup

### Setup MySQL database

- [Setup MySQL database](./scripts/mysql/mysql.md)
- [Init tables and data](./src/main/resources/sql/V0.1__init.sql)

## Order API Tests

### Create Order
Request:
```bash
http :8080/orders id=11 name="pen" price=2.5
```
Response:
```text
HTTP 201 Created
```

```json
{
  "id": 20,
  "orderItems": [
    {
      "id": 31,
      "price": 2.5,
      "productId": 11
    }
  ],
  "price": 2.5,
  "status": "CREATED"
}
```

### Try to create order but missed some fields

Request:
```bash
http :8080/orders id=99 name="noprice"
```

Response:
```text
HTTP 400 Bad Request
```
```json
{
  "errors": [
    {
      "code": "",
      "description": "Price is mandatory"
    }
  ],
  "message": "Validation failed for arguments",
  "status": 400,
  "timestamp": "2021-09-20 11:03:33"
}
```

### Add product for order

Request:
```bash
http :8080/orders/20/products id=22 name="headset" price=128.00
```

Response:
```text
HTTP 200 OK
```

```json
{
  "id": 20,
  "orderItems": [
    {
      "id": 31,
      "price": 2.5,
      "productId": 11
    },
    {
      "id": 32,
      "price": 128.0,
      "productId": 22
    }
  ],
  "price": 130.5,
  "status": "CREATED"
}
```


### Get order

Request:
```bash
http :8080/orders/20
```

Response:
```text
HTTP 200 OK
```

```json
{
  "id": 20,
  "orderItems": [
    {
      "id": 31,
      "price": 2.5,
      "productId": 11
    },
    {
      "id": 32,
      "price": 128.0,
      "productId": 22
    }
  ],
  "price": 130.5,
  "status": "CREATED"
}
```

### Try to get a not found order

Request:
```bash
http :8080/orders/99
```

Response:
```text
HTTP 400 NOT_FOUND
```

```json
{
  "errors": null,
  "message": "Could not find order 99",
  "status": 404,
  "timestamp": "2021-09-20 11:04:57"
}
```


### Delete product from order

Request:
```bash
http DELETE :8080/orders/20/products?productId=22
```

Response:
```text
HTTP 204 No Content
```

### Complete Order

Request:
```bash
http POST :8080/orders/20/complete
```

Response:
```text
HTTP 200 OK
```

```json
{
    "id": 20,
    "orderItems": [
        {
            "id": 31,
            "price": 2.5,
            "productId": 11
        }
    ],
    "price": 2.5,
    "status": "COMPLETED"
}
```

### Try to add product in the COMPLETED order

Request:
```bash
http :8080/orders/20/products id=33 name="book" price=45.00
```

Response:
```text
HTTP 500 Internal Error
```
```json
{
  "errors": null,
  "message": "The order is in completed state.",
  "status": 500,
  "timestamp": "2021-09-20 11:11:11"
}
```

## TODO

- Map JPA entities to DTO // done
- Exception handling // done
- Unify response
- Use MongoDB for NoSQL example
- Health check
- Graceful shutdown // done
- Log to console/STDOUT // done
- Multiple application profiles
- Flyway data migration
- Unit Tests for domain layer
- Integration tests


## References
- [Spring DDD Sample Code via eugenp](https://github.com/eugenp/tutorials/tree/master/ddd)
- [Jackson Annotations Tutorial](https://www.tutorialspoint.com/jackson_annotations/index.htm)
- [Validation in Spring Boot](https://www.baeldung.com/spring-boot-bean-validation)
- [Validation nested models in spring boot](https://stackoverflow.com/questions/41005850/validation-nested-models-in-spring-boot)
