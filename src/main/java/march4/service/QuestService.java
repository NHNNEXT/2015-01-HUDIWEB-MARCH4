package march4.service;

import java.util.List;

import march4.dao.QuestDao;
import march4.model.Quest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestService {
	@Autowired private QuestDao questDao;
	
	public void insert(Quest quest) {
		questDao.insert(quest);
	}
	
	public List<Quest> selectBypID(String pId) {
		return questDao.selectBypID(pId);
	}
	
	/*
	 * 한 것과 하지 못한 것의 차이는 생각보다 큰 것 같아요.
	 * 시간이 없더라도 단 한 분이라도 시도해보시고 결과를 팀원과 공유하시는게 좋을 것 같아요 :)
	 * 
	 * 참고
	 * - https://goo.gl/7PJAud / 설정
	 * - https://goo.gl/iCdUyP / transaction service
	 * - https://goo.gl/C8laSd
	 * 
	 * - 블로그
	 * 		- http://blog.outsider.ne.kr/869
	 * 		- http://blog.outsider.ne.kr/870
	 * 
	 */
	public String swapOrder(int qId1, int qId2) {
		int order1 = questDao.getOrderOf(qId1);
		int order2 = questDao.getOrderOf(qId2);
		int temp = questDao.getMaxOrder();
		System.out.println(order1+" , "+order2);
//		
//		//TODO transaction. applicationContext.xml 부터 건드릴 게 많아서 일단 기술부채로..ㅎㅎ
		questDao.changeOrder(qId1, temp);
		questDao.changeOrder(qId2, order1);
		questDao.changeOrder(qId1, order2);
		return order2+" , "+order1;
	}
}
