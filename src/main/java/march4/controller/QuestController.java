package march4.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import march4.model.Quest;
import march4.service.QuestService;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/projects/{pId}/quests", headers = {"content-type=application/json"})
public class QuestController {
	private static final Logger log = LoggerFactory
			.getLogger(QuestController.class);
	
	@Autowired
	QuestService q;
	
	@RequestMapping(value = {""}, method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Quest> page(@PathVariable String pId) {
		log.debug("roadmap GET");
		return q.selectBypID(pId);
	}
	
	
	@RequestMapping(value = {""}, method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public String request(@RequestBody String body, @PathVariable String pId) {
		log.debug("roadmap POST");
		System.out.println(body);
		
		Map<String,String> map = new HashMap<String,String>();
		ObjectMapper mapper = new ObjectMapper();
		Quest newQuest = null;
		try {
			map = mapper.readValue(body, new TypeReference<HashMap<String,String>>(){});
			
			log.debug(map.toString());
			
			newQuest = new Quest(
					pId,
					map.get("posX"),
					map.get("posY"),
					map.get("order"),
					map.get("content"),
					map.get("due")
			);
			q.insert(newQuest);
			
			log.debug("reach at the end of try");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("reach at the end of method");
		return "{\"result\":true}";
	}
}
