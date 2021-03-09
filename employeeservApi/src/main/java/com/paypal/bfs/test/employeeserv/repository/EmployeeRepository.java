package com.paypal.bfs.test.employeeserv.repository;

import com.paypal.bfs.test.employeeserv.model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeModel, Integer> {
    Optional<EmployeeModel> findById(Integer id);

    @Override
    <S extends EmployeeModel> S save(S s);


}
