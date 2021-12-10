package fr.insa.mas.StudentInfoService.resource;

import java.util.List;
import java.util.Arrays;

import org.springframework.web.bind.annotation.*;

import fr.insa.mas.StudentInfoService.model.StudentInfo;

@RestController
@RequestMapping("/student")
public class StudentInfoResource {
	
	private List<StudentInfo> etudiants = Arrays.asList(
			new StudentInfo("28-04-1999", "Faure", "Paul"),
			new StudentInfo("16-05-2000", "Giusti", "Laura"),
			new StudentInfo("23-03-2000", "Duluc", "Célia"),
			new StudentInfo("26-10-1999", "Garcia", "Helena")
			);
	
	@GetMapping("{idStudent}")
	public StudentInfo getStudent(@PathVariable("idStudent") int id) {
		System.out.println("C'est moi");
		return etudiants.get(id);
	}

}
