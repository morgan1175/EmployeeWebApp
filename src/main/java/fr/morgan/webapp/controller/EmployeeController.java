package fr.morgan.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.morgan.webapp.bll.EmployeeService;
import fr.morgan.webapp.bo.Employee;

@Controller
@RequestMapping("/home")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@GetMapping
	public String afficherEmployees(Model model) {
		List<Employee> listeEmployees = employeeService.getEmployees();
		model.addAttribute("employees", listeEmployees);
		return "home";
	}

	@GetMapping("/detail/employee{id}") // url faite dans home.html via thymeleaf
	public String afficherEmployee(@PathVariable("id") Long id, Model model) {
		Employee employee = employeeService.getEmployee(id);
		model.addAttribute("employee", employee);
		return "detail";
	}
	
	@GetMapping("/ajouter")
	public String ajouterEmployee(Model model) {
		model.addAttribute("employee", new Employee());
		return "ajouter";
	}
	
	@PostMapping("ajouter")
	public String traitementAjout(@ModelAttribute Employee employee) {
		employeeService.addEmployee(employee);
		return "redirect:/home";
	}
	
	@PostMapping("/supprimer/{id}")
	public String supprimerEmployee(@PathVariable("id") Long id) {
		employeeService.deleteEmployee(id);
		return "redirect:/home";
	}
	
	@GetMapping("/modifier")
	public String modifierEmployee(@RequestParam("id") long id,Model model) {
		Employee employee = employeeService.getEmployee(id);
		model.addAttribute("employee",employee);
		return "modifier";
	}
	
	@PostMapping("/modifier")
	public String traitementModifier(@RequestParam("id") Long id,@ModelAttribute Employee employee) {
		employeeService.modifyEmployee(id,employee);
		return "redirect:/home";
	}
	
	
	
	

}
