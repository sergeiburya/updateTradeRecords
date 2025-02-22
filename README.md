
<h1 align="center">Update Trade Data API </h1>

---
This project provides an API for working with trade data and products.
It allows you to load data from files (CSV), update trade data, and retrieve product information.

---
<h2 align="center"> API Functionality </h2>
<h3> 1.Trade Data Records Controller </h3>

* Update Trade Data: - Load trade data from a file (CSV).  
Update trade data using product information.

<h3>2.Product Controller</h3>
* Load Products: - Load products from a file (CSV).  
* Get a list of products: - Returns a list of all products stored in the system.
---

<h2 align="center"> How to start a service </h2>

<h4> 1. Download Upload the project repository to your local machine using Git </h4>

* [URL: Project Repository](https://github.com/sergeiburya/update-trade-data)
* Download the repository to your local machine

<h4> 2. Install docker on your local machine.(if it is not present) </h4>

<h4> 3. Go to the root folder of the project and enter the command in the console and <br> 
      build and run the application:</h4>

```
bash
-----------------------------------------------------------
docker-compose up --build
-----------------------------------------------------------
```
Wait for the Redis database container and API to complete loading.

<h4> 4. To send requests you can use Postman or use Swagger.</h4> 

* [URL: Swagger](http://localhost:8081/swagger-ui.html)<br>

* You can also use the command line to send requests. 


---

<h3 align="center"> How to use the API</h3>

1. Trade Data Controller

* Update trade data,
* Method: POST,
* URL: /tradeData/update

* Parameters: 
  - filePath: (optional) Path to the trade data file.<br>Default: src/main/resources/tradeData.csv.<br>
  - ~~stringData: (optional) String of data in JSON, XML, or text format~~.

Example request:

```
bash
-----------------------------------------------------------------------------------------------
curl -X POST "http://localhost:8081/tradeData/update?filePath=src/main/resources/tradeData.csv"
-----------------------------------------------------------------------------------------------
```

Example response:

```
json
----------------------------------------------------------------------------------------------
[
   {
      "date": "20231001",
      "productName": "Apple",
      "currency": "USD",
      "price": 1.23
   }
]
----------------------------------------------------------------------------------------------
```
---

2.Product API

* Loading products
* Method: POST
* URL: /products/load
* Parameters:
   - filePath: (optional) Path to the product data file.<br> Default: src/main/resources/productData.csv.
   - stringData: (optional) String of data in JSON, XML, or text format.

Example request:

```
bash
----------------------------------------------------------------------------------------------
curl -X POST "http://localhost:8081/products/load?filePath=src/main/resources/productData.csv"
----------------------------------------------------------------------------------------------
```

Example response:

```
text
----------------------------------------------------------------------------------------------
Products loaded successfully from src/main/resources/productData.csv
----------------------------------------------------------------------------------------------
```

* Getting a list of products
* Method: GET
* URL: /products

Example query:
```
bash
----------------------------------------------------------------------------------------------
curl -X GET "http://localhost:8081/products"
----------------------------------------------------------------------------------------------
```

Example response:

```
json
----------------------------------------------------------------------------------------------
[
   {
      "productId": 1,
      "productName": "Apple"
   }
]
```

---

<h2 align="center"> Technology Stack </h2>

* Programming Language: Kotlin (Java17)
* Framework: Spring Boot 3.2.1
* Database: Redis (for product storage)
* Parsers: OpenCSV (for CSV), ~~Jackson (for JSON/XML)~~
* API Documentation: Swagger (OpenAPI 3.0)
* Logging: SLF4J + Logback
* Containerization: Docker

---

<h2 align="center"> Code limitations </h2>

* Date format: The date in the trade data must be in the format yyyyMMdd.<br> Other formats are not supported.

* File types: Only CSV, ~~JSON, and XML files~~ are supported.

* Error handling: Not all errors are handled explicitly.<br> For example, an incorrect file format can lead to unexpected errors.

* Data storage: Products are stored in Redis. <br> If Redis is unavailable, the API will not work.

---
<h2 align="center"> Technical limitations </h2>

* Redis: Redis is used to store products. This means that all product data will be lost after a Redis restart if persistence is not used.

* Scaling: The current code does not support horizontal scaling due to the use of Redis as the only data source.

* File system: The default file paths (src/main/resources/tradeData.csv and src/main/resources/productData.csv) only work in a local environment. For use in other environments, absolute paths must be specified.

* File size limitations: Large files can cause performance issues as the entire file is loaded into memory.

---
<h2 align="center"> API documentation</h2>

<h4>API documentation is available via the Swagger UI after running the application:
[http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html) <h4>

---

<h2 align="center"> Improvements that need to be implemented </h2>

* It is necessary to refine and fix the services for parsing xml, json and string data.
* Perform testing after implementing the services.
* Optimize the loading of large data sets (asynchronous data processing)

...in progress
