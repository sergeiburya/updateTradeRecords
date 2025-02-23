
<h1 align="center">Update Trade Data API </h1>

---
This project provides an API for working with trade data and products.
It allows you to load data from files (CSV, XML, and JSON), update trade data, and retrieve product information.

---
<h2 align="center"> API Functionality </h2>
<h3> 1.Trade Data Records Controller </h3>

* Update Trade Data: - Load trade data from a file (CSV, XML, and JSON).  
Update trade data using product information.

<h3>2.Product Controller</h3>
* Load Products: - Load products from a file (CSV, XML, and JSON).  
* Get a list of products: - Returns a list of all products stored in the system.
---

<h2 align="center"> How to start a service </h2>

<h3> 1. Download Upload the project repository to your local machine using Git </h3>

* [URL: Project Repository](https://github.com/sergeiburya/updateTradeRecords)
* Download the repository to your local machine

<h3> 2. Install docker on your local machine.(if it is not present) </h3>

<h3> 3. Install In MemoryDB Redis to your local machine.(if it is not present)</h3>>

```
bash
-----------------------------------------------------------
docker run -d -p 6379:6379 redis
-----------------------------------------------------------
```
Wait for the Redis database container to complete loading.

<h3> 4. Launching the Application</h3>

  - <h4> 4.1. Open the project in the development environment and run the application using the main method</h4> 

    [UpdateTradeDataApplication](src/main/kotlin/sb/ua/updatetradedata/UpdateTradeDataApplication.kt)  or...

  - <h4> 4.2. Go to the root folder of the project and enter the command in the console and <br> 
      build and run the application:</h4>

```
bash
-----------------------------------------------------------
docker-compose up --build -d
-----------------------------------------------------------
```


<h4> 4. To send requests you can use Postman or use Swagger.</h4> 

* [URL: Swagger](http://localhost:8080/swagger-ui.html)<br> or

* You can also use the command line to send requests. 

---

<h3 align="center"> How to use the API</h3>

1.Product API

* Loading products
* Method: POST
* URL: /products/load
* Parameters:
  - filePath: (optional) Path to the product data file.<br> Default: src/main/resources/productData.csv.
  
Example request:

```
bash
----------------------------------------------------------------------------------------------
curl -X POST "http://localhost:8080/products/load?filePath=src/main/resources/productData.csv"
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
----------------------------------------------------------------------------------------------
```

2. Trade Data Controller

* Update trade data,
* Method: POST,
* URL: /tradeData/update

* Parameters: 
  - filePath: (optional) Path to the trade data file.<br>Default: src/main/resources/tradeData.csv.<br>
  
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

<h2 align="center"> Technology Stack </h2>

![Java](https://img.shields.io/badge/java:_17-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Spring](https://img.shields.io/badge/spring_boot:3.2.1-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Apache Tomcat](https://img.shields.io/badge/apache%20tomcat-%23F8DC75.svg?style=for-the-badge&logo=apache-tomcat&logoColor=black)
![SLF4J](https://img.shields.io/badge/SLF4J-0078D7?style=for-the-badge&logo=Microsoft-edge&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger:_3.0-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![Edge](https://img.shields.io/badge/CSV-0078D7?style=for-the-badge&logo=Microsoft-edge&logoColor=white)
![XML](https://img.shields.io/badge/XML-0078D7?style=for-the-badge&logo=Microsoft-edge&logoColor=white)
![JSON](https://img.shields.io/badge/JASON-0078D7?style=for-the-badge&logo=Microsoft-edge&logoColor=white)

---

<h2 align="center"> Code limitations </h2>

* Date format: The date in the trade data must be in the format yyyyMMdd.<br> Other formats are not supported.

* File types: Only CSV, JSON, and XML files are supported.

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
[http://localhost:8080/swagger-ui.html](http://localhost:8081/swagger-ui.html) <h4>

---

<h2 align="center"> Improvements that need to be implemented </h2>

* Optimize the loading of large data sets (asynchronous data processing)
