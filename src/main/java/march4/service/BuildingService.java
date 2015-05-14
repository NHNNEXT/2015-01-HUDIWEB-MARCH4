package march4.service;

import java.util.List;

import march4.dao.BuildingDao;
import march4.model.Building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingService {

	// 특별한 이유가 없으면 접근자는 private이 좋습니다.
	@Autowired
	private BuildingDao buildingDao;

	public void addBuilding(Building building) {
		buildingDao.add(building);
	}

	public List<Building> getDefaultBuilding(int uid) {
		return buildingDao.getDefaultBuildingList(uid);
	}

	public void delBuilding(int pid) {
		buildingDao.del(pid);
	}
	
	public Integer getLastpId(){
		return buildingDao.getLastpid();
	}
}
