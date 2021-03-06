package march4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import march4.model.Building;
import march4.service.BuildingService;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/building", headers = {"Accept=application/json"})
public class BuildingController {
	
	@Autowired
	BuildingService buildingService;
	
	private static final Logger log = LoggerFactory
			.getLogger(BuildingController.class);

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/add", method = RequestMethod.POST,  produces = "text/plain;charset=UTF-8")
	public @ResponseBody String addBuilding(@RequestBody @Valid Building building, BindingResult result, ModelMap model) throws JsonGenerationException, JsonMappingException, IOException {
		log.debug("Add building!");

		List<ObjectError> errors = result.getAllErrors();
		if (errors.isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			buildingService.addBuilding(building);		
			building.setPid(buildingService.getLastpId());
			String jsonString = mapper.writeValueAsString(building);
			return jsonString;
		}
		
		List<String> messages = new ArrayList<String>();
		for (ObjectError error : errors) {
			log.debug("error : {}", error.getDefaultMessage());
			messages.add(error.getDefaultMessage());
		}
		return messages.get(0);
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public @ResponseBody String delBuilding(@RequestBody String body, ModelMap model) {
		Map<String,String> map = new HashMap<String,String>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(body, new TypeReference<HashMap<String,String>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("빌띵을 지워버리게ㅅ쌋!!!!");
		log.debug("pid : {}", map.get("pid"));
		
		try {
			buildingService.delBuilding(Integer.parseInt(map.get("pid")));
			return "true";
		} catch (Exception e) {
			log.debug("빌딩을 삭제하지 못했어!! 난 무능한 서버야!!");
		}
		return "false";
	}
	
	
	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public @ResponseBody List<Building> defaultBuilding(@RequestParam("host_uid") String uid) {
		log.debug(uid);
		List<Building> buildings = buildingService.getDefaultBuilding(Integer.parseInt(uid));
		
		log.debug("building {}", buildings.toString());
		return buildings;
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/updatePos", method = RequestMethod.POST)
	public @ResponseBody String updatePos(@RequestBody Building building) {
		log.debug("update building position!");
		buildingService.updateBuilding(building);
		return "true";
	}
}
