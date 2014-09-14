package edu.umd.cs.daemondash.sqlite.model;

public class Food {
	
	private Long id;
	private String name;
	
	public Food() {
	}
	
	public Food(String name) {
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

}
