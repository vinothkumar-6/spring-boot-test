# Project Title

Unit Testing Using Mockito and Junit.

## Description

This sample project on Employee CRUD operations which helps to understand the basics of unit testing using Junit and Mockito framework.


## Quick Notes

## Dao

BDD Style (Behavior Driven Development) given_when_then

@SpringBootTest - is used for Integration testing. It will load entire context(controller layer, service layer, repository layer) of all layers.

@DataJpaTest - used to test DAO Layer. It will load only repository components.

@Test  - test block of code

@DisplayName - custom name to give to each test case

@BeforeEach -  This method will be executed before running JUnit test case. 

## Service:

Stubbing - Mocking two methods.

Mockito - Mockito is used to test service layer methods.

Assert throws

BDDMockito.given().willReturn()   is used to stub the internal mock
BDDMockito.WillDoNothing().given is used to return void while stubing

verify(employeeRepository, times(1)).deleteById(1L);  - verify is used to test how many times the method is called, If the method is returning void, then we have use given in then section of bed

mock()  - Mockito provides a static method mock() which is used to mock object.

@ExtendWith(MockitoExtension.class) - used to test service layer

@Mock - We can use @mock if we are mocking objects multiple times.

@InjectMocks - When we want to inject a mocked object into another mocked object we can use @InjectMocks (It creates the mock object of the class and injects the mocks that are marked with the annotations @Mock into it)


## Controller:

@WebMvcTest - is used in controller layer to UnitTest Controller Layer (If use have more than one controller in springboot application, we can only load the necessary controller needed controller )

@MockBean - is spring api bean. It will create context of service layer to mock its services.

ObjectMapper class of Jackson library is used to serialize and deserialize java objects.

## Hamcreast API to test REST API

Hamcreast provides matches function


## JsonPath Library:

JsonPath expressions always refer to a JSON structure in the same way as Path expression are used in combinatination with an xml document

$ is used to get value from Json object



## Spring Webflux:

Spring web flux is a reactive, non-blocking web framework for building modern stable web applications.
It is asynchronous in nature

It has to return either Mono or Flux of type CorePublisher.
Mono object represents a single or empty
Flux Object  can emit 0 to many values
