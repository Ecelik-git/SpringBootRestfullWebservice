package restfullwebservice03;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SF03StudentBeanService {

	private SF03StudentBeanRepository studentRepo;

	@Autowired
	public SF03StudentBeanService(SF03StudentBeanRepository studentRepo) {
		this.studentRepo = studentRepo;
	}
	
	public List<SF03StudentBean> listStudents(){
		return studentRepo.findAll();
	}
	
	public SF03StudentBean selectStudentById(Long id) {
		if(!studentRepo.findById(id).isPresent()) {
			return new SF03StudentBean();
		}else {
			return studentRepo.findById(id).get();
		}
		
	}
	
	public String deleteStudentById(Long id) {
		if(studentRepo.findById(id).isPresent()) {
			studentRepo.deleteById(id);
			return "Student with the id of "+id+" is deleted successfully";
		}else {
			throw new IllegalStateException(id+" doesn't exist");
		}
	}
	
	public SF03StudentBean updateStudent(Long id, SF03StudentBean student) {
		SF03StudentBean existingStudent = studentRepo.findById(id)
				.orElseThrow(()->new IllegalStateException("Does not exist"));
		String name = existingStudent.getName();
		if(student.getName()==null) {
			existingStudent.setName(null);
		}else if(existingStudent.getName()==null) {
			existingStudent.setName(student.getName());
		}else if(!name.equals(student.getName())) {
			existingStudent.setName(student.getName());
		}
		
		return studentRepo.save(student);
	}
	
}












