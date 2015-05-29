package march4.dao;

import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import march4.model.Quest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
// "@ComponentScan does not work."
@ContextConfiguration("classpath:/application-test.xml")
public class QuestDaoTest {
	private static final Logger log = LoggerFactory.getLogger(QuestDaoTest.class);
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private QuestDao questDao;
	
	@Test
	public void dataSource(){
		assertNotNull(dataSource);
	}

	@Test
	public void insert() {
		Quest quest = new Quest(1, 0, 0, 1, "tt", "2015-01-01 00:00:00");
		questDao.insert(quest);
		log.debug("Insert quest : {}", quest);
	}
	
	@Test
	public void select(){
		Quest quest = questDao.select(18);
//		List<Quest> quest = questDao.selectAll();
		log.debug("Select quest : {}", quest);
	}
	
	@Test
	public void getOrder() {
		System.out.println(questDao.getOrderOf(1));
	}
}
