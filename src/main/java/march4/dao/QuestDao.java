package march4.dao;

import java.lang.reflect.Field;
import java.util.List;

import javax.annotation.PostConstruct;

import march4.controller.QuestController;
import march4.model.Quest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger log = LoggerFactory.getLogger(QuestController.class);

	@PostConstruct
	public void initialize() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		DatabasePopulatorUtils.execute(populator, jdbcTemplate.getDataSource());
	}

	public int insert(Quest quest) {
		log.debug("quest:{}", quest);
		Integer qId = quest.getqId();
		if(qId != null) {
			String sql = "insert into quest values (?, ?, ?, ?, ?, ?, ?)";
			jdbcTemplate.update(sql, quest.getqId(), quest.getpId(), 
					quest.getPosX(), quest.getPosY(), quest.getOrder(), quest.getContents(), quest.getDue());
		} else {
			String sql = "insert into quest values (NULL, ?, ?, ?, ?, ?, ?)";
			jdbcTemplate.update(sql, quest.getpId(), 
					quest.getPosX(), quest.getPosY(), quest.getOrder(), quest.getContents(), quest.getDue());
		}
		return 0; // lastInsertId(); 가져와서 Quest.setId(); 하고 싶음.
	}
	
	public void delete(int qId) {
		String sql = "DELETE FROM quest WHERE qId = ?";
		jdbcTemplate.update(sql, qId);
		// 남은 퀘스트 개수 리턴??
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
	
	
	public List<Quest> selectBypIdOrderedAsc(String pId) {
		String sql = "select * from quest where pId = "+pId+" order by `order`";
		List<Quest> quests = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Quest>(Quest.class));
		return quests;
	}
	
	public int getOrderOf(int qId) {
		log.debug("qid : {}", qId);
		int order = jdbcTemplate.queryForObject("SELECT `order` FROM quest WHERE qId = ?", Integer.class, qId);
		log.debug("order : {}", order);
		return order;
	}
	
	public void changeOrder(int qId, int order) {
		String sql = "update quest set `order` = ? where qId = ?";
		jdbcTemplate.update(sql, order, qId);
	}
	public void increaseOrderEqualAndAfter(int qId) {
		Quest quest = jdbcTemplate.queryForObject("SELECT * FROM quest WHERE qId = ?",
					new BeanPropertyRowMapper<Quest>(Quest.class), qId);
		log.debug("quest:{}, pid:{}, order:{}", quest, quest.getpId(), quest.getOrder());
		
		String sql = "UPDATE quest SET `order`=`order`+1 WHERE pId=? AND `order`>=? ORDER BY `order` DESC";
		jdbcTemplate.update(sql, quest.getpId(), quest.getOrder());
	}
	public void decreaseOrderAfter(int pId, int order) throws EmptyResultDataAccessException {
		String sql = "UPDATE quest SET `order`=`order`-1 WHERE pId=? AND `order`>? ORDER BY `order` ASC";
		jdbcTemplate.update(sql, pId, order);
	}
	
	// 매우 찜찜한 방식 으으으으으음
	public void updateMultipleColumn(int qId, Quest quest) {
		String sql = "UPDATE quest SET ";
		//TODO quest 모든값이 null일리는 없다고 가정.
		for(Field field : quest.getClass().getDeclaredFields()) {
			Object value;
			field.setAccessible(true);
			try {
				value = field.get(quest);
				log.debug("field : {}, value : {}", field.getName(), value);
				if(value != null) {
					if(!sql.endsWith(" ")) sql+= ",";
					sql += field.getName()+"='"+value+"'";
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				// 에러 노 처리 ㅜㅠ
				e.printStackTrace();
			}
			field.setAccessible(false);
		}
		sql += "WHERE qId="+qId;
		log.debug("sql : {}", sql);
		
		jdbcTemplate.update(sql);
	}
		
	
	public int getMaxOrderOfProject(int pId) {
		return jdbcTemplate.queryForObject("SELECT max(`order`)+1 FROM quest WHERE pId="+pId, Integer.class);
	}
}
