package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.model.EmployeeModel;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.response.SaveResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public ResponseEntity<Employee> employeeGetById(String id) {
        Optional<EmployeeModel> employee= null;
        try {
             employee = employeeRepository.findById(Integer.valueOf(id));
        }catch (NumberFormatException ne){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(employee.isPresent()) {
            EmployeeModel employeeModel = employee.get();
            Employee emp = getEmployee(employeeModel);
            return new ResponseEntity<Employee>(emp, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private Employee getEmployee(EmployeeModel employeeModel) {
        Employee emp = new Employee();
        emp.setFirstName(employeeModel.getFirstName());
        emp.setId(employeeModel.getId());
        emp.setLastName(employeeModel.getLastName());

        emp.setLastName(employeeModel.getLastName());
        emp.setDateOfBirth(employeeModel.getDateOfBirth());
        Map<String, String> hm = employeeModel.getAddress();
        Address address = new Address();
        address.setCity(hm.get("city"));
        address.setLine1(hm.get("line1"));
        address.setLine2(hm.get("line2"));
        address.setCountry(hm.get("country"));
        address.setState(hm.get("state"));
        address.setZipCode(hm.get("zip_code"));
        emp.setAddress(address);
        return emp;
    }

    @Override
    public ResponseEntity<SaveResponse> createEmployee(Employee employeeObj) {
        SaveResponse saveResponse  = validateResource(employeeObj);
        if(!saveResponse.isSuccess()){
            return new ResponseEntity<>(saveResponse, HttpStatus.BAD_REQUEST);
        }
        EmployeeModel employeeModel = setEmployeeModel(employeeObj);

        if(employeeRepository.existsById(employeeModel.getId())){
            saveResponse = new SaveResponse(false, " resource already exists");
            return new ResponseEntity<SaveResponse>(saveResponse, HttpStatus.CONFLICT);
        }

        employeeRepository.save(employeeModel);
        return new ResponseEntity<SaveResponse>(saveResponse, HttpStatus.OK);
    }

    private SaveResponse validateResource(Employee employeeModel) {
        SaveResponse saveResponse = new SaveResponse(true, " resource created");

        if(StringUtils.isEmpty(employeeModel.getId())){
            saveResponse.setSuccess(false);
            saveResponse.setMessage("id can not be empty");
            return saveResponse;
        }

        if(StringUtils.isEmpty(employeeModel.getFirstName())){
            saveResponse.setSuccess(false);
            saveResponse.setMessage("firstName can not be empty");
            return saveResponse;
        }
        if(StringUtils.isEmpty(employeeModel.getLastName())){
            saveResponse.setSuccess(false);
            saveResponse.setMessage("last Name can not be empty");
            return saveResponse;
        }
        if(StringUtils.isEmpty(employeeModel.getDateOfBirth())){
            saveResponse.setSuccess(false);
            saveResponse.setMessage("DateOfBirth can not be empty");
            return saveResponse;
        }
        if(StringUtils.isEmpty(employeeModel.getAddress().getCity())){
            saveResponse.setSuccess(false);
            saveResponse.setMessage("city can not be empty");
            return saveResponse;
        }
        if(StringUtils.isEmpty(employeeModel.getAddress().getLine1())){
            saveResponse.setSuccess(false);
            saveResponse.setMessage("line1  can not be empty");
            return saveResponse;
        }
        if(StringUtils.isEmpty(employeeModel.getAddress().getCountry())){
            saveResponse.setSuccess(false);
            saveResponse.setMessage("country  can not be empty");
            return saveResponse;
        }
        if(StringUtils.isEmpty(employeeModel.getAddress().getState())){
            saveResponse.setSuccess(false);
            saveResponse.setMessage("STATE  can not be empty");
            return saveResponse;
        }
        if(StringUtils.isEmpty(employeeModel.getAddress().getZipCode())){
            saveResponse.setSuccess(false);
            saveResponse.setMessage("getZipCode  can not be empty");
            return saveResponse;
        }

        return saveResponse;
    }

    public EmployeeModel setEmployeeModel(Employee employeeObj) {
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setId(employeeObj.getId());
        employeeModel.setFirstName(employeeObj.getFirstName());
        employeeModel.setLastName(employeeObj.getLastName());
        employeeModel.setDateOfBirth(employeeObj.getDateOfBirth());
        Address address = employeeObj.getAddress();
        Map<String, String> hm = new HashMap<>();
        hm.put("city", address.getCity());
        hm.put("line1", address.getLine1());
        hm.put("line2", address.getLine2());
        hm.put("country", address.getCountry());
        hm.put("state", address.getState());
        hm.put("zip_code", address.getZipCode());

        employeeModel.setAddress(hm);
        return  employeeModel;
    }
}
