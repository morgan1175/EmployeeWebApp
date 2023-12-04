package fr.morgan.webapp.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClient.UriSpec;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;

import fr.morgan.webapp.CustomProperties;
import fr.morgan.webapp.bll.EmployeeService;
import fr.morgan.webapp.bo.Employee;

@Repository
@Primary
public class EmployeeProxy implements EmployeeService {

	@Autowired
	private CustomProperties props;
	
	private WebClient buildWebClient (String url) {
		return WebClient.builder()
					.baseUrl(url)
					.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
					.build();
	}
	
	@Override
	public List<Employee> getEmployees() {
		String getEmployeeUrl = props.getApiUrl();
		
		//Instanciation du WebClient
		WebClient client = buildWebClient(getEmployeeUrl);
		
		//Instanciation de la reponse a la requete
		List<Employee> response = client.get()
				.uri(uriBuilder -> uriBuilder.build())
				.retrieve()
				.bodyToFlux(Employee.class)
				.collectList()
				.block();

		return response;
	}

	@Override
	public Employee getEmployee(Long id) {
		String getEmployeeUrl = props.getApiUrl()+"/"+id;
		
		//Instanciation du WebClient
		WebClient client = buildWebClient(getEmployeeUrl);

		Employee response = client.get()
				.uri(uriBuilder -> uriBuilder.build())
				.retrieve()
				.bodyToMono(Employee.class)
				.block();

		return response;
	}

	@Override
	public void addEmployee(Employee employee) {
		String getEmployeeUrl = props.getApiUrl();
		
		//Instanciation du WebClient
		WebClient client = buildWebClient(getEmployeeUrl);
		
		client.post()
		.uri(uriBuilder -> uriBuilder.build())
		.body(BodyInserters.fromValue(employee))  //Insertion de employee dans le corps de requete
		.retrieve()
		.bodyToMono(void.class)
		.block();

	}

	@Override
	public void deleteEmployee(Long id) {
		String getEmployeeUrl = props.getApiUrl()+"/"+id;
		
		//Instanciation du WebClient
		WebClient client = buildWebClient(getEmployeeUrl);
		
		client.delete()
		.uri(uriBuilder -> uriBuilder.build())
		.retrieve()
		.bodyToMono(void.class)
		.block();
	}

	@Override
	public Employee modifyEmployee(Long id, Employee employee) {
		String getEmployeeUrl = props.getApiUrl()+"/"+id;
		
		//Instanciation du WebClient
		WebClient client = buildWebClient(getEmployeeUrl);
		
		Employee response = client.put()
							.uri(uriBuilder -> uriBuilder.build())
							.body(BodyInserters.fromValue(employee))  //Insertion de employee dans le corps de requete
							.retrieve()
							.bodyToMono(Employee.class)
							.block();
		return response;
	}

	

}
