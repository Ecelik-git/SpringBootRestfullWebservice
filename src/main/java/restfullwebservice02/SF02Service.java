package restfullwebservice02;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service  //@Component works as well, but @service is preferred, more readable, more specific
public class SF02Service {
	List<SF02StudentBean> studentList = List.of(
			new SF02StudentBean(101L, "Ali Can", "ac@gmail.com", LocalDate.of(2002, 5, 12), 19),
			new SF02StudentBean(102L, "Veli Han", "vh@gmail.com", LocalDate.of(2003, 6, 21), 18),
			new SF02StudentBean(103L, "Mary Star", "ms@gmail.com", LocalDate.of(2011, 11, 23), 10));

	public List<SF02StudentBean> listStudents(){
		return studentList;
	}
	
	public String selectStById(Long id) {
		if(studentList.stream().filter(t->t.getId()==id).collect(Collectors.toList()).size()==0) {
			return "It is not a valid ID..";
		}else {
			return studentList.stream().filter(t->t.getId()==id).collect(Collectors.toList()).get(0).toString();
		}
	}
	
	
	
	
	
	
	
	
	
	
}





