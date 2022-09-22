# Project structure
- config: Spring Configuration classes for all of the modules
- constant: All constant classes
- controller: REST Endpoints
- exception: Exception handler classes
- filter: Request filter handler classes
- model : Data model classes include: dto, entity, enums, exception, request, response
- proxy : Interface communication for external services
- repository: Database access classes
- security: Security class handler
- service: Service business interface/class
- utils: Utility classes
- validation: Spring validation classes

# Project naming

tinvio-<NOUN>-<PROJECT TYPE>
example: tinvio-accounting-service

# Package structure
Within the module, it is recommended to follow the following package structure:
- src/main/java — Contains Java source code packages and classes
- src/main/resources — Contains non-Java resources, such as property files and Spring configuration
- src/test/java — Contains test source code packages and classes
- src/test/resources — Contains non-Java resources, such as property files and Spring configuration
- Avoid using the default package. Make sure that everything (including the entry point) lives in a well-named package. This is to avoid surprises related to wiring and component scan.

# SOLID
Always applied SOLID when design class, interfaces

# Logging Rules
SLF4J (Simple Logging Facade for Java) is preferred. SLF4J is not an implementation of logging framework, it is an abstraction for all the logging frameworks. Logging abstraction is always preferable than logging framework. If we use a logging abstraction, SLF4J in particular, we can migrate to any logging framework we need at the time of deployment without opting for single dependency.
- Never use System.out
- Never use System.err
- Always use SLF4J (annotate at class level with @Slf4j)
- Always use Logback (comes with Spring Boot)
- Don’t use Apache Commons Logging (JCL) aka Jakarta Commons Logging
- Don’t use Java Util Logging (JUL)

# Auto-configuration
Auto-configuration is the part of Spring Boot that makes our code simply work. Auto-configuration attempts to automatically configure our Spring application based on the jar dependencies that we have added. For example, if H2 database is on the classpath, and we have not manually configured any database connection beans, then Spring Boot auto-configures an H2 in-memory database. We need to opt-in to auto-configuration by adding the @EnableAutoConfiguration or @SpringBootApplication annotations to one of our Configuration classes. It is generally recommended that we add one or the other annotation to our primary Configuration class only. Definitely take advantage of Spring Boot’s auto-configuration instead of manually injecting beans or intercepting any requests.

# Keep Controllers Simple and Clean
The job of the Controllers is to coordinate and delegate, rather than to execute actual business logic. Key practices:
- Controllers should be stateless. Controllers are by default singletons and giving them any state can cause massive issues.
- Controllers should not execute business logic but rely on delegation.
- Controllers should deal with the HTTP layer of the application. This should not be passed down to Services.
- Controllers should be oriented around a use case or business capability.

# Focus Services on Business Logic
Build our services around business capabilities/domains/use-cases. Applications with Services called something like AccountService, UserService are much easier to deal with than those with DatabaseService, ValidationService etc. Usually there is a 1-to-1 mapping between Controllers and Services.

# Favor Constructor Injection Instead of @Autowired
- Constructor injection keeps code clean, does not allow us to create circular dependencies between beans.
- Constructor injection makes it easier to instantiate and test the class in a unit test, without needing to configure an application context.
- Best practice for dependency injection is to use Project Lombok: declare a final property of the interface type, and annotate the class using Project Lombok’s @RequiredArgsConstructor. No need to hard code a constructor for that class. Sample:
```shell
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    ....
}
```  

# Global Exception Handling
Spring Boot provides two main ways of handling exception globally:
- Use ResponseEntityExceptionHandler for defining our global exception handling strategy.
- Annotate our Controllers with @ExceptionHandler. This can come useful if we want to be specific in certain cases. Example below:
```shell
@ExceptionHandler({ValidationErrorException.class})
protected ResponseEntity<?> handleValidationException(ValidationErrorException ex) {
    log.error("handleValidationException:", ex.getMessage());
    var response = BaseResponse.builder()
            .status(ex.getStatus())
            .code(ex.getErrorCode())
            .message(ex.getMessage())
            .build();
    return new ResponseEntity<>(response, ex.getHttpStatus());
}
``` 

# Open API Specification Code Documentation
Suggest to use springdoc-openapi library to automate the generation of API documentation. It automatically generates documentation in JSON/YAML and HTML format APIs. This documentation can be completed by comments using swagger-api annotations. This library supports:
- OpenAPI 3
- Spring-boot (v1 and v2)
- JSR-303, specifically for @NotNull, @Min, @Max, and @Size.
- Swagger-ui
- OAuth 2
- Sample annotations:
```shell
@Operation(summary = "Create account")
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created a account"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "401", description = "Authorization denied"),
        @ApiResponse(responseCode = "500", description = "Unexpected system exception"),
        @ApiResponse(responseCode = "502", description = "An error has occurred with an upstream service")
})
@PostMapping(consumes = JSON)
@SneakyThrows
public ResponseEntity createAccount(@Valid @RequestBody CreateAccountRequest request) {
    ...
}
``` 

# Pagination and Sorting with Spring JdbcTemplate
With Spring JdbcTemplate, implementing pagination and sorting is very straightforward! Be sure to use it in case of large data set retrieval, for performance, and for usability. A few steps to follow:

# Bean Validations
Use bean validation annotations to handle validation logic in our code. It makes code clean. We don’t have to have detailed validation logic in our code. If we cannot find any existing bean validation annotation to address our validation needs, we can always create custom validation annotation such as the @CurrencyCode below.
```shell
@NotBlank @Size(max = 100)
private String firstName;

@Valid @NotNull
private Address address;

@CurrencyCode  //custom annotation
private String currency;
``` 

# External Config Inject at Runtime
Utilize Spring Config Server to externalize application configuration files at runtime. This reduces dependency of the code so we don’t have to modify code for a configuration change, which often varies between Prod environment and the lower environments. Externalized config can be changed dynamically without having to repackage the source code.
- When services become more complicated we need centralize configuration. For example: develop config service

# Expose Health Check Endpoints
Use spring-boot-starter-actuator to expose our app’s health check endpoints.
- Actuator is mainly used to expose operational information about the running application — health, metrics, info, dump, env, etc. It uses HTTP endpoints or JMX beans to enable us to interact with it.
- To enable all endpoints: management.endpoints.web.exposure.include=*
- By default, all Actuator endpoints (see list below) are placed under the /actuator path, can be overwritten by management.endpoints.web.base-path.

# Extra Dependencies Overhead
Do not add unnecessary dependencies in our application, which can slow down startup process, and other dependency issues or runtime issues. Ensure to add the right version of our dependencies, do not keep multiple versions of the same dependency library in our application. Reduce the size of the dependencies to make our application run faster.
- JUnit: The de-facto standard for unit testing Java applications.
- Spring Test & Spring Boot Test: Utilities and integration test support for Spring Boot applications.
- AssertJ: A fluent assertion library.
- Hamcrest: A library of matcher objects (also known as constraints or predicates).
- Mockito: A Java mocking framework.
- JSONassert: An assertion library for JSON.
- JsonPath: XPath for JSON.

# Use Lombok
Lombok is a neat library that offers many features to reduce code repetitiveness and boilerplate code. Use Lombok to auto generate java bytecode into our .class files and eliminate code verbosity:
- Use @Data, @NoArgsConstructor, @AllArgsConstructor to get rid of the getters/setters and constructors in our entities and value objects, as well as java boilerplate code such as toString(), equals(), and hashCode().
- Use the Builder pattern to replace factory pattern or marshallers in our code

# Apply Checkstyle
Checkstyle is a static code analysis tool used in software development for checking if Java source code is compliant with specified coding rules. To apply checkstyle to our code, create checkstyle.xml at project root, then add the following plugin in our root pom. In the example below, the config file is checkstyle.xml. The goal check mentioned in the execution section asks the plugin to run in the verify phase of the build and forces a build failure when a violation of coding standards occurs. If we run the mvn clean install command, it will scan the files for violations and the build will fail if any violations are found.
```shell
mvn checkstyle:checkstyle

# Result in \target\site
``` 

# Dapr for Event Driven Programming
Messaging is an important technique to enable flexible communication patterns among loosely coupled microservices. As a distributed runtime, Dapr provides built-in messaging support for developers to design event-driven microservices applications. With Dapr, the infrastructure code is dramatically simplified. It doesn’t need to distinguish between the different message brokers. Dapr provides this abstraction for you. And if needed, you can easily swap out message brokers, simply change the content of the Dapr component file to your desired component configuration, such as changing from Redis stream to Kafka. No need for any code change.

# Schema-Per-Service Database Design
Attempts were made to try “database-server-per-service” approach, only to find out that it’s nearly impossible to run multiple apps in local docker containers with each app having its own database server due to insufficient memory error.

# Database migration
Liquibase for Database Schema Change Management
- Liquibase facilitates database migrations with database-agnostic formats including XML, YAML, and JSON. When we use non-SQL formats for database migrations, Liquibase generates the database-specific SQL for us. It also supports plain old SQL scripts. Liquibase takes care of variations in data types and SQL syntax for different databases. It lets us track, version, and deploy database code with the velocity to match our application code. So we can collaborate easier, deliver faster. Liquibase enables database schema updates automation, eliminates time-consuming back-and-forths between developers and DBAs.

# JUnit 5 for Unit Test
JUnit 5 is a modular and modern take on the JUnit 4 framework. Given its many advantages over JUnit 4, I recommend using JUnit 5 for writing unit tests in your microservices. Many of us are familiar with JUnit 4, for a migration guide on some of the changes between JUnit 4 and JUnit 5

# Use Developer Tools
Add spring-boot-devtools dependency to your microservice to take advantage of development-time features provided by the devtools, such as:
- Automatic restart whenever files on the classpath change.
- LiveReload. Its embedded LiveReload server can be used to trigger a browser refresh when a resource is changed.
- Global Settings. You can configure global devtools settings by adding a file named .spring-boot-devtools.properties to your $HOME folder (note that the filename starts with “.”). Any properties added to this file will apply to all Spring Boot applications on your machine that use devtools.

# Java Coding Style Automation
Having a uniform coding style brings many benefits, to name a few:
- improves software readability
- minimizes software maintenance costs
- speeds up development

Oracle Code Conventions: https://www.oracle.com/java/technologies/javase/codeconventions-contents.html

# Java Source Files
- Setup import * for more than 20 classes only, so reduce conflict for import statement
- Beginning Comments
  All interfaces source files should begin with a c-style comment that a brief description of the purpose of the method. For example:
```shell
/**
 * Create business account
 * @param request business account request
 * @return CreateBusinessAccountResponse
 */
```  





