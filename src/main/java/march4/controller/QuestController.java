package march4.controller;

import java.util.List;

import march4.model.Quest;
import march4.service.QuestService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/projects/{pId}/quests")
@ResponseBody
public class QuestController {
	private static final Logger log = LoggerFactory
			.getLogger(QuestController.class);
	
	@Autowired
	QuestService q;
	
	@RequestMapping(value = {""}, method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Quest> get(@PathVariable("pId") String pId) {
		log.debug("roadmap GET", pId);
		return q.selectBypIdOrderedAsc(pId);
	}
	
//	@RequestMapping(value = {"/{qId}"}, method = RequestMethod.GET, produces="application/json")
//	@ResponseBody
//	public List<Quest> getByqId(@PathVariable("pId") String pId, @PathVariable("qId") String qId) {
//		log.debug("roadmap getByqId", pId, qId);
//		// TODO
//		return q.selectByqID(pId, qId);
//	}
	
	@RequestMapping(value = {""}, method = RequestMethod.POST, consumes="application/json", produces=MediaType.APPLICATION_JSON_VALUE)
	public String request(@RequestBody Quest quest, @PathVariable int pId) {
		log.debug("roadmap POST");
		quest.setpId(pId);
		q.insert(quest);
		return "{\"result\":true}";
	}
	
	@RequestMapping(value = "/{qId}/movetobefore", method = RequestMethod.PUT)
	public List<Quest> test(@PathVariable("qId") int movingQuestId, @RequestParam("qId") int targetQuestId, @PathVariable("qId") int pId) {
		q.moveToBefore(movingQuestId, targetQuestId);
		log.debug("movingQuestId : {}, targetQuestId : {}", movingQuestId, targetQuestId);
		
		return q.getQuestListOrderedAsc(Integer.toString(pId));	// 테스트용.
	}
}
