package march4.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class User {
	
	@NotNull
	private int uId;
	
	@NotNull
	@Size(min = 2, max = 24)
	private String name;
	
	@Email
	@NotNull
	@NotEmpty
	private String email;

	@Size(min = 5, max = 12)
	@NotNull
	private String password;

	public User(){}
	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getuId() {
		return uId;
	}

	public void setuId(int uId) {
		this.uId = uId;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "User [uId=" + uId + ", name=" + name + ", email=" + email
				+ ", password=" + password + "]";
	}
}
