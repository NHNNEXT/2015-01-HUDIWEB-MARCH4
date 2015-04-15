package march4.model;

public class User {
	private int uid;
	private String email;
	private String pw;
	
	public User(int uid, String email, String pw) {
		super();
		this.uid = uid;
		this.email = email;
		this.pw = pw;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	/*
	 * java equals를 사용한다면 hashCode는 반드시 구현해주셔야 합니다.
	 * "a.equals(b) 라면 a.hashCode() 와 b.hashCode() 의 값이 같다"는 매우 중요한 이유가 있습니다
	 * eclipse의 오른쪽 메뉴-source-"generate hashCode() and equals()"를 사용하면 쉽게 구현할 수 있습니
	 * 
	 * 참고자료
	 * 1. https://breadmj.wordpress.com/2013/07/23/java-%EC%9D%98-hashcode-%EC%97%90-%EB%8C%80%ED%95%9C-%EA%B3%A0%EC%B0%B0/
	 *
	@Override
	public boolean equals(Object obj) {
		return this.uid == ((User)obj).uid;
	}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + uid;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (uid != other.uid)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [userId=" + uid + ", name=" + email + ", pw=" + pw + "]";
	}
}
