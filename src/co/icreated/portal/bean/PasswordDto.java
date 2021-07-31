package co.icreated.portal.bean;

public class PasswordDto {
	
	String password;
	String newPassword;
	String confirmPassword;
	
	public PasswordDto() {}

	public PasswordDto(String password, String newPassword, String confirmPassword) {
		super();
		this.password = password;
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}	

}
