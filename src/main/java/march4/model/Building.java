package march4.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Building {

	private Integer pid;
	private Integer host_uid;
	private Integer posx;
	private Integer posy;
	
	@NotNull(message="이름을 반드시 입력하여야 합니다.")
	@Size(min = 2, max = 24, message="2 ~ 24 사이의 이름을 입력해주세요!")
	private String name;
	
	@NotNull(message="공유항목을 반드시 입력하여야 합니다.")
	@Size(min = 2, max = 24 , message="2 ~ 24 사이의 공유항을 입력해주세요!")
	private String shared;

	public Building() {

	}

	public Building(Integer uid, String name, String shared) {
		super();
		this.host_uid = uid;
		this.name = name;
		this.shared = shared;
	}
	
	public Building(Integer pid, Integer host_uid, Integer posx, Integer posy,
			String name, String shared) {
		super();
		this.pid = pid;
		this.host_uid = host_uid;
		this.posx = posx;
		this.posy = posy;
		this.name = name;
		this.shared = shared;
	}

	
	
	@Override
	public String toString() {
		return "Building [pid=" + pid + ", uid=" + host_uid + ", posx=" + posx
				+ ", posy=" + posy + ", name=" + name + ", shared=" + shared
				+ "]";
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getHost_uid() {
		return host_uid;
	}

	public void setHost_uid(Integer host_uid) {
		this.host_uid = host_uid;
	}

	public Integer getPosx() {
		return posx;
	}

	public void setPosx(Integer posx) {
		this.posx = posx;
	}

	public Integer getPosy() {
		return posy;
	}

	public void setPosy(Integer posy) {
		this.posy = posy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShared() {
		return shared;
	}

	public void setShared(String shared) {
		this.shared = shared;
	}
}
