package com.paypal.bfs.test.employeeserv.model;

import com.paypal.bfs.test.employeeserv.model.converter.AddressConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="employee")
public class EmployeeModel {
    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="first_name")
    private String firstName;


    @Column(name="last_name")
    private String lastName;


    @Column(name="date_of_birth")
    private String dateOfBirth;


    @Convert(converter = AddressConverter.class)
    @Column(name="address")
    private Map<String, String> address ;


}
