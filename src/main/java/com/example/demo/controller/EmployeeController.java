package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResoucreNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins={"http://localhost:3000"})
public class EmployeeController {
	//Autowiring EmployeeRepository.....
	@Autowired
	EmployeeRepository employeeRepository;
	
	@GetMapping("/employees")
	public List<Employee> findAllEmployes() {
		System.out.println("findAllEmployeess.....");
		
		System.out.println("size: "+employeeRepository.findAll().size());
		return employeeRepository.findAll();
	}
	
	@PostMapping("/add-employee")
	public Employee addEmployee(@RequestBody Employee emp)
	{
		System.out.println("addEmployee......");
		return employeeRepository.save(emp);
	}

	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeesById(@PathVariable String id) {
		long id1=Long.valueOf(id);
	Employee employee=employeeRepository.findById(id1).orElseThrow(()->new ResoucreNotFoundException("resource is not found  employee id:  "+id1));
		
		return  ResponseEntity.ok(employee);
	}
	
	
	@PutMapping("/update-employee/{id}")
	public ResponseEntity<Employee> updateEmmployee(@PathVariable Long id,@RequestBody Employee employee) {
	Employee employees=employeeRepository.findById(id).orElseThrow(()->new ResoucreNotFoundException("resource is not found  employee id:  "+id));
	//System.out.println("update employee...");
	employees.setFirstName(employee.getFirstName());
	employees.setLastName(employee.getLastName());
	employees.setEmail(employee.getEmail());
	
	Employee updateEmployee=employeeRepository.save(employees);
	System.out.println("update employee...");

		return ResponseEntity.ok(updateEmployee);
	}
	
	
	@GetMapping("/delete-employees/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteEmployees(@PathVariable Long id) {
		Employee employees=employeeRepository.findById(id).orElseThrow(()->new ResoucreNotFoundException("resource is not found  employee id:  "+id));
		 employeeRepository.delete(employees);
		 Map<String,Boolean> result=new HashMap<>();
		 result.put("delete", true);
		
		return  ResponseEntity.ok(result);
	}
}
