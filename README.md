# Project Title

Unit Testing Using Mockito and Junit

## Description

This sample project demonstrates Employee CRUD operations and serves as a guide to understanding the basics of unit testing using JUnit and Mockito frameworks.

## Table of Contents

- [Dao Testing](#dao-testing)
- [Service Testing](#service-testing)
- [Controller Testing](#controller-testing)
- [Additional Libraries and Tools](#additional-libraries-and-tools)

## Dao Testing

In this section, we focus on testing the DAO layer of the application.

- **BDD Style**: We utilize the given_when_then approach for behavior-driven development.
- **Annotations**:
    - `@SpringBootTest`: Used for integration testing, loading the entire application context.
    - `@DataJpaTest`: Specifically for testing the DAO layer, loading only repository components.
- **Test Execution**:
    - `@Test`: Defines a test block of code.
    - `@DisplayName`: Provides custom names to test cases.
    - `@BeforeEach`: Method executed before each JUnit test case.

## Service Testing

This section deals with testing the Service layer using Mockito framework.

- **Mocking Techniques**:
    - Stubbing: Mocking methods using `given().willReturn()` and `WillDoNothing().given()` for void methods.
    - `@Mock` and `@InjectMocks`: Annotations for mocking objects and injecting mocks into other mocks respectively.
- **Verification**: Using `verify()` to check method invocations.
- **Annotations**:
    - `@ExtendWith(MockitoExtension.class)`: Used to test service layer.

## Controller Testing

Here, we focus on testing the Controller layer.

- **Annotations**:
    - `@WebMvcTest`: Unit testing for the Controller layer.
    - `@MockBean`: Creates a mock context of the service layer to mock its services.
- **Object Serialization/Deserialization**: Utilizes ObjectMapper class of Jackson library.

## Additional Libraries and Tools

- **Hamcrest API**: Utilized for testing REST API with its `matches` function.
- **JsonPath Library**: Used for traversing and querying JSON structures.
- **Spring Webflux**: An asynchronous, non-blocking web framework for building modern web applications. Returns `Mono` for single or empty values and `Flux` for emitting multiple values.

## Conclusion

This README.md file provides an overview of the project structure and testing methodologies employed. For detailed usage and implementation, refer to the respective sections above.
