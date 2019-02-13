How to register the Spring Boot Micro service application into the Eureka Server. Before registering the application, please make sure Eureka Server is running on the port 8761 or first build the Eureka Server and run it.


To register the Spring Boot application into Eureka Server we need to add the following configuration in our application.properties file or application.yml file and specify the Eureka Server URL in our configuration.

eureka:
   client:
      serviceUrl:
         defaultZone: http://localhost:8761/eureka
      instance:
      preferIpAddress: true
spring:
   application:
      name: eurekaclient
      
      
Actuator
Spring Boot Actuator provides secured endpoints for monitoring and managing your Spring Boot application. By default, all actuator endpoints are secured.

we need to disable the security for actuator endpoints. In yaml file.
management:
   security:
      enabled: false

If you want to use the separate port number for accessing the Spring boot actutator endpoints add the management port number in application.properties file.

management.port = 9000

else it will enable on default port 8080.

Note: Management security enabled settings are deprecated and now its managed by spring security bean. To enable all the endopints use below settings

management:
  endpoints:
    web:
      exposure:
        include:
        - '*'
Default allowed : info,health like

http://localhost:8080/actuator/info
http://localhost:8080/actuator/health              

/metrics
To view the application metrics such as memory used, memory free, threads, classes, system uptime etc.
/env
To view the list of Environment variables used in the application.
/beans
To view the Spring beans and its types, scopes and dependency.
/health
To view the application health
/info
To view the information about the Spring Boot application.
/trace
To view the list of Traces of your Rest endpoints.

