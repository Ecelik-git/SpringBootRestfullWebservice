package restfullwebservice02;

import java.time.LocalDate;
import java.time.Period;

public class SF02StudentBean {
	
	private Long id;
	private String name;
	private String email;
	private LocalDate dob;
	private int age;
	private String errMsg="Error Not Found...";
	
	public SF02StudentBean(Long id, String name, String email, LocalDate dob) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.dob = dob;
		this.errMsg= errMsg;
	}
	
	public SF02StudentBean () {
		this.errMsg = "There is no student with this id";
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public int getAge() {
		if(this.dob==null) {
			return 0;
		}
		return Period.between(this.dob, LocalDate.now()).getYears();
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", email=" + email + ", dob=" + dob + ", age=" + age;
	}
	
	
	
	

}
