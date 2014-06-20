package domain;

public class Buddy {
	private String picture;
	private String name;
	
	
	public Buddy(String name, String picture){
		this.name = name;
		this.picture = picture;
	}
	
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
