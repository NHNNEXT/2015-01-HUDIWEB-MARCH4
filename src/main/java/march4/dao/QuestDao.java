package march4.dao;

import java.util.List;

import javax.annotation.PostConstruct;

import march4.model.Dummy;
import march4.model.Quest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

@Repository
public class QuestDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void initialize() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		DatabasePopulatorUtils.execute(populator, jdbcTemplate.getDataSource());
	}

	/*
	 * last_insert_id에 대해서
	 * - oracle ref: https://goo.gl/FOUAOj
	 * - 동시성 문제: http://goo.gl/1uqQHg
	 * - http://goo.gl/yo5R6Q
	 * 
	 * "insert into quest values (NULL, LAST_INSERT_ID()+1, ?, ?, ?, ?, ?)";
	 * 하면 될것 같은데.. 동시성 문제가 있다고 하네요.
	 * 특히 오라클 ref에는 last_insert_id 구현내용상
	 *  1. auto-increment 필드에 대해서 테이블에 하나씩만 동작하고
	 *  2. batch 작업시 여러 행 중 최초 행의 id만 기억한다고 합니다..
	 *  	- http://goo.gl/WhPkgj 
	 */
	public int insert(Quest quest) {
		System.out.println("hi"); // 되도록 로거를 ㅠ
		String sql = "insert into quest values (NULL, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, quest.getpId(), 
				quest.getPosX(), quest.getPosY(), quest.getOrder(), quest.getContents(), quest.getDue());
		return 0; // lastInsertId(); 가져와서 Quest.setId(); 하고 싶음.
	}

	public Quest select(int qId) {
		String sql = "select * from quest where qId = ?";
		try {
			return jdbcTemplate.queryForObject(sql,
					// 기본 생성자가 있어야 한다. Constructor{super();}
					new BeanPropertyRowMapper<Quest>(Quest.class), qId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Quest> selectAll() {
		String sql = "select * from quest";
		List<Quest> quests = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Quest>(Quest.class));
		return quests;
	}
	
	
	public List<Quest> selectBypID(String pId) {
		String sql = "select * from quest where pId = "+pId;
		List<Quest> quests = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Quest>(Quest.class));
		return quests;
	}
	
	
}
