package march4.dao;

import java.util.List;

import march4.model.Building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BuildingDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void add(Building building) {
		String sql = "insert into project values(null, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, building.getUid(), building.getName(),
				building.getShared(), building.getPosx(), building.getPosy());
	}

	public void del(int pid) {
		String sql = "delete from project where pid = ?";
		jdbcTemplate.update(sql, pid);
	}

	public List<Building> getDefaultBuildingList(int uid) {
		String sql = "select * from project where host_uId  = ?";
		Object[] args = new Object[] { uid };

		List<Building> building = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<Building>(Building.class));
		return building;
	}

	public Integer getLastpid() {
		String sql = "select last_insert_id() as pid";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	public void updatePositoin(Building building) {
		String sql = "update project set posx = ?, posy = ? where pid = ?";
		jdbcTemplate.update(sql, building.getPosx(), building.getPosy(),
				building.getPid());
	}
}
