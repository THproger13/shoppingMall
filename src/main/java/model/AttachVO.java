package model;

public class AttachVO {
	private int aid;
	private String gid;
	private String image;
	
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
	public String toString() {
		return "AttachVO [aid=" + aid + ", gid=" + gid + ", image=" + image + "]";
	}
}
