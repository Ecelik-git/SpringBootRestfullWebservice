package restfullwebservice03;

public enum ApplicationUserPermissions {

	STUDENT_READ("student:read"),
	ADMIN_READ("admin:read"),
	ADMIN_WRITE("admin:write");
	
	
	private final String permissions;

	public String getPermissions() {
		return permissions;
	}

	ApplicationUserPermissions(String permissions) {
		this.permissions = permissions;
	}
	
}
