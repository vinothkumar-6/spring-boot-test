package com.getajob.springboottest.repository;

import com.getajob.springboottest.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);


    // CUSTOM  query with index parameters
    @Query("select e from Employee e where e.firstName =?1 and e.lastName=?2")
    Employee findByJPQL(String firstName, String lastName);

    // CUSTOM query HQL query with named parameters
    @Query("select e from Employee e where e.firstName =:firstName and e.lastName=:lastName")
    Employee findByJPQLNamedParam(@Param("firstName")String firstName, @Param("lastName") String lastName);


    //Custom query for native sql index statement
    @Query(value = "select * from employees e where e.first_name=?1 and e.last_name=?2", nativeQuery = true)
    Employee findByNativeSQL(String firstName, String lastName);

    //custom query for native sql with named parameters
    @Query("select e from Employee e where e.firstName=:firstName and e.lastName=:lastName")
    Employee findByNativeNamedParam(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
