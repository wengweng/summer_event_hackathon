package domain;

import android.graphics.drawable.Drawable;

public class Buddy {
	private Drawable picture;
	private String name;
	
	
	public Buddy(String name, Drawable picture){
		this.name = name;
		this.picture = picture;
	}
	
	public Drawable getPicture() {
		return picture;
	}
	public void setPicture(Drawable picture) {
		this.picture = picture;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
