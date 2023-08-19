package model;

public class PurchaseVO extends UserVO{
	private String pid;
	private String recName;
	private String recPhone;
	private String recAddress1;
	private String recAddress2;
	private int sum;
	private int status;
	private String purDate;
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getRecName() {
		return recName;
	}
	public void setRecName(String recName) {
		this.recName = recName;
	}
	public String getRecPhone() {
		return recPhone;
	}
	public void setRecPhone(String recPhone) {
		this.recPhone = recPhone;
	}
	public String getRecAddress1() {
		return recAddress1;
	}
	public void setRecAddress1(String recAddress1) {
		this.recAddress1 = recAddress1;
	}
	public String getRecAddress2() {
		return recAddress2;
	}
	public void setRecAddress2(String recAddress2) {
		this.recAddress2 = recAddress2;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPurDate() {
		return purDate;
	}
	public void setPurDate(String purDate) {
		this.purDate = purDate;
	}
}
