package march4.dao;

import march4.exception.EmailDuplicationExeption;
import march4.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void signup(User user) {
		String sql = "insert into user values(null, ?, ?, ?)";
		try {
			jdbcTemplate.update(sql, user.getEmail(), user.getName(), user.getPassword());
		} catch (DuplicateKeyException e) {
			//이미 존재하는 이메일일 경우.
			throw new EmailDuplicationExeption("이메일이 이미 존재합니다. 회원가입에 실패합니다.", e);
		}
	}

	public Boolean loginSuccess(User user) {
		String sql = "select count(uId) as num from user where email = ? and pw = ?";
		int result = jdbcTemplate.queryForObject(sql, Integer.class, user.getEmail(), user.getPassword());
		if(result == 1)
			return true;
		return false;
	}
	
	public Boolean existEmail(User user) {
		String sql = "select count(uId) as num from user where email = ?";
		int result = jdbcTemplate.queryForObject(sql, Integer.class, user.getEmail());
		if(result == 1)
			return true;
		return false;
	}
	
	public User getUserByEmail(String email) {
		try{
			String sql = "select * from user where email = ?";
			User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), email);
			return user;
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
}
