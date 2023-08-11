# Spring-Boot-GraphQL-API-Consumer
It is a spring boot application to demonstrate consuming of a GraphQL API

---
## Approach Used: Using RestTemplate exchange() method

In terms of dealing with APIs, the RestTemplate exchange() method offers a great deal of flexibilty to the developers when dealing with different types of API and MediaTypes.

Generally, we can see that many people makes use of additional GraphQL clients to deal with consuming a GraphQL API but there one other way as well by which we can consume it using the RestTemplate exchange() method provided by Spring Boot.

The process is similar to what we used to do when consuming any other REST API.

The difference comes in **what we set as headers** as they will be used to specify the data format that the client (requester) is willing to accept or provide.

There are 2 important headers which we need to provide for effective communication between clients and servers:

* **Accept**
  * It is included in the HTTP request sent by the client to the server
  * By setting the Accept header, the client tells the server the preferred format for the response data.
  * The server then tries to provide a response in a compatible format based on what the client can accept.
  
  
* **Content-Type**
  * It is included in the HTTP request sent by the client and the response sent by the server
  * It indicates the media type of the data being sent in the request or response body
  * When a client sends data to a server, the Content-Type header specifies the format of the data being sent
  * When the server responds with data, the Content-Type header specifies the format of the data in the response
  
As per this info, we can set these headers as follows in our application when sending the request
```
HttpHeaders headers = new HttpHeaders();
headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
headers.setContentType(MediaType.valueOf("application/graphql"));
```
  