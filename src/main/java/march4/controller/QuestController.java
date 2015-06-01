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
	QuestService qs;
	
	@RequestMapping(value = {""}, method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Quest> getQuests(@PathVariable("pId") String pId) {
		log.debug("roadmap GET", pId);
		return qs.selectBypIdOrderedAsc(pId);
	}
	
	@RequestMapping(value = {""}, method = RequestMethod.POST, consumes="application/json", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Quest> newQuest(@RequestBody Quest quest, @PathVariable("pId") String pId) {
		log.debug("roadmap POST");
		quest.setpId(pId);
		//TODO order값이 있다면 insertBefore해줘야겠지만...... 흠. 그럴리없는걸로.
		qs.insert(quest);
		return qs.selectBypIdOrderedAsc(pId);
	}
	
	@RequestMapping(value = "/{qId}/movetobefore", method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Quest> updateOrder(@PathVariable("qId") int movingQuestId, @RequestParam("qId") int targetQuestId, @PathVariable("pId") String pId) {
		log.debug("roadmap UPDATE", pId);
		qs.moveToBefore(movingQuestId, targetQuestId);
		log.debug("movingQuestId : {}, targetQuestId : {}", movingQuestId, targetQuestId);
		
		return qs.selectBypIdOrderedAsc(pId);
	}

	@RequestMapping(value = {"/{qId}"}, method = RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Quest> deleteQuest(@PathVariable("qId") int qId, @PathVariable("pId") String pId) {
		log.debug("roadmap DELETE", pId);
		qs.remove(qId);
		return qs.selectBypIdOrderedAsc(pId);
	}
}
