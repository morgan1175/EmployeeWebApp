package fr.morgan.webapp.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.morgan.webapp.bo.Employee;

@Service
public interface EmployeeService {

	List<Employee> getEmployees();
	Employee getEmployee(Long id);
	void addEmployee(Employee employee);
	void deleteEmployee(Long id);
	Employee modifyEmployee(Long id,Employee employee);
}
