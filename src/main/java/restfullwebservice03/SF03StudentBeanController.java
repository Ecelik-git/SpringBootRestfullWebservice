package restfullwebservice03;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SF03StudentBeanController {
	
	private SF03StudentBeanService studentService;
	
	@Autowired
	public SF03StudentBeanController(SF03StudentBeanService studentService) {
		this.studentService = studentService;
	}
	
	@GetMapping(path="api/v1/allStudents")
	public List<SF03StudentBean> getAllStudents(){
		return studentService.listStudents();
	}
	
	@GetMapping(path="api/v1/allStudents/{id}")
	public SF03StudentBean getStdById(@PathVariable Long id) {
		return studentService.selectStudentById(id);
	}
	
	@DeleteMapping(path="api/v1/deleteStudents/{id}")
	public String deleteStdById(@PathVariable Long id) {
		return studentService.deleteStudentById(id);
	}
	
	@PutMapping(path = "api/v1/updateStudents/{id}")
	public ResponseEntity<SF03StudentBean> updateStudent(@PathVariable Long id, @Validated @RequestBody SF03StudentBean student){
		return ResponseEntity.ok(studentService.updateStudent(id, student));		
	}
	
	@PatchMapping(path="api/v1/updateStudentsPartially/{id}")
	public ResponseEntity<SF03StudentBean> updateStudentPartially(@PathVariable Long id, @Validated @RequestBody SF03StudentBean student){
		return ResponseEntity.ok(studentService.updateStPartially(id, student));
	}

	@PostMapping(path = "api/v1/addNewStudent")
	public ResponseEntity<SF03StudentBean> updateStudentPartially(@Validated @RequestBody SF03StudentBean student) throws ClassNotFoundException, SQLException{
		return ResponseEntity.ok(studentService.addStudent(student));		
	}
}




















