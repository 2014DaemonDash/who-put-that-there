package edu.umd.cs.daemondash.sqlite.model;

public class Diner {
	
	private Long id;
	private String name;
	
	public Diner() {
	}
	
	public Diner(String name) {
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
