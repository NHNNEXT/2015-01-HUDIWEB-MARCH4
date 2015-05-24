package march4.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import march4.model.Building;
import march4.service.BuildingService;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String building(ModelMap model) {
		log.debug("빌띵빌띵삘띵삘띵!!!!!");
		return "building";
	}
	
	/*
	 * @see BuildingControllerMockTest
	 * 
	 * addBuilding과 delBuilding 메소드에 대해서 리팩토링을 해봤습니다.
	 * 주석을 해제하고 전/후 코드에 대한 테스트코드를 돌려보면 결과는 동일합니다.
	 * 
	 * 1. json string으로 넘어오는 값을 객체로 받을 수 있고, 각 field의 이름에 맞추어 바인딩된 결과를 받을 수 있습니다.
	 * 2. controller의 exceptionHandler 사용방법입니다.
	 * 		- 참고: http://goo.gl/HALjY7
	 * 		- 요약하자면. globalExceptionHandler뿐 아니라 controller별 exceptionHandler를 만들 수 있고 HandlerExceptionResolver를 구현하여 사용할 수도 있습니다.
	 * 		- delBuilding은 controller별 예외핸들러를 사용해서 조금 더 명확하게 바꿔보았습니다.
	 * 3. BuildingControllerMockTest를 참고하여 MockTest도 만들어보세요 :)
	 * 		- MockTest는 SpringTest와 다르게 Spring에 종속적이지 않아서 로직검증에 효율적인것 같습니다..!! 
	 */
	
	/*
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody Building addBuilding(@RequestBody String body, ModelMap model) {
		log.debug("빌띵이 올라온다!!");
		Map<String,String> map = new HashMap<String,String>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(body, new TypeReference<HashMap<String,String>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("uid : {}", map.get("uid"));
		log.debug("name : {}", map.get("name"));
		
		log.debug("빌띵이 들어간다!!!!");
		
		Building building = new Building(Integer.parseInt(map.get("uid")), map.get("name"), map.get("shared"));
		buildingService.addBuilding(building);
		
		//pid를 받아온다.
		Building addBuilding = new Building(buildingService.getLastpId(), Integer.parseInt(map.get("uid")), map.get("name"), map.get("shared"));
		//받아온 pid와 나머지 정보 조합하여 building만듬.
		return addBuilding;
		//그리고 그거 리턴.
	}*/
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody Building addBuilding(@RequestBody Building building, ModelMap model) {
		buildingService.addBuilding(building);
		
		int pid = buildingService.getLastpId();
		building.setPid(pid);
		
		return building;
	}
	
	
	/*
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
	}*/
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public @ResponseBody String delBuilding(@RequestBody Building building, ModelMap model) {
		
		buildingService.delBuilding(building.getPid());
		
		return "true";
	}
	
	@ExceptionHandler(value=Exception.class)
	public @ResponseBody String etc() {
		
		return "false";
	}
	
	
	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public @ResponseBody List<Building> defaultBuilding(@RequestParam("uid") String uid) {
		List<Building> buildings = buildingService.getDefaultBuilding(Integer.parseInt(uid));
		log.debug(uid);
		log.debug("building {}", buildings);
		return buildings;
	}
}
