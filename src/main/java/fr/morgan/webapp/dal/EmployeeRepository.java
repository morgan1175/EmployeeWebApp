package fr.morgan.webapp.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import fr.morgan.webapp.CustomProperties;
import fr.morgan.webapp.bll.EmployeeService;
import fr.morgan.webapp.bo.Employee;

@Repository
public class EmployeeRepository implements EmployeeService {
		
	@Autowired
	private CustomProperties props;
	private RestTemplate restTemplate = new RestTemplate();


	@Override
	public List<Employee> getEmployees() {
		String baseApiUrl = props.getApiUrl();
		String getEmployeeUrl = baseApiUrl + "/employees";

		ResponseEntity<List<Employee>> response = restTemplate.exchange(getEmployeeUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Employee>>() {
				});

		return response.getBody();
	}

	@Override
	public Employee getEmployee(Long id) {
		String baseApiUrl = props.getApiUrl();
		String getEmployeeUrl = baseApiUrl + "/employees/" + id;

		ResponseEntity<Employee> response = restTemplate.exchange(getEmployeeUrl, HttpMethod.GET, null, Employee.class);

		return response.getBody();
	}

	@Override
	public void addEmployee(Employee employee) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteEmployee(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Employee modifyEmployee(Long id,Employee employee) {
		// TODO Auto-generated method stub
		return null;
	}

}
