package march4.model;

public class Quest {
	// primitive type(int)은 db의 NULL 값을 만나면 TypeMismatchException 발생.
	private Integer qId;
	private Integer pId;
	private Integer posX;
	private Integer posY;
	private Integer order;
	private String contents;
	private String due;

	// RowMapper 에서 기본생성자 필요.
	public Quest(){}
	
	public Quest(Integer qId, Integer pId, Integer posX, Integer posY, Integer order,
			String contents, String due) {
		this.qId = qId;
		this.pId = pId;
		this.posX = posX;
		this.posY = posY;
		this.order = order;
		this.contents = contents;
		this.due = due;
	}
	public Quest(Integer pId, Integer posX, Integer posY, Integer order,
			String contents, String due) {
		this.pId = pId;
		this.posX = posX;
		this.posY = posY;
		this.order = order;
		this.contents = contents;
		this.due = due;
	}
	
	public Quest(String pId, String posX, String posY, String order,
			String contents, String due) {
		this.pId = Integer.parseInt(pId);
		this.posX = Integer.parseInt(posX);
		this.posY = Integer.parseInt(posY);
		this.order = Integer.parseInt(order);
		this.contents = contents;
		this.due = due;
	}

	public Integer getqId() {
		return qId;
	}

	public void setqId(Integer qId) {
		this.qId = qId;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public void setpId(String pId) {
		this.pId = Integer.parseInt(pId);
	}

	public Integer getPosX() {
		return posX;
	}

	public void setPosX(Integer posX) {
		this.posX = posX;
	}

	public Integer getPosY() {
		return posY;
	}

	public void setPosY(Integer posY) {
		this.posY = posY;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getDue() {
		return due;
	}

	public void setDue(String due) {
		this.due = due;
	}
	
	@Override
	public String toString() {
		return "Quest [contents="+contents+",order="+order+",qId="+qId+",pId="+pId+"]";
	}
}
