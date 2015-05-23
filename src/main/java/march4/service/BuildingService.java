package march4.service;

import java.util.List;

import march4.dao.BuildingDao;
import march4.model.Building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingService {

	@Autowired
	BuildingDao buildingDao;

	public void addBuilding(Building building) {
		buildingDao.add(building);
	}

	public List<Building> getDefaultBuilding(int uid) {
		return buildingDao.getDefaultBuildingList(uid);
	}

	public void delBuilding(int pid) {
		buildingDao.del(pid);
	}
	
	public void updateBuilding(Building building){
		buildingDao.updatePositoin(building);
	}
	
	public Integer getLastpId(){
		return buildingDao.getLastpid();
	}
}
