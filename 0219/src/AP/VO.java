package AP;

public class VO {
	
	private String writeDate;
	private String type;
	private String item;
	private int money;
	private String memo;
	
	public VO () {}
	
	public VO(String writeDate, String type, String item, int money, String memo) {

		this.writeDate = writeDate;
		this.type = type;
		this.item = item;
		this.money = money;
		this.memo = memo;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getItem() {
		return item;
	}
	
	public void setItem(String item) {
		this.item = item;
	}
	
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toString() {
		return "VO [writeDate=" + writeDate + ", money=" + money + ", type=" + type + ", item=" + item
				+ ", memo=" + memo + "]";
	}
	
	

}
