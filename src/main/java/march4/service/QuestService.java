package march4.service;

import java.util.List;

import march4.controller.QuestController;
import march4.dao.QuestDao;
import march4.model.Quest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestService {
	@Autowired private QuestDao questDao;
	private static final Logger log = LoggerFactory.getLogger(QuestController.class);
	public void insert(Quest quest) {
		questDao.insert(quest);
	}
	
	public List<Quest> selectBypIdOrderedAsc(String pId) {
		return questDao.selectBypIdOrderedAsc(pId);
	}
	public List<Quest> getQuestListOfSameProjectOrderedAsc(int qId) {
		// 숫자를 가지는 필드에 대해서 타입들이 중구난방(int, string, Integer..). ???.
		return questDao.selectBypIdOrderedAsc(Integer.toString(questDao.select(qId).getpId()));
	}
	
	//TODO 트랜잭션. storedProcedure가 낫나.
	public Quest remove(int qId) {
		Quest removedQuest = questDao.select(qId);
		int prevOrder = questDao.getOrderOf(qId);
		questDao.changeOrder(qId, questDao.getMaxOrderOfProject(removedQuest.getpId()));
		log.debug("qId:{} || quest : {}, qid : {}, pid : {}, order : {}", qId, removedQuest, removedQuest.getqId(), removedQuest.getpId(), removedQuest.getOrder());
		questDao.decreaseOrderAfter(removedQuest.getpId(), prevOrder);
		questDao.delete(qId);
		return removedQuest;
	}
	public void insertBefore(Quest newQuest, int targetQuestId) {
		//TODO 두 퀘스트의 pId 가 다를경우에 대해서도 에러처리를 해야 함.
		Quest targetQuest = questDao.select(targetQuestId);
		newQuest.setOrder(targetQuest.getOrder());
		log.debug("newQuest : {}, order : {}", newQuest, newQuest.getOrder());
		questDao.increaseOrderAfter(targetQuestId);
		questDao.insert(newQuest);
	}
	public void moveToBefore(int movingQuestId, int targetQuestId) {
		Quest removedQuest = remove(movingQuestId);
		log.debug("quest : {}, qid : {}", removedQuest, targetQuestId);
		insertBefore(removedQuest, targetQuestId);
	}
}
