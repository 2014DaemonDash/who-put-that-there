package edu.umd.cs.daemondash.sqlite.model;

public class ContainerFood {
	
	private Long tstamp;
	private Food food;
	
	public ContainerFood() {
	}
	
	public ContainerFood(Long tstamp, Food food) {
		this.tstamp = tstamp;
		this.food = food;
	}
	
	public void setTstamp(Long tstamp) {
		this.tstamp = tstamp;
	}
	
	public Long getTstamp() {
		return tstamp;
	}
	
	public void setFood(Food food) {
		this.food = food;
	}
	
	public Food getFood() {
		return food;
	}
}
