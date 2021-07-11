package restfullwebservice03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SF03StudentBeanService {

	private SF03StudentBeanRepository studentRepo;

	@Autowired
	public SF03StudentBeanService(SF03StudentBeanRepository studentRepo) {
		this.studentRepo = studentRepo;
	}

	public List<SF03StudentBean> listStudents() {
		return studentRepo.findAll();
	}

	public SF03StudentBean selectStudentById(Long id) {
		if (!studentRepo.findById(id).isPresent()) {
			return new SF03StudentBean();
		} else {
			return studentRepo.findById(id).get();
		}

	}

	// For invalid ids, the method will throw IllegalStateException with "id does
	// not exist" message
	// For valid ids, student with the id will be removed from the database and you
	// will get a success
	// message on the console like "Student whose id is 'id' is successfully
	// deleted"
	public String deleteStudentById(Long id) {
		if (studentRepo.findById(id).isPresent()) {
			studentRepo.deleteById(id);
			return "Student with the id of " + id + " is deleted successfully";
		} else {
			throw new IllegalStateException(id + " doesn't exist");
		}
	}

	// This method is for fully update
	public SF03StudentBean updateStudent(Long id, SF03StudentBean newStudent) {

		SF03StudentBean existingStudentById = studentRepo.findById(id)
				.orElseThrow(() -> new IllegalStateException(id + " id does not exist.."));

		String name = existingStudentById.getName();
		if (newStudent.getName() == null) {
			existingStudentById.setName(null);
		} else if (existingStudentById.getName() == null) {
			existingStudentById.setName(newStudent.getName());
		} else if (!name.equals(newStudent.getName())) {
			existingStudentById.setName(newStudent.getName());
		}

		Optional<SF03StudentBean> existingStudentByEmail = studentRepo
				.findSF03StudentBeanByEmail(newStudent.getEmail());

		if (existingStudentByEmail.isPresent()) {
			throw new IllegalStateException("Email is taken, cannot be used again...");
		} else if (newStudent.getEmail() == null) {
			throw new IllegalArgumentException("Email must be sent...");
		} else if (!newStudent.getEmail().contains("@")) {
			throw new IllegalArgumentException("Invalid email id is used, fix it...");
		} else if (!existingStudentById.getEmail().equals(newStudent.getEmail())) {
			existingStudentById.setEmail(newStudent.getEmail());
		}

		if (Period.between(newStudent.getDob(), LocalDate.now()).isNegative()) {
			throw new IllegalStateException("Date of birth cannot be selected from future...");
		} else if (!existingStudentById.getDob().equals(newStudent.getDob())) {
			existingStudentById.setDob(newStudent.getDob());
		}

		existingStudentById.setAge(newStudent.getAge());
		existingStudentById.setErrMsg("No error...");
		return studentRepo.save(existingStudentById);
	}

	// partially update
	public SF03StudentBean updateStPartially(Long id, SF03StudentBean newStudent) {
		SF03StudentBean existingStudentById = studentRepo.findById(id)
				.orElseThrow(() -> new IllegalStateException(id + " id does not exist.."));

		if (newStudent.getName() != null) {
			existingStudentById.setName(newStudent.getName());
		}

		if (newStudent.getEmail() == null) {
			newStudent.setEmail("");
		}

		Optional<SF03StudentBean> existingStudentByEmail = studentRepo
				.findSF03StudentBeanByEmail(newStudent.getEmail());
		if (existingStudentByEmail.isPresent()) {
			throw new IllegalStateException("Email exists in DB, email must be unique...");
		} else if (!newStudent.getEmail().contains("@") && newStudent.getEmail() != "") {
			throw new IllegalStateException("Invalid email...");
		} else if (newStudent.getEmail() != "") {
			existingStudentById.setEmail(newStudent.getEmail());
		}

		if (newStudent.getDob() != null) {
			existingStudentById.setDob(newStudent.getDob());
		}

		existingStudentById.setAge(newStudent.getAge());
		existingStudentById.setErrMsg("No error...");

		return studentRepo.save(existingStudentById);
	}

	// add new student
	public SF03StudentBean addStudent(SF03StudentBean newStudent) throws ClassNotFoundException, SQLException {
		Optional<SF03StudentBean> existingStudentByEmail = studentRepo
				.findSF03StudentBeanByEmail(newStudent.getEmail());
		if (existingStudentByEmail.isPresent()) {
			throw new IllegalStateException("Email exists in DB, email must be unique...");
		}

		if (newStudent.getName() == null) {
			throw new IllegalStateException("Name must be entered for new students.");
		}

		Class.forName("oracle.jdbc.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/ORClcdb.localdomain", "dsteel",
				"12345");
		Statement st = con.createStatement();
		String sql = "select max(id) from students";
		ResultSet result = st.executeQuery(sql);
		Long maxId = 0L;
		while (result.next()) {
			maxId = result.getLong(1);
		}
		newStudent.setId(maxId + 1);

		return studentRepo.save(newStudent);
	}

}
